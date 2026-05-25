/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import list.MountainList;
import list.StudentList;
import model.Student;
import service.FileService;
import show.View;
import valid.Validator;

/**
 *
 * @author Hiu
 */
public class MountainHiking {

    private final StudentList stList = new StudentList();
    private final MountainList mtList = new MountainList();
    private final FileService fileService = new FileService();
    private final View view = new View(new Scanner(System.in));

    private boolean isChanged = false;

    public MountainHiking() {
        fileService.loadMountainFromFile(mtList, "MountainList.csv");
        fileService.loadStudentFromObject(stList, "RegistrationList.dat");
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
                    case 5:
                        main.searchParticipantsByName();
                        break;
                    case 6:
                        main.filterByCampus();
                        break;
                    case 7:
                        main.statisticsByMountain();
                        break;
                    case 8:
                        main.saveData();
                        break;
                    case 9:
                        main.exitSystem();
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
        String id = stList.enterId(stList);
        String name = stList.enterName();
        String phone = stList.enterPhone();
        String email = stList.enterEmail();
        String mountainCode = stList.enterMountainCode(mtList);
        double fee = stList.enterFee(phone);

        stList.addRegistration(new Student(id, name, phone, email, mountainCode, fee));
        isChanged = true;
        view.showMessage(">> Registration successful!");
    }

    public void updateRegistration() {
        String id;
        boolean update = true;
        id = view.readString("\nEnter Student Id: ");
        if (Validator.validStudentId(id)) {
            if (stList.findById(id) != null) {
                Student st = stList.findById(id);
                while (update) {
                    try {
                        int choice = view.showUpdateMenu();
                        switch (choice) {
                            case 1:
                                st.setName(stList.enterName());
                                view.showMessage(">> Update Name success!!!");
                                isChanged = true;
                                break;
                            case 2:
                                double fee = st.getTutionFee();
                                String oldPhone = st.getPhone();
                                st.setPhone(stList.enterPhone());
                                if (Validator.isViettelOrVNPT(oldPhone) && !Validator.isViettelOrVNPT(st.getPhone())) {
                                    fee = fee / 0.65;
                                    view.showMessage(">> Discount 35% Expired!");
                                } else if (!Validator.isViettelOrVNPT(oldPhone) && Validator.isViettelOrVNPT(st.getPhone())) {
                                    fee = fee * 0.65;
                                    view.showMessage(">> Discount 35% applied!");
                                }
                                st.setTutionFee(fee);
                                view.showMessage(">> Update Phone success!!!");
                                isChanged = true;
                                break;
                            case 3:
                                st.setEmail(stList.enterEmail());
                                view.showMessage(">> Update Email success!!!");
                                isChanged = true;
                                break;
                            case 4:
                                st.setMountainCode(stList.enterMountainCode(mtList));
                                view.showMessage(">> Update Mountain Peak Code success!!!");
                                isChanged = true;
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
            view.showMessage("\n>> No students have registered yet");
        } else {
            view.showList(list);
        }
    }

    public void deleteRegistration() {
        String id;
        id = view.readString("\nEnter Student Id to Delete: ");
        if (Validator.validStudentId(id)) {
            Student st = stList.findById(id);
            if (st != null) {
                System.out.println("===== Student Information =====");
                System.out.println("ID       : " + st.getId());
                System.out.println("Name     : " + st.getName());
                System.out.println("Phone    : " + st.getPhone());
                System.out.println("Mountain : " + st.getMountainCode());
                System.out.println("Fee      : " + String.format("%,.0f", st.getTutionFee()));

                String confirm = view.readString("Are you sure you want to delete this registration? (Y/N): ");
                if (confirm.equalsIgnoreCase("Y")) {
                    stList.delete(st, stList.getAll());
                    view.showMessage(">> Deleted successfully!");
                } else {
                    view.showMessage(">> Delete canceled.");
                }
            } else {
                view.showMessage(">> This student has not registered yet.");
            }
        } else {
            view.showMessage(">> Invalid! ID format (SE123456)!!!");
        }
    }

    public void searchParticipantsByName() {
        List<Student> list = stList.getAll();

        if (list.isEmpty()) {
            view.showMessage("\n>> No students have registered yet");
        } else {
            List<Student> searchResult = new ArrayList<>();
            String name;
            name = view.readString("\nEnter Student Name for Search: ");
            for (Student s : list) {
                if (s.getName().toLowerCase().contains(name.toLowerCase())) {
                    searchResult.add(s);
                }
            }
            if (!searchResult.isEmpty()) {
                view.showList(searchResult);
            } else {
                view.showMessage(">> No one matches the search criteria!");
            }

        }
    }

    public void filterByCampus() {
        List<Student> list = stList.getAll();

        if (list.isEmpty()) {
            view.showMessage("\n>> No students have registered yet");
        } else {
            List<Student> fliterList = new ArrayList<>();
            String campus;
            campus = view.readString("\nEnter Campus Code (e.g., SE, HE, DE, QE, CE): ");
            for (Student s : list) {
                if (s.getId().contains(campus.toUpperCase())) {
                    fliterList.add(s);
                }
            }

            if (!fliterList.isEmpty()) {
                view.showList(fliterList);
            } else {
                view.showMessage(">> No students have registered under this campus.");
            }
        }
    }

    public void statisticsByMountain() {
        List<Student> student = stList.getAll();
        List<model.Mountain> mountain = mtList.getAll();

        if (student.isEmpty()) {
            view.showMessage(">> No statistics available (No mountains have registrations yet).");
            return;
        }

        view.showStatisticsTable(mountain, student);
    }

    public void saveData() {
        fileService.saveStudentToObject(stList, "RegistrationList.dat");
        isChanged = false;
    }

    public void exitSystem() {
        if (isChanged) {
            String response = view.readString("Do you want to save the changes before exiting? (Y/N): ");
            if (response.equalsIgnoreCase("Y")) {
                fileService.saveStudentToObject(stList, "RegistrationList.dat");
                view.showMessage("Data saved. Goodbye!");
                System.exit(0);
            } else if (response.equalsIgnoreCase("N")) {
                String confirm = view.readString("You have unsaved changes. Are you sure you want to exit without saving? (Y/N): ");
                if (confirm.equalsIgnoreCase("Y")) {
                    view.showMessage("Goodbye!");
                    System.exit(0);
                }
            }
        } else {
            view.showMessage("Goodbye!");
            System.exit(0);
        }
    }
}
