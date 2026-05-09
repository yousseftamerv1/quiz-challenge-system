//###########################################################################
//#   File: QuizEngine.java                                                 #
//#                                                                         #
//#   Date: 9/5/2026                                                        #
//#                                                                         #
//#   Description: The core engine of the system. Manages data storage,     #
//#                question banks, user lists, and leaderboard logic.       #
//#                                                                         #
//###########################################################################

import java.io.*; //import all the i/o libiraries to read and write files
import java.util.Random; //import random from utils

//making the class QuizEngine with the implementation of serialization - to save the questions into the hard drive
public class QuizEngine implements Serializable {
    private static final long serialVersionUID = 1L; //
    private Question[] questionBank = new Question[100]; //arraylist can that can have up to 100 questions
    private int questionCount = 0; //pointer to show how many questions are registered
    private User[] userList = new User[50]; //array list that can have max 50 users
    private int userCount = 0; //pointer to show how many users are registered

    //method that uses serilization to save the file - "the data of the classes and questions" into a file named SystemData.dat
    public void saveToFile() {
        //try and catch to prevent crashing and save ram resources
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("SystemData.dat"))){ //define an OOS to save the QuizEngine object into a file 
            oos.writeObject(this); //applying and writing the object in the file
        } catch (IOException e) { e.printStackTrace(); } //if there is an error display it without crashing the applicaton
    }

   //method used to load the saved file and it's belong to the class not the object to make it load the file before creating any objects
    public static QuizEngine loadFromFile() {
        //trying to load the file using object input stream
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("SystemData.dat"))) {
            return (QuizEngine) ois.readObject();
        } catch (Exception e) { //if there is no file to load
            return new QuizEngine(); //rerun the class to create a new file
        }
    }

    //method used to add new questions 
    public void addQuestionToBank(Question q) {
        //checks if the question count is less than 100, because the max is 100
        // if not it will add the new question
        if (questionCount < 100) { questionBank[questionCount++] = q; }
    }

    //method used to generate random questions without duplicates
    public void generateRandomQuiz(Question[] quizArr, int count) {
        Random rand = new Random(); //making an object from random to generate random int
        int loopLimit = count; //count of the quiz questions
        
        //if the questions don't meet the limit
        if (questionCount < count) {
            loopLimit = questionCount; //the loop limit will be the questions count
        }

        boolean[] used = new boolean[questionCount]; //array to track chosen questions
        int added = 0;

        //while loop to ensure we pick distinct random questions
        while (added < loopLimit) {
            int randomIndex = rand.nextInt(questionCount);
            if (!used[randomIndex]) {
                quizArr[added] = questionBank[randomIndex];
                used[randomIndex] = true;
                added++;
            }
        }
    }
    
    //a method to add a defualt questions (Acts as loadQuestions from UML)
    public void loadDefaultQuestions() {
        //if the question count equals zero
        if (questionCount == 0) {
            //adding questions by adding a new question object
            addQuestionToBank(new Question(1, "What hides internal details in OOP?", new String[]{"Inheritance", "Encapsulation", "Polymorphism", "Abstraction"}, 1));
            addQuestionToBank(new Question(2, "Which keyword is used to inherit a class in Java?", new String[]{"implements", "super", "extends", "inherits"}, 2));
            addQuestionToBank(new Question(2, "Which keyword is used to inherit a class in Java?", new String[]{"implements", "super", "extends", "inherits"}, 2));
        }
    }

    //a method used to add a user if it's not exceeding the limit of 50
    public void addUser(User u) { if (userCount < 50) userList[userCount++] = u; }
    
    //method to get users by there name
    public User getUserByName(String name) {
        //using for loop to search if user is found in one of the already existed users or not
        for (int i = 0; i < userCount; i++) if (userList[i].getUsername().equals(name)) return userList[i];
        return null; //if there is no match it will return null value
    }
    
    public int getQuestionCount() { return questionCount; }
    
    //method to calculate score based on user answers - implemented based on UML
    public int calculateScore(int[] answers, Question[] quizQuestions, int count) {
        int score = 0;
        for (int i = 0; i < count; i++) {
            if (quizQuestions[i] != null && quizQuestions[i].checkAnswer(answers[i])) {
                score++;
            }
        }
        return score;
    }

    //method to return a formatted string for the leaderboard using Bubble Sort
    public String showLeaderboard() {
        //creating temporary array to not modify original references
        User[] tempUsers = new User[userCount];
        for(int i=0; i<userCount; i++) { tempUsers[i] = userList[i]; }

        //Bubble Sort algorithm to sort users by totalScore in descending order
        for (int i = 0; i < userCount - 1; i++) {
            for (int j = 0; j < userCount - i - 1; j++) {
                if (tempUsers[j].getTotalScore() < tempUsers[j + 1].getTotalScore()) {
                    //swap operation
                    User temp = tempUsers[j];
                    tempUsers[j] = tempUsers[j + 1];
                    tempUsers[j + 1] = temp;
                }
            }
        }

        StringBuilder sb = new StringBuilder("--- Leaderboard ---\n");
        for (int i = 0; i < userCount; i++) {
            sb.append((i+1) + ". " + tempUsers[i].getUsername() + " - Total Score: " + tempUsers[i].getTotalScore() + "\n");
        }
        return sb.toString();
    }
    
    //legacy call backward compatibility
    public String getLeaderboardData() {
        return showLeaderboard();
    }

    // method to find a question by its ID
    public Question getQuestionById(int id) {
        for (int i = 0; i < questionCount; i++) {
            if (questionBank[i].getQuestionID() == id) return questionBank[i];
        }
        return null; // if not found
    }

    // method used to edit/update an existing question
    public void updateQuestion(int id, Question updatedQ) {
        for (int i = 0; i < questionCount; i++) {
            if (questionBank[i].getQuestionID() == id) {
                questionBank[i] = updatedQ; // replace with updated data
                return;
            }
        }
    }

    // method used to remove a question from the bank and shift elements
    public void removeQuestion(int id) {
        int indexToRemove = -1;
        // find the index of the question
        for (int i = 0; i < questionCount; i++) {
            if (questionBank[i].getQuestionID() == id) {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove != -1) {
            // shift elements to the left to cover the gap
            for (int i = indexToRemove; i < questionCount - 1; i++) {
                questionBank[i] = questionBank[i + 1];
            }
            questionBank[--questionCount] = null; // reduce count and clear last slot
        }
    }
}