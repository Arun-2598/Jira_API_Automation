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

	public static WebDriver driver;
	public static File src;
	public ExtentTest test;
	public ExtentReports report;
	public static FileInputStream fis;
	//public static HSSFSheet sh2;
	//public static HSSFWorkbook wb;
	public static String Build;
	
	public static void SendMail() throws Exception {
		
		//src = new File("Input\\Configurations\\CloudBLM_DevSmoke_SuitConfig.xls");
		//fis = new FileInputStream(src);
		//wb = new HSSFWorkbook(fis);
		//sh2 = wb.getSheetAt(1);
		//System.out.println(sh2.getRow(2).getCell(2).getStringCellValue());
		//Build = sh2.getRow(2).getCell(2).getStringCellValue();
		
		/*System.out.println(sh2.getRow(1).getCell(3).getStringCellValue());
     	String Email_1 = sh2.getRow(1).getCell(3).getStringCellValue();
		System.out.println(sh2.getRow(2).getCell(3).getStringCellValue());
		String Email_2 = sh2.getRow(2).getCell(3).getStringCellValue();
		System.out.println(sh2.getRow(3).getCell(3).getStringCellValue());
		String Email_3 = sh2.getRow(3).getCell(3).getStringCellValue();
		System.out.println(sh2.getRow(4).getCell(3).getStringCellValue());
		String Email_4 = sh2.getRow(4).getCell(3).getStringCellValue();
		System.out.println(sh2.getRow(5).getCell(3).getStringCellValue());
		String Email_5 = sh2.getRow(5).getCell(3).getStringCellValue();
		System.out.println(sh2.getRow(6).getCell(3).getStringCellValue());
		String Email_6 = sh2.getRow(6).getCell(3).getStringCellValue();
		System.out.println(sh2.getRow(7).getCell(3).getStringCellValue());
		String Email_7 = sh2.getRow(7).getCell(3).getStringCellValue();
		System.out.println(sh2.getRow(8).getCell(3).getStringCellValue());
		String Email_8 = sh2.getRow(8).getCell(3).getStringCellValue();*/
		
		
		
		    // Email data
		       String Email_Id = "smtpsstech@srinsofttech.com";
				// change to your email ID
				String password = "$rin@$oft#098";
				// change to your password
		
		
		
		String mail_subject = "CloudBLM Test Result - "+Build+" Environment";

		// Set mail properties for Gmail
		/*Properties props = System.getProperties();
		String host_name = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host_name);
		props.put("mail.smtp.user", Email_Id);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", "true");*/
	
		
		// Set mail properties for Outlook
				Properties props = System.getProperties();
				String host_name = "smtp.office365.com";
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", host_name);
				props.put("mail.smtp.user", "smtpsstech@srinsofttech.com");
				props.put("mail.smtp.password","$rin@$oft#098");
			    props.put("mail.smtp.ssl.protocols","TLSv1.2");
				props.put("mail.smtp.port", "587");
				props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		// Set email data
		message.setFrom(new InternetAddress(Email_Id));
		
		
		message.addRecipients(Message.RecipientType.TO, "manimaran.e@srinsofttech.com");
	   
	
		
		 //message.addRecipients(Message.RecipientType.TO, "manimaran.e@srinsofttech.com");
		message.setSubject(mail_subject);

		MimeMultipart multipart = new MimeMultipart();
		BodyPart messageBodyPart = new MimeBodyPart();
		// Create another object to add another content
		MimeBodyPart messageBodyPart2 = new MimeBodyPart();
		messageBodyPart2.setText(" Hi Team, "+"\n"+"  Kindly find the CloudBLM Smoke Test Report");
		// Mention the file which you want to send
		//String filename = "Report\\CloudBLM "+Build+" Smoke Test Report.html";

		// Create data source and pass the filename
		//DataSource source = new FileDataSource(filename);

		// set the handler
		//messageBodyPart2.setDataHandler(new DataHandler(source));
	
		// set the file
		//messageBodyPart2.setFileName(filename);
	
		// Create object of MimeMultipart class
		// add body part 1
		multipart.addBodyPart(messageBodyPart2);

		// set the content
		message.setContent(multipart);

		// HTML mail content
		String htmlText = readEmailFromHtml("Input\\Jira.html");
		messageBodyPart.setContent(htmlText, "text/html");
	    multipart.addBodyPart(messageBodyPart);
		message.setContent(multipart);

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
	  SendMail();
	
}

}


