import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloader {

    public static BufferedImage downloadImage(String imageUrl) throws AvatarGenerationException {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new AvatarGenerationException("HTTP error code: " + responseCode);
            }

            return ImageIO.read(connection.getInputStream());

        } catch (IOException e) {
            throw new AvatarGenerationException("Ошибка загрузки изображения: " + e.getMessage(), e);
        }
    }
}