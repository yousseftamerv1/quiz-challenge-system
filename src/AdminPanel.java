//###########################################################################
//#   File: AdminPanel.java                                                 #
//#                                                                         #
//#   Date: 9/5/2026                                                        #
//#                                                                         #
//#   Description: A GUI panel for the administrator to manage the          #
//#                question bank. Includes two tabs: one for adding new     #
//#                questions, and another for editing or deleting existing  #
//#                questions dynamically.                                   #
//#                                                                         #
//###########################################################################

import java.awt.*; //import the abstract window toolkit for UI layouts and colors
import javax.swing.*;  //import swing library for gui components like JPanal, JButton etc..
import javax.swing.border.EmptyBorder; //import the empty border to create padding and margins around the GUI

//creating the class that inherts from JPanal
public class AdminPanel extends JPanel {
    //class constructor
    public AdminPanel(MainFrame parent, QuizEngine engine) { //takes object from Mainframe for navigation and QuizEngine for displaying data
        //setting the layout
        setLayout(new BorderLayout());
        //setting the background
        setBackground(Color.WHITE);
        
        // TabbedPane split between adding and managing questions
        JTabbedPane tabs = new JTabbedPane();

        //Tab 1: adding questions
        JPanel addPanel = new JPanel(new GridBagLayout()); 
        addPanel.setBackground(Color.WHITE);
        JPanel box = new JPanel(new GridLayout(7, 1, 10, 10));
        box.setPreferredSize(new Dimension(450, 450)); box.setBackground(Color.WHITE);
        box.setBorder(new EmptyBorder(20, 20, 20, 20));
        //text field for adding the options of the question
        JTextField q = new JTextField(); q.setBorder(BorderFactory.createTitledBorder("Question"));
        JTextField o1 = new JTextField(); o1.setBorder(BorderFactory.createTitledBorder("Option 1"));
        JTextField o2 = new JTextField(); o2.setBorder(BorderFactory.createTitledBorder("Option 2"));
        JTextField o3 = new JTextField(); o3.setBorder(BorderFactory.createTitledBorder("Option 3"));
        JTextField o4 = new JTextField(); o4.setBorder(BorderFactory.createTitledBorder("Option 4"));
        //combo box to select the right answer of the four options
        JComboBox<String> ans = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        //button to save the questions
        JButton addBtn = new JButton("Add & Save Question");
        addBtn.setBackground(new Color(40, 167, 69)); addBtn.setForeground(Color.BLACK);
        //adding action listner to handle the click event
        addBtn.addActionListener(e -> {
            //saving the choices in an array list
            String[] choices = {o1.getText(), o2.getText(), o3.getText(), o4.getText()};
            //create new question object using the addQuestionTobank
            engine.addQuestionToBank(new Question(engine.getQuestionCount() + 1, q.getText(), choices, ans.getSelectedIndex()));
            //saving it using serialization to make it permanent in the hard drive
            engine.saveToFile();
            //showing a popup message after successfully adding a question
            JOptionPane.showMessageDialog(this, "Question Added Successfully!");
        });
        //adding a secondary panal for the botton and the boxes
        box.add(new JLabel("Add New Question", SwingConstants.CENTER));
        box.add(q); box.add(o1); box.add(o2); box.add(o3); box.add(o4); box.add(ans); box.add(addBtn);
        addPanel.add(box);

        //Tab 2: edit and delete 
        JPanel managePanel = new JPanel(new GridBagLayout()); 
        managePanel.setBackground(Color.WHITE);
        //Creating an inner box with 11 rows to make a space for the search bar, edit fields and action buttons
        JPanel mBox = new JPanel(new GridLayout(11, 1, 10, 5));
        mBox.setPreferredSize(new Dimension(450, 600)); 
        mBox.setBackground(Color.WHITE);
        
        //adding text field to search for the question id to edit it or delete it
        JTextField idSearch = new JTextField(); 
        idSearch.setBorder(BorderFactory.createTitledBorder("Enter Question ID to Manage"));
        JButton loadBtn = new JButton("Load Question Data");
        
        //adding text fields to edit the options
        JTextField mq = new JTextField(); mq.setBorder(BorderFactory.createTitledBorder("Edit Question Text"));
        JTextField mo1 = new JTextField(); mo1.setBorder(BorderFactory.createTitledBorder("Edit Option 1"));
        JTextField mo2 = new JTextField(); mo2.setBorder(BorderFactory.createTitledBorder("Edit Option 2"));
        JTextField mo3 = new JTextField(); mo3.setBorder(BorderFactory.createTitledBorder("Edit Option 3"));
        JTextField mo4 = new JTextField(); mo4.setBorder(BorderFactory.createTitledBorder("Edit Option 4"));
        //adding a combo box to edit the new answer
        JComboBox<String> mans = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        mans.setBorder(BorderFactory.createTitledBorder("Correct Answer"));
        
        //adding button for updating the question
        JButton updateBtn = new JButton("Update Full Question");
        updateBtn.setBackground(new Color(0, 122, 255)); updateBtn.setForeground(Color.BLACK);
        
        //adding button to delete the question
        JButton deleteBtn = new JButton("Delete Question Permanently");
        deleteBtn.setBackground(new Color(220, 53, 69)); deleteBtn.setForeground(Color.BLACK);

        //adding an action listener to load the data of the question
        loadBtn.addActionListener(e -> {
            try { //try and catch if there is an error
                //parsing the id and convert it into integer
                int id = Integer.parseInt(idSearch.getText());

                Question found = engine.getQuestionById(id); // Using the search method in QuizEngine class
                if (found != null) { //if the return value of found not equal null
                    //start adding the data of the question in every textfield 
                    mq.setText(found.getQuestionText()); 
                    String[] choices = found.getChoices();
                    mo1.setText(choices[0]);
                    mo2.setText(choices[1]);
                    mo3.setText(choices[2]);
                    mo4.setText(choices[3]);
                } else { // if it's equal null
                    JOptionPane.showMessageDialog(this, "ID not found!"); //show message popup
                }
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid ID Format"); } //if there is no id throw an error with a popup message
        });

        // adding an action listener to update the data of the question
        updateBtn.addActionListener(e -> {
            try { //try and catch if there is an error
                //parsing the id and convert it into integer
                int id = Integer.parseInt(idSearch.getText());
                String[] newChoices = {mo1.getText(), mo2.getText(), mo3.getText(), mo4.getText()};
                // Creating a fully updated question object
                Question updated = new Question(id, mq.getText(), newChoices, mans.getSelectedIndex());
                engine.updateQuestion(id, updated);
                engine.saveToFile(); //saving it using serialization to make it permanent in the hard driv
                JOptionPane.showMessageDialog(this, "Question Updated Successfully!");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Update Failed!"); }
        });

        // Logic to delete
        deleteBtn.addActionListener(e -> {
            //parsing the the id and convert it into text
            int id = Integer.parseInt(idSearch.getText());
            //remove the id
            engine.removeQuestion(id);
            //saving it using serialization to make it permanent in the hard driv
            engine.saveToFile();
            JOptionPane.showMessageDialog(this, "Deleted!");
            // Clear fields after deletion
            mq.setText("");
            mo1.setText(""); 
            mo2.setText(""); 
            mo3.setText(""); 
            mo4.setText(""); 
            idSearch.setText("");
        });

        mBox.add(idSearch);
        mBox.add(loadBtn);
        mBox.add(new JSeparator());
        mBox.add(mq); 
        mBox.add(mo1); 
        mBox.add(mo2); 
        mBox.add(mo3);
        mBox.add(mo4); 
        mBox.add(mans); 
        mBox.add(updateBtn); 
        mBox.add(deleteBtn);
        
        // Wrap in ScrollPane because the list of fields is long
        JScrollPane scroll = new JScrollPane(managePanel);
        managePanel.add(mBox);
        //adding the two configured panales
        tabs.addTab("Add New", addPanel);
        tabs.addTab("Manage Existing", scroll);
        //adding the tabs in the center
        add(tabs, BorderLayout.CENTER);
    }
}