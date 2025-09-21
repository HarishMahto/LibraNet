import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages library operations including borrowing, returning, searching, and fine collection
 * Provides centralized management for all library items
 */
public class LibraryManager {
    private Map<Integer, LibraryItem> items;
    private Map<String, List<LibraryItem>> borrowerHistory;
    private Map<String, Double> borrowerFines;
    private int nextItemId;
    
    /**
     * Constructor for LibraryManager
     */
    public LibraryManager() {
        this.items = new HashMap<>();
        this.borrowerHistory = new HashMap<>();
        this.borrowerFines = new HashMap<>();
        this.nextItemId = 1;
    }
    
    /**
     * Adds a new library item
     * @param item The library item to add
     * @return the assigned item ID
     * @throws IllegalArgumentException if item is null
     */
    public int addItem(LibraryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        
        int itemId = nextItemId++;
        item.itemId = itemId; // Update the item's ID
        items.put(itemId, item);
        return itemId;
    }
    
    /**
     * Borrows an item to a borrower
     * @param itemId ID of the item to borrow
     * @param borrowerName Name of the borrower
     * @param borrowDuration Duration string (e.g., "7 days", "2 weeks")
     * @throws IllegalArgumentException if item ID is invalid or borrower name is null
     * @throws IllegalStateException if item is not available
     */
    public void borrowItem(int itemId, String borrowerName, String borrowDuration) {
        LibraryItem item = getItemById(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Item with ID " + itemId + " not found");
        }
        
        item.borrow(borrowerName, borrowDuration);
        
        // Update borrower history
        borrowerHistory.computeIfAbsent(borrowerName, k -> new ArrayList<>()).add(item);
    }
    
    /**
     * Returns an item
     * @param itemId ID of the item to return
     * @throws IllegalArgumentException if item ID is invalid
     * @throws IllegalStateException if item is already available
     */
    public void returnItem(int itemId) {
        LibraryItem item = getItemById(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Item with ID " + itemId + " not found");
        }
        
        String borrowerName = item.getBorrowerName();
        double fine = item.calculateFine();
        
        item.returnItem();
        
        // Update borrower fines
        if (fine > 0) {
            borrowerFines.merge(borrowerName, fine, Double::sum);
        }
    }
    
    /**
     * Gets an item by its ID
     * @param itemId ID of the item
     * @return the item or null if not found
     */
    public LibraryItem getItemById(int itemId) {
        return items.get(itemId);
    }
    
    /**
     * Searches items by title (case-insensitive)
     * @param title Title to search for
     * @return list of matching items
     */
    public List<LibraryItem> searchByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String searchTitle = title.toLowerCase().trim();
        return items.values().stream()
                .filter(item -> item.getTitle().toLowerCase().contains(searchTitle))
                .collect(Collectors.toList());
    }
    
    /**
     * Searches items by author (case-insensitive)
     * @param author Author to search for
     * @return list of matching items
     */
    public List<LibraryItem> searchByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String searchAuthor = author.toLowerCase().trim();
        return items.values().stream()
                .filter(item -> item.getAuthor().toLowerCase().contains(searchAuthor))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets all available items
     * @return list of available items
     */
    public List<LibraryItem> getAvailableItems() {
        return items.values().stream()
                .filter(LibraryItem::isAvailable)
                .collect(Collectors.toList());
    }
    
    /**
     * Gets all borrowed items
     * @return list of borrowed items
     */
    public List<LibraryItem> getBorrowedItems() {
        return items.values().stream()
                .filter(item -> !item.isAvailable())
                .collect(Collectors.toList());
    }
    
    /**
     * Gets items by type
     * @param itemType Type of item (Book, Audiobook, E-Magazine)
     * @return list of items of the specified type
     */
    public List<LibraryItem> getItemsByType(String itemType) {
        if (itemType == null || itemType.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String searchType = itemType.toLowerCase().trim();
        return items.values().stream()
                .filter(item -> item.getItemType().toLowerCase().equals(searchType))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets overdue items
     * @return list of overdue items
     */
    public List<LibraryItem> getOverdueItems() {
        return items.values().stream()
                .filter(item -> !item.isAvailable() && item.calculateFine() > 0)
                .collect(Collectors.toList());
    }
    
    /**
     * Gets total fine amount for a borrower
     * @param borrowerName Name of the borrower
     * @return total fine amount
     */
    public double getBorrowerFine(String borrowerName) {
        if (borrowerName == null || borrowerName.trim().isEmpty()) {
            return 0.0;
        }
        
        return borrowerFines.getOrDefault(borrowerName.trim(), 0.0);
    }
    
    /**
     * Gets all borrowers with outstanding fines
     * @return map of borrower names to their fine amounts
     */
    public Map<String, Double> getAllFines() {
        return new HashMap<>(borrowerFines);
    }
    
    /**
     * Gets borrowing history for a borrower
     * @param borrowerName Name of the borrower
     * @return list of items borrowed by the borrower
     */
    public List<LibraryItem> getBorrowerHistory(String borrowerName) {
        if (borrowerName == null || borrowerName.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return borrowerHistory.getOrDefault(borrowerName.trim(), new ArrayList<>());
    }
    
    /**
     * Gets statistics about the library
     * @return map containing various statistics
     */
    public Map<String, Object> getLibraryStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        int totalItems = items.size();
        int availableItems = (int) items.values().stream().filter(LibraryItem::isAvailable).count();
        int borrowedItems = totalItems - availableItems;
        int overdueItems = (int) items.values().stream()
                .filter(item -> !item.isAvailable() && item.calculateFine() > 0).count();
        
        // Count by type
        Map<String, Long> typeCount = items.values().stream()
                .collect(Collectors.groupingBy(LibraryItem::getItemType, Collectors.counting()));
        
        // Total fines
        double totalFines = borrowerFines.values().stream().mapToDouble(Double::doubleValue).sum();
        
        stats.put("totalItems", totalItems);
        stats.put("availableItems", availableItems);
        stats.put("borrowedItems", borrowedItems);
        stats.put("overdueItems", overdueItems);
        stats.put("typeCount", typeCount);
        stats.put("totalFines", totalFines);
        stats.put("totalBorrowers", borrowerHistory.size());
        
        return stats;
    }
    
    /**
     * Removes an item from the library
     * @param itemId ID of the item to remove
     * @return true if removed, false if not found
     * @throws IllegalStateException if item is currently borrowed
     */
    public boolean removeItem(int itemId) {
        LibraryItem item = items.get(itemId);
        if (item == null) {
            return false;
        }
        
        if (!item.isAvailable()) {
            throw new IllegalStateException("Cannot remove item " + itemId + " as it is currently borrowed");
        }
        
        items.remove(itemId);
        return true;
    }
    
    /**
     * Gets all items in the library
     * @return list of all items
     */
    public List<LibraryItem> getAllItems() {
        return new ArrayList<>(items.values());
    }
    
    /**
     * Gets the total number of items
     * @return total item count
     */
    public int getTotalItemCount() {
        return items.size();
    }
}
