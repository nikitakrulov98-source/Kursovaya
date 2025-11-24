import java.awt.image.BufferedImage;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UIAvatarService implements AvatarService {
    private static final String API_BASE_URL = "https://ui-avatars.com/api/";

    @Override
    public String generateAvatarUrl(AvatarConfig config) throws AvatarGenerationException {
        try {
            if (!NameUtils.isValidName(config.getName())) {
                throw new AvatarGenerationException("Неверное имя пользователя");
            }

            String initials = NameUtils.extractInitials(config.getName());
            String backgroundColor = String.format("%06X", config.getBackgroundColor().getRGB() & 0xFFFFFF);
            String textColor = String.format("%06X", config.getTextColor().getRGB() & 0xFFFFFF);

            StringBuilder urlBuilder = new StringBuilder(API_BASE_URL);
            urlBuilder.append("?name=").append(URLEncoder.encode(initials, StandardCharsets.UTF_8.name()));
            urlBuilder.append("&size=").append(config.getSize());
            urlBuilder.append("&background=").append(backgroundColor);
            urlBuilder.append("&color=").append(textColor);
            urlBuilder.append("&bold=").append(config.isBold());
            urlBuilder.append("&format=").append(config.getFormat().toLowerCase());
            urlBuilder.append("&length=").append(initials.length());

            if (config.isRounded()) {
                urlBuilder.append("&rounded=true");
            }

            return urlBuilder.toString();

        } catch (Exception e) {
            throw new AvatarGenerationException("Ошибка генерации URL: " + e.getMessage(), e);
        }
    }

    @Override
    public BufferedImage generateAvatar(AvatarConfig config) throws AvatarGenerationException {
        try {
            String avatarUrl = generateAvatarUrl(config);
            return HttpDownloader.downloadImage(avatarUrl);
        } catch (Exception e) {
            throw new AvatarGenerationException("Ошибка генерации аватара: " + e.getMessage(), e);
        }
    }

    @Override
    public void saveAvatar(BufferedImage avatar, AvatarConfig config, String filePath) throws AvatarGenerationException {
        try {
            FileUtils.createDirectoryIfNotExists(filePath);
            String fileName = NameUtils.generateFileName(config.getName(), config.getFormat());
            String fullPath = filePath + "/" + fileName;
            FileUtils.saveImage(avatar, config.getFormat(), fullPath);
        } catch (Exception e) {
            throw new AvatarGenerationException("Ошибка сохранения аватара: " + e.getMessage(), e);
        }
    }
}