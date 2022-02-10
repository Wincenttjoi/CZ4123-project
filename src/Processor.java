import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        initializeValidTimestamps();
        for (int i : indexBetweenTimestamp) {
            System.out.println(i);
        }
    }

    private void initializeValidTimestamps() {
        try {
            List<Date> timestamps = new ArrayList<>();
            timestamps = storage.getWeatherTimestamp();
            for (int i = 0; i < timestamps.size(); i++) {
                if (timestamps.get(i).after(new SimpleDateFormat(Common.DATE_FORMAT).parse(EARLIEST_DATE)) &&
                        timestamps.get(i).before(new SimpleDateFormat(Common.DATE_FORMAT).parse(LATEST_DATE))) {
                    indexBetweenTimestamp.add(i);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}
