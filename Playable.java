/**
 * Interface for items that can be played (audiobooks, podcasts, etc.)
 * Provides methods for playback functionality
 */
public interface Playable {
    
    /**
     * Gets the duration of the playable content
     * @return duration in minutes
     */
    int getDuration();
    
    /**
     * Gets the playback format (e.g., "MP3", "AAC", "WAV")
     * @return format string
     */
    String getFormat();
    
    /**
     * Gets the file size in MB
     * @return file size in megabytes
     */
    double getFileSize();
    
    /**
     * Checks if the content is downloadable
     * @return true if downloadable, false otherwise
     */
    boolean isDownloadable();
    
    /**
     * Gets playback quality information
     * @return quality string (e.g., "High", "Medium", "Low")
     */
    String getQuality();
}
