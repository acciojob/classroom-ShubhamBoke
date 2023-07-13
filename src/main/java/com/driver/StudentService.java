package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public void addStudent(Student student) {
        List<Student> studentList = studentRepository.getStudentList();
        studentList.add(student);
    }

    public void addTeacher(Teacher teacher) {
        List<Teacher> teacherList = studentRepository.getTeacherList();
        teacherList.add(teacher);
    }

    public void addStudentTeacherPair(String student, String teacher) {
        List<Student> studentList = studentRepository.getStudentList();
        List<Teacher> teacherList = studentRepository.getTeacherList();
        Student stu = null;
        Teacher tec = null;
        for(Student s: studentList){
            if(s.getName().equals(student)){
                stu = s;
                break;
            }
        }
        for(Teacher t: teacherList){
            if(t.getName().equals(teacher)){
                tec = t;
                break;
            }
        }
        if(stu == null || tec == null) return;

        HashMap<Teacher, ArrayList<Student>> STMap = studentRepository.getStudentTeacherMap();
        ArrayList<Student> studentListUnderTeacher = STMap.getOrDefault(tec, new ArrayList<>());
        studentListUnderTeacher.add(stu);
        STMap.put(tec, studentListUnderTeacher);
        System.out.println(studentListUnderTeacher);
        tec.setNumberOfStudents(tec.getNumberOfStudents()+1);
    }

    public Student getStudent(String name) {
        List<Student> studentList = studentRepository.getStudentList();
        for(Student s: studentList){
            if(s.getName().equals(name)){
                return s;
            }
        }
        return null;
    }

    public Teacher getTeacher(String name) {
        List<Teacher> teacherList = studentRepository.getTeacherList();
        for(Teacher t: teacherList){
            if(t.getName().equals(name)){
                return t;
            }
        }
        return null;
    }

    public List<String> getStudentNameList(String teacherName) {
        List<Teacher> teacherList = studentRepository.getTeacherList();
        Teacher teacher = null;
        for(Teacher t: teacherList){
            if(t.getName().equals(teacherName)){
                teacher = t;
                break;
            }
        }
        HashMap<Teacher, ArrayList<Student>> STMap = studentRepository.getStudentTeacherMap();
        List<Student> studentListUnderTeacher = STMap.getOrDefault(teacher, new ArrayList<>());
        List<String> res = new ArrayList<>();
        for(Student s: studentListUnderTeacher){
            res.add(s.getName());
        }
        return res;
    }

    public List<String> getAllStudentNameList() {
        List<Student> studentList = studentRepository.getStudentList();
        List<String> res = new ArrayList<>();
        for(Student s: studentList){
            res.add(s.getName());
        }
        return res;
    }

    public void deleteTeacherWithHerStudents(String teacherName) {
        List<Student> studentList = studentRepository.getStudentList();
        List<Teacher> teacherList = studentRepository.getTeacherList();
        Teacher teacher = null;
        for(Teacher t: teacherList){
            if(t.getName().equals(teacherName)){
                teacher = t;
                break;
            }
        }
        HashMap<Teacher, ArrayList<Student>> STMap = studentRepository.getStudentTeacherMap();
        List<Student> studentListUnderTeacher = STMap.getOrDefault(teacher, new ArrayList<>());

        for(Student s: studentListUnderTeacher){
            studentList.remove(s);
        }
        teacherList.remove(teacher);
        studentRepository.removeTeacher(teacher);
    }

    public void deleteAllTeachersWithHerStudents() {
        List<Teacher> teacherList = studentRepository.getTeacherList();
        List<Teacher> teacherListCopy = new ArrayList<>();
        teacherListCopy.addAll(teacherList);
        for(Teacher t: teacherListCopy){
            deleteTeacherWithHerStudents(t.getName());
        }
    }
}
