package com.zhang.yong.quit.programminglearning.modules.qz.service;

import com.zhang.yong.quit.programminglearning.modules.qz.dao.QzQuizScoreDao;
import com.zhang.yong.quit.programminglearning.modules.qz.entity.QzQuizScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhangyong created on 2019/11/7
 **/
@Service
public class QzQuizScoreService {

    @Autowired
    private QzQuizScoreDao qzQuizScoreDao;

    @Transactional
    public void save(QzQuizScore quizScore) {
        qzQuizScoreDao.save(quizScore);
    }

}
