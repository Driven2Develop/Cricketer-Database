package src.Cricket;

import java.util.ArrayList;
import java.util.Scanner;

/*
 * Assignment 1
 * Iymen Abdella
 * Written by: Iymen Abdella 40218280
 * Responsible for program initialization and getting preliminary information from user. 
 * Also responsible for driving the program and controlling the flow of the main menu.
*/

public class driver {    
    private static String mainMenutext = "What do you want to do?\n" +
            "1. Enter new Cricketers (password required).\n" +
            "2. Change information of a Cricketer (password required).\n" +
            "3. Display available Cricketers with a bowlingAvg greater than user value. \n" +
            "4. Display all Cricketers that can play as an AllRounder. \n" + 
            "5. Quit\n" +
            "Please enter your choice >";
    
    public static void main(String[] args){
        System.out.println("Welcome to the Cricketer management database.\nTo start, provide the maximum number of cricketers to be managed, then press Enter.");
        
        Scanner scan = new Scanner(System.in);
        int maxCricketers=0; //max must be positive
        while(true){
            String errorMsg = "Invalid choice. The size of the database must be a positive integer.";
            
            if (scan.hasNextInt()){
                maxCricketers = scan.nextInt();
                
                if (0 < maxCricketers){
                    scan.nextLine();
                    break;
                }else{
                    System.out.println(errorMsg);
                }
            }else{
                System.out.println(errorMsg);
            }
            scan.nextLine();
        }
        
        //intializes an instance of MenuOptions for controlling the flow of menus and their display. 
        MenuOptions menuOptions = new MenuOptions(new ArrayList<Cricketer>(maxCricketers));
        Boolean exit = true;
        
        /*
         * main menu loop which interprets user choice, then displays appropriate menu.  
         * exits if max consecutive password failures has been reached, or option 5 is selected.
         */
        int choice=0;
        while (exit){ 
            
            if (menuOptions.getPasswordFailures() >= menuOptions.getmax_PasswordFailures() && choice == 1){
                exit = false;
                menuOptions.closeScanner();
                break;
            }
            
            choice = promptMainMenu(scan); 

            if (choice == 1){
                menuOptions.CreateNewCricketer();
            }else if (choice == 2){
                menuOptions.ChangeCricketerInfo();
            }else if (choice == 3){
                menuOptions.DisplayCricketers();
            }else if (choice == 4){
                menuOptions.DisplayCricketersAllrounders();
            }else if (choice == 5 || menuOptions.getPasswordFailures() >= menuOptions.getmax_PasswordFailures()){
                exit = false;
                menuOptions.closeScanner();
                break;
            }
        }
    System.out.println("Exiting the Cricketer management database.");
    scan.close();
    }

    /*
     * helper method to display main menu, with list of options.
     * returns user choice as an integer between 1 and 5.
     */
    public static int promptMainMenu(Scanner scan){
        int to_return;
        System.out.println(mainMenutext);
        while (true){
            String errorMsg = "Invalid choice. Provide a positive integer value between 1 and 5.";
            
            if (scan.hasNextInt()){
                to_return = scan.nextInt();
                
                if (0 < to_return && to_return < 6){
                    scan.nextLine();
                    break;
                }else{
                    System.out.println(errorMsg);
                }
            }else{
                System.out.println(errorMsg);
            }
            scan.nextLine();
        }
        return to_return;
    }

}
