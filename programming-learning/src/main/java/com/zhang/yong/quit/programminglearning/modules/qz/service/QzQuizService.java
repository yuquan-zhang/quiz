package com.zhang.yong.quit.programminglearning.modules.qz.service;

import com.zhang.yong.quit.programminglearning.modules.qz.dao.QzQuizDao;
import com.zhang.yong.quit.programminglearning.modules.qz.entity.QzQuiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangyong created on 2019/10/24
 **/
@Service
public class QzQuizService {

    @Autowired
    private QzQuizDao qzQuizDao;

    public List<QzQuiz> getAll() {
        return qzQuizDao.findAll();
    }

    public QzQuiz getById(Long id) {
        return qzQuizDao.findById(id).orElse(null);
    }
}
