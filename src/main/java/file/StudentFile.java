/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import model.Student;

/**
 *
 * @author Hiu
 */
public class StudentFile {
    private final String filePath;

    public StudentFile() {
        this.filePath = "src/main/java/resources/RegistrationList.dat";
    }
    public ArrayList<Student> loadFile(){
        ArrayList<Student> studentList = new ArrayList<>();

        File file = new File(filePath);
        if(!file.exists() || file.length() == 0) {
            return studentList;
        }
        
        try(
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis)
        ){
            studentList = (ArrayList<Student>) ois.readObject();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(">> Loaded " + studentList.size() + " registered students from file.");
        return studentList;
    }
    
    public void saveStudentToFile(ArrayList<Student> studentList) {
        if (studentList.isEmpty()) {
            System.out.println(">> Nothing to save! The registration list is empty.");
            return;
        }
        try (
            FileOutputStream fos = new FileOutputStream(filePath);  
            ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(studentList);
            System.out.println("Registration data has been successfully saved.");
        } catch (Exception e) {
            System.err.println(">> [ERROR] Unable to save file: " + e.getMessage());
        }
    }
}
