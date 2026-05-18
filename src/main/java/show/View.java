/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package show;

import java.util.Scanner;

/**
 *
 * @author Hiu
 */
public class View {
    private final Scanner sc;
    
    public View(Scanner sc){
        this.sc = sc;
    }
    
    public int showMenu(){
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
    
    public void showMessage(String msg){
        System.out.println(msg);
    }
    
    public String readString(String prompt){
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
}
