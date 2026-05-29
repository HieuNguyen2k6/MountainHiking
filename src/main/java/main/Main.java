/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package main;

import controller.Controller;
import file.MountainFile;
import file.StudentFile;
import java.util.Scanner;
import service.MountainService;
import service.StudentService;
import view.Menu;
import view.View;

/**
 *
 * @author Hiu
 */
public class Main {
    public static void main(String[] args) {
        View view = new View(new Scanner(System.in));
        MountainService mountainService = new MountainService(new MountainFile());
        StudentService studentService = new StudentService(new StudentFile(), view);

        view.setStudentService(studentService);
        
        Controller studentController = new Controller(studentService, mountainService, view);
        Menu menu = new Menu(studentController, view);
        menu.displayMenu();
    }
}
