package com.zhang.yong.quit.programminglearning.modules.admin.entity;

import com.zhang.yong.quit.programminglearning.modules.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author zhangyong created on 2019/10/24
 **/

@Getter
@Setter
@Entity
@Table(name = "qz_menu")
public class QzRole extends BaseEntity {
    private String name;
}
