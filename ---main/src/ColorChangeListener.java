import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorChangeListener implements ActionListener {
    private final Component parent;
    private final ColorCallback onColorSelected;

    public ColorChangeListener(Component parent, ColorCallback onColorSelected) {
        this.parent = parent;
        this.onColorSelected = onColorSelected;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Color currentColor = ((JButton) e.getSource()).getBackground();
        Color newColor = JColorChooser.showDialog(parent, "Выберите цвет", currentColor);

        if (newColor != null) {
            onColorSelected.onColorSelected(newColor);
        }
    }

    public interface ColorCallback {
        void onColorSelected(Color color);
    }
}