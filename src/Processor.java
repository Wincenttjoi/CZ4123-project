import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/** Author: Wincent Tjoi
 * Matric Number: N2101669E
 * To process: station and humidity extreme values of each month between year 2009 to 2019 at Changi
 */

public class Processor {

    private static final String STATION = "Changi";

    private Storage storage;

    private List<Integer> timestampIndex = new ArrayList<>();
    private List<Integer> filteredStationIndex = new ArrayList<>();
    private List<Integer> filteredMaxHumidityIndex = new ArrayList<>();
    private List<Integer> filteredMinHumidityIndex = new ArrayList<>();

    public Processor(Storage storage) {
        this.storage = storage;
    }

    public void processData() {
        // month from 0 to 11
        initializeValidTimestamps(2019, 1);
        filterStation(STATION);
        findHumidity();
//        for (int i = 0; i < filteredMinHumidityIndex.size(); i++) {
//            System.out.println("test" + filteredMinHumidityIndex.get(i));
//        }

    }

    private void initializeValidTimestamps(int year, int month) {
        List<Date> timestamps = storage.getWeatherTimestamp();
        for (int i = 0; i < timestamps.size(); i++) {
            Calendar minCal = new GregorianCalendar(year, month,1);
            Calendar maxCal = new GregorianCalendar(year, month + 1,1);
            Date minDate = minCal.getTime();
            Date maxDate = maxCal.getTime();
            if (timestamps.get(i).after(minDate) && timestamps.get(i).before(maxDate)) {
                timestampIndex.add(i);
            }
        }
    }

    private void filterStation(String str) {
        List<String> weatherStations = storage.getWeatherStation();
        for (int i = 0; i < timestampIndex.size(); i++) {
            int index = timestampIndex.get(i);
            if (weatherStations.get(index).equals(STATION)) {
                filteredStationIndex.add(index);
            }
        }
    }

    private void findHumidity() {
        List<Double> weatherHumidity = storage.getWeatherHumidity();
        findMaxHumidity(weatherHumidity);
        findMinHumidity(weatherHumidity);
    }

    private void findMaxHumidity(List<Double> weatherHumidity) {
        double mx = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < filteredStationIndex.size(); i++) {
            int index = filteredStationIndex.get(i);
            if (weatherHumidity.get(index) > mx && !weatherHumidity.get(index).isNaN()) {
                mx = weatherHumidity.get(index);
            }
        }
        addHumidityIndex(filteredMaxHumidityIndex, weatherHumidity, mx);
    }

    private void findMinHumidity(List<Double> weatherHumidity) {
        double mn = Double.POSITIVE_INFINITY;
        for (int i = 0; i < filteredStationIndex.size(); i++) {
            int index = filteredStationIndex.get(i);
            if (weatherHumidity.get(index) < mn && !weatherHumidity.get(index).isNaN()) {
                mn = weatherHumidity.get(index);
            }
        }
        addHumidityIndex(filteredMinHumidityIndex, weatherHumidity, mn);
    }

    private void addHumidityIndex(List<Integer> filteredHumidityIndex, List<Double> weatherHumidity, Double d) {
        for (int i = 0; i < filteredStationIndex.size(); i++) {
            int index = filteredStationIndex.get(i);
            if (weatherHumidity.get(index).equals(d)) {
                filteredHumidityIndex.add(index);
            }
        }
    }

}
