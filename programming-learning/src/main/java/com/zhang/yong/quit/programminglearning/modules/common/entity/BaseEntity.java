package com.zhang.yong.quit.programminglearning.modules.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author zhangyong created on 2019/10/24
 **/
@Getter
@Setter
@MappedSuperclass
public class BaseEntity extends IdEntity{
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "create_user_id")
    private Long createUserId;
    @Column(name = "update_user_id")
    private Long updateUserId;
}
