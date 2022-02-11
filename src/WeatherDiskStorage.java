import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherDiskStorage extends WeatherMemoryStorage {
    private static final String EMPTY_FIELD = "M";
    private static final String ID_FILE = "weatherId.xml";
    private static final String TIMESTAMP_FILE = "weatherTimestamp.xml";
    private static final String STATION_FILE = "weatherStation.xml";
    private static final String TEMPERATURE_FILE = "weatherTemperature.xml";
    private static final String HUMIDITY_FILE = "weatherHumidity.xml";

    private List<String> weatherColumnHeader = new ArrayList<String>();
    private List<Integer> weatherId = new ArrayList<Integer>();
    private List<Date> weatherTimestamp = new ArrayList<Date>();
    private List<String> weatherStation = new ArrayList<String>();
    private List<Double> weatherTemperature = new ArrayList<Double>();
    private List<Double> weatherHumidity = new ArrayList<Double>();

    ResourceManager rm;

    public WeatherDiskStorage() {
        rm = new ResourceManager();
    }

    public List<Date> getWeatherTimestamp() {
        weatherTimestamp = rm.loadDataDate(TIMESTAMP_FILE);
        return weatherTimestamp;
    }

    public List<String> getWeatherStation() {
        weatherStation = rm.loadDataString(STATION_FILE);
        return weatherStation;
    }

    public List<Double> getWeatherTemperature() {
        weatherTemperature = rm.loadDataDouble(TEMPERATURE_FILE);
        return weatherTemperature;
    }

    public List<Double> getWeatherHumidity() {
        weatherHumidity = rm.loadDataDouble(HUMIDITY_FILE);
        return weatherHumidity;
    }

    public void addHeaders(List<String> headers) {
        weatherColumnHeader.addAll(headers);
    }

    public List<Object> getIndexAttributes(int index) {
        List<Object> attributes = new ArrayList<>();
        attributes.add(weatherTimestamp.get(index));
        attributes.add(weatherStation.get(index));
        attributes.add(weatherTemperature.get(index));
        attributes.add(weatherHumidity.get(index));
        return attributes;
    }

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

    public void storeToDiskStorage() {
        rm.saveData(ID_FILE, weatherId);
        rm.saveData(TIMESTAMP_FILE, weatherTimestamp);
        rm.saveData(STATION_FILE, weatherStation);
        rm.saveData(TEMPERATURE_FILE, weatherTemperature);
        rm.saveData(HUMIDITY_FILE, weatherHumidity);
    }


}
