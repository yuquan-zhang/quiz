package com.zhang.yong.quit.programminglearning.modules.admin.service;

import com.zhang.yong.quit.programminglearning.modules.admin.dao.QzMenuDao;
import com.zhang.yong.quit.programminglearning.modules.admin.entity.QzMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangyong created on 2019/10/24
 **/
@Service
public class QzMenuService {

    @Autowired
    private QzMenuDao qzMenuDao;

    public List<QzMenu> getAll() {
        return qzMenuDao.findAll();
    }

    public QzMenu getById(Long id) {
        return qzMenuDao.findById(id).get();
    }
}
