package com.cs.entity;

import java.math.BigDecimal;

/**
 * 选课记录实体类，对应数据库中的 selection 表
 * 注意：score 字段目前暂不处理业务，但实体类保留
 */
public class Selection {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private BigDecimal score;  // 数据库中字段为score DECIMAL(5, 2) DEFAULT NULL,用BigDecimal存储

    public Selection() {}
    public Selection(Integer id, Integer studentId, Integer courseId, BigDecimal score) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.score = score;
    }

    // Getter 和 Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }

    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }

    public BigDecimal getScore() { return score; }
    public void setScore(BigDecimal score) { this.score = score; }

    @Override
    public String toString() {
        return "Selection{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", score=" + score +
                '}';
    }
}
