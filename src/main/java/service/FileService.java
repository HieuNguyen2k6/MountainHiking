/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import list.MountainList;
import list.StudentList;
import model.Mountain;
import model.Student;

/**
 *
 * @author Hiu
 */
public class FileService {

    // 1. Đọc danh sách núi từ file CSV
    public void loadMountainFromFile(MountainList mtList, String filePath) {
        try ( BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Bỏ qua dòng tiêu đề CSV
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] data = line.split(",");
                if (data.length < 3) {
                    continue;
                }

                String code = data[0].trim();
                if (Integer.parseInt(code) > 10) {
                    code = "MT" + code;
                } else {
                    code = "MT0" + code;
                }
                String name = data[1].trim();
                String province = data[2].trim();
                String desc = (data.length >= 4) ? data[3].trim() : "";

                Mountain m = new Mountain(code, name, province, desc);
                mtList.getAll().add(m);
            }
            System.out.println(">> Download successful " + mtList.getAll().size() + " mountains from master data.");
        } catch (IOException e) {
            System.err.println(">> [ERROR] Unable to read mountain list file: " + e.getMessage());
        }
    }

    // 2. Ghi danh sách sinh viên đăng ký ra file text/csv (Dùng cho chức năng Save)
    public void saveStudentToFile(StudentList stList, String filePath) {
        List<Student> students = stList.getAll();
        if (students.isEmpty()) {
            System.out.println(">> Nothing to save! The registration list is empty.");
            return;
        }

        try ( BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Ghi dòng tiêu đề
            bw.write("StudentID,Name,Phone,Email,MountainCode,TuitionFee");
            bw.newLine();

            // Ghi dữ liệu từng sinh viên
            for (Student s : students) {
                String line = String.format("%s,%s,%s,%s,%s,%.0f",
                        s.getId(),
                        s.getName(),
                        s.getPhone(),
                        s.getEmail(),
                        s.getMountainCode(),
                        s.getTutionFee());
                bw.write(line);
                bw.newLine();
            }
            System.out.println(">> Save data to file successfully! (Saved " + students.size() + " records)");
        } catch (IOException e) {
            System.err.println(">> [ERROR] Unable to save student file: " + e.getMessage());
        }
    }

    public void loadStudentFromFile(StudentList stList, String filePath) {
        try ( BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Bỏ qua dòng tiêu đề
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] data = line.split(",");
                if (data.length < 6) {
                    continue;
                }

                String id = data[0].trim();
                String name = data[1].trim();
                String phone = data[2].trim();
                String email = data[3].trim();
                String mountainCode = data[4].trim();
                double fee = Double.parseDouble(data[5].trim());

                Student s = new Student(id, name, phone, email, mountainCode, fee);
                stList.getAll().add(s);
            }
            System.out.println(">> Loaded successful " + stList.getAll().size() + " registered students from file.");
        } catch (Exception e) {
            // Lần đầu tiên chạy chưa có file sinh viên thì sẽ bắt đầu danh sách mới
            System.out.println(">> No previous registration data found. Starting fresh!");
        }
    }
}
