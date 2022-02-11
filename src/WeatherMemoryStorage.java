import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherMemoryStorage implements Storage {
    private static final String EMPTY_FIELD = "M";

    private List<String> weatherColumnHeader = new ArrayList<String>();
    private List<Integer> weatherId = new ArrayList<Integer>();
    private List<Date> weatherTimestamp = new ArrayList<Date>();
    private List<String> weatherStation = new ArrayList<String>();
    private List<Double> weatherTemperature = new ArrayList<Double>();
    private List<Double> weatherHumidity = new ArrayList<Double>();

//    public List<Integer> getWeatherId() {
//        return weatherId;
//    }
//
//    public void setWeatherId(List<Integer> weatherId) {
//        this.weatherId = weatherId;
//    }

    public List<Date> getWeatherTimestamp() {
        return weatherTimestamp;
    }

//    public void setWeatherTimestamp(List<Date> weatherTimestamp) {
//        this.weatherTimestamp = weatherTimestamp;
//    }

    public List<String> getWeatherStation() {
        return weatherStation;
    }

//    public void setWeatherStation(List<String> weatherStation) {
//        this.weatherStation = weatherStation;
//    }

    public List<Double> getWeatherTemperature() {
        return weatherTemperature;
    }

//    public void setWeatherTemperature(List<Double> weatherTemperature) {
//        this.weatherTemperature = weatherTemperature;
//    }

    public List<Double> getWeatherHumidity() {
        return weatherHumidity;
    }

//    public void setWeatherHumidity(List<Double> weatherHumidity) {
//        this.weatherHumidity = weatherHumidity;
//    }

    public void addHeaders(List<String> headers) {
        weatherColumnHeader.addAll(headers);
    }

//    public List<String> getHeaders() {
//        return weatherColumnHeader;
//    }

    public void addAttributes(String[] attributes) throws ParseException {
        int id = Integer.parseInt(attributes[0]);
        weatherId.add(id);

        Date timestamp;
        if (!attributes[1].equals(EMPTY_FIELD)) {
            timestamp = new SimpleDateFormat(Common.DATE_FORMAT).parse(attributes[1]);
        } else {
            timestamp = null;
        }
        weatherTimestamp.add(timestamp);

        String station;
        if (!attributes[2].equals(EMPTY_FIELD)) {
            station = attributes[2];
        } else {
            station = "";
        }
        weatherStation.add(station);

        double temperature;
        if (!attributes[3].equals(EMPTY_FIELD)) {
            temperature = Double.parseDouble(attributes[3]);
        } else {
            temperature = Double.NaN;
        }
        weatherTemperature.add(temperature);

        double humidity;
        if (!attributes[4].equals(EMPTY_FIELD)) {
            humidity = Double.parseDouble(attributes[4]);
        } else {
            humidity = Double.NaN;
        }
        weatherHumidity.add(humidity);
    }

    public List<Object> getIndexAttributes(int index) {
        List<Object> attributes = new ArrayList<>();
        attributes.add(weatherTimestamp.get(index));
        attributes.add(weatherStation.get(index));
        attributes.add(weatherTemperature.get(index));
        attributes.add(weatherHumidity.get(index));
        return attributes;
    }


}
