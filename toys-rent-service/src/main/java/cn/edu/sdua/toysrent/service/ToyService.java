package cn.edu.sdua.toysrent.service;

import cn.edu.sdua.toysrent.entity.Toy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ToyService {

    void addToy(Toy toy);

    List<Toy> getAllToys();

    Toy getToyById(long toyId);

    Toy getToyByName(String toyName);

    void updateToy(Toy toy);

    void deleteToy(long toyId);

    Page<Toy> getToyByCategoryId(long categoryId,  Pageable pageable);

    Page<Toy> getToyByDailyRate(double dailyRate, Pageable pageable);

    Page<Toy> getToyByStock(long stock,  Pageable pageable);

    Page<Toy> getToyByCondition(String toyCondition,  Pageable pageable);

    void uploadToyImage(long toyId, MultipartFile image) throws IOException;

    ResponseEntity<byte[]> getToyImage(String imageUrl) throws IOException;

    ResponseEntity<byte[]> getToyImage(long toyId) throws IOException;

    void deleteToyImage(long toyId);

    Page<Toy> getToysByPage(Pageable pageable);

    void returnToy(long id, long num);

    Page<Toy> getToyByNameLike(String toyName, Pageable pageable);
}
