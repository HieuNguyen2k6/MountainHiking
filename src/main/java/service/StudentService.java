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
    
    public String enterId() {
        String id;
        while (true) {
            id = view.readString("Enter Student Id: ");
            id = id.toUpperCase();
            if (Validator.validStudentId(id)) {
                if (findById(id) == null) {
                    break;
                }
                view.showMessage(">> ID already exists!");
            } else {
                view.showMessage(">> Invalid! ID format (SE123456)!!!");
            }
        }
        return id;
    }

    public String enterName() {
        String name;
        while (true) {
            name = view.readString("Enter Name: ");
            if (!Validator.validStudentName(name)) {
                view.showMessage(">> Invalid! Name must be 2-20 characters!!!");
            } else {
                break;
            }
        }
        return name;
    }

    public String enterPhone() {
        String phone;
        while (true) {
            phone = view.readString("Enter Phone: ");
            if (!Validator.vadlidPhone(phone)) {
                view.showMessage(">> Invalid! Phone must belong to a valid Vietnamese network operator!!!");
            } else {
                break;
            }
        }
        return phone;
    }

    public String enterEmail() {
        String email;
        while (true) {
            email = view.readString("Enter Email: ");
            if (!Validator.vadlidEmail(email)) {
                view.showMessage(">> Invalid! Email must be follow standard email format (...@gmail.com) !!!");
            } else {
                break;
            }
        }
        return email;
    }

    public String enterMountainCode(MountainService mtList) {
        String mountainCode;
        List<Mountain> list = mtList.getAll();
        while (true) {
            view.showMountainCode(list);
            mountainCode = view.readString("Enter Mountain Code (1-13): ");
            Mountain m = mtList.findByCode(mountainCode);
            if (m != null) {
                view.showMessage(">>  You have chosen the mountain: " + m.getMountain() + " (" + m.getProvince() + ")");
                break;
            } else {
                view.showMessage(">> The mountain code does not exist! Please re-enter.");
            }
        }
        if (mountainCode.matches("\\d+")) {
            int code = Integer.parseInt(mountainCode);
            mountainCode = (code > 9) ? "MT" + code : "MT0" + code;
        } else {
            mountainCode = mountainCode.toUpperCase();
        }
        return mountainCode;
    }

    public double enterFee(String phone) {
        double fee = view.readFee("Enter Fee (Default 6,000,000): ");
        if (Validator.isViettelOrVNPT(phone)) {
            fee = fee * 0.65;
            view.showMessage(">> Discount 35% applied!");
        }
        return fee;
    }
}
