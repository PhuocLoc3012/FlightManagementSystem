/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyUtil;

import Controller.FlightManagement;
import Model.Flight;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


/**
 *
 * @author Asus
 */
public class Utils {

    public static boolean checkChange = false;
    private static Scanner sc = new Scanner(System.in);

    
    public static String inputSeatLocation(String msg, String seats[][], int rows, int cols ){
        FlightManagement f  = new FlightManagement();
        while(true){
            System.out.print(msg);
            String location = sc.nextLine().trim().toUpperCase();
            if ( f.searchLocation(location, seats,rows,cols) != null && !location.equalsIgnoreCase("X"))
            {//co cho ngoi va khong lay cho cua nguoi khac
                return location;
            }
            if (location.isEmpty()){
                System.out.println("Not allow empty!");
            }
            System.out.println("The seat is not valid (selected or not exist)! Enter again!");
        } 
    }
    
    public static String inputEmail(){
        String msg ="Enter the email (username@domain): ";
        String pattern ="^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$";//[]1 [a-zA-Z0-9_]: \\w \\: k phai '\'
        String s;                                                                           //it nhat 2 ki tu 
        while (true) {
            System.out.print(msg);
            s = sc.nextLine();
            if (s.matches(pattern) && s.isEmpty() == false) {
                break;
            }
            if ( s.isEmpty())
            {
                System.out.println("Not allow empty!");
            }
            System.out.println("Must be follow the formating(username@domain)!");
            System.out.println("EX: example@gmail.com or example@gmail.com.vn ");
        }
        return s.trim();        
    }

    
      
    public static String inputCode(String msg, String pattern, ArrayList<Flight> list) {
        FlightManagement f = new FlightManagement();
        String code;
        while (true) {
            System.out.print(msg);
            code = sc.nextLine().toUpperCase();
            if (code.isEmpty()) {
                System.out.println("Not allowed empty! Enter again");
            }

            if (code.matches(pattern) && f.searchFlight(code, list) == null) {
                return code.replaceAll("\\s", "");
            }
            if ( !code.matches(pattern))
                System.out.println("Must follow formating (FXXXX)!");
            if (f.searchFlight(code, list) != null) {
                System.out.println("Duplicate code! The flight number has existed!");
            }
        }
    }

    public static boolean comfirmMsg(String msg) {
        String answer = inputRequiredString(msg, "Y|N");
        if (answer.equalsIgnoreCase("Y")) {
            return true;
        } else {
            return false;
        }
    }

    public static String inputRequiredString(String msg, String pattern) {
        String s;
        while (true) {
            System.out.print(msg);
            s = sc.nextLine();
            s = s.replaceAll("\\s", "").toUpperCase();// remove all spaces
            if (s.isEmpty()){
                System.out.println("Not allow empty!");
            }
            if (s.matches(pattern) && s.isEmpty() == false) {
                break;
            }
            System.out.println("Must be follow the formating! ");
        }
        return s;

    }

    public static String inputString(String msg) {
        String s = null;
        while (true) {
            System.out.print(msg);
            s = sc.nextLine().replaceAll("\\s+", " ");
            if (s.isEmpty() == false && s.equals(" ")==false ) {
                break;
            }
            System.out.println("Not allowed empty! Enter again");
        }
        return standardizeString(s);

    }
   

    public static int inputInteger(String msg, int min, int max, String Notification1, String Notification2) {
        boolean check = true;
        int number = 0;
        do {
            try {
                System.out.print(msg);
                number = Integer.parseInt(sc.nextLine());
                if ( number > max){
                    System.out.println(Notification1 + max + "!");
                    check = false;
                }                
                if (number < min || number > max) {
                    System.out.println(Notification2 + min + " and " + max +"!");
                    check = false;
                }
                else {
                    check = true;
                }
            } catch (Exception e) {
                System.out.println("Input again! Must be number! ");
                check = false;
            }
        } while (check == false);
        return number;
    }

    public static String inputReservationDate(String msg) {
            String date;
            String currenDate =separateDate(currentDate(), 1);//lay ngay
             
            while (true) {
                System.out.print(msg);
                date = sc.nextLine().trim().replaceAll("\\s", "");
                if ( isValidDate(date,"dd/MM/yyyy")){
                    date = convertToFormattedDate(date, "dd/MM/yyyy");//convert
                    
                    if ( date.equalsIgnoreCase(currenDate))//so ngay
                    { //neu ma ngay nhap vao la ngay hien tai thi lay gio hien tai
                        return currentDate();// gio he thong
                    }
                    else
                    {
                       date = date + " 00:00"; //bat dau 1 ngay moi
                       return date;                       
                    }
                }
                else
                {
                    System.out.println("The time is not valid! Must be follow formating (dd/MM/yyyy)!");
                }
                
            }    
        }    
        private static String convertToFormattedDate(String date, String format){
        try {
            Date tmpDate = new Date();
            SimpleDateFormat formater = new SimpleDateFormat(format);
            
            tmpDate = formater.parse(date);// convert Sring date into Date object
            String result = formater.format(tmpDate);// convert day--> string
            return result;
        } catch (ParseException e) {
            return null;//neu convert that bai
        }

    }
    public static String inputDate(String msg) {
        String date = null;
        while (true) {
            System.out.print(msg);
            date = sc.nextLine().trim().replaceAll("\\s+", " ");
            if (isValidDate(date,"dd/MM/yyyy HH:mm")) {
                return convertToFormattedDate(date, "dd/MM/yyyy HH:mm");
            } else 
                System.out.println("The time is not valid! Must be follow formating (dd/MM/yyyy HH:mm)!");
        }
    }

    public static String currentDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String currentDate = formatter.format(date);
        return currentDate;
    }

    public static boolean isValidDate(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false); //khong chap nhan ngay thang khong le 31/2
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(date);
            return true;
            // ngay thang hop le khong co exception
        } catch (ParseException e) {
            //date khong hop le, co exception
            return false;
        }

    }

    public static int compareDate(String departureTime, String arrivalTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = dateFormat.parse(departureTime);
            endTime = dateFormat.parse(arrivalTime);
        } catch (ParseException e) {
            System.out.println("");
        }
        int result = startTime.compareTo(endTime);
        return result; //1/-1/0
            
    }

    private static String standardizeString(String s) {
        s = s.trim().toLowerCase().replaceAll("\\s+", " "); //remove all consecutive spaces into 1 space
        String tmp[] = s.split(" ");
        for (int i = 0; i < tmp.length; i++) {
            char c = Character.toUpperCase(tmp[i].charAt(0));
            tmp[i] = c + tmp[i].substring(1);
        }
        String result = "";
        for (int i = 0; i < tmp.length; i++) {
            result += tmp[i] + " ";
        }
        return result.trim();
    }
    
    public static String getChoice(String msg){
        String choice;
        while ( true)
        {
            System.out.print(msg);
            choice = sc.nextLine();
            if ( choice.matches("[1-7]"))
                break; 
            System.out.println("Must be following the format! Only 1 digit (1-7)!");
        }
        return choice;

    }
    
    public static String separateDate(String date, int choice) {
        String s[] = date.split(" ");
        if ( choice == 1)
            return s[0];// date
        return s[1];//hour
    }
    
  //  check gio hien tai muon dat ve phai lon hon gio bat dau bay 2 tieng
    public static boolean availableHours(String flyTime, String startTime, int minutes){
//  chi check khi 2 ngay bang nhau va khac ve gio    gio chon: 11:00   gio bay 12:00--> false
        String s1 = separateDate(flyTime, 2);// hour: minute
        String s2 = separateDate(startTime, 2);    
        
        String tmp1[] = s1.split(":");// separate hour, minute
        String tmp2[] = s2.split(":"); 
        int t1 = Integer.valueOf(tmp1[0])* 60 + Integer.valueOf(tmp1[1]);// convert into minute 
        int t2 = Integer.valueOf(tmp2[0])* 60 + Integer.valueOf(tmp2[1]); 
        if ( t1 - t2 >= minutes)// 2 hours
            return true;      
        return false;
    }
    
    public static boolean checkConditionAddFlight(String date1, String date2, int condition){
        //check ngay thang nam lon hon
        String s1 = separateDate(date1, 1);// 17/10/2023
        String s2 = separateDate(date2, 1);// 22/12/2023
        
        String tmp1[] = s1.split("/");
        String tmp2[] = s2.split("/");
        
        int year1 = Integer.valueOf(tmp1[2]);
        int month1 = Integer.valueOf(tmp1[1]); 
        
        int year2 = Integer.valueOf(tmp2[2]);
        int month2 = Integer.valueOf(tmp2[1]);
        
        if ( year2 > year1)
            return true;
        if (year2 == year1)
        {
            //convert into day
            if (( month2*30 + Integer.valueOf(tmp2[0]))-( month1*30 + Integer.valueOf(tmp1[0])) >= condition)
                return true;
            return false;
        }
        return false;// <
    }
    
    public static String boardingTime(String time){// 11:30
        String result =null;
        String s[] =time.split(":");
        
        int hour = Integer.valueOf(s[0]);
        int minute = Integer.valueOf(s[1]);
        
        while(true){//tranh khi update hour, m so tiep dk
            if ( hour == 0 && minute < 30 ){ //00:15
                hour = 23;                      //23
                minute = minute + 30;  //15-30 + 60  = 23:45, lui 1 gio
                break;
            }
            if ( hour > 0 && minute >= 30)//12:30
            {
                 minute = minute -30; 
                 break;
            }
            if ( hour > 0 && minute < 30) // 1:15
            {
                hour = hour - 1;
                minute = minute + 30;
                break;
            } 
        }
        String tmpHour = String.valueOf(hour);
        String tmpMinute = String.valueOf(minute);
        if (hour < 10){// 9---> 09
            tmpHour ="0"+ hour;
        }
        if ( minute < 10){ 
            tmpMinute ="0" + minute;
        }
        
        result= tmpHour + ":" + tmpMinute;       
        return result;
    }
     
}
