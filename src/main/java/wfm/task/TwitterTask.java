package wfm.task;


import javax.inject.Named;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.ConfigurationBuilder;

@Named
public class TwitterTask implements JavaDelegate{
	private static final String ACCESS_TOKEN = "1507264177-0ftwhAZ15w0WH90mGchgYdPRD2EEyEJLRKfHtm8";
	private static final String ACCESS_TOKEN_SECRET = "SKENHIkD28MfI3gaXEWDOaCtG9TTYFUmT4fz7VPPo";
	private static final String CONSUMER_KEY = "EFC6rs5cgA8MV37KjTisqg";
	private static final String CONSUMER_SECRET = "hx9eWPpklMNLj8RfMPZtd5Vusj3sK7SSu4Ah9yRuQ";

	
	private static final Logger log = LoggerFactory.getLogger(LoginTask.class);

	private static  int c =0;


	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("Execution " + execution.getId() + " was executed.");
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
    	builder.setOAuthAccessToken(ACCESS_TOKEN);
    	builder.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
    	builder.setOAuthConsumerKey(CONSUMER_KEY);
    	builder.setOAuthConsumerSecret(CONSUMER_SECRET);
        OAuthAuthorization auth = new OAuthAuthorization(builder.build());
        Twitter twitter = new TwitterFactory().getInstance(auth);
		try {
			c++;
			twitter.updateStatus("Tweet "+c+": User x subscribed to course y.");
		} catch (TwitterException e) {
			log.error("Error occurred while updating the status: "+e.getMessage());
			return;
		}
        log.info("Successfully updated the status.");
	
		
	}
}