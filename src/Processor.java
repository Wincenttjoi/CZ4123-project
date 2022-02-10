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

    private Integer[] maxHumidity = new Integer[12];
    private Integer[] minHumidity = new Integer[12];
    private Integer[] maxTemperature = new Integer[12];
    private Integer[] minTemperature = new Integer[12];

    public Processor(Storage storage) {
        this.storage = storage;
    }

    public void processData() {
        // month from 0 to 11
        initializeValidTimestamps(2019, 11);
        filterStation(STATION);
        
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
                System.out.println(index);
            }
        }
    }


}
