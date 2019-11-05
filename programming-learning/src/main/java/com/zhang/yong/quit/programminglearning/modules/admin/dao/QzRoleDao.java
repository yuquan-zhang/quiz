package com.zhang.yong.quit.programminglearning.modules.admin.dao;

import com.zhang.yong.quit.programminglearning.modules.admin.entity.QzRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangyong created on 2019/10/28
 **/
@Repository
public interface QzRoleDao extends JpaRepository<QzRole,Long> {

}
