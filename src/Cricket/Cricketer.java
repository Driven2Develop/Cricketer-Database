package src.Cricket;

/*
 * Assignment 1
 * Iymen Abdella
 * Written by: Iymen Abdella 40218280
 * Plain old Java Cricketer class
 */

class Cricketer{
    private long cricketerID;
    private String cricketerName;
    private float battingAvg;
    private float bowlingAvg;
    private float strikeRate;
    private float economyRate;
    private boolean isAvailable;

    private static int playerCount = 0;

    /*
     * Main constructor of the Cricketer class. 
     * Cricketer ID is automatically generated during creation, and isAvailable defaults to true. 
     */
    public Cricketer(String cricketerName, float battingAvg, float bowlingAvg, float strikeRate, float economyRate, Boolean isAvailable){
        this.cricketerID = playerCount + 1;
        this.cricketerName = cricketerName;
        this.battingAvg = battingAvg;
        this.bowlingAvg = bowlingAvg;
        this.strikeRate = strikeRate;
        this.economyRate = economyRate;
        this.isAvailable = isAvailable;

        playerCount ++;
    }
    
    public long getCricketerID(){ return cricketerID;}
    
    public String getCricketerName(){ return cricketerName;}
    public void setCricketerName(String cricketerName){ this.cricketerName = cricketerName; }

    public float getBattingAvg(){ return battingAvg; }
    public void setBattingAvg(float battingAvg){ this.battingAvg = battingAvg; }

    public float getBowlingAvg(){ return bowlingAvg; }
    public void setBowlingAvg(float bowlingAvg){ this.bowlingAvg = bowlingAvg; }

    public float getStrikeRate(){ return strikeRate; }
    public void setStrikeRate(float strikeRate){ this.strikeRate = strikeRate; }

    public float getEconomyRate(){ return economyRate; }
    public void setEconomyRate(float economyRate){ this.economyRate = economyRate; }

    public boolean getIsAvailable(){ return isAvailable; }
    public void setIsAvailable(boolean isAvailable){ this.isAvailable = isAvailable; }

    /*
     * Returns a multi-line string representation of a Cricketer object in the form
     * Cricketer ID: 
     * Cricketer Name: 
     * Batting Average:
     * Bowling Average:
     * Strike Rate:
     * Economy Rate:
     * Available?: true
     */
    public String toString(){
        return String.format(
            "Cricketer ID: %s%n Cricketer Name: %s%n Batting Average: %s%n Bowling Average: %s%n Strike Rate: %s%n Economy Rate: %s%n Available?: %s%n ",
            this.getCricketerID(), this.getCricketerName(), this.getBattingAvg(), this.getBowlingAvg(), this.getStrikeRate(), this.getEconomyRate(), this.getIsAvailable());
    }

    public int getPlayerCount(){ return playerCount; }

    public boolean equals(Cricketer cricketer){
        Boolean flag = false;
        
        if (this.getIsAvailable() && cricketer.getIsAvailable()){
            if (this.getCricketerID() == cricketer.getCricketerID()){
                flag = true;
            }
        }
        return flag;
    }
}