//###########################################################################
//#   File: Question.java                                                   #
//#                                                                         #
//#   Date: 9/5/2026                                                        #
//#                                                                         #
//#   Description: Represents a single multiple-choice question.            #
//#                Stores text, 4 options, and the correct answer index.    #
//#                                                                         #
//###########################################################################

import java.io.Serializable; //import the serializtion from i/o

//making the class Questions with the implementation of serialization - to save the questions into the hard drive
public class Question implements Serializable {
    
    //the class attributes
    private int questionID;
    private String questionText;
    private String[] choices; //array list that saves the 4 options
    private int correctAnswer; //the pointer that identify the correct answer

    //constructor
    public Question(int id, String text, String[] choices, int correctAns) {
        this.questionID = id;
        this.questionText = text;
        this.choices = choices;
        this.correctAnswer = correctAns;
    }

    //method to display the question details - added based on UML
    public void displayQuestion() {
        System.out.println("Question ID: " + questionID);
        System.out.println("Text: " + questionText);
        for(int i=0; i<4; i++) {
            System.out.println((i+1) + ". " + choices[i]);
        }
    }

    //method that checks if the answer is correct or not
    public boolean checkAnswer(int userAnswer) {
        //return true if the user answer is equal to the user answer
        return this.correctAnswer == userAnswer;
    }

    //getters methods to return the private attributes values   
    public int getQuestionID() { return this.questionID; }
    public String getQuestionText() { return questionText; }
    public String[] getChoices() { return choices; }
}
