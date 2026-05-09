//###########################################################################
//#   File: Account.java                                                    #
//#                                                                         #
//#   Date: 9/5/2026                                                        #
//#                                                                         #
//#   Description: Abstract base class for all system users.                #
//#                Defines core authentication and polymorphism contracts.  #
//#                                                                         #
//###########################################################################

import java.io.Serializable; //import the serializtion from i/o

//creating an abstract class that implements serialization
public abstract class Account implements Serializable {
    //class attributes and it's modifiers set to protected
    protected String username; 
    protected String password;

    //constructor
    public Account(String user, String pass) {
        this.username = user;
        this.password = pass;
    }

    //authentication method
    public boolean login(String user, String pass) {
        return this.username.equals(user) && this.password.equals(pass);
    }

    //absrtact method to show dashboard - polymorphism
    public abstract void showDashboard(); 
}