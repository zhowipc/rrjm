package wolf.night.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("wolf.night.service.mapper")
@ComponentScan("wolf.night")
public class WolfApplication {
    public static void main(String[] args) {
        SpringApplication.run(WolfApplication.class, args);
    }
}
