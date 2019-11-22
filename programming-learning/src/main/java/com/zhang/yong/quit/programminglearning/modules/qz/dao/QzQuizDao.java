package com.zhang.yong.quit.programminglearning.modules.qz.dao;

import com.zhang.yong.quit.programminglearning.modules.common.dao.TableNames;
import com.zhang.yong.quit.programminglearning.modules.qz.entity.QzQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhangyong created on 2019/10/24
 **/
@Repository
public interface QzQuizDao extends JpaRepository<QzQuiz,Long> {

    @Query(value = "select qz.* from " + TableNames.QZ_QUIZ + " qz where qz.id in (select quz.quiz_id from "
            + TableNames.QZ_USER_QUIZ +" quz where quz.user_id = ?1)", nativeQuery = true)
    List<QzQuiz> queryAllByUserId(Long userId);

    void queryAllByCreateUserId(Long userId);
}
