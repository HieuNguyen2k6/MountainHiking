/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.List;
import java.util.Scanner;
import model.Mountain;
import model.Student;
import service.MountainService;
import service.StudentService;
import valid.Validator;

/**
 *
 * @author Hiu
 */
public class View {

    private StudentService stList;
    private final Scanner sc;

    public View(Scanner sc) {
        this.sc = sc;
    }

    public int showMenu() {
        System.out.println("\n===== Mountain Hiking Registration =====");
        System.out.println("1.New Registration");
        System.out.println("2.Update Registration Information");
        System.out.println("3.Display Registered List");
        System.out.println("4.Delete Registration Information");
        System.out.println("5.Search Participants by Name");
        System.out.println("6.Filter Data by Campus");
        System.out.println("7.Statistics of Registration Numbers by Location");
        System.out.println("8.Save Data to File");
        System.out.println("9.Exit the Program");
        System.out.println("-------------------------------------------");
        System.out.print("Your Choice: ");
        return Integer.parseInt(sc.nextLine());
    }

    public void showMountainCode(List<Mountain> mountains) {
        System.out.println("\n===== Display Mountain Code List =====");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-10s | %-10s\n", "Code", "Mountain", " Province", " Description");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Mountain m : mountains) {
            System.out.printf("%-10s | %-20s | %-10s | %-10s\n", m.getMountainCode(), m.getMountain(), m.getProvince(), m.getDescription());
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public int showUpdateMenu() {
        System.out.println("\n===== Update Registration Information =====");
        System.out.println("1.Name");
        System.out.println("2.Phone Number");
        System.out.println("3.Email");
        System.out.println("4.Mountain Peak Code");
        System.out.println("5.Fee");
        System.out.println("6.Exit");
        System.out.println("-------------------------------------------");
        System.out.print("Your Choice: ");
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

    public void showStatisticsTable(List<model.Mountain> mountains, List<Student> students) {
        System.out.println("---------------------------------------------------");
        System.out.printf("%-9s | %-22s | %-20s\n", "Peak Name", "Number of Participants", "Total Cost");
        System.out.println("---------------------------------------------------");

        // Duyệt qua từng ngọn núi trong danh sách master để đếm sinh viên
        for (model.Mountain m : mountains) {
            int count = 0;
            double totalCost = 0;

            for (Student s : students) {
                // Kiểm tra xem sinh viên có đăng ký ngọn núi này không
                if (s.getMountainCode().equalsIgnoreCase(m.getMountainCode())) {
                    count++;
                    totalCost += s.getTutionFee();
                }
            }

            // Đề bài LAB211 yêu cầu: Chỉ hiển thị những ngọn núi nào CÓ sinh viên đăng ký (count > 0)
            if (count > 0) {
                System.out.printf("%-9s | %-22d | %-20s\n",
                        m.getMountainCode(),
                        count,
                        String.format("%,.0f", totalCost));

            }
        }
        System.out.println("---------------------------------------------------");
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
                    return Validator.getDefaultFee();
                }

                // Nếu nhập vào thì parse sang double
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println(">> Invalid number, try again.");
            }
        }
    }

    public String enterId() {
        String id;
        while (true) {
            id = readString("Enter Student Id: ");
            id = id.toUpperCase();
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
            if (!Validator.validEmail(email)) {
                showMessage(">> Invalid! Email must be follow standard email format (...@gmail.com) !!!");
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
            showMountainCode(list);
            mountainCode = readString("Enter Mountain Code (1-13): ");
            Mountain m = mtList.findByCode(mountainCode);
            if (m != null) {
                showMessage(">>  You have chosen the mountain: " + m.getMountain() + " (" + m.getProvince() + ")");
                break;
            } else {
                showMessage(">> The mountain code does not exist! Please re-enter.");
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
        double fee = readFee("Enter Fee (Default 6,000,000): ");
        if (Validator.isViettelOrVNPT(phone)) {
            fee = fee * 0.65;
            showMessage(">> Discount 35% applied!");
        }
        return fee;
    }

    public void setStudentService(StudentService stList) {
        this.stList = stList;
    }   
}
