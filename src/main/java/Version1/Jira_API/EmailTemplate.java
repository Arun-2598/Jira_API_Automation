package Version1.Jira_API;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class EmailTemplate {

	
	public static File src;
	public ExtentTest test;
	public ExtentReports report;
	public static FileInputStream fis;
	//public static HSSFSheet sh2;
	//public static HSSFWorkbook wb;
	public static String Build;
	
	public static void SendMail(String htmlpath) throws Exception {
		//SMTP Email ID
		       String Email_Id = "smtpsstech@srinsofttech.com";
		//SMTP Password
				String password = "$rin@$oft#098";
				
	
		
		
		// Set mail properties for Outlook
				Properties props = System.getProperties();
				String host_name = "smtp.office365.com"; //for Gmail - smtp.gmail.com
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", host_name);
				props.put("mail.smtp.user", Email_Id);
				props.put("mail.smtp.password",password);
			    props.put("mail.smtp.ssl.protocols","TLSv1.2");
				props.put("mail.smtp.port", "587");    //for Gmail - 465
				props.put("mail.smtp.auth", "true");

	   //create Session
		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		// Set email Recipients
		message.setFrom(new InternetAddress(Email_Id));
		//message.addRecipients(Message.RecipientType.TO, "Naveenkumar.k@srinsofttech.com");
		//message.addRecipients(Message.RecipientType.TO, "MohamedMusthafa@srinsofttech.com");
		message.addRecipients(Message.RecipientType.TO, "arun.kumar@srinsofttech.com");

	
		//Set email subject
		String mail_subject = "Test email for JIRA Automated Report";
		message.setSubject(mail_subject);

		MimeMultipart multipart = new MimeMultipart();
		
		
		MimeBodyPart messageBodyPart1 = new MimeBodyPart();// Create another object to add another content
		
		String htmlText = readEmailFromHtml(htmlpath);
		messageBodyPart1.setContent(" Hi Team, "+"<br>"+
		"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+
		"Kindly find the Jira Automated Report for this week"+htmlText,"text/html; charset=utf-8");
		multipart.addBodyPart(messageBodyPart1);

		// set the content
		message.setContent(multipart);

		// HTML mail content
		/*MimeMultipart multipart2 = new MimeMultipart();
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		String htmlText = readEmailFromHtml("Input\\Jira.html");
		messageBodyPart.setContent(htmlText, "text/html");
	    
		multipart2.addBodyPart(messageBodyPart);
	    message.setContent(multipart2);*/

		// Conect to smtp server and send Email
		Transport transport = session.getTransport("smtp");
		transport.connect(host_name, Email_Id, password);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		System.out.println("Mail Send Succesfully");
	}

	// Method to replace the values for keys
	protected static String readEmailFromHtml(String filePath) throws IOException {
		String msg = readContentFromFile(filePath);
		return msg;

	}

	// Method to read HTML file as a String
	// Method to read HTML file as a String
	private static String readContentFromFile(String fileName) throws IOException {
		StringBuffer contents = new StringBuffer();

		BufferedReader reader = new BufferedReader(new FileReader(fileName));

		String line;
		while ((line = reader.readLine()) != null) {
			contents.append(line);
			contents.append(System.getProperty("line.separator"));
		}

		{
			reader.close();
		}

		return contents.toString();
	}

  public static void main(String[] args) throws Exception {
	  //SendMail();
	
}

}


