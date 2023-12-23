/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Booking;
import Model.Crew;
import Model.Flight;
import Model.Passenger;
import MyUtil.Utils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Asus
 */
public class FlightManagement {

    private int number = 1;//inceasing numer (start with 1)    
    private ArrayList<Flight> listFlight = new ArrayList<>();
    private ArrayList<Booking> listBooking = new ArrayList<>();
    private String fileFlight ="flight.dat";

    public ArrayList<Flight> getListFlight() {
        return listFlight;
    }

    public void setListFlight(ArrayList<Flight> listFlight) {
        this.listFlight = listFlight;
    }

    
    
    private String increasingNumber() {
        String result = null;
        while (true) {
            result = "B";
            String s = String.valueOf(number); // convert number into string
            for (int i = 0; i < 4 - s.length(); i++) {
                result += "0";
            }
            result = result + s;// append B0001
            if ( searchListBooking(result) == false) // check duplicate
                 break; // if not found-> not duplicate
            number++;// if duplicate code then number++
        };
        return result;
    }
//search if code booking exist
    public boolean searchListBooking(String code){
      
        for (Booking b :listBooking ) {
            if ( code.equalsIgnoreCase(b.getBookingID()))
                return true;
        }
        return false;
    }
    //FUNCTION 1
    
//  search flight number duplicate
    public Flight searchFlight(String code, ArrayList<Flight> list) {
        for (Flight flight : list) {
            if (flight.getFlightNumber().equalsIgnoreCase(code)) {
                return flight;
            }
        }
        return null;
    }    
    
 
    public void addFlight() {
        String flightNumber;
        String departureLocation;
        String arrivalLocation;
        String departureTime;
        String arrivalTime;
        int seat;
        boolean confirm = false;
        do {
            System.out.println("Enter details information of new flight:");
            flightNumber = Utils.inputCode("Enter the flight number, must be FXXXX (X is digit): ", "F\\d{4}", listFlight);
            departureLocation = Utils.inputString("Enter the departure location: ");
            arrivalLocation = Utils.inputString("Enter the arrival location: ");
            while (true) {
                departureTime = Utils.inputDate("Enter the departure time (dd/MM/yyyy HH:mm): ");
                
                if (Utils.checkConditionAddFlight(Utils.currentDate(), departureTime, 14) ) {                    
                    break;
                }
                else
                {
                    System.out.println("The departure time of flight must be before the current time ("+Utils.separateDate(Utils.currentDate(), 1)+") at least 2 weeks!");
                }
            }
            while (true) {
                arrivalTime = Utils.inputDate("Enter the arrival time (dd/MM/yyyy HH:mm): ");
                if (Utils.compareDate(arrivalTime,departureTime)==1) {//gio den phai lon hon gio bay
                    break;
                }
                System.out.println("The departure time must be larger than the departure time!");
            }
            seat = Utils.inputInteger("Enter the number of seats for this flight: ", 150, 400,"The maximum number of seats is ","The number of seats must be between ");

            Flight flight = new Flight(flightNumber, departureLocation, arrivalLocation, departureTime, arrivalTime, seat);
//            int cols = 7;
            float numberSeat = seat;
            int rows = Math.round(numberSeat/6);  
            flight.setRows(rows);
            
            flight.setSeatMap(seatMap(numberSeat,rows, flight.getCols()));//7
            
            
            flight.setCrew(new Crew("N/A", "N/A", "N/A"));
         
            listFlight.add(flight);
            
            System.out.println("Add new flight successfully!");
            showOneFlight(flight);            
            confirm = Utils.comfirmMsg("Do you want to continue add new flight (Y/N)?: ");
        } while (confirm);
    }
    
    
    public void showFlight(ArrayList<Flight> list, String msg){
        System.out.println("__________________________________________________________________________________________________");

        System.out.format("|%-28s%-38s%-29s|"," ", msg,"");
        System.out.println();        
        System.out.println("|________________________________________________________________________________________________|");
        System.out.format("|%-15s|%-12s|%-12s|%-18s|%-18s|%-16s|"
                , "Flight number ","From   ","To    ", " Departure time", " Arrival time", "Available seats");
        System.out.println();
        for (Flight f : list) {
            System.out.format("|%-15s|%-12s|%-12s|%-16s|%-16s|%-16s|",f.getFlightNumber(),f.getDepartureLocation()
            ,f.getArrivalLocation()," "+ f.getDepartureTime()+" "," "+f.getArrivalTime()+" ","      "+f.getAvailableSeat());
            System.out.println();
        }
        System.out.println("|________________________________________________________________________________________________|");        
        System.out.println();
    }
    public void showOneFlight(Flight f){
        System.out.println();
        System.out.format("|%-15s|%-12s|%-12s|%-18s|%-18s|%-16s|"
                , "Flight number ","From","To", " Departure time", " Arrival time", "Available seats");
        System.out.println();
        System.out.format("|%-15s|%-12s|%-12s|%-18s|%-18s|%-16s|",f.getFlightNumber(),f.getDepartureLocation()
                ,f.getArrivalLocation(), " "+f.getDepartureTime()+" "," " +f.getArrivalTime()+" ","      "+f.getAvailableSeat());
        System.out.println();   
        System.out.println();         
    }
    
    
    
    
  //2. Draw map  
    public String[][] seatMap(float numberSeat, int rows,int cols){
        char[] columns = {'A', 'B', 'C', '\t','D', 'E', 'F'};//chua cho loi di chinh giua
        int count = 0;//đủ chỗ 
        String[][] seats = new String[rows][cols];// create mang 2 chieu luu tru seats
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                count++;//print seat
                if ( j == 3 || count >(numberSeat + rows))// 160 seat thi 160/6 = 27 ( du 2)
                    seats[i][j]="";// 160 + 27 = 187                 27 x 6 = 162
               else 
                    seats[i][j] = "" + columns[j] + (i + 1);// A1 B1 C1 D1 E1 F1
            }
        } 
        return seats;
    }
    
    public void printSeatMap(String[][] seats, int rows, int cols, String flightNumber){
        System.out.println("_____________________________________________________");        
        System.out.println("|            THE SEATS OF FLIGHT NUMBER " +flightNumber +"       |" );
        System.out.println("|___________________________________________________|");
        System.out.println("|                      Aisle                        |");
        for (int i = 0; i < rows; i++) {
            System.out.print("| ");
            for (int j = 0; j < cols; j++) {
                if ( j == cols - 1)// cot cuoi k co \t
                    System.out.format("%-4s", seats[i][j]);// A10 |
                else
                    System.out.print(seats[i][j] + "\t"); //  A10   |    
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("|___________________________________________________|");  
                
    }
    


    

//2. BOOKING
    
// check xem noi bat dau va noi den
    public boolean checkAvailableLocation(String location, int choice){
        int check =0;
        if ( choice == 1)
        {
            for (Flight f : listFlight) {
                if (location.equalsIgnoreCase(f.getDepartureLocation()))
                    check++;
            }
            if ( check ==0)
                return false;
            return true;
        }
        else
        {
            for (Flight f : listFlight) {
                if (location.equalsIgnoreCase(f.getArrivalLocation()))
                    check++;
            }
            if ( check ==0)
                return false;
            return true;            
        }
    }

    public void reservateFlight() {
        Booking b = new Booking();
        String reservationID = null;
        String departureLocation;
        String arrivalLocation;
        String startTime;
        String returnTime;
        Flight f = null;
        int seat = 0;
        Passenger p;
        ArrayList<Flight> listAvailableFlight = new ArrayList<>();        

        departureLocation = Utils.inputString("Enter your departure location: ");
        arrivalLocation = Utils.inputString("Enter your arrival location: ");

        if (checkAvailableLocation(departureLocation, 1)==false || checkAvailableLocation(arrivalLocation, 2)== false)
        {//Co noi muon den, muon di k
            System.out.println("No have any flights for these location!");
            return;              
        }
        
        while (true) {
            startTime = Utils.inputReservationDate("Enter the departure date(dd/MM/yyyy): ");// khi luu tru se co du dd/MM/yyyy HH:mm de so sanh 2 gio
            if (Utils.compareDate( startTime, Utils.currentDate())==1 || Utils.compareDate( startTime, Utils.currentDate())==0) {
                break;// ngay muon di lon hon hoac bang ngay hien tai
            }
            System.out.println("The departure day must be larger than or equal current date (" + Utils.separateDate(Utils.currentDate(), 1)+")!");
        }
        while (true){
            returnTime = Utils.inputReservationDate("Enter the return date (dd/MM/yyyy): ");
            //kiem tra ngay tro ve >= ngay muon di
            if (Utils.compareDate( returnTime,startTime)==0// equal 
                || Utils.compareDate(returnTime, startTime)==1 ) {//larger
                
                break;
            }
            System.out.println("The return day must be larger than or equals the departure day!");
        }
        
  //tim trong list xem co chuyen bay nao phu hop hay ko
        for (Flight f1 : listFlight) {
            if (departureLocation.equalsIgnoreCase(f1.getDepartureLocation())
                && arrivalLocation.equalsIgnoreCase(f1.getArrivalLocation())
                    //ngay bay bang gio muon di
                && (Utils.compareDate(f1.getDepartureTime(), Utils.currentDate())== 1)
                && (Utils.separateDate(f1.getDepartureTime(), 1).equalsIgnoreCase(Utils.separateDate(startTime, 1))// ngay bay bang ngay nhap
             )&& f1.getAvailableSeat() > 0  
                    )
            {
                    listAvailableFlight.add(f1);
            }
            /*   && Utils.availableHours(f1.getDepartureTime(),startTime, 120)*/
        }
        if ( listAvailableFlight.isEmpty()) 
        {
            System.out.println("The date you selected does not have a flight or out of seat!");          
            return;
        }        
        showFlight(listAvailableFlight,"       LIST THE AVAILABLE FLIGHTS      ");
        
        do {
            String flightID = Utils.inputRequiredString("Enter your flight number that you want to reserve (FXXXX): ", "F\\d{4}");
            f = searchFlight(flightID, listAvailableFlight);
            int numberOfPassenger;
            //More than 9 Passenger's can not travel.

            if (f != null) 
            {
                numberOfPassenger = Utils.inputInteger("Enter the number of passengers: ",1,9,"The maximum number of passengers can reservate is ", "The number of passenger must be between ");
                b.setNumberPassenger(numberOfPassenger);//update lai attribute so hanh khac              
                f.setAvailableSeat(f.getAvailableSeat() - numberOfPassenger);
                //contact information
                String name;
                String phone;
                String email;
                System.out.println();
                System.out.println("Enter contact informaton for passenger (Receive mail,tickets and payment): ");
                b.setName(Utils.inputString("Enter name: "));
                phone = Utils.inputRequiredString("Enter the phone number (10 digits): ", "\\d{10}");
                b.setPhone(phone);
                email = Utils.inputEmail();
                b.setEmail(email);
                //add imformation of passenger
                for (int i = 0; i < numberOfPassenger; i++)
                {
                  System.out.println();
                  System.out.println("Enter information of Passenger " + (i + 1) );
                  p = addPassenger( b.getPassenger() );  
                  b.getPassenger().add(p);
                }
                break;
            }
            else 
            {
                System.out.println("Fight number is not available! Enter again!");
            }

        } while (true);
        reservationID = increasingNumber();        
        b.setBookingID(reservationID);
        b.setFlight(f);// them chuyen bay vao booking
        listBooking.add(b);        
        System.out.println("Booking successfully!");
        showBooking(b.getPassenger(), b);
        Utils.checkChange = true;//using to check change to remind user save data
    }
      
    // show booking information
    public void showBooking(ArrayList<Passenger> listPassenger, Booking b){
        //please arrive at the airport 120 minutes in advance
      System.out.println("|===============================================================================|");
        System.out.format("|%-79s|","PREPARED FOR             <<+<+---PHUOCLOC AIRLINES---+>+>>");
        System.out.println("");
        for (Passenger p : listPassenger) {
          System.out.format("|%-79s|",p.getName().toUpperCase());
          System.out.println("");
        }
        System.out.format("|%-16s %-62s|","RESERVATION ID: ",b.getBookingID());  
        System.out.println();
        System.out.println("|BOOKING TIME: "+ Utils.currentDate() +"                                                 |");
        System.out.println("|_______________________________________________________________________________|");        
        System.out.format("|%-15s|%-12s|%-12s|%-18s|%-18s|"
                , " Flight number ","From","To","Departure time", "Arrival time");
        System.out.println();        
        System.out.format("|%-15s|%-12s|%-12s|%-18s|%-18s|"," "+ b.getFlight().getFlightNumber()
                ,b.getFlight().getDepartureLocation(), b.getFlight().getArrivalLocation(),
                b.getFlight().getDepartureTime(),b.getFlight().getArrivalTime());         
        System.out.println();
        System.out.println("|_______________________________________________________________________________|");         
        System.out.format("|%-79s|","CONTACT INFORMATION");
        System.out.println();
        System.out.format("|%-22s|%-28s|%-27s|", "Name", "Phone","Email");
        System.out.println();
        System.out.format("|%-22s|%-28s|%-27s|", b.getName(), b.getPhone(),b.getEmail());        
        System.out.println(); 
        System.out.println("|_______________________________________________________________________________|");        
        System.out.format("|%-79s|","PASSENGER INFORMATION");
        System.out.println();
        System.out.format("|%-19s|%-14s|%-14s|%-18s|%-10s|","Passenger Name","Citizen ID","Nationality", "Seats", "Status");
        System.out.println();
        for (Passenger p : listPassenger) {
           System.out.format("|%-19s|%-14s|%-14s|%-18s|%-10s|",p.getName(),p.getPassengerID(),p.getNationality(),"Check-In Required","Comfirmed"); 
           System.out.println();   
        }      
        System.out.println("|_______________________________________________________________________________|"); 
        System.out.println("|   This reservation code will be canceled if no payment occur within 6 hours   |");
        System.out.println("|            and check-in after the department date 1 hour                      |");        
        System.out.println("|_______________________________________________________________________________|");
        System.out.println();
    }
    
    //
    public void showBoardingPass(Passenger p, Flight f){
        System.out.println(" _____________________________________________________________");
        System.out.println("|                          BOARDING PASS                      |");
        System.out.println("|_____________________________________________________________|");
        System.out.format("|%-22s%-36s|", "  Carrier:               ", "    Flight number:");
        System.out.println();
        System.out.format("|%-22s%-32s|", "  PHUOCLOC AIRLINES          ", f.getFlightNumber());
        System.out.println();
        System.out.format("|%-61s|", "  Name:");
        System.out.println();
        System.out.format("|%-61S|","  "+ p.getName());
        System.out.println();
   //     System.out.println("|___________________________________________________________|");
        System.out.format("|%-29s%-19s%-13s|", "  From:", "To:", "Date:");
        System.out.println();

        System.out.format("|%-29S%-19S%-13S|", "  "+f.getDepartureLocation(), f.getArrivalLocation(), Utils.separateDate(f.getDepartureTime(), 1));
        System.out.println();
        System.out.format("|%-29s%-32s|", "  Departure time:", "Seat:");
        System.out.println();
        System.out.format("|%-29s%-32s|", "  "+Utils.separateDate(f.getDepartureTime(), 2), p.getSeat());
        System.out.println();
    //    System.out.println("|___________________________________________________________|");

        System.out.format("|%-61s|", "  Boarding time:");
        System.out.println();
        System.out.format("|%-61s|", "  "+Utils.boardingTime(Utils.separateDate(f.getDepartureTime(), 2)));
        System.out.println();
        System.out.format("|%-61s|", "  Remark:    Please present at the boarding gate at least ");
        System.out.println();
        System.out.format("|%-61s|", "             30 minutes before departure time");
        System.out.println();
        System.out.println("|_____________________________________________________________|");
        System.out.println();
        System.out.println();      
    }
    // add passenger
    public Passenger addPassenger(ArrayList<Passenger> listP) {
        Passenger p = new Passenger();
        String name;
        String passengerID;
        String nationality;
        p.setName(Utils.inputString("Enter name of passenger: "));
        while (true){
            passengerID = Utils.inputRequiredString("Enter passenger's ID (12 digits): ", "\\d{12}");   
            if ( checkPassengerID(listP, passengerID)==false)
            {
                p.setPassengerID(passengerID);
                break;
            }
            else
            {
                System.out.println("The passenger's ID has existed! Enter again!");
            }
        }
        nationality = Utils.inputString("Enter passenger's nationality: ");
        p.setNationality(nationality);
        p.setSeat("N/A");
        return p;
    }

    //check passenger ID duplicate
    public boolean checkPassengerID(ArrayList<Passenger> listPassenger,String id){
        for (Passenger p : listPassenger) {
            if (p.getPassengerID().equalsIgnoreCase(id))
                return true;
        }
        return false;
    }
    //check booking ID duplicate
    public Booking searchBookingID(String bookingID){
        for (Booking b : listBooking) {
            if ( b.getBookingID().equalsIgnoreCase(bookingID))
                return b;
        }
        return null;
    }
  
    

    public static void printInforPassenger(Passenger p){
        System.out.format("|%-20s|%-12s|%-20s|","Name","Identity ID","Nationality");
        System.out.println();
        System.out.format("|%-20s|%-12s|%-20s|",p.getName(),p.getPassengerID(),p.getNationality());
        System.out.println();
        
    }
    
    
    
    
    
    
// 3. CHECK-IN    

    public void checkInAllocation(){

        String bookingID = Utils.inputRequiredString("Enter the reservation ID (BXXXX) (X is digits): ", "B\\d{4}");
        Booking b = searchBookingID(bookingID);
        if (b != null)
        {
            Flight f = b.getFlight();            
            if (b.isCheckIn())
            {
                System.out.println();
                System.out.println("This reservation ID has already been checked in!");
                System.out.println();

                for (Passenger p : b.getPassenger()) {
                    System.out.println("Boarding pass of passenger " + p.getName());
                    showBoardingPass(p, f);
                }
                return;
                
            }
             // gio check-in be hon hoac bang gio bay hoac neu cung ngay ma gio bay khonhg > gio hien tai 60p
            if (  Utils.compareDate(Utils.currentDate(),f.getDepartureTime())== 1 //hien tai > gio bay
                  || Utils.compareDate(Utils.currentDate(),f.getDepartureTime())== 0// bang
                  ||( Utils.separateDate(Utils.currentDate(), 1).equalsIgnoreCase(Utils.separateDate(f.getDepartureTime(), 1))//ucng ngay
                && !Utils.availableHours(f.getDepartureTime(), Utils.currentDate(), 60) )   )
            {
                System.out.println("Can not check-in because your reservation ID is canceled and  the flight "+ f.getFlightNumber()+" is not valid!");
                return;
            }
            //neu ngay hien tai bang ngay bay va phai chi duoc check in truoc 60 phut
            
            showBooking(b.getPassenger(), b);
            printSeatMap(f.getSeatMap(), f.getRows(), f.getCols(),f.getFlightNumber());
            String location;//cho ngoi
            
            for (int i = 0; i < b.getNumberPassenger(); i++) {
                System.out.println("Select seat carefully because of are not allowed change the seat!");                
                location = Utils.inputSeatLocation("Select the seat for passenger " + b.getPassenger().get(i).getName()+": ", f.getSeatMap(),f.getRows(), f.getCols());
                b.getPassenger().get(i).setSeat(location);
                changeValue(location, f.getSeatMap(), f.getRows(), f.getCols());
                printSeatMap(f.getSeatMap(),f.getRows(), f.getCols(), f.getFlightNumber());            
                System.out.println("Selected seat successfully! "+ b.getPassenger().get(i).getName() +"'s seat is " +b.getPassenger().get(i).getSeat() ); 
                System.out.println();
                
            }
            
            Utils.checkChange = true;
            b.setCheckIn(true);// checked in cap nhat trang thai
            
            //show ticket
            for (Passenger p : b.getPassenger()) {
                System.out.println(" Boarding pass of passenger " + p.getName());
                showBoardingPass(p, f);
            }
        }
        else
        {
            System.out.println("The reservation ID is not exist!");
        }
        
    }
    
    
    //check xem seat co ton tai hay k
    public String searchLocation(String location, String seats[][], int rows, int cols){
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ( location.equalsIgnoreCase(seats[i][j]))
                    return seats[i][j];
            }
        }            
        return null;
    }
    
    public void changeValue(String location, String seats[][], int rows, int cols){
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ( location.equalsIgnoreCase(seats[i][j])){
                    seats[i][j] = "X";                    
                }   
            }
        }            
    }  
    

    
    //4. CREW ASSIGNMENT
    
    public void addCrew(){
        String flightID;
        String pilot;
        String flight_Attendant;
        String ground_Staff;
        System.out.println();
        System.out.println("Enter the information of crew assignments for each flight");
        Flight f = null;
        while (true)
        {
            flightID = Utils.inputRequiredString("Enter the flight number, must be FXXXX (X is digit): ", "F\\d{4}");
            f = searchFlight(flightID, listFlight);

            if ( f != null)
            {
                if ( Utils.compareDate(f.getArrivalTime(), Utils.currentDate())==-1)
                {//may bay da cat canh hoac ko bay
                    System.out.println("The flight has finished already or not exist!");
                    return;
                }
                
                if ( f.getCrew().getPilot().equalsIgnoreCase("N/A"))//chua them thong tin
                {
                    pilot = Utils.inputString("Enter the name of the pilot for flight number "+ flightID +": ");
                    flight_Attendant = Utils.inputString("Enter the name of the flight attendant for flight number "+ flightID +": ");
                    ground_Staff = Utils.inputString("Enter the name of the ground staff for flight number "+ flightID +": ");
                    System.out.println("Add crew assignments successfully!");
                    f.getCrew().setPilot(pilot);
                    f.getCrew().setFlight_Attendant(flight_Attendant);
                    f.getCrew().setGround_Staff(ground_Staff);
                    System.out.println();
                    showCrew(f);
                    System.out.println();
                    System.out.println();

                    Utils.checkChange = true;                    
                }
                
                else
                {
                    System.out.println("The flight had crew assignments!");
                    System.out.println();
                    showCrew(f); 
                    System.out.println();
                    System.out.println();
                }
                break;
            }
            else
            {
                System.out.println("The flight number is not exist! Enter again!");                              
            }

        }

    }

    public void showCrew(Flight f){
        System.out.format("|%-13s|%-17s|%-17s|%-17s|","Flight Number","Pilot", "Flight attendant","Ground staff");
        System.out.println();
        System.out.format("|%-13s|%-17s|%-17s|%-17s|",f.getFlightNumber(),f.getCrew().getPilot(),f.getCrew().getFlight_Attendant(), f.getCrew().getGround_Staff());
        
    }
    
    
    
    //5.

    
    public void showListFlight() {
        Collections.sort(listFlight, new Comparator<Flight>() {
            @Override
            public int compare(Flight o1, Flight o2) {
                return Utils.compareDate(o2.getDepartureTime(), o1.getDepartureTime());
            }

        });

        for (Flight f : listFlight) {
            System.out.println();
            System.out.println("___________________________________________________________________________________________");

            System.out.format("|%-40s%-11s%-38s|", " ", f.getFlightNumber(), " ");
            System.out.println();
            System.out.println("|_________________________________________________________________________________________|");
            System.out.format("|%-15s|%-13s|%-22s|%-19s|%-16s|"
                , "From","To", "Departure time", "Arrival time","Available seats");            
            System.out.println();
            System.out.format("|%-15s|%-13s|%-22s|%-19s|%-16s|",f.getDepartureLocation()
            ,f.getArrivalLocation(),f.getDepartureTime(),f.getArrivalTime(),"   "+f.getAvailableSeat()+"/"+f.getSeat()
            );           
            System.out.println();
            System.out.println("|_________________________________________________________________________________________|");
            System.out.format("|%-89s|", "CREW INFORMATION");
            System.out.println();
            System.out.format("|%-24s|%-19s|%-44s|", "Pilot", "Flight attendant", "Ground staff");
            System.out.println();
            System.out.format("|%-24s|%-19s|%-44s|", f.getCrew().getPilot(), f.getCrew().getFlight_Attendant(), f.getCrew().getGround_Staff());
            System.out.println();
            System.out.println("|_________________________________________________________________________________________|");
            System.out.format("|%-89s|", "PASSENGER INFORMATION");
            System.out.println();
            System.out.format("|%-14s|%-20s|%-19s|%-20s|%-12s|", "Reservation ID","Passenger Name", "Citizen ID", "Nationality", "Seats");
            System.out.println();
            for (Booking b : listBooking) {
                if (b.getFlight() == f) {
                    for (int i = 0; i < b.getPassenger().size(); i++) {
                        System.out.format("|%-14s|%-20s|%-19s|%-20s|%-12s|", b.getBookingID(),b.getPassenger().get(i).getName(),
                        b.getPassenger().get(i).getPassengerID(), b.getPassenger().get(i).getNationality(), b.getPassenger().get(i).getSeat());
                        System.out.println();
                    }
                }
            }
            System.out.println("|_________________________________________________________________________________________|");
            System.out.println();
            System.out.println();
        }
    }        
        
   
   
    
    //6
    
    public void saveToFile(){//String fileName
        try {
            
            if (listFlight.isEmpty()) {
                return;
            }
            FileOutputStream f = new FileOutputStream(fileFlight);//tao buffer de ghi du lieu vao file
            ObjectOutputStream of = new ObjectOutputStream(f);//ghi cac object thanh nhi phan vao file
            of.writeObject(listFlight);
            of.writeObject(listBooking);

            f.close();
            of.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Save successfully!");
        Utils.checkChange = false;

    }   
    
     public void loadFromFileFlights() {

        try {

            FileInputStream f = new FileInputStream(fileFlight);// tao buffer de doc byte tu file ra
            ObjectInputStream of = new ObjectInputStream(f);//de doc du lieu object, su dung f de doc byte convert object
            listFlight= (ArrayList<Flight>) of.readObject();
            listBooking = (ArrayList<Booking>)of.readObject();
            f.close();
            of.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
        
        
        
    public  void closeApp(){
        boolean answer = true;
        while (true){
            if ( Utils.checkChange){
                answer = Utils.comfirmMsg("You haven't saved the data yet, right?"+"\n"+
                                          "Do you want to save the data before closing? (Y/N): ");
                if ( answer == true)
                { // YES->save
                    saveToFile();
                    System.out.println("Good bye! See you later !");                       
                    return;
                }
                System.out.println("Good bye! See you later !");                 
                return;
            }
            System.out.println("Good bye! See you later !");                             
            return;
      
        }
    }
   
}
