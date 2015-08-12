package reserve;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MakeReservation extends JPanel implements ActionListener {
	//Labels to identify the fields
	    JLabel amountroomsLabel;
	    JLabel departuredateLabel;
	    JLabel arrivaldateLabel;
		JLabel nameLabel;
	
	//Buttons for room selection
		JButton bookRoom;
	
		//Fields for data entry
	    JFormattedTextField amountroomsField;
	    JFormattedTextField departuredateField;
	    JFormattedTextField arrivaldateField;
		JFormattedTextField nameField;
	
	public MakeReservation(){
		new BorderLayout();
		
		JRadioButton standardRoom = new JRadioButton("Standard Room");
        standardRoom.setMnemonic(KeyEvent.VK_K);
        standardRoom.setActionCommand("Standard Room");
        standardRoom.setSelected(true);

        JRadioButton familyRoom = new JRadioButton("Family Room");
        familyRoom.setMnemonic(KeyEvent.VK_F);
        familyRoom.setActionCommand("Family Room");

        JRadioButton suiteRoom = new JRadioButton("Suite");
        suiteRoom.setMnemonic(KeyEvent.VK_S);
        suiteRoom.setActionCommand("Suite");

		//Add Booking Button
		ImageIcon bookRoomIcon = createImageIcon("images/book.png");
		bookRoom = new JButton("Book Room", bookRoomIcon);
		bookRoom.setVerticalTextPosition(AbstractButton.BOTTOM);
		bookRoom.setHorizontalTextPosition(AbstractButton.CENTER);
		bookRoom.setMnemonic(KeyEvent.VK_M);
		bookRoom.addActionListener(this);
		bookRoom.setActionCommand("book");
		

		//Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(standardRoom);
        group.add(familyRoom);
        group.add(suiteRoom);
		
		//Create the labels.
		nameLabel = new JLabel("Name: ");
        amountroomsLabel = new JLabel("How many rooms? ");
        departuredateLabel = new JLabel("Check-Out Date: ");
        arrivaldateLabel = new JLabel("Check-In Date: ");
		
		//Create the text fields and set them up.
		nameField = new JFormattedTextField();
        nameField.setColumns(10);
		
        amountroomsField = new JFormattedTextField(new Integer(1));
		amountroomsField.setValue(new Integer(1));
        amountroomsField.setColumns(10);

		departuredateField = new JFormattedTextField(new java.util.Date());
        departuredateField.setColumns(10);

		arrivaldateField = new JFormattedTextField(new java.util.Date());
        arrivaldateField.setColumns(10);

		//Tell accessibility tools about label/textfield pairs.
		nameLabel.setLabelFor(nameField);
        amountroomsLabel.setLabelFor(amountroomsField);
        departuredateLabel.setLabelFor(departuredateField);
        arrivaldateLabel.setLabelFor(arrivaldateField);

        //Lay out the labels in a panel.
        JPanel labelPane1 = new JPanel(new GridLayout(0,1));

        labelPane1.add(amountroomsLabel);

		JPanel labelPane2 = new JPanel(new GridLayout(0,1));
        labelPane2.add(departuredateLabel);

		JPanel labelPane3 = new JPanel(new GridLayout(0,1));
        labelPane3.add(arrivaldateLabel);

		JPanel labelPane4 = new JPanel(new GridLayout(0,1));
		labelPane4.add(nameLabel);

        //Layout the text fields in a panel.
        JPanel fieldPane1 = new JPanel(new GridLayout(0,1));
        fieldPane1.add(amountroomsField);

        JPanel fieldPane2 = new JPanel(new GridLayout(0,1));
		fieldPane2.add(departuredateField);
		
		JPanel fieldPane3 = new JPanel(new GridLayout(0,1));
        fieldPane3.add(arrivaldateField);

		JPanel fieldPane4 = new JPanel(new GridLayout(0,1));
		fieldPane4.add(nameField);

		//Put the radio buttons in a column in a panel.
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(standardRoom);
        radioPanel.add(familyRoom);
        radioPanel.add(suiteRoom);

        //Put the panels in this panel, labels on left,
        //text fields on right.
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelPane1, BorderLayout.LINE_START);
		add(fieldPane1, BorderLayout.LINE_END);
		add(labelPane2, BorderLayout.LINE_START);
		add(fieldPane2, BorderLayout.LINE_END);
		add(labelPane3, BorderLayout.LINE_START);
		add(fieldPane3, BorderLayout.LINE_END);
		add(labelPane4, BorderLayout.LINE_START);
		add(fieldPane4, BorderLayout.LINE_END);
		
		add(radioPanel, BorderLayout.LINE_END);
		add(bookRoom);
	}
	
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void showGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("CaliKing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new MakeReservation());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

	protected static ImageIcon createImageIcon(String path) {
	        java.net.URL imgURL = MakeReservation.class.getResource(path);
	        if (imgURL != null) {
	            return new ImageIcon(imgURL);
	        } else {
	            System.err.println("Couldn't find file: " + path);
	            return null;
	        }
	    }
	
	public void actionPerformed(ActionEvent a) {
		if ("book".equals(a.getActionCommand())){
			try{  
			Class.forName("com.mysql.jdbc.Driver");  

			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/sonoo","root","root");  

			//here sonoo is database name, root is username and password  

			Statement stmt=con.createStatement();  

			ResultSet rs=stmt.executeQuery("select * from emp");  

			while(rs.next())  
			System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  

			con.close();  

			}catch(Exception e){ System.out.println(e);}
		}
		
		
	}

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
	        UIManager.put("swing.boldMetal", Boolean.FALSE);
                showGUI();
            }
        });
    }
   
}