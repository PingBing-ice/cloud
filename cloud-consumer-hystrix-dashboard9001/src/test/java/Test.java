import java.net.MalformedURLException;
import java.net.URL;

public class Test {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://www.baidu.com");
            System.out.println(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
