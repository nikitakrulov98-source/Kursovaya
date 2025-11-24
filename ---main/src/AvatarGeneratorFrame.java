import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class AvatarGeneratorFrame extends JFrame {
    private AvatarService avatarService;
    private AvatarConfig avatarConfig;

    private AvatarPanel avatarPanel;
    private SettingsPanel settingsPanel;
    private ControlPanel controlPanel;

    private BufferedImage currentAvatar;
    private SaveButtonListener saveButtonListener;

    public AvatarGeneratorFrame() {
        this.avatarService = new UIAvatarService();
        this.avatarConfig = new AvatarConfig();
        initComponents();
        setupLayout();
        setupListeners();
        setupFrame();
    }

    private void initComponents() {
        avatarPanel = new AvatarPanel();
        settingsPanel = new SettingsPanel();
        controlPanel = new ControlPanel();
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // Левая панель - только аватар
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(400, 500));
        leftPanel.add(avatarPanel, BorderLayout.CENTER);

        // Правая панель - настройки и управление
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(300, 500));
        rightPanel.add(settingsPanel, BorderLayout.CENTER);
        rightPanel.add(controlPanel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void setupListeners() {
        // Инициализируем слушатель сохранения
        saveButtonListener = new SaveButtonListener(
                avatarService, avatarConfig, currentAvatar,
                new Runnable() {
                    @Override
                    public void run() {
                        showMessage("Аватар успешно сохранен!", "Успех", JOptionPane.INFORMATION_MESSAGE);
                    }
                },
                new SaveButtonListener.ErrorCallback() {
                    @Override
                    public void onError(String error) {
                        showMessage(error, "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                });

        settingsPanel.addBackgroundColorListener(new ColorChangeListener(this,
                new ColorChangeListener.ColorCallback() {
                    @Override
                    public void onColorSelected(Color color) {
                        settingsPanel.setBackgroundColor(color);
                        avatarConfig.setBackgroundColor(color);
                        regenerateAvatarIfExists();
                    }
                }));

        settingsPanel.addTextColorListener(new ColorChangeListener(this,
                new ColorChangeListener.ColorCallback() {
                    @Override
                    public void onColorSelected(Color color) {
                        settingsPanel.setTextColor(color);
                        avatarConfig.setTextColor(color);
                        regenerateAvatarIfExists();
                    }
                }));

        controlPanel.getGenerateButton().addActionListener(new GenerateButtonListener(
                avatarService, avatarConfig,
                new Runnable() {
                    @Override
                    public void run() {
                        controlPanel.setSaveEnabled(true);
                        showMessage("Аватар успешно сгенерирован!", "Успех", JOptionPane.INFORMATION_MESSAGE);
                    }
                },
                new GenerateButtonListener.AvatarCallback() {
                    @Override
                    public void onAvatarGenerated(BufferedImage avatar) {
                        currentAvatar = avatar;
                        avatarPanel.setAvatar(avatar);
                        // Обновляем аватар в слушателе сохранения
                        if (saveButtonListener != null) {
                            saveButtonListener.setAvatar(avatar);
                        }
                    }
                },
                new GenerateButtonListener.ErrorCallback() {
                    @Override
                    public void onError(String error) {
                        showMessage(error, "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }));

        controlPanel.getSaveButton().addActionListener(saveButtonListener);

        controlPanel.getClearButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAll();
            }
        });

        addInstantPreviewListeners();
    }

    private void addInstantPreviewListeners() {
        // Слушатель для поля имени
        settingsPanel.getNameField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateConfigFromSettings();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateConfigFromSettings();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateConfigFromSettings();
            }
        });

        // Слушатели для комбобоксов и чекбоксов
        settingsPanel.getSizeComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateConfigFromSettings();
                regenerateAvatarIfExists();
            }
        });

        settingsPanel.getFormatComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateConfigFromSettings();
            }
        });

        settingsPanel.getBoldCheckBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateConfigFromSettings();
                regenerateAvatarIfExists();
            }
        });

        settingsPanel.getRoundedCheckBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateConfigFromSettings();
                regenerateAvatarIfExists();
            }
        });
    }

    // Метод для перегенерации аватара если он уже существует
    private void regenerateAvatarIfExists() {
        if (currentAvatar != null && avatarConfig.getName() != null && !avatarConfig.getName().trim().isEmpty()) {
            new SwingWorker<BufferedImage, Void>() {
                @Override
                protected BufferedImage doInBackground() throws Exception {
                    return avatarService.generateAvatar(avatarConfig);
                }

                @Override
                protected void done() {
                    try {
                        BufferedImage newAvatar = get();
                        currentAvatar = newAvatar;
                        avatarPanel.setAvatar(newAvatar);
                        // Обновляем аватар в слушателе сохранения
                        if (saveButtonListener != null) {
                            saveButtonListener.setAvatar(newAvatar);
                        }
                    } catch (Exception ex) {
                        // Игнорируем ошибки при автоматической перегенерации
                    }
                }
            }.execute();
        }
    }

    private void updateConfigFromSettings() {
        avatarConfig.setName(settingsPanel.getName());
        avatarConfig.setSize(settingsPanel.getAvatarSize());
        avatarConfig.setBackgroundColor(settingsPanel.getBackgroundColor());
        avatarConfig.setTextColor(settingsPanel.getTextColor());
        avatarConfig.setFormat(settingsPanel.getFormat());
        avatarConfig.setBold(settingsPanel.isBold());
        avatarConfig.setRounded(settingsPanel.isRounded());
    }

    private void clearAll() {
        settingsPanel.getNameField().setText("");
        avatarConfig = new AvatarConfig();
        settingsPanel.setBackgroundColor(avatarConfig.getBackgroundColor());
        settingsPanel.setTextColor(avatarConfig.getTextColor());
        currentAvatar = null;
        avatarPanel.clearAvatar();
        controlPanel.setSaveEnabled(false);

        // Сбрасываем аватар в слушателе сохранения
        if (saveButtonListener != null) {
            saveButtonListener.setAvatar(null);
        }
    }

    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private void setupFrame() {
        setTitle("Генератор Аватаров по Инициалам");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 500));
        setMinimumSize(new Dimension(700, 400));
        pack();
        setLocationRelativeTo(null);
    }
}