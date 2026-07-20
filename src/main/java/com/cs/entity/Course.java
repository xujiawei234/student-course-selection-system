package com.cs.entity;

/**
 * 课程实体类，对应数据库中的 course 表
 */

public class Course {
    private Integer id;
    private String name;
    private String teacher;
    private Integer capacity;
    private Integer selected;

    public Course(){}
    public Course(Integer id, String name, String teacher, Integer capacity, Integer selected) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.capacity = capacity;
        this.selected = selected;
    }

    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return this.teacher;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Integer getCapacity() {
        return this.capacity;
    }
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getSelected() {
        return this.selected;
    }
    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacher='" + teacher + '\'' +
                ", capacity='" + capacity +
                ", selected='" + selected +
                '}';
    }

}
