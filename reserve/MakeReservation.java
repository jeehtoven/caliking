package reserve;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
import java.util.List;

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
	
		String currentDate_checkin;
		String currentDate_checkout;
	
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

		//java.util.Date dt_checkin = new java.util.Date();
		LocalDate today = LocalDate.now();
		//java.text.SimpleDateFormat sdf_checkin = new java.text.SimpleDateFormat("MM/dd/yyyy");
		currentDate_checkin = today.toString();
		checkindateField = new JFormattedTextField(currentDate_checkin);
        checkindateField.setColumns(10);

		//java.util.Date dt_checkout = new java.util.Date();
		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
		//java.text.SimpleDateFormat sdf_checkout = new java.text.SimpleDateFormat("MM/dd/yyyy");
		currentDate_checkout = tomorrow.toString();
		checkoutdateField = new JFormattedTextField(currentDate_checkout);
        checkoutdateField.setColumns(10);

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
			
			bookRoom();
			//ResultSet rs=stmt.executeQuery("select * from emp"); 
			/*if (!result_arr.next() && !result_dep.next()){
				
				bookRoom();
				
				//Query Prep for checking for open rooms
				/*String query_checkrooms = "SELECT * FROM makereservation_rooms WHERE type = '" + code + "' AND booked = '0';";
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
					System.out.println("(arrival) Does " + roomtypeCompare_arr + " equal " + room_type + "?");
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
					System.out.println("(departure) Does " + roomtypeCompare_dep + " equal " + room_type + "?");
					if (roomtypeCompare_dep.equals(room_type)){
						String  checkindateDB_dep = result_dep.getString("checkin");
						String  checkoutdateDB_dep = result_dep.getString("checkout");
						System.out.println("There is a room booked on " +  checkindateDB_dep + " that checks out on " +  checkoutdateDB_dep + ". Please select another date.");
					}
					
					else {
						System.out.println("Room Booked!");
					}	
				}*/
			con.close();  

			}
			
			catch(Exception e){ System.out.println(e);}
		}
		
		
	}
	
	public void bookRoom(){
		try{
			int code = 0;
			Connection con=DriverManager.getConnection("jdbc:mysql://67.20.111.85:3306/jeehtove_caliking?relaxAutoCommit=false","jeehtove_ck","Z_^PBBZT+kcy"); 
			Statement stmt_insert = con.createStatement(); 
			Statement stmt_tableRange = con.createStatement();
			Statement stmt_checkrooms = con.createStatement();
			Statement stmt_checkrooms_2 = con.createStatement();
			
			String insert = null;
			int flag = 0;
		
			String room_type;
			if (standardRoom.isSelected()) {room_type = "Standard"; code = 1;}
			else if (familyRoom.isSelected()) {room_type = "Family"; code = 2;}
			else {room_type = "Suite"; code = 3;}
		
			
			//Converting string from checkin and checkout dates so that they can access the correct table
			String currentDate_checkin_getText =  checkindateField.getText();
			String currentDate_checkin_rest = currentDate_checkin_getText.substring(5);
			String currentDate_checkin_replace = currentDate_checkin_rest.replace("-","_");
			String currentDate_checkin_year = currentDate_checkin.substring(0,4);
			String currentDate_checkin_final = currentDate_checkin_year + "_" + currentDate_checkin_replace;
			
			String currentDate_checkout_getText =  checkoutdateField.getText();
			String currentDate_checkout_rest = currentDate_checkout_getText.substring(5);
			String currentDate_checkout_replace = currentDate_checkout_rest.replace("-","_");
			String currentDate_checkout_year = currentDate_checkout.substring(0,4);
			String currentDate_checkout_final = currentDate_checkout_year + "_" + currentDate_checkout_replace;
			
			//MySQL Statement to select tables from schema that are in between checkin and checkout range
			String tableRange = "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='jeehtove_caliking' AND TABLE_NAME BETWEEN 'rooms_" + currentDate_checkin_final + "' AND 'rooms_" + currentDate_checkout_final + "';";
			ResultSet tb_Range = stmt_tableRange.executeQuery(tableRange);
			
			System.out.println(tableRange);
			List tb_range_list = new ArrayList();
			
			while (tb_Range.next()){
				String tb_range_date = tb_Range.getString("table_name");
				tb_range_list.add(tb_range_date);
				//String current_table = tb_Range.getString("table_name");
				//System.out.println(tb_range_list.add(tb_Range.getString("table_name")));
			}
			
			stmt_tableRange.close();
			
			for (int x = 0; x < tb_range_list.size(); x++){
				String query_checkrooms = "SELECT * FROM " + tb_range_list.get(x) + " WHERE type = '" + code + "' AND booked = '0' LIMIT 1;";
				//System.out.println(query_checkrooms);
				ResultSet checkrooms = stmt_checkrooms.executeQuery(query_checkrooms);
				while (checkrooms.next()){
					String getroomnumber = checkrooms.getString("room");
					insert = "INSERT INTO `reservations` VALUES ('" + getroomnumber + "', '" +  checkindateField.getText() + "', '" +  checkoutdateField.getText() + "',  '" + room_type + "',  '" + nameField.getText() + "');";
					String bookroom = "UPDATE `" + tb_range_list.get(x) + "` SET booked = '1' WHERE room = '" + getroomnumber + "';";
			//System.out.println(insert); 
				int cr=stmt_checkrooms_2.executeUpdate(bookroom);
				flag = 1;
				System.out.println("Room Booked!");
				}
			}
				
			if (flag == 1) {
				int rs=stmt_insert.executeUpdate(insert);
				}	
				
			else {
					System.out.println("All " + room_type + " rooms are booked for the time requested.");}
			
			
				
			
			/*String query_checkrooms = "SELECT * FROM rooms_" + currentDate_checkin_final + " WHERE type = '" + code + "' AND booked = '0';";
			System.out.println(query_checkrooms);
			Statement stmt_checkrooms = con.createStatement();
			ResultSet checkrooms = stmt_checkrooms.executeQuery(query_checkrooms);*/
		
			/*if (checkrooms.next()){
				String getroomnumber = checkrooms.getString("room");
				String insert = "INSERT INTO `reservations` VALUES ('" + getroomnumber + "', '" +  checkindateField.getText() + "', '" +  checkoutdateField.getText() + "',  '" + room_type + "',  '" + nameField.getText() + "');";
				String bookroom = "UPDATE `rooms_" + currentDate_checkin_final + "` SET booked = '1' WHERE room = '" + getroomnumber + "';";
		//System.out.println(insert);
			int rs=stmt_insert.executeUpdate(insert); 
			int cr=stmt_checkrooms.executeUpdate(bookroom);
			con.commit();
			System.out.println("Room Booked!");
			}*/
			
			//else System.out.println("All " + room_type + " rooms are booked for the time requested.");
			System.out.println("Complete.");
		}
		
		catch(Exception e){ System.out.println(e);}	
		
	}

	public static void makeRoomTables(){
		String tableDay = "";
		int leap_year_days = 28;
		String year = "2015";
		int leap = Integer.parseInt(year.trim());
		
		if((leap % 400 == 0) || ((leap % 4 == 0) && (leap % 100 != 0)))
	                        {leap_year_days = 29;}
	                else
	                        {}
		String[] month = new String[13];
		//populate array
		month[1] = "01";
		month[2] = "02";
		month[3] = "03";
		month[4] = "04";
		month[5] = "05";
		month[6] = "06";
		month[7] = "07";
		month[8] = "08";
		month[9] = "09";
		month[10] = "10";
		month[11] = "11";
		month[12] = "12";
		
		try{
			
			Connection con=DriverManager.getConnection("jdbc:mysql://67.20.111.85:3306/jeehtove_caliking?relaxAutoCommit=true","jeehtove_ck","Z_^PBBZT+kcy");  
			Statement stmt_query=con.createStatement();
			Statement stmt_addroomdata = con.createStatement();
			
			for (int i = 1; i <= 12; i++) {
				if (month[i] == "01" || month[i] == "03" || month[i] == "05" || month[i] == "07" || month[i] == "08" || month[i] == "10" || month[i] == "12") {
					for (int j = 1; j <= 31; j++){
						if (j >=1 && j <=9) {
							tableDay = year + "_" + month[i] + "_0" + j;
						}
						else tableDay = year + "_" + month[i] + "_" + j;
						String query = "CREATE TABLE if not exists jeehtove_caliking.rooms_" + tableDay + "(`ROOM` int(11) NOT NULL,`TYPE` int(11) NOT NULL,`BOOKED` int(11) NOT NULL DEFAULT '0') ENGINE=MyISAM DEFAULT CHARSET=latin1;";
						String tableData = "INSERT INTO `rooms_" + tableDay + "` (`ROOM`, `TYPE`, `BOOKED`) VALUES (101, 1, 0), (102, 1, 0), (103, 1, 0), (104, 1, 0), (105, 1, 0), (106, 1, 0), (107, 1, 0), (108, 1, 0), (109, 1, 0), (110, 2, 0), (111, 2, 0), (112, 2, 0), (113, 2, 0), (114, 2, 0), (115, 2, 0), (116, 2, 0), (117, 2, 0), (118, 2, 0), (119, 3, 0);";
						
						int maketables = stmt_query.executeUpdate(query);
						int addroomdata = stmt_addroomdata.executeUpdate(tableData);
						System.out.println("Room Tables Created for " + tableDay + ".");
					}
				}
				
				if (month[i] == "04" || month[i] == "06" || month[i] == "09" || month[i] == "11") {
					for (int j = 1; j <= 30; j++){
						if (j >=1 && j <=9) {
							tableDay = year + "_" + month[i] + "_0" + j;
						}
						else tableDay = year + "_" + month[i] + "_" + j;
						String query = "CREATE TABLE if not exists jeehtove_caliking.rooms_" + tableDay + "(`ROOM` int(11) NOT NULL,`TYPE` int(11) NOT NULL,`BOOKED` int(11) NOT NULL DEFAULT '0') ENGINE=MyISAM DEFAULT CHARSET=latin1;";
						String tableData = "INSERT INTO `rooms_" + tableDay + "` (`ROOM`, `TYPE`, `BOOKED`) VALUES (101, 1, 0), (102, 1, 0), (103, 1, 0), (104, 1, 0), (105, 1, 0), (106, 1, 0), (107, 1, 0), (108, 1, 0), (109, 1, 0), (110, 2, 0), (111, 2, 0), (112, 2, 0), (113, 2, 0), (114, 2, 0), (115, 2, 0), (116, 2, 0), (117, 2, 0), (118, 2, 0), (119, 3, 0);";
						
						int maketables = stmt_query.executeUpdate(query);
						int addroomdata = stmt_addroomdata.executeUpdate(tableData);
						System.out.println("Room Tables Created for " + tableDay + ".");
					}
				}
				
				if (month[i] == "02") {
					for (int j = 1; j <= leap_year_days ; j++){
						if (j >=1 && j <=9) {
							tableDay = year + "_" + month[i] + "_0" + j;
						}
						else tableDay = year + "_" + month[i] + "_" + j;
						String query = "CREATE TABLE if not exists jeehtove_caliking.rooms_" + tableDay + "(`ROOM` int(11) NOT NULL,`TYPE` int(11) NOT NULL,`BOOKED` int(11) NOT NULL DEFAULT '0') ENGINE=MyISAM DEFAULT CHARSET=latin1;";
						String tableData = "INSERT INTO `rooms_" + tableDay + "` (`ROOM`, `TYPE`, `BOOKED`) VALUES (101, 1, 0), (102, 1, 0), (103, 1, 0), (104, 1, 0), (105, 1, 0), (106, 1, 0), (107, 1, 0), (108, 1, 0), (109, 1, 0), (110, 2, 0), (111, 2, 0), (112, 2, 0), (113, 2, 0), (114, 2, 0), (115, 2, 0), (116, 2, 0), (117, 2, 0), (118, 2, 0), (119, 3, 0);";
						
						int maketables = stmt_query.executeUpdate(query);
						int addroomdata = stmt_addroomdata.executeUpdate(tableData);
						System.out.println("Room Tables Created for " + tableDay + ".");
					}
				}
				
			}
			
			System.out.println("Table creation has been completed.");
		
		}
		
		catch(Exception e){ System.out.println(e);}
		
	}

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
		
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
	        UIManager.put("swing.boldMetal", Boolean.FALSE);
			//Make Room Tables. ONLY execute this function when creating new room status tables for the upcoming year.
			//makeRoomTables();
            showGUI();
            }
        });
    }
   
}