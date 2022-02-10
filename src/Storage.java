import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface Storage {
    List<Date> getWeatherTimestamp();
    List<String> getWeatherStation();
    List<Double> getWeatherHumidity();
    List<Double> getWeatherTemperature();
    void addHeaders(List<String> headers);
    void addAttributes(String[] attributes) throws ParseException;
    List<Object> getIndexAttributes(int index);
}
