/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package show;

import java.util.List;
import java.util.Scanner;
import list.MountainList;
import list.StudentList;
import model.Mountain;
import model.Student;
import valid.Validator;
import static valid.Validator.DEFAULT_FEE;

/**
 *
 * @author Hiu
 */
public class View {

    private final Scanner sc;

    public View(Scanner sc) {
        this.sc = sc;
    }

    public int showMenu() {
        System.out.println("\n===== Mountain Hiking Registration =====");

        String[] options = {"New Registration",
            "Update Registration",
            "Display Registered List",
            "Delete Registration",
            "Search Participants by Name",
            "Filter Data by Campus",
            "Statistics by Mountain",
            "Save Data to File",
            "Exit",};

        Menu.showMenu(options);
        return Integer.parseInt(sc.nextLine());

    }

    public int showUpdateMenu() {
        System.out.println("\n===== Update Registration Information =====");

        String[] options = {"Name", "Phone Number", "Email", "Mountain Peak Code", "Exit"};
        Menu.showMenu(options);
        return Integer.parseInt(sc.nextLine());
    }

    public void showList(List<Student> list) {
        showMessage("\n===== Display Registration List =====");
        System.out.println("---------------------------------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-10s | %-10s | %-12s\n", "Student ID", "Name", "Phone", "Peak Code", "Fee");
        System.out.println("---------------------------------------------------------------------------");
        for (Student s : list) {
            System.out.printf("%-10s | %-20s | %-10s | %-10s | %,.0f\n", s.getId(), s.getName(), s.getPhone(), s.getMountainCode(), s.getTutionFee());
        }
        System.out.println("---------------------------------------------------------------------------");
    }
    
    public boolean delete(Student s, List<Student> list) {
        if (s != null) {
            list.remove(s);
            return true;
        }
        return false;
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public double readFee(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = sc.nextLine().trim();

                // Nếu người dùng bỏ trống (chỉ ấn Enter), lấy luôn giá trị mặc định
                if (input.isEmpty()) {
                    return DEFAULT_FEE;
                }

                // Nếu nhập vào thì parse sang double
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println(">> Invalid number, try again.");
            }
        }
    }

    public String enterId(StudentList stList) {
        String id;
        while (true) {
            id = readString("Enter Student Id: ");
            if (Validator.validStudentId(id)) {
                if (stList.findById(id) == null) {
                    break;
                }
                showMessage(">> ID already exists!");
            } else {
                showMessage(">> Invalid! ID format (SE123456)!!!");
            }
        }
        return id;
    }

    public String enterName() {
        String name;
        while (true) {
            name = readString("Enter Name: ");
            if (!Validator.validStudentName(name)) {
                showMessage(">> Invalid! Name must be 2-20 characters!!!");
            } else {
                break;
            }
        }
        return name;
    }

    public String enterPhone() {
        String phone;
        while (true) {
            phone = readString("Enter Phone: ");
            if (!Validator.vadlidPhone(phone)) {
                showMessage(">> Invalid! Phone must belong to a valid Vietnamese network operator!!!");
            } else {
                break;
            }
        }
        return phone;
    }

    public String enterEmail() {
        String email;
        while (true) {
            email = readString("Enter Email: ");
            if (!Validator.vadlidEmail(email)) {
                showMessage(">> Invalid! Email must be follow standard email format (...@gmail.com) !!!");
            } else {
                break;
            }
        }
        return email;
    }

    public String enterMountainCode(MountainList mtList) {
        String mountainCode;
        while (true) {
            mountainCode = readString("Enter Mountain Code (1-13): ");
            Mountain m = mtList.findByCode(mountainCode);
            if (m != null) {
                showMessage(">>  You have chosen the mountain: " + m.getMountain() + " (" + m.getProvince() + ")");
                break;
            } else {
                showMessage(">> The mountain code does not exist! Please re-enter.");
            }
        }
        return mountainCode;
    }

    public double enterFee(String phone) {
        double fee = readFee("Enter Fee (Default 6,000,000): ");
        if (Validator.isViettelOrVNPT(phone)) {
            fee = fee * 0.65;
            showMessage(">> Discount 35% applied!");
        }
        return fee;
    }
}
