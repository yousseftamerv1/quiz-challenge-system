//###########################################################################
//#   File: Admin.java                                                      #
//#                                                                         #
//#   Date: 9/5/2026                                                        #
//#                                                                         # 
//#   Description: represents a admin entity in the system                  #
//#                Inherits from Account class and is used to               #
//#                add/edit/remove questions in the system.                 #
//#                                                                         #
//###########################################################################

// creating an admin class that inherits attributs & methods from class Account
public class Admin extends Account {
    private int adminID; // initializes the adminID
    private QuizEngine engine; // reference to engine to perform actions

    // constructor
    public Admin(int id, String user, String pass, QuizEngine engine) {
        super(user, pass); 
        this.adminID = id;
        this.engine = engine;
    }
    // method to add questions
    public void addQuestion(Question ques) {
        engine.addQuestionToBank(ques);
        engine.saveToFile();
    }
    // method to update/edit questions
    public void editQuestion(int quesID, Question updatedQ) {
        engine.updateQuestion(quesID, updatedQ);
        engine.saveToFile();
    }
    // method to remove questions
    public void removeQuestion(int quesID) {
        engine.removeQuestion(quesID);
        engine.saveToFile();
    }

    // overriden method
    @Override
    public void showDashboard() {
        System.out.println("Admin " + username + " is managing the bank.");
    }
    // returns the adminID
    public int getAdminID() { return adminID; }
}