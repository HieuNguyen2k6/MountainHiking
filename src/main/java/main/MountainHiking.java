/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package main;

import java.util.Scanner;
import list.MountainList;
import list.StudentList;
import model.Mountain;
import model.Student;
import show.View;
import valid.Validator;

/**
 *
 * @author Hiu
 */
public class MountainHiking {

    private final StudentList stList = new StudentList();
    private final MountainList mtList = new MountainList();
    private final View view = new View(new Scanner(System.in));
    private boolean isChanged = false;

    public MountainHiking() {
        mtList.loadFromFile("MountainList.csv");
    }

    public static void main(String[] args) {
        MountainHiking main = new MountainHiking();
        while (true) {
            try {
                int choice = main.view.showMenu();
                switch (choice) {
                    case 1:
                        main.newRegistration();
                        break;
                    default:
                        main.view.showMessage("This function is not available");
                }
            } catch (Exception e) {
                main.view.showMessage("Invalid input. Please try again.");
            }
        }
    }

    public void newRegistration() {
        view.showMessage("\n===== New Registration =====");
        String id;
        while (true) {
            id = view.readString("Enter Student Id: ");
            if (Validator.validStudentId(id)) {
                if (stList.findByCode(id) == null) {
                    break;
                }
                view.showMessage("ID already exists!");
            } else {
                view.showMessage("Invalid! ID format (SE123456)!!!");
            }
        }

        String name;
        while (true) {
            name = view.readString("Enter Name: ");
            if (!Validator.validStudentName(name)) {
                view.showMessage("Invalid! Name must be 2-20 characters!!!");
            } else {
                break;
            }
        }

        String phone;
        while (true) {
            phone = view.readString("Enter Phone: ");
            if (!Validator.vadlidPhone(phone)) {
                view.showMessage("Invalid! Phone must belong to a valid Vietnamese network operator!!!");
            } else {
                break;
            }
        }

        String email;
        while (true) {
            email = view.readString("Enter Email: ");
            if (!Validator.vadlidEmail(email)) {
                view.showMessage("Invalid! Email must be follow standard email format (...@gmail.com) !!!");
            } else {
                break;
            }
        }

        String mountainCode;
        while (true) {
            mountainCode = view.readString("Enter Mountain Code (1-13): ");
            Mountain m = mtList.findByCode(mountainCode);
            if (m != null) {
                view.showMessage(">> You have chosen the mountain: " + m.getMountain() + " (" + m.getProvince() + ")");
                break;
            } else {
                view.showMessage("The mountain code does not exist! Please re-enter.");
            }
        }

        double fee = view.readDouble("Enter Fee (Default 6,000,000): ", 6000000);
            if (Validator.isViettelOrVNPT(phone)){
                fee = fee * 0.65;
            view.showMessage("Discount 35% applied!");
        }
            
        stList.addRegistration(new Student(id, name, phone, email, mountainCode, fee));
        isChanged = true;
        view.showMessage("Registration successful!");    
    }
}
