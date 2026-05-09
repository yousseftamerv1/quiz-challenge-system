//###############################################################################
//#   File: UserDashboardPanel.java                                             #
//#                                                                             #
//#   Date: 9/5/2026                                                            #
//#                                                                             #
//#   Description: A visual panel for the users to interact with the system.    #
//#                Allows the user to access the quizzes & view the leaderboard.#
//#                                                                             #
//###############################################################################

import java.awt.*; // import the abstract window toolkit for UI layouts and colors
import javax.swing.*; //import swing library for gui components like JPanal, JButton etc..

// creates a class named 'UserDashboardPanel'
public class UserDashboardPanel extends JPanel {
    // constructor
    public UserDashboardPanel(MainFrame parent, User u, QuizEngine engine) {
        setLayout(new GridBagLayout()); setBackground(new Color(245, 246, 250));
        JPanel box = new JPanel(new GridLayout(3, 1, 20, 20));
        box.setPreferredSize(new Dimension(300, 300)); box.setOpaque(false); 

        JButton start = new JButton("Start Random Quiz");

        // Waits for the user to click the 'Start random quiz' button then shows the quiz panel to the user.
        start.addActionListener(e -> {
            int availableQuestions = engine.getQuestionCount();
            int quizSize = (availableQuestions >= 3) ? 3 : availableQuestions; //prevent null pointer error
            
            // checks if there are questions in the QuestionBank
            if (quizSize == 0) {
                JOptionPane.showMessageDialog(this, "No questions in the bank yet!");
                return;
            }
            // starts the quiz if it finds questions in the QuestionBank
            Question[] q = new Question[quizSize]; 
            engine.generateRandomQuiz(q, quizSize);
            parent.startQuiz(u, q);
        });

        JButton lead = new JButton("Leaderboard");
        // Navigation updated to use actual GUI Panel instead of OptionPane
        lead.addActionListener(e -> parent.showLeaderboard(u));

        box.add(new JLabel("Welcome, " + u.getUsername(), SwingConstants.CENTER));
        box.add(start); box.add(lead);
        add(box);
    }
}