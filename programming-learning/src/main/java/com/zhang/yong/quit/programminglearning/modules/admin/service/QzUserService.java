package com.zhang.yong.quit.programminglearning.modules.admin.service;

import com.zhang.yong.quit.programminglearning.modules.admin.dao.QzUserDao;
import com.zhang.yong.quit.programminglearning.modules.admin.entity.QzUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangyong created on 2019/10/24
 **/
@Service
public class QzUserService {

    @Autowired
    private QzUserDao qzUserDao;

    public List<QzUser> getAll() {
        return qzUserDao.findAll();
    }
}
