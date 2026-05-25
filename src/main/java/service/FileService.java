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
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

/**
 *
 * @author Hiu
 */
public class FileService {

    // Đọc danh sách núi từ file CSV
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
                if (Integer.parseInt(code) > 9) {
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

    // Lưu binary object
    public void saveStudentToObject(StudentList stList, String filePath) {
        List<Student> students = stList.getAll();
        if (students.isEmpty()) {
            System.out.println(">> Nothing to save! The registration list is empty.");
            return;
        }
        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(students);
            System.out.println("Registration data has been successfully saved to `" + filePath + "`.");
        } catch (IOException e) {
            System.err.println(">> [ERROR] Unable to save file: " + e.getMessage());
        }
    }

    // Đọc binary object
    @SuppressWarnings("unchecked")
    public void loadStudentFromObject(StudentList stList, String filePath) {
        try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Student> loaded = (List<Student>) ois.readObject();
            stList.getAll().addAll(loaded);
            System.out.println(">> Loaded " + stList.getAll().size() + " registered students from file.");
        } catch (Exception e) {
            System.out.println(">> No previous registration data found. Starting fresh!");
        }
    }
}
