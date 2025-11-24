import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    private JTextField nameField;
    private JComboBox<String> sizeComboBox;
    private JButton backgroundColorButton;
    private JButton textColorButton;
    private JComboBox<String> formatComboBox;
    private JCheckBox boldCheckBox;
    private JCheckBox roundedCheckBox;

    private Color currentBackgroundColor = Color.decode("#0D8ABC");
    private Color currentTextColor = Color.WHITE;

    public SettingsPanel() {
        initComponents();
        setupLayout();
    }

    private void initComponents() {
        // Широкое поле для имени - увеличиваем количество колонок
        nameField = new JTextField(25); // Было 20, стало 25 (шире)
        nameField.setFont(new Font("Arial", Font.PLAIN, 12));

        String[] sizes = {"64", "128", "256", "512"};
        sizeComboBox = new JComboBox<>(sizes);
        sizeComboBox.setSelectedIndex(2);

        // Кнопка цвета фона - просто цветной квадрат
        backgroundColorButton = createColorButton(currentBackgroundColor);

        // Кнопка цвета текста - просто цветной квадрат
        textColorButton = createColorButton(currentTextColor);

        String[] formats = {"PNG", "JPG"};
        formatComboBox = new JComboBox<>(formats);

        boldCheckBox = new JCheckBox("Жирный шрифт", true);
        roundedCheckBox = new JCheckBox("Закругленные углы");

        // Стилизуем чекбоксы
        styleCheckBox(boldCheckBox);
        styleCheckBox(roundedCheckBox);
    }

    private JButton createColorButton(Color color) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(40, 25));
        button.setMinimumSize(new Dimension(40, 25));
        button.setMaximumSize(new Dimension(40, 25));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createLineBorder(color, 2)
        ));
        button.setBackground(color);

        // Убираем текст и делаем непрозрачным
        button.setText("");
        button.setOpaque(true);
        button.setContentAreaFilled(true);

        return button;
    }

    private void styleCheckBox(JCheckBox checkBox) {
        checkBox.setFont(new Font("Arial", Font.PLAIN, 12));
        checkBox.setFocusPainted(false);
        checkBox.setOpaque(false);
    }

    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Имя - занимает всю ширину
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Имя:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.weightx = 1.0; // Разрешаем растягиваться
        add(nameField, gbc);
        gbc.weightx = 0.0; // Сбрасываем для следующих элементов

        // Размер
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        add(new JLabel("Размер:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        add(sizeComboBox, gbc);

        // Цвет фона
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        add(new JLabel("Цвет фона:"), gbc);
        gbc.gridx = 1;
        add(backgroundColorButton, gbc);
        // Добавляем текстовое представление цвета
        gbc.gridx = 2;
        JLabel bgColorLabel = new JLabel("#0D8ABC");
        bgColorLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        add(bgColorLabel, gbc);

        // Цвет текста
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Цвет текста:"), gbc);
        gbc.gridx = 1;
        add(textColorButton, gbc);
        // Добавляем текстовое представление цвета
        gbc.gridx = 2;
        JLabel textColorLabel = new JLabel("#FFFFFF");
        textColorLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        add(textColorLabel, gbc);

        // Формат
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Формат:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        add(formatComboBox, gbc);

        // Чекбоксы
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 3;
        add(boldCheckBox, gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 3;
        add(roundedCheckBox, gbc);

        setBorder(BorderFactory.createTitledBorder("Настройки аватара"));
    }

    public void setBackgroundColor(Color color) {
        currentBackgroundColor = color;
        updateColorButton(backgroundColorButton, color);
    }

    public void setTextColor(Color color) {
        currentTextColor = color;
        updateColorButton(textColorButton, color);
    }

    private void updateColorButton(JButton button, Color color) {
        button.setBackground(color);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createLineBorder(color, 2)
        ));

        // Обновляем текстовые метки цветов
        updateColorLabels();
    }

    private void updateColorLabels() {
        // Находим и обновляем текстовые метки цветов
        Component[] components = getComponents();
        for (Component comp : components) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (label.getText().startsWith("#")) {
                    if (label.getText().equals("#0D8ABC") || label.getText().length() == 7) {
                        // Обновляем цвет фона
                        label.setText(String.format("#%06X", currentBackgroundColor.getRGB() & 0xFFFFFF));
                    } else if (label.getText().equals("#FFFFFF")) {
                        // Обновляем цвет текста
                        label.setText(String.format("#%06X", currentTextColor.getRGB() & 0xFFFFFF));
                    }
                }
            }
        }
    }

    // Геттеры
    public JTextField getNameField() {
        return nameField;
    }

    public String getName() {
        return nameField.getText().trim();
    }

    public int getAvatarSize() {
        return Integer.parseInt((String) sizeComboBox.getSelectedItem());
    }

    public Color getBackgroundColor() {
        return currentBackgroundColor;
    }

    public Color getTextColor() {
        return currentTextColor;
    }

    public String getFormat() {
        return (String) formatComboBox.getSelectedItem();
    }

    public boolean isBold() {
        return boldCheckBox.isSelected();
    }

    public boolean isRounded() {
        return roundedCheckBox.isSelected();
    }

    public void addBackgroundColorListener(ActionListener listener) {
        backgroundColorButton.addActionListener(listener);
    }

    public void addTextColorListener(ActionListener listener) {
        textColorButton.addActionListener(listener);
    }

    // Геттеры для элементов управления
    public JComboBox<String> getSizeComboBox() {
        return sizeComboBox;
    }

    public JComboBox<String> getFormatComboBox() {
        return formatComboBox;
    }

    public JCheckBox getBoldCheckBox() {
        return boldCheckBox;
    }

    public JCheckBox getRoundedCheckBox() {
        return roundedCheckBox;
    }
}