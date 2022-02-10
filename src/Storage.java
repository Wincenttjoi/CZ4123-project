import java.text.ParseException;
import java.util.List;

public interface Storage {
    public void addHeaders(List<String> headers);
    public void addAttributes(String[] attributes) throws ParseException;
}
