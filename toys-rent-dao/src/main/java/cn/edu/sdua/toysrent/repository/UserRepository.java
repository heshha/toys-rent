package cn.edu.sdua.toysrent.repository;

import cn.edu.sdua.toysrent.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 根据用户名查询用户
    User findByUsername(String username);

    // 根据用户名和密码查询用户
    User findByUsernameAndPassword(String username, String password);

    Page<User> findByUserLevel(User.UserLevel userLevel,  Pageable pageable);

    boolean existsByUsername(String username);

    // 分页查询
    Page<User> findAll(Pageable pageable);

    Page<User> findByUsernameContaining(String username, Pageable pageable);
}
