/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package main;

import java.util.HashSet;
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
                    case 2:
                        main.UpdateRegistration();
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
        String id = view.enterId(stList);
        String name = view.enterName();
        String phone = view.enterPhone();
        String email = view.enterEmail();
        String mountainCode = view.enterMountainCode(mtList);
        double fee = view.enterFee(phone);
        stList.addRegistration(new Student(id, name, phone, email, mountainCode, fee));
        isChanged = true;
        view.showMessage("Registration successful!");
    }

    public void UpdateRegistration() {
        String id;
        boolean update = true;
        id = view.readString("Enter Student Id: ");
        if (Validator.validStudentId(id)) {
            if (stList.findByCode(id) != null) {
                Student st = stList.findByCode(id);
                while (update) {
                    try {
                        int choice = view.showUpdateMenu();
                        switch (choice) {
                            case 1:
                                st.setName(view.enterName());
                                view.showMessage("Update Name success!!!");
                                break;
                            case 2:
                                st.setPhone(view.enterPhone());
                                view.showMessage("Update Phone success!!!");
                                break;
                            case 3:
                                st.setEmail(view.enterEmail());
                                view.showMessage("Update Email success!!!");
                                break;
                            case 4:
                                st.setMountainCode(view.enterMountainCode(mtList));
                                view.showMessage("Update Mountain Peak Code success!!!");
                                break;
                            case 5:
                                update = false;
                                break;
                            default:
                                view.showMessage("This function is not available");
                        }
                    } catch (Exception e) {
                        view.showMessage("Invalid input. Please try again.");
                    }
                }
            }
            view.showMessage("This student has not registered yet.");
        } else {
            view.showMessage("Invalid! ID format (SE123456)!!!");
        }
    }
}
