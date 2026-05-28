/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import file.MountainFile;
import java.util.List;
import model.Mountain;

/**
 *
 * @author Hiu
 */
public class MountainService {
    private List<Mountain> mountainList;
    private MountainFile mountainFile;
    
    public List<Mountain> getAll() {
        return mountainList;
    }
    
    public MountainService(MountainFile mountain) {
        this.mountainFile = mountain;
        this.mountainList = mountain.loadMountainFromFile();
    }
    
    public Mountain findByCode(String code) {
        for (Mountain m : mountainList) {
            // Hỗ trợ tìm kiếm linh hoạt cả mã dạng số "1" hoặc dạng chuỗi đầy đủ "MT01"
            if (m.getMountainCode().equalsIgnoreCase(code) || m.getMountainCode().contains(code)) {
                return m;
            }
        }
        return null;
    }
}
