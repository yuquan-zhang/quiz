package com.zhang.yong.quit.programminglearning.modules.qz.service;

import com.zhang.yong.quit.programminglearning.modules.qz.dao.QzQuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangyong created on 2019/10/28
 **/
@Service
public class QzQuestionService {

    @Autowired
    private QzQuestionDao qzQuestionDao;
}
