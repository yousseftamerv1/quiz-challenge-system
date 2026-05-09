//#######################################################################
//#   File: App.java                                                    #
//#                                                                     #
//#   Date: 9/5/2026                                                    #
//#                                                                     #
//#   Description: Entry point of the Quiz & Challenge System.          #
//#                Handles UI theme setup and launches the MainFrame.   #
//#                                                                     #
//#######################################################################

import javax.swing.UIManager; //imports the UIManager that managee the UI and look and feel of the application

public class App { //implement of the App class
    public static void main(String[] args) { //main method

        try { //try and catch if there is an error in the theme
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //applying the theme according to the operating system used to run the application
        } catch (Exception e) { //if there is an error
            e.printStackTrace(); //print the error and continue the application
        }

        //open the mainframe class
        javax.swing.SwingUtilities.invokeLater(() -> { //invokeLater a method used to prevent the freezing of the application
            new MainFrame().setVisible(true); // creating the main frame and make it visble to the user
        });
    }
}