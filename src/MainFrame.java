
//###########################################################################
//#   File: MainFrame.java                                                  #
//#                                                                         #
//#   Date: 9/5/2026                                                        #
//#                                                                         #
//#   Description: The MainFrame class represents the main graphical user   #
//#                interface (GUI) window of the Quiz & Challenge System.   #
//#                It is responsible for managing the application screens,  #
//#                including login, user dashboard, admin panel, quiz panel,#
//#                and leaderboard panel using CardLayout navigation.       # 
//#                                                                         #
//#                                                                         #
//#                                                                         #
//#                                                                         #
//###########################################################################

import java.awt.*; // import the abstract window toolkit for UI layouts and colors
import javax.swing.*; // import swing library for gui components like JPanal, JButton etc..
import javax.swing.border.EmptyBorder;

// Creates a class that inherits from JFrame
public class MainFrame extends JFrame {
    private CardLayout cardLayout = new CardLayout(); // CardLayout used to switch between different screens/panels
    private JPanel container = new JPanel(cardLayout); // Main container that stores all GUI panels
    private QuizEngine engine; // Main system engine that handles logic and data
    private JLabel userLabel = new JLabel(""); // Label used to display current logged-in user
    private JButton logoutBtn = new JButton("Logout"); // Logout Button

    // Constructor
    public MainFrame() {
        // Load saved system data from file
        engine = QuizEngine.loadFromFile();
        // Load default quiz questions into the system
        engine.loadDefaultQuestions();

        setTitle("Quiz and Challenge System");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        // Adding the Custom logo to the program
        try {
            // adding the custom logo
            ImageIcon appLogo = new ImageIcon(getClass().getResource("img/image.png"));
            // changes the current logo
            setIconImage(appLogo.getImage());
        } catch (Exception ex) {
            // if the logo is not found, the code doesn't crash
            System.out.println("Logo image not found! Please check the path.");
        }
        // ------------------------------

        // Saves System Data when closing the system
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) { engine.saveToFile(); System.exit(0); }
        });

        // creates top header panel
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(45, 52, 54));
        header.setPreferredSize(new Dimension(900, 60));
        header.setBorder(new EmptyBorder(0, 20, 0, 20));

        JLabel title = new JLabel("QUIZ CHALLENGE");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.add(title, BorderLayout.WEST);

        // navigation Section
        JPanel nav = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15)); // Panel for user info and logout
        nav.setOpaque(false); // Make navigation panel transparent 
        userLabel.setForeground(Color.LIGHT_GRAY);
        logoutBtn.setVisible(false); // Hide logout button initially
        logoutBtn.addActionListener(e -> logout()); // Logout button action
        nav.add(userLabel); nav.add(logoutBtn);
        header.add(nav, BorderLayout.EAST); // adds navigation panel to right side of header

        add(header, BorderLayout.NORTH);
        add(container, BorderLayout.CENTER);

        showLogin(); // Open Login Screen First
    }
    // method to show the login screen
    public void showLogin() {
        logoutBtn.setVisible(false); // hides logout button 
        userLabel.setText(""); // Clear user label
        container.add(new LoginPanel(this, engine), "LOGIN"); 
        cardLayout.show(container, "LOGIN"); // display login panel
    }
    // method to show the user login success message
    public void loginSuccess(User u) {
        logoutBtn.setVisible(true); // shows logout button
        userLabel.setText("Student: " + u.getUsername()); // Display logged in username
        container.add(new UserDashboardPanel(this, u, engine), "USER"); // opens user dashboard panel
        cardLayout.show(container, "USER"); // switch to user dashboard panel
    }
    // method to show the admin login success message
    public void loginAdmin() {
        logoutBtn.setVisible(true); // shows logout button
        userLabel.setText("ADMIN MODE"); // Displays admin mode text
        container.add(new AdminPanel(this, engine), "ADMIN"); // opens admin panel
        cardLayout.show(container, "ADMIN"); // switch to admin panel
    }
    // method to show the quiz panel
    public void startQuiz(User u, Question[] q) {
        // Open quiz panel and pass user/questions
        container.add(new QuizPanel(this, u, q, q.length, engine), "QUIZ");
        cardLayout.show(container, "QUIZ"); // switch to quiz panel
    }

    // Method to open the Leaderboard panel
    public void showLeaderboard(User u) {
        container.add(new LeaderboardPanel(this, engine, u), "LEADERBOARD"); // opens leaderboard panel
        cardLayout.show(container, "LEADERBOARD"); // switch to leaderboard panel
    }

    // logout method
    public void logout() { 
        engine.saveToFile(); // Save data before logout
        showLogin();  // return to login panel
        }
    
    // main method
    public static void main(String[] args) {
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // applies system look and feel
            } 
            catch(Exception e){}
        // run GUI on Swing Thread
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}