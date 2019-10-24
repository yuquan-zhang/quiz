package com.zhang.yong.quit.programminglearning.modules.admin.entity;

import com.zhang.yong.quit.programminglearning.modules.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author zhangyong created on 2019/10/24
 **/
@Getter
@Setter
@Entity
@Table(name = "qz_menu")
public class QzMenu extends BaseEntity {
    private String name;
    private String url;
    private String icon;
    private String permission;
    private boolean type; // true=菜单；false=按钮
    @Column(name = "can_show")
    private boolean canShow;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pid")
    private QzMenu parent;
}
