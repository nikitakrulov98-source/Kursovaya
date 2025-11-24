public class AvatarRequest {
    private AvatarConfig config;
    private boolean saveToFile;
    private String outputPath;

    public AvatarRequest(AvatarConfig config) {
        this.config = config;
        this.saveToFile = false;
        this.outputPath = "output/avatars/";
    }

    public AvatarConfig getConfig() { return config; }
    public void setConfig(AvatarConfig config) { this.config = config; }

    public boolean isSaveToFile() { return saveToFile; }
    public void setSaveToFile(boolean saveToFile) { this.saveToFile = saveToFile; }

    public String getOutputPath() { return outputPath; }
    public void setOutputPath(String outputPath) { this.outputPath = outputPath; }
}