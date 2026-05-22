/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package list;

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
    
    public Mountain findByCode(String code) {
        for (Mountain m : mountains) {
            // Hỗ trợ tìm kiếm linh hoạt cả mã dạng số "1" hoặc dạng chuỗi đầy đủ "MT01"
            if (m.getMountainCode().equalsIgnoreCase(code) || m.getMountainCode().contains(code)) {
                return m;
            }
        }
        return null;
    }
}
