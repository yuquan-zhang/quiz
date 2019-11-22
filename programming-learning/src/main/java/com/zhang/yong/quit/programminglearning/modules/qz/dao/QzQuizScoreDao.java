package com.zhang.yong.quit.programminglearning.modules.qz.dao;

import com.zhang.yong.quit.programminglearning.modules.qz.entity.QzQuizScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangyong created on 2019/11/7
 **/
@Repository
public interface QzQuizScoreDao extends JpaRepository<QzQuizScore,Long> {

}
