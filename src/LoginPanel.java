//###########################################################################
//#   File: LoginPanel.java                                                 #
//#                                                                         #
//#   Date: 9/5/2026                                                        #
//#                                                                         #
//#   Description: The LoginPanel class represents the login screen of the  #
//#                Quiz & Challenge System.                                 #
//#                It allows users and admins to                            # 
//#                enter their usernames and passwords to                   #
//#                access the system.                                       # 
//#                                                                         #
//###########################################################################
import java.awt.*; // import the abstract window toolkit for UI layouts and colors
import javax.swing.*; //import swing library for gui components like JPanal, JButton etc..
import javax.swing.border.EmptyBorder; //import the empty border to create padding and margins around the GUI

// Creates a class that inherits from JPanel
public class LoginPanel extends JPanel {
    // constructor
    public LoginPanel(MainFrame parent, QuizEngine engine) {
        // Use GridBagLayout to center components perfectly
        setLayout(new GridBagLayout()); 
        setBackground(Color.WHITE);

        JPanel box = new JPanel(new GridLayout(4, 1, 10, 15)); // Creates a panel to hold the components
        box.setBorder(new EmptyBorder(30, 40, 30, 40)); // Add padding around the panel
        box.setBackground(Color.WHITE);
        box.setPreferredSize(new Dimension(350, 300)); // Set prefferred size for the login box

        JLabel title = new JLabel("System Login", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        box.add(title);

        JTextField userField = new JTextField(); // creates username field
        userField.setBorder(BorderFactory.createTitledBorder("Username"));
        box.add(userField); // adds username field to the box

        JPasswordField passField = new JPasswordField(); // creates password field
        passField.setBorder(BorderFactory.createTitledBorder("Password"));
        box.add(passField); // adds password field to the box

        JButton loginBtn = new JButton("Login"); // creates login button
        loginBtn.setBackground(new Color(0, 122, 255));
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setFocusPainted(false);
        
        // Login button action
        loginBtn.addActionListener(e -> { 
            String username = userField.getText();
            String password = new String(passField.getPassword());
            
            // user 1 login
            if (username.equals("User1") && password.equals("1234")) {
                
                User loggedInUser = engine.getUserByName("User1"); // searches for existing user
                
                // if not user is found it creates a new one
                if (loggedInUser == null) {
                    loggedInUser = new User(1, "User1", "1234");
                    engine.addUser(loggedInUser);
                }
                else
                parent.loginSuccess(loggedInUser);
            }

            // user 2 login
            else if (username.equals("User2") && password.equals("5678")) {
                User loggedInUser1 = engine.getUserByName("User2");
                if (loggedInUser1 == null) {
                    loggedInUser1 = new User(2, "User2", "5678");
                    engine.addUser(loggedInUser1);
                }
                else
                parent.loginSuccess(loggedInUser1);
            }
            
            // user 3 login
            else if (username.equals("User3") && password.equals("8910")) {
                User loggedInUser2 = engine.getUserByName("User3");
                if (loggedInUser2 == null) {
                    loggedInUser2 = new User(2, "User3", "8910");
                    engine.addUser(loggedInUser2);
                }
                else
                parent.loginSuccess(loggedInUser2);
            }
            //admin login
            else if (username.equals("admin") && password.equals("admin")) {
                parent.loginAdmin();
            } 

            // invalid login credentials message
            else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials!");
            }
        });
        box.add(loginBtn);
        add(box);
    }
}