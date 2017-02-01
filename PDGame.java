/**
 * *********************************************************
  * Progammer: MOMIN,SHOAIB (
 * 
 * Date Created: October 21, 2016
 *
 * Purpose: This is a Prisoners Dilemma game (GUI). The user and the computer can either remain silent
 * or betray each other. 
 ***********************************************************
 */
package pdgamegui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// This class represents the game itself and controls the logic for it. 
// It also stores the history of user moves .
// The strategies of Computer are stored in the ArrayList
public class PDGame {
     private ArrayList<Integer> userHistoryAL = new ArrayList<>();
    private ArrayList<String> computerStrategiesAL = new ArrayList<>();
    private GameStat stats = new GameStat();
    private int strategy;   // declare the required variables 
    private Scanner sc;

    // constructor , adds strategies to the arrayList. Also open the input file
    // that may be used later.
    public PDGame(String file) {
        computerStrategiesAL.add("Read From Input File .");
        computerStrategiesAL.add("Random Choice by Computer .");  // fill in the strategies in arraylist
        computerStrategiesAL.add("Tit-For-Tat .");
        try // check if the file exists
        {
            File f1 = new File(file);    // open the file
            sc = new Scanner(f1);  // point scanner to the beginning of the file
        } catch (FileNotFoundException e) // if it does not exist
        {
            System.out.println("File Not Found"); // display text and exit the program
            System.exit(0);
        }
    }  // end of constructor
    // This method runs for every round. 
    //Method that generates the computer's decision based on the strategy selected, 
    //determine the outcome based on the two decisions, 
    //and update the GameStat object according to the scoring above. 

    public String playRound(int decision) {
        int user, comp;  // variables that store the current prison sentence for user and computer
        int computerDecision = figureComputerDecision(); // computer decision is computed by another private method
        userHistoryAL.add(decision);  // store the users decision in the userHistory arrayList
        if (decision == 1 && computerDecision == 1) {  // if both remain silent
            user = 2;
            comp = 2;  // both if them get 2yr
            stats.update(user, comp);  // update the GameStat object
            return "You and your partner remain silent. \n" + "You both get 2 years in prison.";  // return the result
        } else if (decision == 2 && computerDecision == 1) {
            user = 1;  // if user testifies and computer remains silent
            comp = 5;//user gets 1 yr and computer gets 5 yrs
            stats.update(user, comp);
            return "You testify against your partner and they remain silent.\n"
                    + "You get 1 year in prison and they get 5.";
        } else if (decision == 1 && computerDecision == 2) {
            user = 5; // if user remains silent and computer testifies
            comp = 1;// user gets 5 yrs and computer gets 1yr
            stats.update(user, comp);
            return "You remain silent and your partner testifies against you \n" + "You get 5 years in prison and they get 1";
        } else {   // if both of them testify against each other
            user = 3;
            comp = 3;  // both get 3 yr sentencing
            stats.update(user, comp);
            return "You both testify against each other \n" + "You both get 3 years in prison";
        }
    } // end of roundPlay
// This method computes Computer Decision for a particular round of game.

    private int figureComputerDecision() {
        if (strategy == 1) {  // if the input is to be read from a file
            return sc.nextInt();  // return number from file.
        } else if (strategy == 2) {  // if a random number is to be generated
            return (int) (Math.random() * 2 + 1);  // generate either 1 or 2

        } else if (userHistoryAL.isEmpty()) { // in Tit for Tat, first return 1
            return 1;
        } else {  // then return the previous value of the user
            int prevDecision = userHistoryAL.get(userHistoryAL.size() - 1);
            return prevDecision;
        }
    }   // end figureComputerDecision
//getters are used to access private members of the class

    public ArrayList<String> getStrategies() {
        return computerStrategiesAL;
    }

    public String getScores() {
        return "\nYour prison sentence is " + stats.getUserSentence() + "\n" + "Your partners prison sentence is " + stats.getCompSentence()+"\n";
    }

    public GameStat getStats() {
        return stats;
    }
// setters are used to set private members of a class

    public void setStrategy(int strategy) {
        this.strategy = strategy;
        // call the GameStat setter to set stratergy in its object 
        stats.setCompStrategy(computerStrategiesAL.get(strategy - 1));
    }

} // end of class
   

