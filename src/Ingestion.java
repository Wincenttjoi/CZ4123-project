import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class Ingestion {
    public static void main(String[] args) {

        // Toggle type for task 2
        WeatherMemoryStorage memStorage = new WeatherMemoryStorage();

        readCSVHeader("src/SingaporeWeather.csv", memStorage);

        readWeatherFromCSV("src/SingaporeWeather.csv", memStorage);

        for (Double w : memStorage.getWeatherHumidity()) {
            System.out.println(w);
        }

    }

    private static void readWeatherFromCSV(String fileName, Storage storage) {
        List<Weather> weathers = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);
        Boolean isHeader = TRUE;
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {
                if (isHeader) {
                    isHeader = FALSE;
                } else {
                    String[] attributes = line.split(",");
                    storage.addAttributes(attributes);
                }
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void readCSVHeader(String fileName, Storage storage) {
        List<String> header = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            // read the first line from the text file
            String line = br.readLine();

            // insert header
            String[] attributes = line.split(",");
            for (int i = 0; i < 5; i++) {
                header.add(attributes[i]);
            }
            line = br.readLine();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        storage.addHeaders(header);
    }

}
