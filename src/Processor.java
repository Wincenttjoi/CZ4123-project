import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/** Author: Wincent Tjoi
 * Matric Number: N2101669E
 * To process: station and humidity extreme values of each month between year 2009 to 2019 at Changi
 */

public class Processor {

    private static final String STATION = "Changi";
    private static final int STARTING_YEAR = 2009;
    private static final int ENDING_YEAR = 2019;
    private static final int STARTING_MONTH = 0;
    private static final int ENDING_MONTH = 11;

    private static final int MAX_HUMIDITY = 0;
    private static final int MIN_HUMIDITY = 1;
    private static final int MAX_TEMPERATURE = 2;
    private static final int MIN_TEMPERATURE = 3;

    private Storage storage;

    private List<Integer> timestampIndex = new ArrayList<>();
    private List<Integer> filteredStationIndex = new ArrayList<>();
    private List<Integer> filteredMaxHumidityIndex = new ArrayList<>();
    private List<Integer> filteredMinHumidityIndex = new ArrayList<>();
    private List<Integer> filteredMaxTemperatureIndex = new ArrayList<>();
    private List<Integer> filteredMinTemperatureIndex = new ArrayList<>();

    FileWriter csvWriter;

    public Processor(Storage storage) {
        this.storage = storage;
    }

    public void processData() {
        try {
            csvWriter = new FileWriter(Common.CSV_FILE_OUTPUT);
            appendCsvHeaderOutput();
            for (int year = STARTING_YEAR; year <= ENDING_YEAR; year++) {
                for (int month = STARTING_MONTH; month <= ENDING_MONTH; month++) {
                    initializeValidTimestamps(year, month);
                    filterStation(STATION);
                    findHumidity();
                    findTemperature();
                    writeOutputIntoCSV();
                    clearIndexes();
                }
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        addIndexPosition(filteredMaxHumidityIndex, weatherHumidity, mx);
    }

    private void findMinHumidity(List<Double> weatherHumidity) {
        double mn = findMinValue(weatherHumidity);
        addIndexPosition(filteredMinHumidityIndex, weatherHumidity, mn);
    }

    private void findTemperature() {
        List<Double> weatherTemperature = storage.getWeatherTemperature();
        findMaxTemperature(weatherTemperature);
        findMinTemperature(weatherTemperature);
    }

    private void findMaxTemperature(List<Double> weatherTemperature) {
        double mx = findMaxValue(weatherTemperature);
        addIndexPosition(filteredMaxTemperatureIndex, weatherTemperature, mx);
    }

    private void findMinTemperature(List<Double> weatherTemperature) {
        double mn = findMinValue(weatherTemperature);
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

    private void clearIndexes() {
        timestampIndex.clear();
        filteredStationIndex.clear();
        filteredMaxHumidityIndex.clear();
        filteredMinHumidityIndex.clear();
        filteredMaxTemperatureIndex.clear();
        filteredMinTemperatureIndex.clear();
    }

    private void writeOutputIntoCSV() {
        appendCsvRow(filteredMaxHumidityIndex, MAX_HUMIDITY);
        appendCsvRow(filteredMinHumidityIndex, MIN_HUMIDITY);
        appendCsvRow(filteredMaxTemperatureIndex, MAX_TEMPERATURE);
        appendCsvRow(filteredMinTemperatureIndex, MIN_TEMPERATURE);
    }

    private void appendCsvHeaderOutput() {
        try {
            csvWriter.append("Date");
            csvWriter.append(",");
            csvWriter.append("Station");
            csvWriter.append(",");
            csvWriter.append("Category");
            csvWriter.append(",");
            csvWriter.append("Value");
            csvWriter.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendCsvRow(List<Integer> filteredIndex, int type) {
        try {
            for (int i = 0; i < filteredIndex.size(); i++) {
                int index = filteredIndex.get(i);
                List<Object> attributes = storage.getIndexAttributes(index);
                appendDate(attributes.get(0));
                appendWeatherStation(attributes.get(1));

                if (type == MAX_HUMIDITY) {
                    csvWriter.append("Max Humidity");
                } else if (type == MIN_HUMIDITY) {
                    csvWriter.append("Min Humidity");
                } else if (type == MAX_TEMPERATURE) {
                    csvWriter.append("Max Temperature");
                } else if (type == MIN_TEMPERATURE) {
                    csvWriter.append("Min Temperature");
                } else {
                    throw new IOException();
                }

                if (type == MAX_TEMPERATURE || type == MIN_TEMPERATURE) {
                    appendValue(attributes.get(2));
                } else if (type == MAX_HUMIDITY || type == MIN_HUMIDITY) {
                    appendValue(attributes.get(3));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String convertDateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(Common.DATE_FORMAT);
        String strDate = dateFormat.format(date);
        return strDate;
    }

    private void appendDate(Object attributes) {
        try {
            Date date = (Date) attributes;
            String strDate = convertDateToString(date);
            csvWriter.append(strDate);
            csvWriter.append(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendWeatherStation(Object attributes) {
        try {
            String weatherStation = (String) attributes;
            csvWriter.append(weatherStation);
            csvWriter.append(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendValue(Object attributes) {
        try {
            csvWriter.append(",");
            String value = attributes.toString();
            csvWriter.append(value);
            csvWriter.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
