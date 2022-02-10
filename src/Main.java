public class Main {
    public static void main(String[] args) {

        // Toggle type for task 2
        WeatherMemoryStorage memStorage = new WeatherMemoryStorage();

//        Ingestion.readCSVHeader(Common.CSV_FILE_PATH, memStorage);

        Ingestion.readWeatherFromCSV(Common.CSV_FILE_PATH, memStorage);

        Processor processor = new Processor(memStorage);
        processor.processData();
    }
}
