package com.zhang.yong.quit.programminglearning.modules.qz.entity;

import com.zhang.yong.quit.programminglearning.modules.common.dao.TableNames;
import com.zhang.yong.quit.programminglearning.modules.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author zhangyong created on 2019/10/24
 **/
@Getter
@Setter
@Entity
@Table(name = TableNames.QZ_OPTION)
public class QzOption extends IdEntity {
    @Column(name = "question_id")
    private Long questionId;
    private String name;
    private String description;
}
