package com.zhang.yong.quit.programminglearning.modules.qz.entity;

import com.zhang.yong.quit.programminglearning.modules.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * @author zhangyong created on 2019/10/24
 **/
@Getter
@Setter
@Entity
@Table(name = "qz_quiz")
public class QzQuiz extends BaseEntity {
    private String name;
    @OneToMany
    @JoinColumn(name = "quiz_id")
    private Set<QzQuestion> questions;
}
