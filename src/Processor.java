import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/** Author: Wincent Tjoi
 * Matric Number: N2101669E
 * To process: station and humidity extreme values of each month between year 2009 to 2019 at Changi
 */

public class Processor {
    private final static String EARLIEST_DATE = "2009-01-01 00:00";
    private final static String LATEST_DATE = "2019-01-01 00:00";

    private Storage storage;
    private List<Integer> indexBetweenTimestamp = new ArrayList<>();
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
        
    }

    private void initializeValidTimestamps(int year, int month) {
        List<Date> timestamps = storage.getWeatherTimestamp();
        for (int i = 0; i < timestamps.size(); i++) {
            Calendar minCal = new GregorianCalendar(year, month,1);
            Calendar maxCal = new GregorianCalendar(year, month + 1,1);
            Date minDate = minCal.getTime();
            Date maxDate = maxCal.getTime();
            if (timestamps.get(i).after(minDate) && timestamps.get(i).before(maxDate)) {
                indexBetweenTimestamp.add(i);
            }
        }
    }


}
