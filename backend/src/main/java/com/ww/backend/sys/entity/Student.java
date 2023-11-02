package com.ww.backend.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author ww
 * @since 2023-05-03
 */
@TableName("x_student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String number;

    private String name;

    private String uid;

    private String chinese;

    private String math;

    private String english;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+24")
    private Date physics;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+24")
    private Date biology;

    private String chemistry;

    private String score;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getMath() {
        return math;
    }

    public void setMath(String math) {
        this.math = math;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public Date getPhysics() {
        return physics;
    }

    public void setPhysics(Date physics) {
        this.physics = physics;
    }

    public Date getBiology() {
        return biology;
    }

    public void setBiology(Date biology) {
        this.biology = biology;
    }

    public String getChemistry() {
        return chemistry;
    }

    public void setChemistry(String chemistry) {
        this.chemistry = chemistry;
    }

    @Override
    public String toString() {
        return "Student{" +
            "id = " + id +
            ", number = " + number +
            ", name = " + name +
            ", uid = " + uid +
            ", chinese = " + chinese +
            ", math = " + math +
            ", english = " + english +
            ", physics = " + physics +
            ", biology = " + biology +
            ", chemistry = " + chemistry +
        "}";
    }
}
