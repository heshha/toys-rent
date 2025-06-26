package cn.edu.sdua.toysrent.vo.resp.simple;

import cn.edu.sdua.toysrent.entity.User;
import lombok.Data;

@Data
public class UserSimpleVo {

    private long userId;

    private String username;

    private User.UserLevel userLevel;
}
