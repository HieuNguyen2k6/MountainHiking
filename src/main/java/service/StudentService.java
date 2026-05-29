/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import file.StudentFile;
import java.util.ArrayList;
import java.util.List;
import model.Mountain;
import model.Student;
import valid.Validator;
import view.View;

/**
 *
 * @author Hiu
 */
public class StudentService {

    private final View view;
    private ArrayList<Student> studentList;
    private StudentFile studentFile;

    public StudentService(StudentFile studentFile, View view) {
        this.studentFile = studentFile;
        this.studentList = studentFile.loadFile();
        this.view = view;
    }
    
    public boolean delete(Student s) {
        if (s != null) {
            return studentList.remove(s);
        }
        return false;
    }
    
    public List<Student> getAll() {
        return studentList;
    }
    
    public void save() {
        studentFile.saveStudentToFile(studentList);
    }
    
    public Student findById(String code) {
        for (Student s : studentList) {
            if (s.getId().equalsIgnoreCase(code)) {
                return s;
            }
        }
        return null;
    }

    public void addRegistration(Student s) {
        studentList.add(s);
    }
}
