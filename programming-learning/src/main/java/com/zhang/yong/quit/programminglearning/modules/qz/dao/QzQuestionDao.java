package com.zhang.yong.quit.programminglearning.modules.qz.dao;

import com.zhang.yong.quit.programminglearning.modules.qz.entity.QzQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangyong created on 2019/10/28
 **/
@Repository
public interface QzQuestionDao extends JpaRepository<QzQuestion,Long> {

}
