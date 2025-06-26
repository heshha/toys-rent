package cn.edu.sdua.toysrent.service.impl;

import cn.edu.sdua.toysrent.entity.User;
import cn.edu.sdua.toysrent.exception.BusinessException;
import cn.edu.sdua.toysrent.exception.ExceptionCodeEnum;
import cn.edu.sdua.toysrent.repository.UserRepository;
import cn.edu.sdua.toysrent.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository  userRepository;

    // 添加用户
    @Override
    public void addUser(User user) {

        //用户名已存在
        if (userRepository.existsByUsername(user.getUsername())){
            throw new BusinessException(ExceptionCodeEnum.USER_ALREADY_EXISTS);
        }

        // 确保userLevel被正确设置
        if(user.getUserLevel() == null) {
            user.setUserLevel(User.UserLevel.普通); // 使用默认值
        } else {
            user.setUserLevel(user.getUserLevel());
        }

        userRepository.save(user);
    }

    // 获取所有用户
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 根据页码获取用户
    @Override
    public Page<User> getUsersByPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // 根据用户ID获取用户
    @Override
    public User getUserById(long userId) {

        Optional<User> user = userRepository.findById(userId);
        // 用户不存在
        if (user.isEmpty()){
            throw new BusinessException(ExceptionCodeEnum.USER_NOT_FOUND);
        }

        return user.get();
    }

    // 根据用户名获取用户
    @Override
    public User getUserByUsername(String username) {

        User user = userRepository.findByUsername(username);
        // 用户不存在
        if (user == null){
            throw new BusinessException(ExceptionCodeEnum.USER_NOT_FOUND);
        }

        return user;
    }

    // 根据用户名模糊获取用户
    @Override
    public Page<User> getUserByUsernameLike(String username, Pageable pageable) {
        return userRepository.findByUsernameContaining(username, pageable);
    }

    // 根据用户等级获取用户
    @Override
    public Page<User> getUserByLevel(String level,  Pageable pageable) {
        return userRepository.findByUserLevel(User.UserLevel.valueOf(level),  pageable);
    }

    // 更新用户
    @Override
    public void updateUser(User user) {
        User oldUser = userRepository.findById(user.getUserId()).orElse(null);
        // 用户不存在
        if (oldUser == null){
            throw new BusinessException(ExceptionCodeEnum.USER_NOT_FOUND);
        }

        // 覆盖原来的值，没带的保持原值
        if (StringUtils.hasText(user.getUsername())){
            oldUser.setUsername(user.getUsername());
        }
        if (user.getUserLevel() != null){
            oldUser.setUserLevel(user.getUserLevel());
        }
        if (user.getBalance() >= 0){
            oldUser.setBalance(user.getBalance());
        }
        if (StringUtils.hasText(user.getPhone())){
            oldUser.setPhone(user.getPhone());
        }
        if (StringUtils.hasText(user.getAddress())){
            oldUser.setAddress(user.getAddress());
        }
        if (user.getUserLevel() == User.UserLevel.管理员){
            throw new BusinessException(ExceptionCodeEnum.ADMIN_REQUIRED);
        }
        userRepository.save(oldUser);
    }

    //  删除用户
    @Override
    public void deleteUser(long userId) {

        if (!userRepository.existsById(userId)){
            throw new BusinessException(ExceptionCodeEnum.USER_NOT_FOUND);
        }
        userRepository.deleteById(userId);
    }
}
