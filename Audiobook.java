/**
 * Concrete class representing an audiobook
 * Extends LibraryItem and implements Playable interface
 */
public class Audiobook extends LibraryItem implements Playable {
    private int duration; // in minutes
    private String narrator;
    private String format;
    private double fileSize; // in MB
    private boolean downloadable;
    private String quality;
    private String language;
    
    /**
     * Constructor for Audiobook
     * @param itemId Unique identifier for the audiobook
     * @param title Title of the audiobook
     * @param author Author of the audiobook
     * @param duration Duration in minutes
     * @param narrator Name of the narrator
     * @param format Audio format (e.g., "MP3", "AAC")
     * @param fileSize File size in MB
     * @param downloadable Whether the audiobook can be downloaded
     * @param quality Audio quality (e.g., "High", "Medium", "Low")
     * @param language Language of the audiobook
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Audiobook(int itemId, String title, String author, int duration, 
                    String narrator, String format, double fileSize, 
                    boolean downloadable, String quality, String language) {
        super(itemId, title, author);
        validateAudiobookInputs(duration, narrator, format, fileSize, quality, language);
        
        this.duration = duration;
        this.narrator = narrator;
        this.format = format;
        this.fileSize = fileSize;
        this.downloadable = downloadable;
        this.quality = quality;
        this.language = language;
    }
    
    /**
     * Validates audiobook-specific inputs
     */
    private void validateAudiobookInputs(int duration, String narrator, String format, 
                                       double fileSize, String quality, String language) {
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be positive: " + duration);
        }
        if (narrator == null || narrator.trim().isEmpty()) {
            throw new IllegalArgumentException("Narrator cannot be null or empty");
        }
        if (format == null || format.trim().isEmpty()) {
            throw new IllegalArgumentException("Format cannot be null or empty");
        }
        if (fileSize <= 0) {
            throw new IllegalArgumentException("File size must be positive: " + fileSize);
        }
        if (quality == null || quality.trim().isEmpty()) {
            throw new IllegalArgumentException("Quality cannot be null or empty");
        }
        if (language == null || language.trim().isEmpty()) {
            throw new IllegalArgumentException("Language cannot be null or empty");
        }
    }
    
    // Playable interface implementation
    @Override
    public int getDuration() {
        return duration;
    }
    
    @Override
    public String getFormat() {
        return format;
    }
    
    @Override
    public double getFileSize() {
        return fileSize;
    }
    
    @Override
    public boolean isDownloadable() {
        return downloadable;
    }
    
    @Override
    public String getQuality() {
        return quality;
    }
    
    /**
     * Gets the narrator of the audiobook
     * @return narrator name
     */
    public String getNarrator() {
        return narrator;
    }
    
    /**
     * Gets the language of the audiobook
     * @return language string
     */
    public String getLanguage() {
        return language;
    }
    
    /**
     * Calculates estimated listening time in hours
     * @return duration in hours (rounded to 1 decimal place)
     */
    public double getDurationInHours() {
        return Math.round((duration / 60.0) * 10.0) / 10.0;
    }
    
    /**
     * Gets playback speed options
     * @return array of available speeds
     */
    public double[] getPlaybackSpeeds() {
        return new double[]{0.5, 0.75, 1.0, 1.25, 1.5, 2.0};
    }
    
    @Override
    public String getItemType() {
        return "Audiobook";
    }
    
    @Override
    public String getSpecificInfo() {
        return String.format("Duration: %d min (%.1f hrs), Narrator: %s, Format: %s, " +
                           "Size: %.1f MB, Quality: %s, Language: %s, Downloadable: %s",
                duration, getDurationInHours(), narrator, format, fileSize, quality, language, downloadable);
    }
    
    @Override
    public String toString() {
        return String.format("%s [ID: %d, Title: %s, Author: %s, Duration: %d min, Available: %s%s]",
                getItemType(), getItemId(), getTitle(), getAuthor(), duration, isAvailable(),
                !isAvailable() ? String.format(", Borrower: %s, Due: %s", getBorrowerName(), getDueDate()) : "");
    }
}
