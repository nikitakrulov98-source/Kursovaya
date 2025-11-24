import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class SaveButtonListener implements ActionListener {
    private final AvatarService avatarService;
    private final AvatarConfig config;
    private BufferedImage avatar; // убираем final
    private final Runnable onSuccess;
    private final ErrorCallback onError;

    public SaveButtonListener(AvatarService avatarService, AvatarConfig config,
                              BufferedImage avatar, Runnable onSuccess,
                              ErrorCallback onError) {
        this.avatarService = avatarService;
        this.config = config;
        this.avatar = avatar; // теперь можно обновлять
        this.onSuccess = onSuccess;
        this.onError = onError;
    }

    // Добавляем метод для обновления аватара
    public void setAvatar(BufferedImage avatar) {
        this.avatar = avatar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Проверяем, что аватар существует
        if (avatar == null) {
            onError.onError("Сначала сгенерируйте аватар");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Сохранить аватар");
        fileChooser.setSelectedFile(new File(generateFileName()));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    String correctExtension = "." + config.getFormat().toLowerCase();
                    String filePath = fileToSave.getAbsolutePath();

                    if (!filePath.toLowerCase().endsWith(correctExtension)) {
                        filePath = filePath + correctExtension;
                    }

                    FileUtils.saveImage(avatar, config.getFormat(), filePath);
                    return null;
                }

                @Override
                protected void done() {
                    try {
                        get();
                        onSuccess.run();
                    } catch (Exception ex) {
                        onError.onError("Ошибка сохранения: " + ex.getMessage());
                    }
                }
            }.execute();
        }
    }

    private String generateFileName() {
        String safeName = config.getName().replaceAll("[^a-zA-Z0-9а-яА-Я]", "_");
        return String.format("avatar_%s.%s", safeName, config.getFormat().toLowerCase());
    }

    public interface ErrorCallback {
        void onError(String error);
    }
}