package cn.edu.sdua.toysrent.repository;

import cn.edu.sdua.toysrent.entity.Toy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToyRepository extends JpaRepository<Toy, Long> {

    Toy findByToyName(String toyName);

    Page<Toy> findByCategoryId(long categoryId, Pageable pageable);

    Page<Toy> findByDailyRate(double dailyRate,  Pageable pageable);

    Page<Toy> findByStock(long stock,  Pageable pageable);

    Page<Toy> findByToyCondition(String toyCondition,  Pageable pageable);

    boolean existsByToyName(String toyName);

    Page<Toy> findAll(Pageable pageable);

    Page<Toy> findByToyNameContaining(String toyName, Pageable pageable);
}
