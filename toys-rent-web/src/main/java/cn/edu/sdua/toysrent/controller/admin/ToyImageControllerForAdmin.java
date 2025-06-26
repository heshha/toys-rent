package cn.edu.sdua.toysrent.controller.admin;

import cn.edu.sdua.toysrent.common.R;
import cn.edu.sdua.toysrent.service.ToyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/image")
@Tag(name = "图片接口(管理员)", description = "图片接口(管理员)")
public class ToyImageControllerForAdmin {

    private final ToyService  toyService;

    // 获取玩具图片
//    @GetMapping("/{toyId}")
//    @Operation(summary = "获取玩具图片", description = "获取玩具图片")
//    public ResponseEntity<byte[]> getToyImage(@PathVariable("toyId") long toyId) throws IOException {
//        ResponseEntity<byte[]> image = toyService.getToyImage(toyId);
//        return image;
//    }

    // 上传玩具图片
    @PostMapping("/{toyId}")
    @Operation(summary = "上传玩具图片", description = "上传玩具图片")
    public R uploadToyImage(@PathVariable(value = "toyId") long  toyId,
                            @RequestParam(value = "image") MultipartFile image) throws IOException {
        System.out.println("toyId: " + toyId);
        System.out.println("image: " + image);

        toyService.uploadToyImage(toyId, image);

        return R.ok();
    }

    // 删除玩具图片
    @DeleteMapping("/{toyId}")
    @Operation(summary = "删除玩具图片", description = "删除玩具图片")
    public R deleteToyImage(@PathVariable(value = "toyId") long  toyId) throws IOException {
        toyService.deleteToyImage(toyId);
        return R.ok();
    }
}
