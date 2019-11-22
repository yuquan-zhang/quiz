package com.zhang.yong.quit.programminglearning.modules.qz.service;

import com.zhang.yong.quit.programminglearning.modules.admin.entity.QzUser;
import com.zhang.yong.quit.programminglearning.modules.qz.dao.QzQuizDao;
import com.zhang.yong.quit.programminglearning.modules.qz.entity.QzQuiz;
import com.zhang.yong.quit.programminglearning.modules.qz.entity.QzQuizScore;
import com.zhang.yong.quit.programminglearning.modules.qz.entity.QzUserQuestion;
import com.zhang.yong.quit.programminglearning.modules.qz.form.AnswerForm;
import com.zhang.yong.quit.programminglearning.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zhangyong created on 2019/10/24
 **/
@Service
public class QzQuizService {

    @Autowired
    private QzQuizDao qzQuizDao;

    @Autowired
    private QzQuizScoreService qzQuizScoreService;

    @Autowired
    private QzUserQuestionService qzUserQuestionService;

    public List<QzQuiz> getAll() {
        return qzQuizDao.findAll();
    }

    public QzQuiz getById(Long id) {
        return qzQuizDao.findById(id).orElse(null);
    }

    public List<QzQuiz> getAllByUserId(Long userId) {
        return qzQuizDao.queryAllByUserId(userId);
    }

    @Transactional
    public void saveAnswer(AnswerForm form) {
        QzQuizScore quizScore = new QzQuizScore();
        quizScore.setId(SecurityUtil.genSysId());
        quizScore.setQuiz(form.getQuiz());
        Long currentUserId = SecurityUtil.getCurrentUser().getId();
        QzUser user = new QzUser();
        user.setId(currentUserId);
        quizScore.setUser(user);
        quizScore.setScore(form.getScore());
        Date now = new Date();
        quizScore.setCreateTime(now);
        quizScore.setCreateUserId(currentUserId);
        quizScore.setUpdateTime(now);
        quizScore.setUpdateUserId(currentUserId);
        List<QzUserQuestion> userQuestions = form.getUserQuestions();
        for (QzUserQuestion userQuestion : userQuestions) {
            userQuestion.setId(SecurityUtil.genSysId());
            userQuestion.setQuizScore(quizScore);
        }
        qzQuizScoreService.save(quizScore);
        qzUserQuestionService.saveAll(userQuestions);
    }
}
