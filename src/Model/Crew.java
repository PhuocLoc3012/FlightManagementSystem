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
public class Crew implements Serializable{
    private String pilot;
    private String flight_Attendant;
    private String ground_Staff;
  //  private boolean add;
    
    public Crew() {
    }

    public Crew(String pilot, String flight_Attendant, String ground_Staff) {
 //       this.flightNumber = flightNumber;
        this.pilot = pilot;
        this.flight_Attendant = flight_Attendant;
        this.ground_Staff = ground_Staff;
    }

    public String getPilot() {
        return pilot;
    }

    public void setPilot(String pilot) {
        this.pilot = pilot;
    }

    public String getFlight_Attendant() {
        return flight_Attendant;
    }

    public void setFlight_Attendant(String flight_Attendant) {
        this.flight_Attendant = flight_Attendant;
    }

    public String getGround_Staff() {
        return ground_Staff;
    }

    public void setGround_Staff(String ground_Staff) {
        this.ground_Staff = ground_Staff;
    }

    
}
