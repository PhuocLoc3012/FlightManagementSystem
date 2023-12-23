/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.FlightManagement;
import Controller.Menu;




/**
 *
 * @author Asus
 */
public class Main {
    public static void main(String[] args){
        Menu m = new Menu();
        FlightManagement f = new FlightManagement();
        f.loadFromFileFlights();
        int choice= 0;
        do
        {
            m.bigMenu();
            choice = m.getChoice();
            switch(choice){
                case 1:
                    f.addFlight();
                    break;
                case 2:   
                    f.reservateFlight();
                    break;
                case 3:
                    f.checkInAllocation();
                    break;
                case 4:
                    f.addCrew();
                    break;
                case 5:
                    f.showListFlight();
                    break;
                case 6:
                    f.saveToFile();                    
                    break;
                case 7:
                    f.closeApp();
                        return;
            }
        }while ( true);
      
        
    }
}
