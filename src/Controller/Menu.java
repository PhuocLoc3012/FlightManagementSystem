/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import MyUtil.Utils;

/**
 *
 * @author Asus
 */
public class Menu {
    
    public void bigMenu(){
        System.out.println(" _______________________________________________________________________");
        System.out.println("|                    <<+<+---PHUOCLOC AIRLINES---+>+>>                  |");
        System.out.println("|_______________________________________________________________________|");        
        System.out.println("|    1. Flight schedule management                                      |");
        System.out.println("|    2. Passenger reservation and booking                               |");
        System.out.println("|    3. Passenger check-in and seat allocation                          |");
        System.out.println("|    4. Crew management and assignments                                 |");
        System.out.println("|    5. Print all lists from file                                       |");
        System.out.println("|    6. Data storage for flight details, reservations, and assignments  |");
        System.out.println("|    7. Quit                                                            |");
        System.out.println("|_______________________________________________________________________|");

    }
    public int getChoice(){
        return Integer.valueOf(Utils.getChoice("Enter your choice (1-7): "));
    }
    
}
