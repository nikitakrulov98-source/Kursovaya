import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class GenerateButtonListener implements ActionListener {
    private final AvatarService avatarService;
    private final AvatarConfig config;
    private final Runnable onSuccess;
    private final AvatarCallback onAvatarGenerated;
    private final ErrorCallback onError;

    public GenerateButtonListener(AvatarService avatarService, AvatarConfig config,
                                  Runnable onSuccess, AvatarCallback onAvatarGenerated,
                                  ErrorCallback onError) {
        this.avatarService = avatarService;
        this.config = config;
        this.onSuccess = onSuccess;
        this.onAvatarGenerated = onAvatarGenerated;
        this.onError = onError;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (config.getName() == null || config.getName().trim().isEmpty()) {
            onError.onError("Введите имя для генерации аватара");
            return;
        }

        new SwingWorker<BufferedImage, Void>() {
            @Override
            protected BufferedImage doInBackground() throws Exception {
                return avatarService.generateAvatar(config);
            }

            @Override
            protected void done() {
                try {
                    BufferedImage avatar = get();
                    onAvatarGenerated.onAvatarGenerated(avatar);
                    onSuccess.run();
                } catch (Exception ex) {
                    onError.onError("Ошибка генерации аватара: " + ex.getMessage());
                }
            }
        }.execute();
    }

    public interface AvatarCallback {
        void onAvatarGenerated(BufferedImage avatar);
    }

    public interface ErrorCallback {
        void onError(String error);
    }
}