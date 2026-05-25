/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Mountain;

/**
 *
 * @author Hiu
 */
public class MountainFile {
    private final String filePath;

    public MountainFile() {
        this.filePath = "src/main/java/resources/MountainList.csv";
    }
    
    public List<Mountain> loadMountainFromFile() {
        List<Mountain> mountainList = new ArrayList<>();
        
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
                mountainList.add(m);
            }
            System.out.println(">> Download successful " + mountainList.size() + " mountains from master data.");
        } catch (IOException e) {
            System.err.println(">> [ERROR] Unable to read mountain list file: " + e.getMessage());
        }
        return mountainList;
        
    }
}
