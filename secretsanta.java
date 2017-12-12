import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class secretsanta implements ActionListener {
	// Properties
	JFrame frame;
	JPanel panel;
	JLabel labelTitle;
	JTextField fieldNames;
	JTextField fieldEmail;
	JButton buttonAdd;
	JButton buttonSubmit;
	JButton buttonSend;
	static int intPeople;
	static int intCounter;

	// Methods
	public void actionPerformed(ActionEvent evt) {
		int intCount = 0;

		if (evt.getSource() == buttonAdd && intCounter <= 10) { // Adding a Person to the List
			if (fieldNames.getText().equals("") || fieldEmail.getText().equals("")) { // If Input is empty
				System.out.println("Empty Input");
			} else { // If Input is not Empty add the name to the list
				ssMethods.strNames[intCount][0] = fieldNames.getText();
				ssMethods.strNames[intCount][1] = fieldEmail.getText();

				try {
					FileWriter file = new FileWriter("People.txt", true);
					PrintWriter filedata = new PrintWriter(file);
					filedata.println(ssMethods.strNames[intCount][0]);
					filedata.println(ssMethods.strNames[intCount][1]);
					file.close();
					filedata.close();
				} catch (IOException e) {
					// Something
				}
				fieldNames.setText("");
				fieldEmail.setText("");
				intCounter += 1;
			}

		} else if (evt.getSource() == buttonSubmit) { // Submits the list to be sorted

			try {
				ssMethods.submitPeople();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (evt.getSource() == buttonSend) { // Sends the emails
			for (intCount = 0; intCount < secretsanta.intPeople; intCount++) {
				try {
					ssMethods.sendEmail(ssMethods.strNames[intCount][2], ssMethods.strNames[intCount][1],
							ssMethods.strNames[intCount][0]);

				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// Constructor
	public secretsanta() {
		// Setting Up Panel
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(600, 600));
		panel.setLayout(null);

		// Label Title
		labelTitle = new JLabel("Secret Santa Generator");
		labelTitle.setSize(400, 50);
		labelTitle.setLocation(0, 0);
		panel.add(labelTitle);

		// Field Name
		fieldNames = new JTextField();
		fieldNames.setSize(400, 50);
		fieldNames.setLocation(0, 75);
		panel.add(fieldNames);

		// Field Email
		fieldEmail = new JTextField();
		fieldEmail.setSize(400, 50);
		fieldEmail.setLocation(0, 150);
		panel.add(fieldEmail);

		// Add Button
		buttonAdd = new JButton("Add Person");
		buttonAdd.setSize(200, 50);
		buttonAdd.setLocation(0, 225);
		buttonAdd.addActionListener(this);
		panel.add(buttonAdd);

		// Setting up Submit
		buttonSubmit = new JButton("Submit");
		buttonSubmit.setSize(200, 50);
		buttonSubmit.setLocation(0, 300);
		buttonSubmit.addActionListener(this);
		panel.add(buttonSubmit);

		// Setting up Send Email
		buttonSend = new JButton("Send Emails");
		buttonSend.setSize(200, 50);
		buttonSend.setLocation(0, 375);
		buttonSend.addActionListener(this);
		panel.add(buttonSend);

		// Setting up Frame
		frame = new JFrame("Secret Santa Generator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);

	}

	// Main Program
	public static void main(String[] args) throws IOException {

		FileReader file = new FileReader("People.txt");
		BufferedReader read = new BufferedReader(file);

		String strText;
		strText = read.readLine();
		while (strText != null) {
			strText = read.readLine();
			strText = read.readLine();
			intPeople += 1;
		}
		file.close();
		read.close();
		System.out.println(intPeople);
		new secretsanta();
	}
}
