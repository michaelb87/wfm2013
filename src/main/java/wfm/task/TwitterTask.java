package wfm.task;


import javax.inject.Named;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.ConfigurationBuilder;

@Named("tweet")
public class TwitterTask implements JavaDelegate{
	private static final String ACCESS_TOKEN = "1507264177-0ftwhAZ15w0WH90mGchgYdPRD2EEyEJLRKfHtm8";
	private static final String ACCESS_TOKEN_SECRET = "SKENHIkD28MfI3gaXEWDOaCtG9TTYFUmT4fz7VPPo";
	private static final String CONSUMER_KEY = "EFC6rs5cgA8MV37KjTisqg";
	private static final String CONSUMER_SECRET = "hx9eWPpklMNLj8RfMPZtd5Vusj3sK7SSu4Ah9yRuQ";

   

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Execution " + execution.getId() + " was executed.");
		
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
    	builder.setOAuthAccessToken(ACCESS_TOKEN);
    	builder.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
    	builder.setOAuthConsumerKey(CONSUMER_KEY);
    	builder.setOAuthConsumerSecret(CONSUMER_SECRET);
        OAuthAuthorization auth = new OAuthAuthorization(builder.build());
        Twitter twitter = new TwitterFactory().getInstance(auth);
		try {
			twitter.updateStatus("Test");
		} catch (TwitterException e) {
			System.err.println("Error occurred while updating the status!");
			return;
		}
        System.out.println("Successfully updated the status.");
		
	}
}