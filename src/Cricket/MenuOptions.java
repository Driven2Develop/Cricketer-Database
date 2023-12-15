package src.Cricket;

import java.util.ArrayList;
import java.util.Scanner;

/*
 * Assignment 1
 * Iymen Abdella
 * Written by: Iymen Abdella 40218280
 * This class captures the main logical components of each menu option. 
 * Each option is captured in a public method, with some private methods for sensitive operations like password management
 * Private Helper methods are also included for repetitive operations such as reliably getting user input 
 */

public class MenuOptions {
    private ArrayList<Cricketer> cricketer_db;
    private final String password = "password";
    private String IncorrectPasswordmsg = "Incorrect Password please try again."; 
    private String consecutivePasswordmsg = "Too many consecutive password failures, returning to main menu.";
    private int passwordFailures = 0; //counts the number of consecutive failed password attempts. 
    private int max_passwordFailures = 4;
    private Scanner scan = new Scanner(System.in);

    public MenuOptions(ArrayList<Cricketer> Cricketer_db){
        this.cricketer_db = Cricketer_db;
    }

    public ArrayList<Cricketer> getCricketer_db(){ return cricketer_db; }
    public void setCricketer_db(ArrayList<Cricketer> Cricketer_db){ this.cricketer_db = Cricketer_db; }

    public int getPasswordFailures(){ return passwordFailures; }
    public int getmax_PasswordFailures(){ return max_passwordFailures; }

    public void closeScanner(){ scan.close(); } //cleanup instance var

    //option 1
    public void CreateNewCricketer() {

        if (!passwordAuthenticator()){
            System.out.println(consecutivePasswordmsg); // returning to main menu
            return;
        }

        System.out.println("How many Cricketers would you like to enter into database?");
        int no_cricketers = getIntegerFromUser();

        System.out.println("Provide the cricketer data you would like to add to the database. Only one player data per line.\nThe data must be comma separated, in the form: Cricketer Name, Batting Average, Bowling Average, Strike Rate, Economy Rate.");
        for (int i=0; i<no_cricketers; i++){
            String[] playerData = scan.nextLine().trim().split(",");
            try {

                cricketer_db.add(new Cricketer(playerData[0].trim(), Float.parseFloat(playerData[1].trim()), Float.parseFloat(playerData[2].trim()), Float.parseFloat(playerData[3].trim()), Float.parseFloat(playerData[4].trim()), true));
            
            } catch (Exception e) { //catch any bad formatting or type mismatch errors

                System.out.println(String.format("Could not add player data with name %s to database. %s ", playerData[0], e.getMessage()));
            
            }
            System.out.println(String.format("New player successfully added with information:\n %s", cricketer_db.get(cricketer_db.size()-1).toString()));
        }
    }

    //option 2
    public void ChangeCricketerInfo() {

        if (!passwordAuthenticator()){
            System.out.println(consecutivePasswordmsg); // return to main menu
            return;
        }

        String menutext = "What information would you like to change?\n" +
        "1. Name.\n" +
        "2. Batting Average\n" +
        "3. Bowling Average \n" +
        "4. Strike Rate \n" + 
        "5. Economy\n" +
        "6. Availability \n" + 
        "7. Quit\n" +
        "Please enter your choice >";
        
        long input_ID;
        int to_modify= -1;
        
        // ID check loop
        while (true){
            System.out.println("Provide the ID of the Cricketer you would like to update");
            input_ID = getLongFromUser();

            //get index of Cricketer to modify from database.
            for(int i =0; i< cricketer_db.size(); i++){
                if (cricketer_db.get(i).getCricketerID() == input_ID){
                    to_modify = i;
                    break;
                }
            }
            
            // ID not in database
            String response;
            if (cricketer_db.get(to_modify).getCricketerID() != input_ID){ //TODO WHY ALWAYS TRUE??
                System.out.println(String.format("No cricketer with ID %d has been found in the Database.\nWould you like to search for a new ID? (yes)\nOr go back to the main menu? (quit)", input_ID));
                response = scan.nextLine().trim().toLowerCase();
            }else{
                break;
            }
            while(true){
                if (response == "yes" || response.startsWith("y")){ // repeat loop
                    break;
                }else if (response == "quit" || response.startsWith("q")){
                    return;
                }else{
                    System.out.println("Invalid Entry."); //prompt user again
                    response = scan.nextLine().trim().toLowerCase();
                }
            }
        }

        // show update menu
        while(true){
            System.out.println("Current state of selected Cricketer:\n" + cricketer_db.get(to_modify).toString());
            System.out.println (menutext);

            int choice;
            while (true) {
                choice = getIntegerFromUser();
                if (choice > 7){
                    System.out.println("Invalid option. Provide a positive integer value between 1 and 7.");
                }else{
                    break;
                }
            }

            if (choice == 1){

                System.out.println("Provide a new name to change: ");
                cricketer_db.get(to_modify).setCricketerName(scan.nextLine().trim());
                System.out.println("Name successfully changed!");

            }else if(choice ==2){ 

                System.out.println("Provide a new Batting Average to change: ");
                cricketer_db.get(to_modify).setBattingAvg(getFloatFromUser());
                System.out.println("Batting Average successfully changed!");
            
            }else if(choice == 3){
                
                System.out.println("Provide a new Bowling Average to change: ");
                cricketer_db.get(to_modify).setBowlingAvg(getFloatFromUser());
                System.out.println("Bowling Average successfully changed!");

            }else if(choice == 4){

                System.out.println("Provide a new Strike Rate to change: ");
                cricketer_db.get(to_modify).setStrikeRate(getFloatFromUser());
                System.out.println("Strike Rate successfully changed!");

            }else if(choice == 5){
                
                System.out.println("Provide a new Economy Rate to change: ");
                cricketer_db.get(to_modify).setEconomyRate(getFloatFromUser());
                System.out.println("Economy Rate successfully changed!");

            }else if(choice == 6){

                cricketer_db.get(to_modify).setIsAvailable(!cricketer_db.get(to_modify).getIsAvailable());
                System.out.println("Availability successfully changed!");

            }else{
                System.out.println("Exiting Update menu.");
                break;
            }
        }
    }
    
    // option 3
    public void DisplayCricketers(){

        System.out.println("Provide a maximum bowling average to query:");

        float max_bowlingAvg = getFloatFromUser();
        ArrayList<Cricketer> to_display = new ArrayList<>(cricketer_db.size()); 

        //query db 
        for (Cricketer c: cricketer_db){
            if (c.getBowlingAvg() < max_bowlingAvg)
            to_display.add(c);
        }
        System.out.println(String.format("Here are the Cricketers with bowling average less than %f", max_bowlingAvg ));
        for (Cricketer c: to_display){
            System.out.println(c.toString());
        }
    }
    
    //option 4
    public void DisplayCricketersAllrounders() {
        
        System.out.println("Provide a strike rate to query:");
        float min_strikeRate = getFloatFromUser();

        System.out.println("Provide an economy rate to query:");
        float max_economyRate = getFloatFromUser();
        
        //query db 
        ArrayList<Cricketer> to_display = new ArrayList<>(cricketer_db.size());
        for (Cricketer c: cricketer_db){
            if (c.getStrikeRate() > min_strikeRate && c.getEconomyRate() < max_economyRate)
            to_display.add(c);
        }

        System.out.println(String.format("Here are the Cricketers with strike rate greater than %f, and economy rate less than %f", min_strikeRate, max_economyRate));
        for (Cricketer c: to_display){
            System.out.println(c.toString());
        }
    }
    
    /*
     * private helper method to manage the password authentication. 
     * Captures any security considerations. 
     * Helps to track the number of incorrect password attempts. 
     */
    private Boolean passwordAuthenticator(){
        Boolean flag = false;
        int max_attempts = 3;
        int attempts = 0;
        String pwd;

        System.out.println("Please enter your password:");
        while (attempts < max_attempts){
            pwd = scan.nextLine();

            if (pwd.equals(password)){
                flag = true;
                passwordFailures = 0;
                break;
            }else{
                System.out.println(IncorrectPasswordmsg);
                attempts ++;
            }
        }

        if (attempts >= max_attempts ){ 
            passwordFailures ++;
        }
        if (passwordFailures >= max_passwordFailures){
            System.out.println("Program detected suspicious activities and will terminate immediately!");
        }
        return flag;
    }
    
    /*
     * helper methods for getting exactly one float/int/long value from user. 
     * Will continue to prompt user if input is incorrect. 
     */
    private int getIntegerFromUser(){
        int to_return;
        while (true){
            String errorMsg = "Invalid choice. Provide a positive integer value.";
            
            if (scan.hasNextInt()){
                to_return = scan.nextInt();
                
                if (0 < to_return){
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

    private long getLongFromUser(){
        long to_return;
        while (true){
            String errorMsg = "Invalid choice. Provide a positive Long value.";
            
            if (scan.hasNextLong()){
                to_return = scan.nextLong();
                if (0 < to_return){
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
 
    private float getFloatFromUser(){
        float to_return;
        while (true){
            String errorMsg = "Invalid choice. Provide a positive float value.";
            
            if (scan.hasNextFloat()){
                to_return = scan.nextFloat();
                if (0 < to_return){
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

