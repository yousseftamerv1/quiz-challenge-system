//###########################################################################
//#   File: LeaderboardPanel.java                                           #
//#                                                                         #
//#   Date: 9/5/2026                                                        #
//#                                                                         #
//#   Description: A GUI panel responsible for displaying the sorted        #
//#                leaderboard of students based on their total scores.     #
//#                It provides a back button to return to the dashboard.    #
//#                                                                         #
//###########################################################################

import java.awt.*; //import the abstract window toolkit for UI layouts and colors
import javax.swing.*; //import swing library for gui components like JPanal, JButton etc.. 
import javax.swing.border.EmptyBorder; //import the empty border to create padding and margins around the GUI

//creating the class that inherts from JPanal
public class LeaderboardPanel extends JPanel {
    //class constructor
    public LeaderboardPanel(MainFrame parent, QuizEngine engine, User student) { //takes object from Mainframe for navigation, QuizEngine for displaying data and User
        //setting borderlayout 10 x 10
        setLayout(new BorderLayout(10, 10));
        //setting the background color as white
        setBackground(Color.WHITE);
        //adding padding 
        setBorder(new EmptyBorder(30, 40, 30, 40));
        //setting the titile
        JLabel title = new JLabel("Top Scores", SwingConstants.CENTER);
        //setting the font
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        //adding the title label in the top section
        add(title, BorderLayout.NORTH);

        //adding a text area to add the data(user's scores)
        JTextArea textArea = new JTextArea(engine.getLeaderboardData());
        //making the area can't be editable by the user
        textArea.setEditable(false);
        //setting the textarea font
        textArea.setFont(new Font("Monospaced", Font.BOLD, 18));
        //setting the textarea borders
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        //adding a scrollpane to the text area
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        //adding a button
        JButton backBtn = new JButton("Back to Dashboard");
        //setting the button background
        backBtn.setBackground(new Color(0, 122, 255));
        //setting the button foreground
        backBtn.setForeground(Color.BLACK);
        //setting the button font
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        //adding eventhandler to return to the user dashboard
        backBtn.addActionListener(e -> parent.loginSuccess(student));
        
        //adding a secondary panal for the back button
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setBackground(Color.WHITE);
        bottom.add(backBtn);
        add(bottom, BorderLayout.SOUTH);
    }
}