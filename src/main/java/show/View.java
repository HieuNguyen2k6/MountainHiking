/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package show;

import java.util.List;
import java.util.Scanner;
import list.MountainList;
import model.Mountain;
import model.Student;
import valid.Validator;

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
    
    public void showMountainCode(List<model.Mountain> mountains){
        showMessage("\n===== Display Mountain Code List =====");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-10s | %-10s\n", "Code", "Mountain", " Province", " Description");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Mountain m : mountains){
            System.out.printf("%-10s | %-20s | %-10s | %-10s\n", m.getMountainCode(), m.getMountain(), m.getProvince(), m.getDescription());
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
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
}
