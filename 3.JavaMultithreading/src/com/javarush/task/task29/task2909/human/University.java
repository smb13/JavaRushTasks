package com.javarush.task.task29.task2909.human;

import java.util.ArrayList;
import java.util.List;

public class University {
    private int age;
    private String name;
    private List<Student> students = new ArrayList<>();

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public University(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Student getStudentWithAverageGrade(double averageGrade) {
        for (Student st: students) {
            if (st.getAverageGrade() >= averageGrade)
                return st;
        }
        return null;
    }

    public Student getStudentWithMaxAverageGrade() {
        Student studentWithMaxAverageGrade = students.get(0);
        for (Student st : students) {
            if (st.getAverageGrade() > studentWithMaxAverageGrade.getAverageGrade()) {
                studentWithMaxAverageGrade = st;
            }
        }
        return studentWithMaxAverageGrade;
    }
    public Student getStudentWithMinAverageGrade() {
        Student studentWithMinAverageGrade = students.get(0);
        for (Student st : students) {
            if (st.getAverageGrade() < studentWithMinAverageGrade.getAverageGrade()) {
                studentWithMinAverageGrade = st;
            }
        }
        return studentWithMinAverageGrade;
    }

    public void expel(Student student) {
        if (students.contains(student)) {
            students.remove(student);
        }
    }
}