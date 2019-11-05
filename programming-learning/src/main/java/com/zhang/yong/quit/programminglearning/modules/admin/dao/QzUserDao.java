package com.zhang.yong.quit.programminglearning.modules.admin.dao;

import com.zhang.yong.quit.programminglearning.modules.admin.entity.QzUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangyong created on 2019/10/24
 **/
@Repository
public interface QzUserDao extends JpaRepository<QzUser,Long> {

    QzUser findQzUserByAccount(String account);
}
