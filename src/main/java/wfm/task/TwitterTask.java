package wfm.task;


import javax.inject.Named;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.BpmnError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.ConfigurationBuilder;
import wfm.db.ACT_ID_USER;
import wfm.db.Course;

@Named
public class TwitterTask implements JavaDelegate{
	private static final String ACCESS_TOKEN = "1507264177-0ftwhAZ15w0WH90mGchgYdPRD2EEyEJLRKfHtm8";
	private static final String ACCESS_TOKEN_SECRET = "SKENHIkD28MfI3gaXEWDOaCtG9TTYFUmT4fz7VPPo";
	private static final String CONSUMER_KEY = "EFC6rs5cgA8MV37KjTisqg";
	private static final String CONSUMER_SECRET = "hx9eWPpklMNLj8RfMPZtd5Vusj3sK7SSu4Ah9yRuQ";

	private static final Logger log = LoggerFactory.getLogger(TwitterTask.class);

	private Course courseToApprove;
	private ACT_ID_USER userToApprove;
	private String status;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("TwitterTask " + execution.getId() + " was executed.");

		courseToApprove = (Course) execution.getVariable("courseToApprove");
		userToApprove = (ACT_ID_USER) execution.getVariable("userToApprove");

		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthAccessToken(ACCESS_TOKEN);
		builder.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
		builder.setOAuthConsumerKey(CONSUMER_KEY);
		builder.setOAuthConsumerSecret(CONSUMER_SECRET);
		OAuthAuthorization auth = new OAuthAuthorization(builder.build());
		Twitter twitter = new TwitterFactory().getInstance(auth);
		try {
			status = userToApprove.getFirst_()+" "+userToApprove.getLast_()+" subscribed to course '"+courseToApprove.getName()+"' at '"+courseToApprove.getLocation()+"' on "+courseToApprove.getDate();
			twitter.updateStatus(status);
		} catch (TwitterException e) {
			String msg = "Error occurred while updating the status: "+e.getMessage();
			log.error(msg);
			execution.setVariable("twitterException", msg);
			throw new BpmnError("TwitterExeptionOccured");

		}
		log.info("Successfully updated the status: "+status);


	}
}