import javax.swing.*;
import java.awt.*;

public class PreviewPanel extends JPanel {
    private JLabel previewLabel;

    public PreviewPanel() {
        initComponents();
        setupLayout();
    }

    private void initComponents() {
        previewLabel = new JLabel("Предпросмотр появится здесь");
        previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        previewLabel.setVerticalAlignment(SwingConstants.CENTER);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 100));
        setBorder(BorderFactory.createTitledBorder("Предпросмотр"));
        add(previewLabel, BorderLayout.CENTER);
    }

    public void setPreviewText(String text) {
        previewLabel.setText(text);
    }

    public void setPreviewImage(ImageIcon icon) {
        previewLabel.setIcon(icon);
        previewLabel.setText("");
    }

    public void clearPreview() {
        previewLabel.setIcon(null);
        previewLabel.setText("Предпросмотр появится здесь");
    }
}