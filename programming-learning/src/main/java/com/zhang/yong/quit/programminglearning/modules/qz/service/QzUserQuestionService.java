package com.zhang.yong.quit.programminglearning.modules.qz.service;

import com.zhang.yong.quit.programminglearning.modules.qz.dao.QzUserQuestionDao;
import com.zhang.yong.quit.programminglearning.modules.qz.entity.QzUserQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhangyong created on 2019/11/22
 **/
@Service
public class QzUserQuestionService {

    @Autowired
    private QzUserQuestionDao qzUserQuestionDao;

    @Transactional
    public void saveAll(List<QzUserQuestion> userQuestions) {
        qzUserQuestionDao.saveAll(userQuestions);
    }
}
