package wfm.task;


import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.fedy2.weather.YahooWeatherService;
import org.fedy2.weather.data.Channel;
import org.fedy2.weather.data.unit.DegreeUnit;

public class WeatherCheckTask implements JavaDelegate {

	public static String weoidVienna = "12591694";

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		// http://developer.yahoo.com/weather/
		// for more information on how the weather service works and the codes
		// need to be interpreted

		YahooWeatherService service = new YahooWeatherService();
		Channel channel = service.getForecast(WeatherCheckTask.weoidVienna,
				DegreeUnit.CELSIUS);
		// read the current weather condition
		int condition = channel.getItem().getCondition().getCode();
		// http://developer.yahoo.com/weather/#codes
		//for testing always bad weather: if (condition < 100) {
		if (condition < 19 || condition >= 37 || condition == 35) {
			// cancel course
			System.out.println("Weahter is really bad. it is " + channel.getItem().getCondition().getText());
			execution.setVariable("weatherOk", false);
		} else {
			System.out.println("Weather is " + channel.getItem().getCondition().getText()
					+ " - Course is taking place as scheduled: ");
			execution.setVariable("weatherOk", true);
		}

	}

}
