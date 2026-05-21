/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package valid;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Hiu
 */
public class Validator {
    public static final double DEFAULT_FEE = 6000000;
    private static final String ID_FORMAT = "^[S|H|D|Q|C]E\\d{6}$";
    private static final String NAME_FORMAT = "^.{2,20}$";
    private static final String PHONE_FORMAT = "^0(3[2-9]|5[2689]|7[06-9]|8[1-9]|9[0-46-9])\\d{7}$";
    private static final String EMAIL_FORMAT = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[A-Za-z]{2,6}$";
    
    private static final Set<String> VIETTEL_PREFIXES = new HashSet<>(Arrays.asList("086","096","097","098","032","033","034","035","036","037","038","039"));
    private static final Set<String> VNPT_PREFIXES = new HashSet<>(Arrays.asList("088","091","094","083","084","085"));
    
    public static boolean validStudentId(String id){
        return id != null && id.matches(ID_FORMAT);
    }
    public static boolean validStudentName(String name){
        return name != null && name.matches(NAME_FORMAT);
    }
    
    public static boolean vadlidPhone(String phone){
        return phone !=null && phone.matches(PHONE_FORMAT);
    }
    
    public static boolean vadlidEmail(String email){
        return email != null && email.matches(EMAIL_FORMAT);
    }
    
    public static boolean isViettelOrVNPT(String phone){
        phone = phone.substring(0, 3);
        return VIETTEL_PREFIXES.contains(phone) || VNPT_PREFIXES.contains(phone);
    }
}
