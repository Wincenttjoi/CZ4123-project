import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface Storage {
    public List<Date> getWeatherTimestamp();
    public List<String> getWeatherStation();
    public List<Double> getWeatherHumidity();
    public List<Double> getWeatherTemperature();
    public void addHeaders(List<String> headers);
    public void addAttributes(String[] attributes) throws ParseException;
}
