package com.training.thread.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Wang Junwei
 * @Date 2022/4/8 16:27
 * @Description
 */


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private String name;

    private Integer age;

    private String clazz;

    private Integer score;

    private String course;

    public static String score(String name, String name1) {
        return name + name1;
    }

    public static Student sum(Student student1, Student student2) {
        student1.setScore(student1.getScore() + student2.getScore());
        return student1;
    }
}
