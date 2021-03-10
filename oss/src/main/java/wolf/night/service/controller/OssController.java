package wolf.night.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wolf.night.service.entity.vo.R;
import wolf.night.service.service.OssService;

@RestController
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping("/toss/fileoss")
    public R uploadOssFile(MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        return R.sub("上传成功！", url);
    }

}
