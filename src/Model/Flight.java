/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;




/**
 *
 * @author Asus
 */
public class Flight implements Serializable{
    private String flightNumber;
    private String departureLocation;
    private String arrivalLocation;
    private String departureTime;
    private String arrivalTime;
    private int seat;
    private int availableSeat;// ban dau cho bang nhau
    
    //Map
    private int cols = 7;
    private int rows;
    private String seatMap[][];     
    private Crew crew;
    public Flight() {
    }

    public Flight(String flightNumber, String departureLocation, String arrivalLocation, String departureTime, String arrivalTime, int seat) {
        this.flightNumber = flightNumber;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seat = seat;
        this.availableSeat = seat;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }
    

    public int getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(int availableSeat) {
        this.availableSeat = availableSeat;
    }

    public String[][] getSeatMap() {
        return seatMap;
    }

    public void setSeatMap(String[][] seatMap) {
        this.seatMap = seatMap;
    }


    public Crew getCrew() {
        return crew;
    }

    public void setCrew(Crew crew) {
        this.crew = crew;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }



    
    
    @Override
    public String toString() {
        return "Flight Number: " + flightNumber + ", From: " + departureLocation + ", To: " + arrivalLocation + ", departureTime: " + departureTime + ", arrivalTime: " + arrivalTime + ", available Seat: " + availableSeat + '}';
    }

      
    
}
