package wfm.task;

import java.util.Properties;

import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wfm.db.Course;

@Named
public class NotifyTrainerAboutWeather implements JavaDelegate {

	private static final Logger log = LoggerFactory.getLogger(LoginTask.class);

	private String add;
	private String cName;
	private String bwcondition;		
	private String userId;

	private Course c;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		c = (Course) execution.getVariable("courseToApprove");
		log.debug("The name of the course is: " + c);

		bwcondition = (String) execution.getVariable("badWeatherCondition");
		cName = (String) execution.getVariable("courseToApprove");
		userId = (String) execution.getVariable("username"); 


		//ACT_ID_USER trainer = (ACT_ID_USER) execution.getVariable("trainer"); //variable is set in addCourseTask

		//ACT_ID_USER trainerToBeNotified = entityManager.find(ACT_ID_USER.class, userId); //null, why? -> SPRING is for web development here, not for service tasks, i mentioned this in my previous commit when i corrected hannes whole code
		//so it's not possible to use spring annotations in service tasks...
		//log.info("yyy: "+trainerToBeNotified);		
		//add = trainerToBeNotified.getEmail_();

		System.out.println("Sending mail...to: "+add+" with name "+userId+" about affected course by bad weather condition: "+cName+" with condition: "+bwcondition);
		//initializeMailService(add,cName,userId,bwcondition);
		System.out.println("done...");
	}
	private void initializeMailService(String add,String cName, String userId, String bwcondition) throws AddressException, MessagingException {

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
			message.setSubject("SCCMS: Weather notification!");

			// Now set the actual message
			message.setText("Dear "+userId+"!\n" +
					"This Email is a notification that one of your planned outdoor course should be canceled due to bad weather forecast!\n\n" +
					"The affected course is: "+cName+"\n\n"+
					"Following conditions are forecasted: "+bwcondition+"\n"+
					"Please feel free to decide if the weather conditions compromise a proper course activity.\n\n" +
					"This is an automatically generated weather-forecast notification.\n\n" +
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