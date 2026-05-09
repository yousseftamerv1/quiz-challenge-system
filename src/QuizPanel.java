//###########################################################################
//#   File: QuizPanel.java                                                  #
//#                                                                         #
//#   Date: 9/5/2026                                                        #
//#                                                                         #
//#   Description: The Main Quiz GUI Panel that the users use to answer     #
//#                the questions.                                           #
//#                provides the result at the end of the quiz              #
//#                                                                         #
//###########################################################################

import java.awt.*; //import the abstract window toolkit for UI layouts and colors
import javax.swing.*; //import swing library for gui components like JPanal, JButton etc..
import javax.swing.border.EmptyBorder;

// creates a class that inherits from JPanel
public class QuizPanel extends JPanel {
    private MainFrame parent;
    private User student;
    private Question[] quizQuestions;
    private QuizEngine engine;
    private int quizLength, currentIndex = 0, score = 0;
    private int[] userAnswers; //array to track user responses based on UML

    private JLabel questionLabel;
    private JRadioButton[] radioButtons;
    private ButtonGroup buttonGroup;
    private JButton nextButton;
    
    // constructor
    public QuizPanel(MainFrame parent, User student, Question[] questions, int length, QuizEngine engine) {
        this.parent = parent; this.student = student;
        this.quizQuestions = questions; this.quizLength = length; this.engine = engine;
        this.userAnswers = new int[length]; //initialize array
        
        // sets the layout of the quiz panel
        setLayout(new BorderLayout());
        // changes the background color to white
        setBackground(Color.WHITE);
        // sets the border for the panel
        setBorder(new EmptyBorder(40, 50, 40, 50));

        questionLabel = new JLabel("");
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 15));
        optionsPanel.setBackground(Color.WHITE);
        radioButtons = new JRadioButton[4];
        buttonGroup = new ButtonGroup();
        
        // for loop to add the choices in the question
        for (int i = 0; i < 4; i++) {
            radioButtons[i] = new JRadioButton();
            radioButtons[i].setFont(new Font("SansSerif", Font.PLAIN, 18));
            radioButtons[i].setBackground(Color.WHITE);
            buttonGroup.add(radioButtons[i]);
            optionsPanel.add(radioButtons[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);
        // next button
        nextButton = new JButton("Next");
        nextButton.setBackground(new Color(0, 122, 255));
        nextButton.setForeground(Color.BLACK);
        nextButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        nextButton.addActionListener(e -> processAnswer());
        
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(Color.WHITE);
        bottom.add(nextButton);
        add(bottom, BorderLayout.SOUTH);

        // loads the saved questions
        loadQuestion();
    }
    // method to load the questions and display it for the user
    private void loadQuestion() {
        Question q = quizQuestions[currentIndex];
        questionLabel.setText("Q" + (currentIndex + 1) + ": " + q.getQuestionText());
        for (int i = 0; i < 4; i++) {
            radioButtons[i].setText(q.getChoices()[i]);
            buttonGroup.clearSelection();
        }
        if (currentIndex == quizLength - 1) nextButton.setText("Submit");
    }
    // method to process the user's answer & records the answer to display the final result correctly
    private void processAnswer() {
        int selectedOption = -1;
        // loops through the answers and checks if the user has clicked on an answer
        for (int i = 0; i < 4; i++) { if (radioButtons[i].isSelected()) selectedOption = i; }
        // if not it displays a message 
        if (selectedOption == -1) { JOptionPane.showMessageDialog(this, "Select an answer!"); return; }
        
        userAnswers[currentIndex] = selectedOption; //record answer
        currentIndex++;
        // checks if the user has finished all the questions. if not, it displays another question
        if (currentIndex < quizLength) {
            loadQuestion();
        } else {
            //calculate final score using the engine logic
            score = engine.calculateScore(userAnswers, quizQuestions, quizLength);
            student.saveScore(score);
            engine.saveToFile(); // الحفظ الفوري
            JOptionPane.showMessageDialog(this, "Finished! Score: " + score + "/" + quizLength);
            parent.loginSuccess(student); // بيرجع للوحة الطالب
        }
    }
}