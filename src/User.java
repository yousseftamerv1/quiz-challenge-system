//###########################################################################
//#   File: User.java                                                       #
//#                                                                         #
//#   Date: 9/5/2026                                                        #
//#                                                                         #
//#   Description: Represents a user entity in the system.                  #
//#                Inherits from Account and manages score history.         #
//#                                                                         #
//###########################################################################

//making a class  User that inherts all the traits from the abstract class Account
public class User extends Account {
    //User class attributes
    private int userID;
    private int totalScore;
    private int[] historyScores; //arraylist to save the history of the user scores
    private int historyCount; //to counts all the exams

    //constructor
    public User(int userID, String user, String pass) {
        super(user, pass); //to get the inherted attributes from the abstract Account class
        this.userID = userID;
        this.totalScore = 0;
        this.historyScores = new int[10]; // the log have limit of 10 scores only
        this.historyCount = 0;
    }

    public void takeQuiz(Question[] quesList, int quesCount) { //method to start the quiz
        System.out.println("User is taking the quiz..."); //checking in the terminal if the method is working or not
    }

    //method used to track the history points of the user - updated with userID parameter based on UML
    public void historyTrack(int targetUserID) {
        if(this.userID == targetUserID) {
            //print the username and the id of the user
            System.out.println("History for User: " + this.username + " (ID: " + this.userID + ")");
            
            //for loop to check the index to prevent printing any unnecessary data - prevent print 0 in the history
            for (int i = 0; i < historyCount; i++) {
                System.out.println("Attempt " + (i + 1) + ": " + historyScores[i] + " points.");
            }
        }
    }

    //method to save the score of the user after the quiz
    public void saveScore(int score) {
        if (historyCount < 10) { //checks if the history count less than 10
            historyScores[historyCount] = score; //saves the score in the array list
            historyCount++; //adding to the counter
        }
        this.totalScore += score; //adding the score into the total score
    }

    @Override //using the abstract method show dashboard and overriding it
    public void showDashboard() {
        //printing into the terminal to check if it's working
        System.out.println("Opening Student Dashboard...");
    }
    
    //getters methods to return the private attributes values
    public int getUserID() { return userID; }
    public int getTotalScore() { return totalScore; }
    public String getUsername() { return username; }
}