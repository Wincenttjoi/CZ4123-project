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
    private List<Integer> filteredMaxTemperatureIndex = new ArrayList<>();
    private List<Integer> filteredMinTemperatureIndex = new ArrayList<>();

    public Processor(Storage storage) {
        this.storage = storage;
    }

    public void processData() {
        // month from 0 to 11
        initializeValidTimestamps(2019, 1);
        filterStation(STATION);
        findHumidity();
        findTemperature();
//        for (int i = 0; i < filteredMinTemperatureIndex.size(); i++) {
//            System.out.println("min temp " + filteredMinTemperatureIndex.get(i));
//        }
//        for (int i = 0; i < filteredMaxTemperatureIndex.size(); i++) {
//            System.out.println("max temp " + filteredMaxTemperatureIndex.get(i));
//        }
//        for (int i = 0; i < filteredMinHumidityIndex.size(); i++) {
//            System.out.println("min humid " + filteredMinHumidityIndex.get(i));
//        }
//        for (int i = 0; i < filteredMaxHumidityIndex.size(); i++) {
//            System.out.println("max humid " + filteredMaxHumidityIndex.get(i));
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
        double mx = findMaxValue(weatherHumidity);
//        System.out.println("max humid = " + mx);
        addIndexPosition(filteredMaxHumidityIndex, weatherHumidity, mx);
    }

    private void findMinHumidity(List<Double> weatherHumidity) {
        double mn = findMinValue(weatherHumidity);
//        System.out.println("min humid = " + mn);
        addIndexPosition(filteredMinHumidityIndex, weatherHumidity, mn);
    }

    private void findTemperature() {
        List<Double> weatherTemperature = storage.getWeatherTemperature();
        findMaxTemperature(weatherTemperature);
        findMinTemperature(weatherTemperature);
    }

    private void findMaxTemperature(List<Double> weatherTemperature) {
        double mx = findMaxValue(weatherTemperature);
//        System.out.println("max temp is " + mx);
        addIndexPosition(filteredMaxTemperatureIndex, weatherTemperature, mx);
    }

    private void findMinTemperature(List<Double> weatherTemperature) {
        double mn = findMinValue(weatherTemperature);
//        System.out.println("min temp is " + mn);
        addIndexPosition(filteredMinTemperatureIndex, weatherTemperature, mn);
    }

    private double findMaxValue(List<Double> weatherAttribute) {
        double mx = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < filteredStationIndex.size(); i++) {
            int index = filteredStationIndex.get(i);
            if (weatherAttribute.get(index) > mx && !weatherAttribute.get(index).isNaN()) {
                mx = weatherAttribute.get(index);
            }
        }
        return mx;
    }

    private double findMinValue(List<Double> weatherAttribute) {
        double mn = Double.POSITIVE_INFINITY;
        for (int i = 0; i < filteredStationIndex.size(); i++) {
            int index = filteredStationIndex.get(i);
            if (weatherAttribute.get(index) < mn && !weatherAttribute.get(index).isNaN()) {
                mn = weatherAttribute.get(index);
            }
        }
        return mn;
    }

    private void addIndexPosition(List<Integer> fromFilteredIndex, List<Double> weatherAttribute, Double d) {
        for (int i = 0; i < filteredStationIndex.size(); i++) {
            int index = filteredStationIndex.get(i);
            if (weatherAttribute.get(index).equals(d)) {
                fromFilteredIndex.add(index);
            }
        }
    }
}
