package com.zhang.yong.quit.programminglearning.modules.qz.form;

import com.zhang.yong.quit.programminglearning.modules.qz.entity.QzQuiz;
import com.zhang.yong.quit.programminglearning.modules.qz.entity.QzUserQuestion;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zhangyong created on 2019/11/22
 **/
@Getter
@Setter
public class AnswerForm {
    private QzQuiz quiz;
    private List<QzUserQuestion> userQuestions;
    private String score;
}
