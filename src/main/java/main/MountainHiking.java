/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package main;

import java.util.List;
import java.util.Scanner;
import list.MountainList;
import list.StudentList;
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
                        main.updateRegistration();
                        break;
                    case 3:
                        main.displayRegistration();
                        break;
                    case 4:
                        main.deleteRegistration();
                        break;
                    default:
                        main.view.showMessage(">> This function is not available");
                }
            } catch (Exception e) {
                main.view.showMessage(">> Invalid input. Please try again.");
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
        view.showMessage(">> Registration successful!");
    }

    public void updateRegistration() {
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
                                view.showMessage(">> Update Name success!!!");
                                break;
                            case 2:
                                double fee = st.getTutionFee();
                                String oldPhone = st.getPhone();
                                st.setPhone(view.enterPhone());
                                if (Validator.isViettelOrVNPT(oldPhone) && !Validator.isViettelOrVNPT(st.getPhone())) {
                                    fee = fee / 0.65;
                                    view.showMessage(">> Discount 35% Expired!");
                                } else if (!Validator.isViettelOrVNPT(oldPhone) && Validator.isViettelOrVNPT(st.getPhone())) {
                                    fee = fee * 0.65;
                                    view.showMessage(">> 3Discount 35% applied!");
                                }
                                st.setTutionFee(fee);
                                view.showMessage(">> Update Phone success!!!");
                                break;
                            case 3:
                                st.setEmail(view.enterEmail());
                                view.showMessage(">> Update Email success!!!");
                                break;
                            case 4:
                                st.setMountainCode(view.enterMountainCode(mtList));
                                view.showMessage(">> Update Mountain Peak Code success!!!");
                                break;
                            case 5:
                                update = false;
                                break;
                            default:
                                view.showMessage(">> This function is not available");
                        }
                    } catch (Exception e) {
                        view.showMessage(">> Invalid input. Please try again.");
                    }
                }
            } else {
                view.showMessage(">> This student has not registered yet.");
            }
        } else {
            view.showMessage(">>  Invalid! ID format (SE123456)!!!");
        }
    }

    public void displayRegistration() {
        List<Student> list = stList.getAll();
        if (list.isEmpty()) {
            view.showMessage("\nNo students have registered yet");
        } else {
            view.showMessage("\n===== Display Registration List =====");
            System.out.println("---------------------------------------------------------------------------");
            System.out.printf("%-10s | %-20s | %-10s | %-10s | %-12s\n", "Student ID", "Name", "Phone", "Peak Code", "Fee");
            System.out.println("---------------------------------------------------------------------------");
            for (Student s : list) {
                System.out.printf("%-10s | %-20s | %-10s | %-10s | %,.0f\n", s.getId(), s.getName(), s.getPhone(), s.getMountainCode(), s.getTutionFee());
            }
            System.out.println("---------------------------------------------------------------------------");

        }
    }

    public void deleteRegistration() {
        String id;
        id = view.readString("Enter Student Id to Delete: ");
        if (Validator.validStudentId(id)) {
            Student st = stList.findByCode(id);
            if (st != null) {
                System.out.println("\n===== Student Information =====");
                System.out.println("ID       : " + st.getId());
                System.out.println("Name     : " + st.getName());
                System.out.println("Phone    : " + st.getPhone());
                System.out.println("Mountain : " + st.getMountainCode());
                System.out.println("Fee      : " + String.format("%,.0f", st.getTutionFee()));

                String confirm = view.readString("Are you sure you want to delete this registration? (Y/N): ");
                if (confirm.equalsIgnoreCase("Y")) {
                    stList.delete(id);
                    view.showMessage(">>  Deleted successfully!");
                } else {
                    view.showMessage(">>  Delete canceled.");
                }
            } else {
                view.showMessage(">>  This student has not registered yet.");
            }
        } else {
            view.showMessage(">>  Invalid! ID format (SE123456)!!!");
        }
    }
}
