import java.sql.Timestamp;

public class Weather {
    private int id;
    private String timestamp;
    private String station;
    private double temperature;
    private double humidity;

    private Weather(int id, String timestamp, String station, double temperature, double humidity) {
        this.id = id;
        this.timestamp = timestamp;
        this.station = station;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public static Weather createWeather(String[] metadata) {
        int id = Integer.parseInt(metadata[0]);
        String timestamp;
        if (!metadata[1].equals("M")) {
            timestamp = metadata[1];
        } else {
            timestamp = "";
        }
        String station;
        if (!metadata[2].equals("M")) {
            station = metadata[2];
        } else {
            station = "";
        }
        double temperature;
        if (!metadata[3].equals("M")) {
            temperature = Double.parseDouble(metadata[3]);
        } else {
            temperature = Double.NaN;
        }
        double humidity;
        if (!metadata[4].equals("M")) {
            humidity = Double.parseDouble(metadata[4]);
        } else {
            humidity = Double.NaN;
        }
        return new Weather(id, timestamp, station, temperature, humidity);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "Weather: [id=" + id + ", timestamp=" + timestamp + ", station=" + station +
                ", temperature=" + temperature + ", humidity=" + humidity;
    }
}
