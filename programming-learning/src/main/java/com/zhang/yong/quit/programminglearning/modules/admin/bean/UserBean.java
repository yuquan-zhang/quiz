package com.zhang.yong.quit.programminglearning.modules.admin.bean;

import com.zhang.yong.quit.programminglearning.modules.admin.entity.QzMenu;
import com.zhang.yong.quit.programminglearning.modules.admin.entity.QzRole;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author zhangyong created on 2019/10/23
 **/
@Getter
@Setter
public class UserBean {
    private Long id;
    private String name;
    private String account;
    private Set<QzRole> roles;
    private Set<QzMenu> menus;
}
