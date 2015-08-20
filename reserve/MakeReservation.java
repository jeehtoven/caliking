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

import java.util.*;

public class MakeReservation extends JPanel implements ActionListener {
	//Labels to identify the fields
	    JLabel amountroomsLabel;
	    JLabel  checkoutdateLabel;
	    JLabel  checkindateLabel;
		JLabel nameLabel;
	
	//Buttons for room selection
		JButton bookRoom;
	
		//Fields for data entry
	    JFormattedTextField amountroomsField;
	    JFormattedTextField  checkoutdateField;
	    JFormattedTextField  checkindateField;
		JFormattedTextField nameField;
		
		ButtonGroup group = new ButtonGroup();
		JRadioButton standardRoom = new JRadioButton("Standard Room");
		JRadioButton familyRoom = new JRadioButton("Family Room");
		JRadioButton suiteRoom = new JRadioButton("Suite");
	
	public MakeReservation(){
		new BorderLayout();
		
        standardRoom.setMnemonic(KeyEvent.VK_K);
        standardRoom.setActionCommand("Standard Room");
        standardRoom.setSelected(true);

        familyRoom.setMnemonic(KeyEvent.VK_F);
        familyRoom.setActionCommand("Family Room");

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
        group.add(standardRoom);
        group.add(familyRoom);
        group.add(suiteRoom);
		
		//Create the labels.
		nameLabel = new JLabel("Name: ");
        amountroomsLabel = new JLabel("How many rooms? ");
         checkoutdateLabel = new JLabel("Check-Out Date: ");
         checkindateLabel = new JLabel("Check-In Date: ");
		
		//Create the text fields and set them up.
		nameField = new JFormattedTextField();
        nameField.setColumns(10);
		
        amountroomsField = new JFormattedTextField(new Integer(1));
		amountroomsField.setValue(new Integer(1));
        amountroomsField.setColumns(10);

		java.util.Date dt_checkout = new java.util.Date();
		java.text.SimpleDateFormat sdf_checkout = new java.text.SimpleDateFormat("MM/dd/yyyy");
		String currentDate_checkout = sdf_checkout.format(dt_checkout);
		checkoutdateField = new JFormattedTextField(currentDate_checkout);
        checkoutdateField.setColumns(10);

		java.util.Date dt_checkin = new java.util.Date();
		java.text.SimpleDateFormat sdf_checkin = new java.text.SimpleDateFormat("MM/dd/yyyy");
		String currentDate_checkin = sdf_checkin.format(dt_checkin);
		checkindateField = new JFormattedTextField(currentDate_checkin);
        checkindateField.setColumns(10);

		//Tell accessibility tools about label/textfield pairs.
		nameLabel.setLabelFor(nameField);
        amountroomsLabel.setLabelFor(amountroomsField);
         checkoutdateLabel.setLabelFor( checkoutdateField);
         checkindateLabel.setLabelFor( checkindateField);

        //Lay out the labels in a panel.
        JPanel labelPane1 = new JPanel(new GridLayout(0,1));

        labelPane1.add(amountroomsLabel);

		JPanel labelPane3 = new JPanel(new GridLayout(0,1));
        labelPane3.add( checkindateLabel);

		JPanel labelPane2 = new JPanel(new GridLayout(0,1));
        labelPane2.add( checkoutdateLabel);

		JPanel labelPane4 = new JPanel(new GridLayout(0,1));
		labelPane4.add(nameLabel);

        //Layout the text fields in a panel.
        JPanel fieldPane1 = new JPanel(new GridLayout(0,1));
        fieldPane1.add(amountroomsField);

		JPanel fieldPane3 = new JPanel(new GridLayout(0,1));
        fieldPane3.add(checkindateField);
        
		JPanel fieldPane2 = new JPanel(new GridLayout(0,1));
		fieldPane2.add(checkoutdateField);
		
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
		add(labelPane3, BorderLayout.LINE_START);
		add(fieldPane3, BorderLayout.LINE_END);
		add(labelPane2, BorderLayout.LINE_START);
		add(fieldPane2, BorderLayout.LINE_END);
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
		int code = 0;
		String roomtypeCompare_arr = null;
		String roomtypeCompare_dep = null;
		if ("book".equals(a.getActionCommand())){
			try{  
			Class.forName("com.mysql.jdbc.Driver");  
			
			//Database connection information. Please note that when porting the code, you must have the jdbc Driver installed where the code is running.
			Connection con=DriverManager.getConnection("jdbc:mysql://67.20.111.85:3306/jeehtove_caliking?relaxAutoCommit=true","jeehtove_ck","Z_^PBBZT+kcy");  

			//The SELECT query 
			String query_arr = "SELECT * from reservations WHERE checkin BETWEEN '" +  checkindateField.getText() + "' AND '" +  checkoutdateField.getText() + "'";
			String query_dep = "SELECT * from reservations WHERE checkout BETWEEN '" +  checkindateField.getText() + "' AND '" +  checkoutdateField.getText() + "'";
			
			Statement stmt_arr=con.createStatement(); 
			Statement stmt_dep=con.createStatement(); 
			Statement stmt_insert=con.createStatement(); 
			ResultSet result_arr = stmt_arr.executeQuery(query_arr);
			ResultSet result_dep = stmt_dep.executeQuery(query_dep);
			
			//Get room type from radio buttons
			String room_type;
			if (standardRoom.isSelected()) {room_type = "Standard"; code = 1;}
			else if (familyRoom.isSelected()) {room_type = "Family"; code = 2;}
			else {room_type = "Suite"; code = 3;}
			
			//ResultSet rs=stmt.executeQuery("select * from emp"); 
			if (!result_arr.next() && !result_dep.next()){
				//Query Prep for checking for open rooms
				String query_checkrooms = "SELECT * FROM makereservation_rooms WHERE type = '" + code + "' AND booked = '0';";
				Statement stmt_checkrooms = con.createStatement();
				ResultSet checkrooms = stmt_checkrooms.executeQuery(query_checkrooms);
				
				if (checkrooms.next()){
				String getroomnumber = checkrooms.getString("room");
				String insert = "INSERT INTO `reservations` VALUES ('" + getroomnumber + "', '" +  checkindateField.getText() + "', '" +  checkoutdateField.getText() + "',  '" + room_type + "',  '" + nameField.getText() + "');";
				String bookroom = "UPDATE `makereservation_rooms` SET booked = '1' WHERE room = '" + getroomnumber + "';";
				//System.out.println(insert);
				int rs=stmt_insert.executeUpdate(insert); 
				int cr=stmt_checkrooms.executeUpdate(bookroom);
				con.commit();
				System.out.println("Room Booked!");
			}
			
				else System.out.println("All " + room_type + " rooms are booked for the time requested.");
			}
			
			else {
				if (result_arr.next()){
					roomtypeCompare_arr = result_arr.getString("type");
					System.out.println("Does " + roomtypeCompare_arr + " equal " + room_type + "?");
					if (roomtypeCompare_arr.equals(room_type)){
						String  checkindateDB_arr = result_arr.getString("checkin");
						String  checkoutdateDB_arr = result_arr.getString("checkout");
						System.out.println("There is a room booked on " +  checkindateDB_arr + " that checks out on " +  checkoutdateDB_arr + ". Please select another date.");
					}
					
					else {
						
						System.out.println("Room Booked!");
					}
				}
			}
				//System.out.println(query_dep);
				if (result_dep.next()){
					roomtypeCompare_dep = result_dep.getString("type");
					System.out.println("Does " + roomtypeCompare_dep + " equal " + room_type + "?");
					if (roomtypeCompare_dep.equals(room_type)){
						String  checkindateDB_dep = result_dep.getString("checkin");
						String  checkoutdateDB_dep = result_dep.getString("checkout");
						System.out.println("There is a room booked on " +  checkindateDB_dep + " that checks out on " +  checkoutdateDB_dep + ". Please select another date.");
					}
					
					else {
						System.out.println("Room Booked!");
					}	
				}
			con.close();  

			}
			
			catch(Exception e){ System.out.println(e);}
		}
		
		
	}
	
	public void checkRooms(){
		
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