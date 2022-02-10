import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class Ingestion {
    public static void main(String[] args) {
        List<String> header = readCSVHeader("src/SingaporeWeather.csv");

        List<Weather> weathers = readWeatherFromCSV("src/SingaporeWeather.csv"); // let's print all the person read from CSV file for (Book b : books) { System.out.println(b); }
        for (Weather w : weathers) {
            System.out.println(w);
        }

    }

    private static List<Weather> readWeatherFromCSV(String fileName) {
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
                    Weather weather = createWeather(attributes);
                    weathers.add(weather);
                }
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return weathers;
    }

    private static List<String> readCSVHeader(String fileName) {
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
        return header;
    }

    private static Weather createWeather(String[] metadata) {
        int id = Integer.parseInt(metadata[0]);
        String timestamp;
        if (!metadata[1].equals("M")) {
            timestamp = metadata[1];
        } else {
            timestamp = "";
        }
        String station;
        if (!metadata[2].equals("M")) {
            station = metadata[2];
        } else {
            station = "";
        }
        double temperature;
        if (!metadata[3].equals("M")) {
            temperature = Double.parseDouble(metadata[3]);
        } else {
            temperature = Double.NaN;
        }
        double humidity;
        if (!metadata[4].equals("M")) {
            humidity = Double.parseDouble(metadata[4]);
        } else {
            humidity = Double.NaN;
        }
        return new Weather(id, timestamp, station, temperature, humidity);
    }

}
