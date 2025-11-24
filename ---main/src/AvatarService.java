import java.awt.image.BufferedImage;

public interface AvatarService {
    String generateAvatarUrl(AvatarConfig config) throws AvatarGenerationException;
    BufferedImage generateAvatar(AvatarConfig config) throws AvatarGenerationException;
    void saveAvatar(BufferedImage avatar, AvatarConfig config, String filePath) throws AvatarGenerationException;
}