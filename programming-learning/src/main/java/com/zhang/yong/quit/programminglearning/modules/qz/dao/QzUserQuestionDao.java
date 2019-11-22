package com.zhang.yong.quit.programminglearning.modules.qz.dao;

import com.zhang.yong.quit.programminglearning.modules.qz.entity.QzUserQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangyong created on 2019/11/22
 **/
@Repository
public interface QzUserQuestionDao extends JpaRepository<QzUserQuestion,Long> {
}
