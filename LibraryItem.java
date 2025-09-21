import java.time.LocalDate;
import java.util.Objects;

/**
 * Abstract base class for all library items (books, audiobooks, e-magazines)
 * Provides common attributes and operations for all library items
 */
public abstract class LibraryItem {
    protected int itemId;
    protected String title;
    protected String author;
    protected boolean isAvailable;
    protected LocalDate borrowDate;
    protected LocalDate dueDate;
    protected String borrowerName;
    
    // Fine rate per day (in rupees)
    protected static final double FINE_RATE_PER_DAY = 10.0;
    
    /**
     * Constructor for LibraryItem
     * @param itemId Unique identifier for the item
     * @param title Title of the item
     * @param author Author of the item
     * @throws IllegalArgumentException if itemId is negative or title/author is null/empty
     */
    public LibraryItem(int itemId, String title, String author) {
        validateInputs(itemId, title, author);
        this.itemId = itemId;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.borrowDate = null;
        this.dueDate = null;
        this.borrowerName = null;
    }
    
    /**
     * Validates input parameters
     */
    private void validateInputs(int itemId, String title, String author) {
        if (itemId < 0) {
            throw new IllegalArgumentException("Item ID cannot be negative: " + itemId);
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }
    }
    
    /**
     * Borrows the item to a borrower
     * @param borrowerName Name of the borrower
     * @param borrowDuration Duration string (e.g., "7 days", "2 weeks")
     * @throws IllegalStateException if item is not available
     * @throws IllegalArgumentException if borrower name is invalid or duration parsing fails
     */
    public void borrow(String borrowerName, String borrowDuration) {
        if (!isAvailable) {
            throw new IllegalStateException("Item " + itemId + " is not available for borrowing");
        }
        
        validateBorrowerName(borrowerName);
        LocalDate duration = parseBorrowDuration(borrowDuration);
        
        this.borrowerName = borrowerName.trim();
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(duration.toEpochDay());
        this.isAvailable = false;
    }
    
    /**
     * Returns the item
     * @throws IllegalStateException if item is already available
     */
    public void returnItem() {
        if (isAvailable) {
            throw new IllegalStateException("Item " + itemId + " is already available");
        }
        
        this.borrowerName = null;
        this.borrowDate = null;
        this.dueDate = null;
        this.isAvailable = true;
    }
    
    /**
     * Checks if the item is available for borrowing
     * @return true if available, false otherwise
     */
    public boolean isAvailable() {
        return isAvailable;
    }
    
    /**
     * Calculates fine for overdue items
     * @return fine amount in rupees, 0 if not overdue
     */
    public double calculateFine() {
        if (isAvailable || dueDate == null) {
            return 0.0;
        }
        
        LocalDate currentDate = LocalDate.now();
        if (currentDate.isAfter(dueDate)) {
            long overdueDays = currentDate.toEpochDay() - dueDate.toEpochDay();
            return overdueDays * FINE_RATE_PER_DAY;
        }
        
        return 0.0;
    }
    
    /**
     * Parses borrow duration string to LocalDate
     * @param duration Duration string (e.g., "7 days", "2 weeks", "1 month")
     * @return LocalDate representing the duration
     * @throws IllegalArgumentException if duration format is invalid
     */
    protected LocalDate parseBorrowDuration(String duration) {
        if (duration == null || duration.trim().isEmpty()) {
            throw new IllegalArgumentException("Borrow duration cannot be null or empty");
        }
        
        String[] parts = duration.trim().toLowerCase().split("\\s+");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid duration format. Expected format: 'number unit' (e.g., '7 days')");
        }
        
        try {
            int amount = Integer.parseInt(parts[0]);
            if (amount <= 0) {
                throw new IllegalArgumentException("Duration amount must be positive: " + amount);
            }
            
            String unit = parts[1];
            switch (unit) {
                case "day":
                case "days":
                    return LocalDate.ofEpochDay(amount);
                case "week":
                case "weeks":
                    return LocalDate.ofEpochDay(amount * 7L);
                case "month":
                case "months":
                    return LocalDate.ofEpochDay(amount * 30L); // Approximate
                default:
                    throw new IllegalArgumentException("Unsupported time unit: " + unit);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid duration amount: " + parts[0], e);
        }
    }
    
    /**
     * Validates borrower name
     */
    private void validateBorrowerName(String borrowerName) {
        if (borrowerName == null || borrowerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Borrower name cannot be null or empty");
        }
    }
    
    // Getters
    public int getItemId() { return itemId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public String getBorrowerName() { return borrowerName; }
    
    // Abstract methods to be implemented by subclasses
    public abstract String getItemType();
    public abstract String getSpecificInfo();
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LibraryItem that = (LibraryItem) obj;
        return itemId == that.itemId;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }
    
    @Override
    public String toString() {
        return String.format("%s [ID: %d, Title: %s, Author: %s, Available: %s%s]",
                getItemType(), itemId, title, author, isAvailable,
                !isAvailable ? String.format(", Borrower: %s, Due: %s", borrowerName, dueDate) : "");
    }
}
