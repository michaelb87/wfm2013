package wfm.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.fedy2.weather.YahooWeatherService;
import org.fedy2.weather.data.Channel;
import org.fedy2.weather.data.unit.DegreeUnit;

public class WeatherCheck implements JavaDelegate{
	
	public static String weoidVienna = "12591694";

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		YahooWeatherService service = new YahooWeatherService();
		
		Channel channel = service.getForecast(WeatherCheck.weoidVienna, DegreeUnit.CELSIUS);
		
		
		
		

		
	}

}
