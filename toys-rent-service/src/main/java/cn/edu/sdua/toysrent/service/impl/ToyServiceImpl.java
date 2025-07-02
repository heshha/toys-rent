package cn.edu.sdua.toysrent.service.impl;

import cn.edu.sdua.toysrent.entity.Toy;
import cn.edu.sdua.toysrent.exception.BusinessException;
import cn.edu.sdua.toysrent.exception.ExceptionCodeEnum;
import cn.edu.sdua.toysrent.repository.ToyRepository;
import cn.edu.sdua.toysrent.service.ToyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;
@RequiredArgsConstructor
@Service
public class ToyServiceImpl implements ToyService {

    private final ToyRepository toyRepository;

    @Value("${spring.servlet.multipart.location}")
    String imagePath;

    // 添加玩具
    @Override
    public void addToy(Toy toy){
        // 玩具已存在
        if (toyRepository.existsByToyName(toy.getToyName())) {
            throw new BusinessException(ExceptionCodeEnum.TOY_ALREADY_EXISTS);
        }
        toyRepository.save(toy);
    }

    //  获取所有玩具
    @Override
    public List<Toy> getAllToys() {
        return toyRepository.findAll();
    }

    // 分页获取玩具
    @Override
    public Page<Toy> getToysByPage(Pageable pageable) {
        return toyRepository.findAll(pageable);
    }

    //  通过id获取玩具
    @Override
    public Toy getToyById(long toyId) {
        Toy toy = toyRepository.findById(toyId)
                .orElseThrow(() -> new BusinessException(ExceptionCodeEnum.TOY_NOT_FOUND));

        return toy;
    }

    // 通过名称获取玩具
    @Override
    public Toy getToyByName(String toyName) {
        Toy toy = toyRepository.findByToyName(toyName);
        if (toy == null) {
            throw new BusinessException(ExceptionCodeEnum.TOY_NOT_FOUND);
        }
        return toy;
    }

    // 通过名称模糊查询玩具
    @Override
    public Page<Toy> getToyByNameLike(String toyName,  Pageable pageable) {
        return toyRepository.findByToyNameContaining(toyName,  pageable);
    }

    // 更新玩具
    @Override
    public void updateToy(Toy toy){
        Toy oldToy = toyRepository.findById(toy.getToyId())
                .orElseThrow(() -> new BusinessException(ExceptionCodeEnum.TOY_NOT_FOUND));
        // 覆盖原来的值，没带的保持原值
        if (StringUtils.hasText(toy.getToyName())){
            oldToy.setToyName(toy.getToyName());
        }
        if (StringUtils.hasText(toy.getDescription())){
            oldToy.setDescription(toy.getDescription());
        }
        if (toy.getCategoryId() > 0){
            oldToy.setCategoryId(toy.getCategoryId());
        }
        if (toy.getDailyRate() >= 0){
            oldToy.setDailyRate(toy.getDailyRate());
        }
        if (toy.getStock() >= 0){
            oldToy.setStock(toy.getStock());
        }
        if (StringUtils.hasText(toy.getToyCondition())){
            oldToy.setToyCondition(toy.getToyCondition());
        }


        toyRepository.save(oldToy);
    }

    //  删除玩具
    @Override
    public void deleteToy(long toyId) {

        // 玩具不存在
        if (!toyRepository.existsById(toyId)){
            throw new BusinessException(ExceptionCodeEnum.TOY_NOT_FOUND);
        }
        toyRepository.deleteById(toyId);
    }

    @Override
    public Page<Toy> getToyByCategoryId(long categoryId,  Pageable pageable) {
        return toyRepository.findByCategoryId(categoryId,  pageable);

    }

    @Override
    public Page<Toy> getToyByDailyRate(double dailyRate,  Pageable pageable) {
        return toyRepository.findByDailyRate(dailyRate,  pageable);
    }

    @Override
    public Page<Toy> getToyByStock(long stock,  Pageable pageable) {
        return toyRepository.findByStock(stock,  pageable);
    }

    @Override
    public Page<Toy> getToyByCondition(String toyCondition,  Pageable pageable) {
        return toyRepository.findByToyCondition(toyCondition,  pageable);
    }

    @Override
    public void returnToy(long id, long num) {
        Toy toy = toyRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ExceptionCodeEnum.TOY_NOT_FOUND));
        toy.setStock(toy.getStock() + num);
        toyRepository.save(toy);
    }
    // 上传玩具图片
    @Override
    public void uploadToyImage(long toyId, MultipartFile image) throws IOException {
        // 获取玩具
        Toy toy = toyRepository.findById(toyId)
                .orElseThrow(() -> new BusinessException(ExceptionCodeEnum.TOY_NOT_FOUND));
        // 获取文件名和文件大小
        String fileName = image.getOriginalFilename();
        long fileSize = image.getSize();
        System.out.println("fileName: " + fileName);
        System.out.println("size: " + fileSize);
        // 获取文件流
//        InputStream inputStream = image.getInputStream();
        // 删除旧图片
        if (toy.getImage() != null){
            deleteToyImage(toyId);
        }

        // 保存路径

        String path = imagePath + toyId + "_" + fileName;
        System.out.println("path: " + path);
        // 保存文件
        image.transferTo(new File(path));
        System.out.println("保存文件成功");
        // 更新 Toy 表中的 image 字段
        toy.setImage(path);
        toyRepository.save(toy);

    }

    // 根据玩具图片路径获取玩具图片
    @Override
    public ResponseEntity<byte[]> getToyImage(String imageUrl) throws IOException {
        if (imageUrl == null){
            throw new BusinessException(ExceptionCodeEnum.TOY_IMAGE_NOT_FOUND);
        }
        File file = new File(imageUrl);
        if (!file.exists()) {
            throw new BusinessException(ExceptionCodeEnum.TOY_IMAGE_NOT_FOUND);
        }
        // 获取文件流
        FileInputStream fileInputStream = new FileInputStream(imageUrl);
        // 获取文件数组
        byte[] imageBytes = fileInputStream.readAllBytes();
        imageUrl = URLEncoder.encode(imageUrl, "UTF-8");
        fileInputStream.close();
        //  返回图片
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG) // 根据实际类型设置
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline") // 关键修改：inline直接显示
                .body(imageBytes);
    }

    // 根据玩具id获取玩具图片
    @Override
    public ResponseEntity<byte[]> getToyImage(long toyId) throws IOException {
        Toy toy = toyRepository.findById(toyId)
                .orElseThrow(() -> new BusinessException(ExceptionCodeEnum.TOY_NOT_FOUND));
        String imageUrl = toy.getImage();
        return getToyImage(imageUrl);
//        File file = new File(imageUrl);
//        if (!file.exists()) {
//            throw new BusinessException(ExceptionCodeEnum.TOY_IMAGE_NOT_FOUND);
//        }
//
//        // 获取图片
//        FileInputStream fileInputStream = new FileInputStream(imageUrl);
//        byte[] imageBytes = fileInputStream.readAllBytes();
//        imageUrl = URLEncoder.encode(imageUrl, "UTF-8");
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .contentLength(imageBytes.length)
//                .header("Content-Disposition", "attachment; filename=\"" + imageUrl + "\"")
//                .body(imageBytes);
    }

    @Override
    public void deleteToyImage(long toyId) {
        Toy toy = toyRepository.findById(toyId)
                .orElseThrow(() -> new BusinessException(ExceptionCodeEnum.TOY_NOT_FOUND));
        // 删除服务器上的图片
        File file = new File(toy.getImage());
        if (file.exists()) {
            file.delete();
        }
        // 更新 Toy 表中的 image 字段
        toy.setImage(null);
        toyRepository.save(toy);
    }


}
