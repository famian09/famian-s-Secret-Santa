import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.Properties;
import java.util.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ssMethods {
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
	static String[][] strNames = new String[secretsanta.intPeople][3];
	
	public static void submitPeople() throws IOException {
		FileReader file = new FileReader("People.txt");
		BufferedReader read = new BufferedReader(file);
		
		
		
		int intCount;
		for(intCount = 0;intCount <secretsanta.intPeople;intCount++) {
			strNames[intCount][0] = read.readLine();
			strNames[intCount][1] = read.readLine();
			
		}
		
		file.close();
		read.close(); 
		
		
		
		/* for(intCount = 0;intCount <9;intCount++) {
			System.out.println(strNames[intCount][0]);
			System.out.println(strNames[intCount][1]);
			
		} */
		
		
		
		strNames = ssMethods.assignNames(strNames);
		//ssMethods.sendEmail(strNames[1][0],strNames[1][1]);		
	}
	
	public static String[][] assignNames(String[][] strNames) throws IOException {
		int intCount;
		int intRand;

		ArrayList<Integer> intList = new ArrayList<Integer>();
		FileWriter file2 = new FileWriter("List.txt",false);
		PrintWriter write = new PrintWriter(file2);
		
		for(intCount =0;intCount < secretsanta.intPeople;intCount++) {
			intRand = (int) (Math.random()*(secretsanta.intPeople-0)) +0; 
			
			//System.out.println(intRand);
			
			while(intList.contains(intRand) || strNames[intRand][0].equals(strNames[intCount][0])) {
				intRand = (int) (Math.random()*(secretsanta.intPeople-0)) +0; 
			}
			
			strNames[intCount][2] = strNames[intRand][0];
			intList.add(intRand);
			
			System.out.println(strNames[intCount][0]+" - "+strNames[intCount][2]);
			write.println(strNames[intCount][0]+" - "+strNames[intCount][2]);
		}
		
				
		System.out.println("Done");
		file2.close();
		write.close();
		return strNames;
	}
	
	
	public static void sendEmail(String strName, String strEmail,String strSantaName) throws IOException, AddressException, MessagingException {
		//Sending an email
				// Step1
				System.out.println("\n 1st ===> setup Mail Server Properties..");
				mailServerProperties = System.getProperties();
				mailServerProperties.put("mail.smtp.port", "587");
				mailServerProperties.put("mail.smtp.auth", "true");
				mailServerProperties.put("mail.smtp.starttls.enable", "true");
				System.out.println("Mail Server Properties have been setup successfully..");
				 
				// Step2
				System.out.println("\n\n 2nd ===> get Mail Session..");
				getMailSession = Session.getDefaultInstance(mailServerProperties, null);
				generateMailMessage = new MimeMessage(getMailSession);
				generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(strEmail));
				//generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("damian.mustatea@gmail.com"));
				generateMailMessage.setSubject("Secret Santa");
				String emailBody = "Hello "+strSantaName+" you got "+strName+". Sorry for the spam this is the real email. This is the person you should buy a gift for. The price range is around $20.";
				//String emailBody = "This is a test email.";
				generateMailMessage.setContent(emailBody, "text/html");
				System.out.println("Mail Session has been created successfully..");
			
				// Step3
				System.out.println("\n\n 3rd ===> Get Session and Send mail");
				Transport transport = getMailSession.getTransport("smtp");
				// Enter your correct gmail UserID and Password
				// if you have 2FA enabled then provide App Specific Password
				transport.connect("smtp.gmail.com", "famiansSecretSanta@gmail.com", "53cR3t54nt4");
				transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
				transport.close();
		
	}
}
