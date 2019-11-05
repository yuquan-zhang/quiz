package com.zhang.yong.quit.programminglearning.modules.admin.dao;

import com.zhang.yong.quit.programminglearning.modules.admin.entity.QzMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author zhangyong created on 2019/10/24
 **/
@Repository
public interface QzMenuDao extends JpaRepository<QzMenu,Long> {

    @Query(value = "select qm.* from qz_menu qm where qm.id in (select qrm.menu_id from qz_role_menu qrm where qrm.role_id in " +
            "(select qur.role_id from qz_user_role qur where qur.user_id = ?1))", nativeQuery = true)
    Set<QzMenu> findByUserId(Long userId);
}
