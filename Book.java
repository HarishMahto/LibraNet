/**
 * Concrete class representing a physical or digital book
 * Extends LibraryItem with book-specific functionality
 */
public class Book extends LibraryItem {
    private int pageCount;
    private String isbn;
    private String genre;
    private String publisher;
    private int publicationYear;
    
    /**
     * Constructor for Book
     * @param itemId Unique identifier for the book
     * @param title Title of the book
     * @param author Author of the book
     * @param pageCount Number of pages in the book
     * @param isbn ISBN of the book
     * @param genre Genre of the book
     * @param publisher Publisher of the book
     * @param publicationYear Year of publication
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Book(int itemId, String title, String author, int pageCount, 
                String isbn, String genre, String publisher, int publicationYear) {
        super(itemId, title, author);
        validateBookInputs(pageCount, isbn, genre, publisher, publicationYear);
        
        this.pageCount = pageCount;
        this.isbn = isbn;
        this.genre = genre;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
    }
    
    /**
     * Validates book-specific inputs
     */
    private void validateBookInputs(int pageCount, String isbn, String genre, 
                                   String publisher, int publicationYear) {
        if (pageCount <= 0) {
            throw new IllegalArgumentException("Page count must be positive: " + pageCount);
        }
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Genre cannot be null or empty");
        }
        if (publisher == null || publisher.trim().isEmpty()) {
            throw new IllegalArgumentException("Publisher cannot be null or empty");
        }
        if (publicationYear < 1000 || publicationYear > java.time.Year.now().getValue()) {
            throw new IllegalArgumentException("Invalid publication year: " + publicationYear);
        }
    }
    
    /**
     * Gets the page count of the book
     * @return number of pages
     */
    public int getPageCount() {
        return pageCount;
    }
    
    /**
     * Gets the ISBN of the book
     * @return ISBN string
     */
    public String getIsbn() {
        return isbn;
    }
    
    /**
     * Gets the genre of the book
     * @return genre string
     */
    public String getGenre() {
        return genre;
    }
    
    /**
     * Gets the publisher of the book
     * @return publisher string
     */
    public String getPublisher() {
        return publisher;
    }
    
    /**
     * Gets the publication year
     * @return publication year
     */
    public int getPublicationYear() {
        return publicationYear;
    }
    
    /**
     * Calculates estimated reading time based on page count
     * Assumes average reading speed of 2 minutes per page
     * @return estimated reading time in minutes
     */
    public int getEstimatedReadingTime() {
        return pageCount * 2; // 2 minutes per page
    }
    
    @Override
    public String getItemType() {
        return "Book";
    }
    
    @Override
    public String getSpecificInfo() {
        return String.format("Pages: %d, ISBN: %s, Genre: %s, Publisher: %s, Year: %d",
                pageCount, isbn, genre, publisher, publicationYear);
    }
    
    @Override
    public String toString() {
        return String.format("%s [ID: %d, Title: %s, Author: %s, Pages: %d, Available: %s%s]",
                getItemType(), getItemId(), getTitle(), getAuthor(), pageCount, isAvailable(),
                !isAvailable() ? String.format(", Borrower: %s, Due: %s", getBorrowerName(), getDueDate()) : "");
    }
}
