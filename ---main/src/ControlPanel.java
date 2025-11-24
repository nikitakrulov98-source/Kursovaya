import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private JButton generateButton;
    private JButton saveButton;
    private JButton clearButton;

    public ControlPanel() {
        initComponents();
        setupLayout();
    }

    private void initComponents() {
        // Кнопка "Сгенерировать" - сплошная зеленая с белым текстом
        generateButton = createSolidButton("Сгенерировать", new Color(46, 125, 50));

        // Кнопка "Сохранить" - сплошная синяя с белым текстом
        saveButton = createSolidButton("Сохранить", new Color(21, 101, 192));
        saveButton.setEnabled(false);

        // Кнопка "Очистить" - сплошная красная с белым текстом
        clearButton = createSolidButton("Очистить", new Color(198, 40, 40));
    }

    private JButton createSolidButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);

        // Убираем все стандартные стили
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE); // Белый текст для всех кнопок
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);

        // Убираем границы и добавляем отступы
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Жирный белый шрифт
        button.setFont(new Font("Arial", Font.BOLD, 12));

        // Эффект при наведении
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.brighter());
            }
        });

        return button;
    }

    private void setupLayout() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        add(generateButton);
        add(saveButton);
        add(clearButton);
    }

    public JButton getGenerateButton() { return generateButton; }
    public JButton getSaveButton() { return saveButton; }
    public JButton getClearButton() { return clearButton; }

    public void setSaveEnabled(boolean enabled) {
        saveButton.setEnabled(enabled);
        if (enabled) {
            saveButton.setBackground(new Color(21, 101, 192)); // Синий
            saveButton.setForeground(Color.WHITE); // Белый текст
        } else {
            saveButton.setBackground(Color.GRAY); // Серый когда отключена
            saveButton.setForeground(Color.WHITE); // Белый текст даже когда отключена
        }
    }
}