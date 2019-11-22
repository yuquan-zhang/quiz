package com.zhang.yong.quit.programminglearning.modules.qz.entity;

import com.zhang.yong.quit.programminglearning.modules.common.dao.TableNames;
import com.zhang.yong.quit.programminglearning.modules.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * @author zhangyong created on 2019/10/24
 **/
@Getter
@Setter
@Entity
@Table(name = TableNames.QZ_QUESTION)
public class QzQuestion extends BaseEntity {
    @Column(name = "quiz_id")
    private Long quizId;
    private String name;
    @Column(name = "correct_result")
    private String correctResult;
    private Type type;
    @OneToMany
    @JoinColumn(name = "question_id")
    private Set<QzOption> options;


    public enum Type {
        RADIO("单选"),CHECKBOX("多选");
        private String title;
        Type(String title) {
            this.title = title;
        }
    }
}
