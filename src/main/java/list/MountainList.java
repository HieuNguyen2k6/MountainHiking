/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package list;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import model.Mountain;

/**
 *
 * @author Hiu
 */
public class MountainList {

    private List<Mountain> mountains = new ArrayList<>();

    public List<Mountain> getAll() {
        return mountains;
    }

    public void loadFromFile(String filePath) {
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
                String name = data[1].trim();
                String province = data[2].trim();
                String desc = (data.length >= 4) ? data[3].trim() : "";

                Mountain m = new Mountain(code, name, province, desc);
                mountains.add(m);
            }
            System.out.println(">> Download successful " + mountains.size() + " the mountain is derived from the original data.");
        } catch (Exception e) {
            // Khắc phục lỗi "Silent Catch": In thông báo lỗi rõ ràng ra màn hình Console
            System.err.println("[ERROR] Unable to read mountain list file: " + e.getMessage());
        }
    }

    public Mountain findByCode(String code) {
        for (Mountain m : mountains) {
            if (m.getMountainCode().equalsIgnoreCase(code)) {
                return m;
            }
        }
        return null;
    }
}
