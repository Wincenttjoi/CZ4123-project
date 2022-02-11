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
    private static final String COMMA_DELIMITER = ",";

    public static void readWeatherFromCSV(String fileName, Storage storage) {
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
                    String[] attributes = line.split(COMMA_DELIMITER);
                    storage.addAttributes(attributes);
                }
                line = br.readLine();
            }

            if (storage instanceof WeatherDiskStorage) {
                ((WeatherDiskStorage) storage).storeToDiskStorage();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void readCSVHeader(String fileName, Storage storage) {
        List<String> header = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            // read the first line from the text file
            String line = br.readLine();

            // insert header
            String[] attributes = line.split(COMMA_DELIMITER);
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
