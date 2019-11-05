package com.zhang.yong.quit.programminglearning.modules.admin.service;

import com.zhang.yong.quit.programminglearning.modules.admin.dao.QzRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangyong created on 2019/10/28
 **/
@Service
public class QzRoleService {

    @Autowired
    private QzRoleDao qzRoleDao;
}
