package wolf.night.service.config.security;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import wolf.night.service.config.security.component.*;
import wolf.night.service.entity.Admin;
import wolf.night.service.entity.SysMenu;
import wolf.night.service.entity.SysRole;
import wolf.night.service.entity.SysUser;
import wolf.night.service.service.SysMenuService;
import wolf.night.service.service.SysUserService;

import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SysUserService userService;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private CustomerFilter customerFilter;
    @Autowired
    private CustomUrlDecisionManager customUrlDecisionManager;

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            SysUser user = userService.getUserInfoByUsername(username);
            if (null != user) {
                List<SysRole> roles = userService.getRolesByUserId(user.getId());

                user.setRoles(roles);

                Admin admin = new Admin();
                BeanUtils.copyProperties(user, admin);
                return admin;
            }
            throw new UsernameNotFoundException("???????????????????????????");
        };
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/login",
                "/logout",
                "/ws/**",
                "/css/**",
                "/js/**",
                "/index.html",
                "favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ??????jwt????????????csrf
        http.csrf().disable()
                // ??????token????????????session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // ??????????????????
                .antMatchers("/login", "/logout")
                .permitAll()
                .anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {

                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(customUrlDecisionManager);
                        object.setSecurityMetadataSource(customerFilter);
                        return object;
                    }
                })
                .and()
                .headers()
                .cacheControl();

        //??????jwt????????????????????????
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        //????????????????????????????????????????????????
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }
}
