package com.zhang.yong.quit.programminglearning.modules.qz.dao;

import com.zhang.yong.quit.programminglearning.modules.qz.entity.QzQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangyong created on 2019/10/24
 **/
@Repository
public interface QzQuizDao extends JpaRepository<QzQuiz,Long> {

}
