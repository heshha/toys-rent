package cn.edu.sdua.toysrent.service;

import cn.edu.sdua.toysrent.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    void addUser(User user);

    List<User> getAllUsers();

    Page<User> getUsersByPage(Pageable pageable);

    User getUserById(long userId);

    User getUserByUsername(String username);

    void updateUser(User user);

    void deleteUser(long userId);

    Page<User> getUserByLevel(String level,  Pageable pageable);

    Page<User> getUserByUsernameLike(String username,  Pageable pageable);
}
