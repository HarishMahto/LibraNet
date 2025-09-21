import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Demo class showcasing the LibraNet library management system
 * Demonstrates all features including borrowing, returning, searching, and fine management
 */
public class LibraNetDemo {
    
    public static void main(String[] args) {
        System.out.println("=== LibraNet Library Management System Demo ===\n");
        
        // Create library manager
        LibraryManager library = new LibraryManager();
        
        try {
            // Create sample items
            createSampleItems(library);
            
            // Demonstrate basic operations
            demonstrateBasicOperations(library);
            
            // Demonstrate searching functionality
            demonstrateSearching(library);
            
            // Demonstrate fine calculation
            demonstrateFineCalculation(library);
            
            // Demonstrate specialized behaviors
            demonstrateSpecializedBehaviors(library);
            
            // Show library statistics
            showLibraryStatistics(library);
            
            // Demonstrate error handling
            demonstrateErrorHandling(library);
            
        } catch (Exception e) {
            System.err.println("Demo error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Creates sample library items
     */
    private static void createSampleItems(LibraryManager library) {
        System.out.println("📚 Creating Sample Library Items...");
        
        // Create books
        Book book1 = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald", 
                             180, "978-0-7432-7356-5", "Fiction", "Scribner", 1925);
        Book book2 = new Book(2, "To Kill a Mockingbird", "Harper Lee", 
                             281, "978-0-06-112008-4", "Fiction", "J.B. Lippincott", 1960);
        
        // Create audiobooks
        Audiobook audiobook1 = new Audiobook(3, "The Hobbit", "J.R.R. Tolkien", 
                                           480, "Rob Inglis", "MP3", 256.5, true, "High", "English");
        Audiobook audiobook2 = new Audiobook(4, "1984", "George Orwell", 
                                           420, "Simon Prebble", "AAC", 198.3, true, "High", "English");
        
        // Create e-magazines
        EMagazine magazine1 = new EMagazine(5, "National Geographic", "Susan Goldberg", 
                                          156, "National Geographic Society", "Science", 
                                          LocalDate.of(2024, 1, 15), 120, "https://example.com/ng-cover.jpg");
        EMagazine magazine2 = new EMagazine(6, "Time Magazine", "Edward Felsenthal", 
                                           789, "Time Inc.", "News", 
                                           LocalDate.of(2024, 2, 1), 80, "https://example.com/time-cover.jpg");
        
        // Add articles to magazines
        magazine1.addArticle("Climate Change: The Latest Research");
        magazine1.addArticle("Space Exploration: Mars Mission Update");
        magazine1.addArticle("Ocean Conservation: Protecting Marine Life");
        
        magazine2.addArticle("Global Politics: Current Affairs");
        magazine2.addArticle("Technology Trends: AI Revolution");
        magazine2.addArticle("Economy: Market Analysis");
        
        // Add items to library
        library.addItem(book1);
        library.addItem(book2);
        library.addItem(audiobook1);
        library.addItem(audiobook2);
        library.addItem(magazine1);
        library.addItem(magazine2);
        
        System.out.println("✅ Created " + library.getTotalItemCount() + " library items\n");
    }
    
    /**
     * Demonstrates basic borrowing and returning operations
     */
    private static void demonstrateBasicOperations(LibraryManager library) {
        System.out.println("🔄 Demonstrating Basic Operations...");
        
        // Borrow some items
        System.out.println("Borrowing items:");
        library.borrowItem(1, "Alice Johnson", "7 days");
        library.borrowItem(3, "Bob Smith", "2 weeks");
        library.borrowItem(5, "Carol Davis", "1 month");
        
        System.out.println("✅ Successfully borrowed 3 items\n");
        
        // Show borrowed items
        System.out.println("Currently borrowed items:");
        List<LibraryItem> borrowedItems = library.getBorrowedItems();
        for (LibraryItem item : borrowedItems) {
            System.out.println("  - " + item.toString());
        }
        System.out.println();
        
        // Return one item
        System.out.println("Returning item ID 1...");
        library.returnItem(1);
        System.out.println("✅ Item returned successfully\n");
        
        // Show available items
        System.out.println("Available items:");
        List<LibraryItem> availableItems = library.getAvailableItems();
        for (LibraryItem item : availableItems) {
            System.out.println("  - " + item.toString());
        }
        System.out.println();
    }
    
    /**
     * Demonstrates searching functionality
     */
    private static void demonstrateSearching(LibraryManager library) {
        System.out.println("🔍 Demonstrating Search Functionality...");
        
        // Search by title
        System.out.println("Searching for 'Gatsby':");
        List<LibraryItem> titleResults = library.searchByTitle("Gatsby");
        for (LibraryItem item : titleResults) {
            System.out.println("  - " + item.toString());
        }
        
        // Search by author
        System.out.println("\nSearching for 'Tolkien':");
        List<LibraryItem> authorResults = library.searchByAuthor("Tolkien");
        for (LibraryItem item : authorResults) {
            System.out.println("  - " + item.toString());
        }
        
        // Search by type
        System.out.println("\nSearching for 'Book' type:");
        List<LibraryItem> bookResults = library.getItemsByType("Book");
        for (LibraryItem item : bookResults) {
            System.out.println("  - " + item.toString());
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates fine calculation
     */
    private static void demonstrateFineCalculation(LibraryManager library) {
        System.out.println("💰 Demonstrating Fine Calculation...");
        
        // Simulate overdue items by modifying due dates (for demo purposes)
        LibraryItem overdueItem = library.getItemById(3);
        if (overdueItem != null && !overdueItem.isAvailable()) {
            System.out.println("Item ID 3 is currently borrowed.");
            System.out.println("Due date: " + overdueItem.getDueDate());
            System.out.println("Fine calculation: ₹" + overdueItem.calculateFine() + " (₹10/day)");
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates specialized behaviors of different item types
     */
    private static void demonstrateSpecializedBehaviors(LibraryManager library) {
        System.out.println("🎯 Demonstrating Specialized Behaviors...");
        
        // Book-specific behavior
        System.out.println("Book-specific features:");
        List<LibraryItem> books = library.getItemsByType("Book");
        for (LibraryItem item : books) {
            if (item instanceof Book) {
                Book book = (Book) item;
                System.out.println("  - " + book.getTitle() + ": " + book.getPageCount() + " pages");
                System.out.println("    Estimated reading time: " + book.getEstimatedReadingTime() + " minutes");
                System.out.println("    ISBN: " + book.getIsbn());
            }
        }
        
        // Audiobook-specific behavior (Playable interface)
        System.out.println("\nAudiobook-specific features (Playable interface):");
        List<LibraryItem> audiobooks = library.getItemsByType("Audiobook");
        for (LibraryItem item : audiobooks) {
            if (item instanceof Audiobook) {
                Audiobook audiobook = (Audiobook) item;
                System.out.println("  - " + audiobook.getTitle());
                System.out.println("    Duration: " + audiobook.getDuration() + " minutes (" + 
                                 audiobook.getDurationInHours() + " hours)");
                System.out.println("    Format: " + audiobook.getFormat());
                System.out.println("    File size: " + audiobook.getFileSize() + " MB");
                System.out.println("    Quality: " + audiobook.getQuality());
                System.out.println("    Downloadable: " + audiobook.isDownloadable());
                System.out.println("    Narrator: " + audiobook.getNarrator());
            }
        }
        
        // E-Magazine-specific behavior
        System.out.println("\nE-Magazine-specific features:");
        List<LibraryItem> magazines = library.getItemsByType("E-Magazine");
        for (LibraryItem item : magazines) {
            if (item instanceof EMagazine) {
                EMagazine magazine = (EMagazine) item;
                System.out.println("  - " + magazine.getTitle());
                System.out.println("    Issue #" + magazine.getIssueNumber());
                System.out.println("    Articles: " + magazine.getArticleCount());
                System.out.println("    Published: " + magazine.getPublicationDate());
                System.out.println("    Age: " + magazine.getAgeInDays() + " days");
                System.out.println("    Recent: " + magazine.isRecent());
                System.out.println("    Archived: " + magazine.isArchived());
                
                // Demonstrate archiving
                if (!magazine.isArchived()) {
                    System.out.println("    Archiving issue...");
                    magazine.archiveIssue();
                    System.out.println("    ✅ Issue archived successfully");
                }
            }
        }
        
        System.out.println();
    }
    
    /**
     * Shows library statistics
     */
    private static void showLibraryStatistics(LibraryManager library) {
        System.out.println("📊 Library Statistics:");
        
        Map<String, Object> stats = library.getLibraryStatistics();
        
        System.out.println("  Total items: " + stats.get("totalItems"));
        System.out.println("  Available items: " + stats.get("availableItems"));
        System.out.println("  Borrowed items: " + stats.get("borrowedItems"));
        System.out.println("  Overdue items: " + stats.get("overdueItems"));
        System.out.println("  Total borrowers: " + stats.get("totalBorrowers"));
        System.out.println("  Total fines: ₹" + stats.get("totalFines"));
        
        System.out.println("\n  Items by type:");
        @SuppressWarnings("unchecked")
        Map<String, Long> typeCount = (Map<String, Long>) stats.get("typeCount");
        for (Map.Entry<String, Long> entry : typeCount.entrySet()) {
            System.out.println("    " + entry.getKey() + ": " + entry.getValue());
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates error handling
     */
    private static void demonstrateErrorHandling(LibraryManager library) {
        System.out.println("⚠️ Demonstrating Error Handling...");
        
        try {
            // Try to borrow an already borrowed item
            System.out.println("Attempting to borrow already borrowed item...");
            library.borrowItem(3, "Eve Wilson", "1 week");
        } catch (IllegalStateException e) {
            System.out.println("✅ Caught expected error: " + e.getMessage());
        }
        
        try {
            // Try to borrow non-existent item
            System.out.println("\nAttempting to borrow non-existent item...");
            library.borrowItem(999, "Frank Miller", "1 week");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Caught expected error: " + e.getMessage());
        }
        
        try {
            // Try to return already available item
            System.out.println("\nAttempting to return already available item...");
            library.returnItem(1);
        } catch (IllegalStateException e) {
            System.out.println("✅ Caught expected error: " + e.getMessage());
        }
        
        try {
            // Try to create invalid book
            System.out.println("\nAttempting to create book with invalid data...");
            new Book(-1, "", "Valid Author", 100, "ISBN", "Genre", "Publisher", 2020);
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Caught expected error: " + e.getMessage());
        }
        
        System.out.println();
    }
}
