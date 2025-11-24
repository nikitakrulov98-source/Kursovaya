import java.awt.Color;

public class AvatarConfig {
    private String name;
    private int size;
    private Color backgroundColor;
    private Color textColor;
    private String format;
    private boolean bold;
    private boolean rounded;

    public AvatarConfig() {
        this.size = 256;
        this.backgroundColor = Color.decode("#0D8ABC");
        this.textColor = Color.WHITE;
        this.format = "PNG";
        this.bold = true;
        this.rounded = false;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public Color getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(Color backgroundColor) { this.backgroundColor = backgroundColor; }

    public Color getTextColor() { return textColor; }
    public void setTextColor(Color textColor) { this.textColor = textColor; }

    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }

    public boolean isBold() { return bold; }
    public void setBold(boolean bold) { this.bold = bold; }

    public boolean isRounded() { return rounded; }
    public void setRounded(boolean rounded) { this.rounded = rounded; }
}