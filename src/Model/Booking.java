/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Asus
 */
public class Booking implements Serializable{
    private String bookingID;//tu dong
    private Flight flight;
    private ArrayList<Passenger> passenger = new ArrayList<>();
    private int numberPassenger;
    //check-in true or false;
    private boolean checkIn;
    
    //contact information , nguoi dung ra dat
    private String name;
    private String phone;
    private String email;
    
    
    public Booking() {
    }

    public Booking(String bookingID, Flight flight, int number) {
        this.bookingID = bookingID;
        this.flight = flight;
  //      this.numberPassenger = numberPassenger;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public ArrayList<Passenger> getPassenger() {
        return passenger;
    }

    public void setPassenger(ArrayList<Passenger> passenger) {
        this.passenger = passenger;
    }

    public int getNumberPassenger() {
        return numberPassenger;
    }

    public void setNumberPassenger(int numberPassenger) {
        this.numberPassenger = numberPassenger;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    public boolean isCheckIn() {
        return checkIn;
    }

    
    
    
    
    @Override
    public String toString() {
        return "Reservation code: " + bookingID + ", flight code: " + flight + ", passenger=" + passenger + '}';
    }

    
}
