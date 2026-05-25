/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.StudentController;
import java.util.Scanner;

public class Menu {

    private final View view;
    private StudentController studentController;

    public Menu(StudentController studentController, View view) {
        this.studentController = studentController;
        this.view = view;
    }

    public void displayMenu() {

        int choice = 0;
        do {
            try {
                choice = view.showMenu();
                switch (choice) {
                    case 1:
                        studentController.newRegistration();
                        break;
                    case 2:
                        studentController.updateRegistration();
                        break;
                    case 3:
                        studentController.displayRegistration();
                        break;
                    case 4:
                        studentController.deleteRegistration();
                        break;
                    case 5:
                        studentController.searchParticipantsByName();
                        break;
                    case 6:
                        studentController.filterByCampus();
                        break;
                    case 7:
                        studentController.statisticsByMountain();
                        break;
                    case 8:
                        studentController.saveData();
                        break;
                    case 9:
                        studentController.exitSystem();
                        break;
                    default:
                         System.out.println(">> This function is not available");
                         break;
                }
            } catch (Exception e) {
                System.out.println(">> Invalid input. Please try again.");
            }
        } while (choice != 9);
    }
}
