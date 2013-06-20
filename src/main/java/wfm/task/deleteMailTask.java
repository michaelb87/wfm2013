package wfm.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;


@Named("deleteMail")
public class deleteMailTask implements JavaDelegate {

	private List<String> userMails = new ArrayList<String>();
	private List<String> courseNames = new ArrayList<String>();
	private List<String> userNames = new ArrayList<String>();
	  
	private String add;
	private String uName;
	private String cName;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(DelegateExecution execution) throws Exception {
				
		System.out.println("Found emails: "+execution.getVariable("deleteProcess"));
		System.out.println("Found courseNames: "+execution.getVariable("courseName"));
		System.out.println("Found userNames: "+execution.getVariable("userName"));
		userMails = (List<String>) execution.getVariable("deleteProcess");
		courseNames = (List<String>) execution.getVariable("courseName");
		userNames = (List<String>) execution.getVariable("userName");
				
		if(!userMails.isEmpty()){
			
			for(int i=0;i<userMails.size();i++){
				add = userMails.get(i);
				uName = userNames.get(i);		
				cName = courseNames.get(i);
				
				System.out.println("Sending mail...to: "+add);
				initializeMailService(add,uName,cName);
				System.out.println("done...");
			}		
		}		
	}
	private void initializeMailService(String add, String uName, String cName) throws AddressException, MessagingException {
		
	   // Sender's email ID needs to be mentioned
	   String from = "sscms.sender@gmail.com";
	   String pass = "12345pass";
	   // Recipient's email ID needs to be mentioned.
	   String to = add;

	   String host = "smtp.gmail.com";

	   // Get system properties
	   Properties properties = System.getProperties();
	   // Setup mail server
	   properties.put("mail.smtp.starttls.enable", "true");
	   properties.put("mail.smtp.host", host);
	   properties.put("mail.smtp.user", from);
	   properties.put("mail.smtp.password", pass);
	   properties.put("mail.smtp.port", "587");
	   properties.put("mail.smtp.auth", "true");

	   // Get the default Session object.
	   Session session = Session.getInstance(properties);

	   try{
	      // Create a default MimeMessage object.
	      MimeMessage message = new MimeMessage(session);

	      // Set From: header field of the header.
	      message.setFrom(new InternetAddress(from));

	      // Set To: header field of the header.
	      message.addRecipient(Message.RecipientType.TO,
	                               new InternetAddress(to));

	      // Set Subject: header field
	      message.setSubject("SCCMS: Course canceled!");

	      // Now set the actual message
	      message.setText("Dear "+uName+"!\n" +
	      		"This Email is a notification that a course you have been subscribed to has been canceled!\n\n" +
	      		"The affected course is: "+cName+"\n"+
	      		"Please feel free to select an alternative.\n\n" +
	      		"This is an automatically generated course cancelling notification.\n\n" +
	      		"Best regards,\nSCCMS");	    

	      // Send message
	      Transport transport = session.getTransport("smtp");
	      transport.connect(host, from, pass);
	      transport.sendMessage(message, message.getAllRecipients());
	      transport.close();
	      System.out.println("Sent message successfully....");
	   }catch (MessagingException mex) {
	      mex.printStackTrace();
	   }			
	}
}