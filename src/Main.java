/**
 * Main class to run the program.
 * To process data of 4 extreme values (max and min of humidity and temperature)
 * of each month between 2009 and 2019.
 * Output is a CSV file ScanResult.
 */
public class Main {
    public static void main(String[] args) {

        // Toggle storage type for task 2
//        WeatherMemoryStorage storage = new WeatherMemoryStorage();
        WeatherDiskStorage storage = new WeatherDiskStorage();

        Ingestion.readWeatherFromCSV(Common.CSV_FILE_PATH, storage);

        Processor processor = new Processor(storage);
        processor.processData();
    }
}
