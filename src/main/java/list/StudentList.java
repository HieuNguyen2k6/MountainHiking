/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package list;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Student;
import show.View;

/**
 *
 * @author Hiu
 */
public class StudentList {

    private List<Student> students = new ArrayList<>();
    
    public List<Student> getAll() {
        return students;
    }

    public void addRegistration(Student s) {
        students.add(s);
    }

    public Student findByCode(String code) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(code)) {
                return s;
            }
        }
        return null;
    }

    public boolean delete(String id) {
        Student s = findByCode(id);
        if (s != null) {
            students.remove(s);
            return true;
        }
        return false;
    }

}
