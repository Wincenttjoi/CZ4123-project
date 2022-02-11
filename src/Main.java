public class Main {
    public static void main(String[] args) {

        // Toggle type for task 2
//        WeatherMemoryStorage storage = new WeatherMemoryStorage();

        WeatherDiskStorage storage = new WeatherDiskStorage();
//        Ingestion.readCSVHeader(Common.CSV_FILE_PATH, memStorage);

        Ingestion.readWeatherFromCSV(Common.CSV_FILE_PATH, storage);

        Processor processor = new Processor(storage);
        processor.processData();
    }
}
