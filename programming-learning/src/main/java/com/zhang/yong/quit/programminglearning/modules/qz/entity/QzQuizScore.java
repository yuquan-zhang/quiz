package com.zhang.yong.quit.programminglearning.modules.qz.entity;

import com.zhang.yong.quit.programminglearning.modules.admin.entity.QzUser;
import com.zhang.yong.quit.programminglearning.modules.common.dao.TableNames;
import com.zhang.yong.quit.programminglearning.modules.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author zhangyong created on 2019/11/7
 **/
@Getter
@Setter
@Entity
@Table(name = TableNames.QZ_QUIZ_SCORE)
public class QzQuizScore extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private QzUser user;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private QzQuiz quiz;
    private String score;
}
