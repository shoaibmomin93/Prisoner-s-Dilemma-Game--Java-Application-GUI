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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// This class sets up the Graphical User Interface for the Prisonner's Dilemma Game
// The user input is received using GUI components and sent to other classes for
// processing of the data. The result is displayed in GUI components as well.
public class PDGameGUI extends JFrame implements ActionListener, ListSelectionListener {
    
    
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> finishedGamesList;
    private final JTextField computerStrategyTF = new JTextField(10);
    private final JLabel computerStrategyL = new JLabel("Computer Strategy-Combo Box");
    private final JButton startB = new JButton("Start New Game");
    private final JComboBox<Object> computerStrategyCB;
    private int computerStrategy = 1;
    private final JLabel decisionL = new JLabel("Your decision this round?");
    private final JTextArea gameResultsTA = new JTextArea(15, 30);
    private PDGame currentPDGame = null;
    private String gameStartTime = null;
    private final HashMap<String, GameStat> stats = new HashMap<>();
    private final JLabel label1 = new JLabel("Rounds Played");
    private final JTextField roundsTF = new JTextField(10);
    private final JLabel label2 = new JLabel("Computer Strategy");
    private final JLabel label3 = new JLabel("Player Sentence");
    private final JTextField playerSentenceTF = new JTextField(10);
    private final JLabel label4 = new JLabel("Computer Sentence");
    private final JTextField computerSentenceTF = new JTextField(10);
    private final JLabel label5 = new JLabel("Winner");
    private final JTextField winnerTF = new JTextField(10);
    private final JButton silentB = new JButton("Remain Silent");
    private final JButton betrayB = new JButton("Testify");
    private final int GAME_LIMIT = 5;

    // make the required Declarations that will be used in this class. 
    public static void main(String[] args) {
        // call this method to set up the GUI of the game .
        createAndShowGUI();
    }

    //  This method will create an object that will set up the GUI required. 
    // Also set up Listeners for various compnents .
    public static void createAndShowGUI() {
        // Create and set up the window.
        PDGameGUI pdg1 = new PDGameGUI(); //call constructor below to set the window to user
        pdg1.addListeners();     //method will add listeners to buttons
        pdg1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Display the window and pack together all the panels, etc
        pdg1.pack();
        pdg1.setVisible(true);
    }

    // This is the constructor where the entire GUI is set up.
    public PDGameGUI() {
        super("GAME - Prisoner's Dilemma ");
        currentPDGame = new PDGame("f1.txt"); // The file is required when Read from Input File strategy is chosen
        Color c1 = new Color(211, 200, 175);  //higher numbers means lighter colors
        Color c2 = new Color(100, 200, 200);
        // setSize(800, 800);
        //setPreferredSize(600,800);
        
        super.setLayout(new BorderLayout());
        // Set up left  panel
        JPanel panel1 = new JPanel(new BorderLayout());
        // "JList of Games holding a List Model"
        super.add(panel1, BorderLayout.WEST); //add to frame
        // Set up top left "list"      
        finishedGamesList = new JList<>(listModel);
        finishedGamesList.setFont(new Font("SansSerif", Font.BOLD, 24));
        finishedGamesList.setVisibleRowCount(10);
        finishedGamesList.setFixedCellWidth(350);
        finishedGamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel1.add(new JScrollPane(finishedGamesList), BorderLayout.NORTH);
        panel1.setBackground(c2);
        //add the list to the panel in a scroll pane and also set the back ground color .
        // set up panel for Bottom Left, that displays the result of games
        JPanel panel2 = new JPanel();    // this panel is to display the results of game. Should be SOuth of panel 1
        // add the required lables and textfields to the Panel
        panel2.setLayout(new GridLayout(5, 2, 5, 5));
        panel2.add(label1);
        panel2.add(roundsTF);
        roundsTF.setEditable(false);  // this is set as false to prevent user from entering data here
        panel2.add(label2);
        panel2.add(computerStrategyTF);
        computerStrategyTF.setEditable(false);
        panel2.add(label3);
        panel2.add(playerSentenceTF);
        playerSentenceTF.setEditable(false);
        panel2.add(label4);
        panel2.add(computerSentenceTF);
        computerSentenceTF.setEditable(false);
        panel2.add(label5);
        panel2.add(winnerTF);
        winnerTF.setEditable(false);
        panel2.setBackground(c2);
        // add this panel to the left panel
        panel1.add(panel2, BorderLayout.SOUTH); // add panel 2 to the south of panel 1.
        // This is used to set up the title border for panel1
        TitledBorder title;
        title = BorderFactory.createTitledBorder("List of Games");
        panel1.setBorder(title);
        // set up panel 3 for the right side
        JPanel panel3 = new JPanel(new BorderLayout());
        super.add(panel3, BorderLayout.EAST); //add to frame
        // set up panel 4 to get strategy from the user and to start a game, and to select user decision
        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout(2, 1));
        // set up panel 5 that goes on panel 4 to select computer strategy and start game
        JPanel panel5 = new JPanel(new FlowLayout());
        panel5.add(computerStrategyL);
        // the combo box is set up to hold the strategies the user wishes to choose 
        Object[] strategyArray = currentPDGame.getStrategies().toArray(); //convert AL to array
        computerStrategyCB = new JComboBox<>(strategyArray);
        computerStrategyCB.setEditable(false);
        computerStrategyCB.setSelectedIndex(0); // set the default value for combo box
        // add the combo box and button
        panel5.add(computerStrategyCB);
        panel5.add(startB);    // set up the background
        panel5.setBackground(c1);
        // panel6 is used to set up buttons used to take in user decisions
        JPanel panel6 = new JPanel(new FlowLayout());
        panel6.add(decisionL);
        panel6.setBackground(c1);
        panel6.add(silentB);
        panel6.add(betrayB);
        // add panel 5 and panel6 to panel4
        panel4.add(panel5);
        panel4.add(panel6);
        panel3.add(panel4, BorderLayout.NORTH);
        // add a text area the bottom right which gives the details of the game as it progresses
        panel3.add(new JScrollPane(gameResultsTA), BorderLayout.SOUTH);
        gameResultsTA.setEditable(false);
        betrayB.setEnabled(false);
        silentB.setEnabled(false);  // at the end of the game, user should only be able to press new game button
        startB.setEnabled(true);
    } //end constructor
    // This method sets up Listeners for various components involved - button, list, comboBox

    public void addListeners() {
        startB.addActionListener(this);
        silentB.addActionListener(this);
        betrayB.addActionListener(this);
        computerStrategyCB.addActionListener(this);
        finishedGamesList.addListSelectionListener(this);

    }

    // Implement the interface.
    // Set up an action associated with every event that occcurs.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startB) {
            startGame();  // start a new game
        } else if (e.getSource() == silentB) {
            cooperate();  // cooperate is the decision of the user
        } else if (e.getSource() == betrayB) {
            betray();  // betray is the decision of the user
        } else if (e.getSource() == computerStrategyCB) {
            computerStrategy = computerStrategyCB.getSelectedIndex() + 1; // get the appropriate strategy
        }
    }

    // This method is to get the item from the list and perform certain action
    @Override
    public void valueChanged(ListSelectionEvent e) {
        String searchKey;
        // If the list is not empty, get the value of the key, and display associated statistics from GameStat
        if (!finishedGamesList.isSelectionEmpty()) {
            searchKey = (String) finishedGamesList.getSelectedValue(); //get out time of game and look up in hash map
            GameStat gameStatsInfo;
            gameStatsInfo = stats.get(searchKey);
            roundsTF.setText(Integer.toString(gameStatsInfo.getRounds()));
            //roundsTF.setFont( new Font("SansSerif", Font.BOLD, 24));
            int playerSentenceYrs = gameStatsInfo.getUserSentence();
            //  playerSentenceTF.setFont( new Font("SansSerif", Font.BOLD, 24));
            playerSentenceTF.setText(String.format("%d %s", playerSentenceYrs,
                    ((playerSentenceYrs > 1) ? " years" : " year")));
            int compSentenceYrs = gameStatsInfo.getCompSentence();
            //computerSentenceTF.setFont( new Font("SansSerif", Font.BOLD, 24));
            computerSentenceTF.setText(String.format("%d %s", compSentenceYrs,
                    ((compSentenceYrs > 1) ? " years" : " year")));
            String compStrategy = gameStatsInfo.getCompStrategy();
            // computerStrategyTF.setFont( new Font("SansSerif", Font.BOLD, 24));
            computerStrategyTF.setText(String.format("%s", compStrategy));
            String win = gameStatsInfo.getWinner();
            winnerTF.setText(String.format("%s", win));
        }
    }

    // This method is executed when new game button is pressed.
    // create a new PDGame object , get the current date, and start the game
    public void startGame() {
        currentPDGame = new PDGame("f1.txt");
        currentPDGame.setStrategy(computerStrategy);
        gameStartTime = (new Date()).toString(); // this is used as a key in the hashmap
        gameResultsTA.append("***Prisoner's Dilemma***\n");
        silentB.setEnabled(true);
        betrayB.setEnabled(true); // initially, the user must not be able to press  the New Game button.
        startB.setEnabled(false);
        promptPlayer();   // This prompts the user for his decision.
    }

    // This method is executed when the user chooses to Cooperate
    public void cooperate() {
        String roundResult = currentPDGame.playRound(1); // returns the result of each round
        gameResultsTA.append(roundResult + "\n");
        if (currentPDGame.getStats().getRounds() >= GAME_LIMIT) { // if the rounds is equal to 5, end the game
            endGame();
        } else {
            promptPlayer();                // or prompt the player for decision of next round
        }
    }

    // This method is executed when the user chooses to Betray
    public void betray() {
        String roundResult = currentPDGame.playRound(2); // returns the result of each round
        gameResultsTA.append(roundResult + "\n");
        if (currentPDGame.getStats().getRounds() >= GAME_LIMIT) {  // if the rounds is equal to 5, end the game
            endGame();
        } else {
            promptPlayer();                  // else prompt the player for the decision of next round
        }
    }

    // This method prompts the player for his decision
    private void promptPlayer() {
        String promptString = "\n1. Cooperate and remain silent." + "\n" + "2. Betray and testify against." + "\n\n" + "What is your decision this round?\n\n";
        gameResultsTA.append(promptString); // append the string to the TextArea
    }

    // This method is called when the number of rounds becomes 5
    // The List is updated with an entry of the statistics
    private void endGame() {
        String endScores = currentPDGame.getScores();
        gameResultsTA.append(endScores + "\n");  // display scores on the text area
        stats.put(gameStartTime, currentPDGame.getStats());  // update the hashmap
        listModel.addElement(gameStartTime);  // update the list
        betrayB.setEnabled(false);
        silentB.setEnabled(false);  // at the end of the game, user should only be able to press new game button
        startB.setEnabled(true);
    }
}  // end of class
