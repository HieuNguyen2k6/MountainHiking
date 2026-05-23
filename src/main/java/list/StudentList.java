/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package list;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Mountain;
import model.Student;
import show.View;
import valid.Validator;

/**
 *
 * @author Hiu
 */
public class StudentList {

    private List<Student> student = new ArrayList<>();
    private final View view = new View(new Scanner(System.in));

    public List<Student> getAll() {
        return student;
    }

    public void addRegistration(Student s) {
        student.add(s);
    }

    public Student findById(String code) {
        for (Student s : student) {
            if (s.getId().equalsIgnoreCase(code)) {
                return s;
            }
        }
        return null;
    }

    public boolean delete(Student s, List<Student> list) {
        if (s != null) {
            list.remove(s);
            return true;
        }
        return false;
    }

    public String enterId(StudentList stList) {
        String id;
        while (true) {
            id = view.readString("Enter Student Id: ");
            id = id.toUpperCase();
            if (Validator.validStudentId(id)) {
                if (stList.findById(id) == null) {
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

    public String enterMountainCode(MountainList mtList) {
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
