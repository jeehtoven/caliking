package reserve;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class MakeReservation extends JPanel {
	//Labels to identify the fields
	    JLabel amountroomsLabel;
	    JLabel departuredateLabel;
	    JLabel arrivaldateLabel;
	
		//Fields for data entry
	    JFormattedTextField amountroomsField;
	    JFormattedTextField departuredateField;
	    JFormattedTextField arrivaldateField;
	
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

		//Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(standardRoom);
        group.add(familyRoom);
        group.add(suiteRoom);
		
		//Create the labels.
        amountroomsLabel = new JLabel("How many rooms? ");
        departuredateLabel = new JLabel("Check-Out Date: ");
        arrivaldateLabel = new JLabel("Check-In Date: ");
		
		//Create the text fields and set them up.
        amountroomsField = new JFormattedTextField(new Integer(1));
		amountroomsField.setValue(new Integer(1));
        amountroomsField.setColumns(10);

		departuredateField = new JFormattedTextField(new java.util.Date());
        departuredateField.setColumns(10);

		arrivaldateField = new JFormattedTextField(new java.util.Date());
        arrivaldateField.setColumns(10);

		//Tell accessibility tools about label/textfield pairs.
        amountroomsLabel.setLabelFor(amountroomsField);
        departuredateLabel.setLabelFor(departuredateField);
        arrivaldateLabel.setLabelFor(arrivaldateField);

        //Lay out the labels in a panel.
        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(amountroomsLabel);
        labelPane.add(departuredateLabel);
        labelPane.add(arrivaldateLabel);

        //Layout the text fields in a panel.
        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        fieldPane.add(amountroomsField);
        fieldPane.add(departuredateField);
        fieldPane.add(arrivaldateField);

		//Put the radio buttons in a column in a panel.
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(standardRoom);
        radioPanel.add(familyRoom);
        radioPanel.add(suiteRoom);

        //Put the panels in this panel, labels on left,
        //text fields on right.
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelPane, BorderLayout.LINE_START);
        add(fieldPane, BorderLayout.LINE_END);
		add(radioPanel, BorderLayout.LINE_END);
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