package cn.edu.sdua.toysrent.controller.admin;

import cn.edu.sdua.toysrent.common.R;
import cn.edu.sdua.toysrent.entity.Toy;
import cn.edu.sdua.toysrent.vo.req.ToyAddVo;
import cn.edu.sdua.toysrent.vo.req.ToyUpdateVo;
import cn.edu.sdua.toysrent.vo.resp.ToyRespVo;
import cn.edu.sdua.toysrent.exception.BusinessException;
import cn.edu.sdua.toysrent.exception.ExceptionCodeEnum;
import cn.edu.sdua.toysrent.service.ToyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Tag(name = "玩具接口(管理员)", description = "玩具接口(管理员)")
public class ToyControllerForAdmin {

    private final ToyService  toyService;

    // 添加玩具
    @PostMapping("/toy")
    @Operation(summary = "添加玩具", description = "添加玩具")
    public R addToy(@RequestBody @Valid ToyAddVo vo){
        log.info(String.valueOf(vo));
        Toy toy = new Toy();
        BeanUtils.copyProperties(vo, toy);
        toyService.addToy(toy);
        return R.ok();
    }

    // 获取所有玩具
    @GetMapping("/toys")
    @Operation(summary = "获取所有玩具", description = "获取所有玩具")
    public R getAllToys() {
        List<Toy> toys = toyService.getAllToys();
        List<ToyRespVo> vos = toys.stream().map(toy -> {
            ToyRespVo vo = new ToyRespVo();
            BeanUtils.copyProperties(toy, vo);
            return vo;
        }).collect(Collectors.toList());
        return R.ok(vos);
    }

    // 分页获取玩具
    @GetMapping("/toys/page")
    @Operation(summary = "分页获取玩具", description = "分页获取玩具")
    public R getToysByPage(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Toy> toyPage = toyService.getToysByPage(pageable);
        Page<ToyRespVo> voPage = toyPage.map(toy -> {
            ToyRespVo vo = new ToyRespVo();
            BeanUtils.copyProperties(toy, vo);
            return vo;
        });
        return R.ok(voPage);
    }

    // 根据id获取玩具
    @GetMapping("/toy/{id}")
    @Operation(summary = "根据id获取玩具", description = "根据id获取玩具")
    public R getToyById(@PathVariable("id") long id) {
        Toy toy = toyService.getToyById(id);
        ToyRespVo vo = new ToyRespVo();
        BeanUtils.copyProperties(toy, vo);
        return R.ok(vo);
    }

    // 根据name获取玩具
    @GetMapping("/toy/name/{toyName}")
    @Operation(summary = "根据名称获取玩具", description = "根据名称获取玩具")
    public R getToyByName(@PathVariable("toyName") String toyName) {
        Toy toy = toyService.getToyByName(toyName);
        ToyRespVo vo = new ToyRespVo();
        BeanUtils.copyProperties(toy, vo);
        return R.ok(vo);
    }

    // 根据玩具名称模糊查询玩具
    @GetMapping("/toy/name/like/{toyName}")
    @Operation(summary = "根据名称模糊分页获取玩具", description = "根据名称模糊分页获取玩具")
    public R getToyByNameLike(
            @PathVariable("toyName") String toyName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Toy> toys = toyService.getToyByNameLike(toyName, pageable);
        if (toys.isEmpty()) {
            throw new BusinessException(ExceptionCodeEnum.DATA_NOT_FOUND);
        }
        Page<ToyRespVo> vos = toys.map(toy -> {
            ToyRespVo vo = new ToyRespVo();
            BeanUtils.copyProperties(toy, vo);
            return vo;
        });
        return R.ok(vos);
    }

    // 根据categoryId获取玩具
    @GetMapping("/toy/category/{categoryId}")
    @Operation(summary = "根据分类分页获取玩具", description = "根据分类分页获取玩具")
    public R getToyByCategoryId(@PathVariable("categoryId") long categoryId,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Toy> toys = toyService.getToyByCategoryId(categoryId,  pageable);
        if (toys.isEmpty()) {
            throw new BusinessException(ExceptionCodeEnum.DATA_NOT_FOUND);
        }
        Page<ToyRespVo> vos = toys.map(toy -> {
            ToyRespVo vo = new ToyRespVo();
            BeanUtils.copyProperties(toy, vo);
            return vo;
        });
        return R.ok(vos);
    }

    // 根据日租价获取玩具
    @GetMapping("/toy/dailyRate/{dailyRate}")
    @Operation(summary = "根据日租价分页获取玩具", description = "根据日租价分页获取玩具")
    public R getToyByDailyRate(@PathVariable("dailyRate") double dailyRate,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Toy> toys = toyService.getToyByDailyRate(dailyRate, pageable);
        if (toys.isEmpty()) {
            throw new BusinessException(ExceptionCodeEnum.DATA_NOT_FOUND);
        }
        Page<Object> vos = toys.map(toy -> {
            ToyRespVo vo = new ToyRespVo();
            BeanUtils.copyProperties(toy, vo);
            return vo;
        });
        return R.ok(vos);
    }

    // 根据库存获取玩具
    @GetMapping("/toy/stock/{stock}")
    @Operation(summary = "根据库存分页获取玩具", description = "根据库存分页获取玩具")
    public R getToyByStock(@PathVariable("stock") long stock,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Toy> toys = toyService.getToyByStock(stock,  pageable);
        if (toys.isEmpty()) {
            throw new BusinessException(ExceptionCodeEnum.DATA_NOT_FOUND);
        }
        Page<Object> vos = toys.map(toy -> {
            ToyRespVo vo = new ToyRespVo();
            BeanUtils.copyProperties(toy, vo);
            return vo;
        });
        return R.ok(vos);
    }

    // 根据条件获取玩具
    @GetMapping("/toy/condition/{toyCondition}")
    @Operation(summary = "根据条件分页获取玩具", description = "根据条件分页获取玩具")
    public R getToyByCondition(@PathVariable("toyCondition") String toyCondition,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Toy> toys = toyService.getToyByCondition(toyCondition, pageable);
        if (toys.isEmpty()) {
            throw new BusinessException(ExceptionCodeEnum.DATA_NOT_FOUND);
        }
        Page<Object> vos = toys.map(toy -> {
            ToyRespVo vo = new ToyRespVo();
            BeanUtils.copyProperties(toy, vo);
            return vo;
        });
        return R.ok(vos);
    }

    // 更新玩具
    @PutMapping("/toy")
    @Operation(summary = "更新玩具", description = "更新玩具")
    public R updateToy(@RequestBody @Valid ToyUpdateVo vo){
        Toy toy = new Toy();
        BeanUtils.copyProperties(vo, toy);
        toyService.updateToy(toy);
        return R.ok();
    }

    // 删除玩具
    @DeleteMapping("/toy/{id}")
    @Operation(summary = "删除玩具", description = "删除玩具")
    public R deleteToy(@PathVariable("id") long id) {
        toyService.deleteToy(id);
        return R.ok();
    }

    // 归还玩具
//    @PutMapping("/toy/{id}/return")
//    @Operation(summary = "归还玩具", description = "归还玩具")
//    public R returnToy(@PathVariable("id") long id, @RequestParam long num) {
//        toyService.returnToy(id, num);
//        return R.ok();
//    }

//    // 获取玩具图片
//    @GetMapping("/toy/{toyId}/image")
//    @Operation(summary = "获取玩具图片", description = "获取玩具图片")
//    public ResponseEntity<byte[]> getToyImage(@PathVariable("toyId") long toyId) throws IOException {
//        ResponseEntity<byte[]> image = toyService.getToyImage(toyId);
//        return image;
//    }
//
//
//
////    @GetMapping("/toy/categorys")
////    @Operation(summary = "获取所有分类", description = "获取所有分类")
////    public R getAllCategorys() {
////        List<Toy> toys = toyService.getAllToys();
////        return R.ok(toys);
////    }
//
//    // 上传玩具图片
//    @PostMapping("/toy/{toyId}/image")
//    @Operation(summary = "上传玩具图片", description = "上传玩具图片")
//    public R uploadToyImage(@PathVariable(value = "toyId") long  toyId,
//                            @RequestParam(value = "image") MultipartFile image) throws IOException {
//        System.out.println("toyId: " + toyId);
//        System.out.println("image: " + image);
//
//        toyService.uploadToyImage(toyId, image);
//
//        return R.ok();
//    }
}
