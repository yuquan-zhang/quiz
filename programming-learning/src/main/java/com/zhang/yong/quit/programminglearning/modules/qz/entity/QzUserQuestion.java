package com.zhang.yong.quit.programminglearning.modules.qz.entity;

import com.zhang.yong.quit.programminglearning.modules.admin.entity.QzUser;
import com.zhang.yong.quit.programminglearning.modules.common.dao.TableNames;
import com.zhang.yong.quit.programminglearning.modules.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author zhangyong created on 2019/11/22
 **/
@Getter
@Setter
@Entity
@Table(name = TableNames.QZ_USER_QUESTION)
public class QzUserQuestion extends IdEntity {
    @ManyToOne
    @JoinColumn(name = "quiz_score_id")
    private QzQuizScore quizScore;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QzQuestion question;
    private String answer;
}
