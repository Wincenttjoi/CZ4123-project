import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Stores and retrieves data from/to disk storage
 */
public class ResourceManager {

    public void saveData(String filename, List<? extends Object> lst) {
        XMLEncoder encoder = null;
        try {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
        } catch (FileNotFoundException fileNotFound) {
            System.out.println("ERROR: While Creating or Opening the File dvd.xml");
        }
        encoder.writeObject(lst);
        encoder.close();
    }

    public List<String> loadDataString(String filename) {
        XMLDecoder decoder = null;
        try {
            decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File dvd.xml not found");
        }
        List<String> weatherAttribute = (ArrayList<String>)decoder.readObject();

        return weatherAttribute;
    }

    public List<Date> loadDataDate(String filename) {
        XMLDecoder decoder = null;
        try {
            decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File dvd.xml not found");
        }
        List<Date> weatherAttribute = (ArrayList<Date>)decoder.readObject();

        return weatherAttribute;
    }

    public List<Double> loadDataDouble(String filename) {
        XMLDecoder decoder = null;
        try {
            decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File dvd.xml not found");
        }
        List<Double> weatherAttribute = (ArrayList<Double>)decoder.readObject();

        return weatherAttribute;
    }
}
