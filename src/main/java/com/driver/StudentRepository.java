package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class StudentRepository {

    private List<Student> studentList = new ArrayList<>();
    private List<Teacher> teacherList = new ArrayList<>();
    private HashMap<Teacher, ArrayList<Student>> underTeacherList = new HashMap<>();

    public List<Student> getStudentList() {
        return studentList;
    }
    public List<Teacher> getTeacherList(){
        return teacherList;
    }

    public HashMap<Teacher, ArrayList<Student>> getStudentTeacherMap() {
        return underTeacherList;
    }

    public void removeTeacher(Teacher teacher) {
        underTeacherList.remove(teacher);
    }
}
