import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Weather {
    private static final String EMPTY_FIELD = "M";

    private int id;
    private Date timestamp;
    private String station;
    private double temperature;
    private double humidity;

    private Weather(int id, Date timestamp, String station, double temperature, double humidity) {
        this.id = id;
        this.timestamp = timestamp;
        this.station = station;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public static Weather createWeather(String[] metadata) throws ParseException {
        int id = Integer.parseInt(metadata[0]);
        Date timestamp;
        if (!metadata[1].equals(EMPTY_FIELD)) {
            timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(metadata[1]);
        } else {
            timestamp = null;
        }
        String station;
        if (!metadata[2].equals(EMPTY_FIELD)) {
            station = metadata[2];
        } else {
            station = "";
        }
        double temperature;
        if (!metadata[3].equals(EMPTY_FIELD)) {
            temperature = Double.parseDouble(metadata[3]);
        } else {
            temperature = Double.NaN;
        }
        double humidity;
        if (!metadata[4].equals(EMPTY_FIELD)) {
            humidity = Double.parseDouble(metadata[4]);
        } else {
            humidity = Double.NaN;
        }
        return new Weather(id, timestamp, station, temperature, humidity);
    }

    @Override
    public String toString() {
        return "Weather: [id=" + id + ", timestamp=" + timestamp + ", station=" + station +
                ", temperature=" + temperature + ", humidity=" + humidity;
    }
}
