import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete class representing an electronic magazine
 * Extends LibraryItem with magazine-specific functionality
 */
public class EMagazine extends LibraryItem {
    private int issueNumber;
    private String publisher;
    private String category;
    private LocalDate publicationDate;
    private List<String> articles;
    private boolean isArchived;
    private String coverImageUrl;
    private int totalPages;
    
    /**
     * Constructor for EMagazine
     * @param itemId Unique identifier for the magazine
     * @param title Title of the magazine
     * @param author Editor/Author of the magazine
     * @param issueNumber Issue number
     * @param publisher Publisher of the magazine
     * @param category Category of the magazine
     * @param publicationDate Publication date
     * @param totalPages Total number of pages
     * @param coverImageUrl URL of the cover image
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public EMagazine(int itemId, String title, String author, int issueNumber,
                    String publisher, String category, LocalDate publicationDate,
                    int totalPages, String coverImageUrl) {
        super(itemId, title, author);
        validateEMagazineInputs(issueNumber, publisher, category, publicationDate, totalPages);
        
        this.issueNumber = issueNumber;
        this.publisher = publisher;
        this.category = category;
        this.publicationDate = publicationDate;
        this.totalPages = totalPages;
        this.coverImageUrl = coverImageUrl;
        this.articles = new ArrayList<>();
        this.isArchived = false;
    }
    
    /**
     * Validates e-magazine-specific inputs
     */
    private void validateEMagazineInputs(int issueNumber, String publisher, String category,
                                        LocalDate publicationDate, int totalPages) {
        if (issueNumber <= 0) {
            throw new IllegalArgumentException("Issue number must be positive: " + issueNumber);
        }
        if (publisher == null || publisher.trim().isEmpty()) {
            throw new IllegalArgumentException("Publisher cannot be null or empty");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        if (publicationDate == null) {
            throw new IllegalArgumentException("Publication date cannot be null");
        }
        if (publicationDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Publication date cannot be in the future: " + publicationDate);
        }
        if (totalPages <= 0) {
            throw new IllegalArgumentException("Total pages must be positive: " + totalPages);
        }
    }
    
    /**
     * Gets the issue number
     * @return issue number
     */
    public int getIssueNumber() {
        return issueNumber;
    }
    
    /**
     * Gets the publisher
     * @return publisher name
     */
    public String getPublisher() {
        return publisher;
    }
    
    /**
     * Gets the category
     * @return category string
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * Gets the publication date
     * @return publication date
     */
    public LocalDate getPublicationDate() {
        return publicationDate;
    }
    
    /**
     * Gets the total number of pages
     * @return total pages
     */
    public int getTotalPages() {
        return totalPages;
    }
    
    /**
     * Gets the cover image URL
     * @return cover image URL
     */
    public String getCoverImageUrl() {
        return coverImageUrl;
    }
    
    /**
     * Checks if the magazine is archived
     * @return true if archived, false otherwise
     */
    public boolean isArchived() {
        return isArchived;
    }
    
    /**
     * Archives the magazine issue
     * @throws IllegalStateException if already archived
     */
    public void archiveIssue() {
        if (isArchived) {
            throw new IllegalStateException("Magazine issue " + issueNumber + " is already archived");
        }
        this.isArchived = true;
    }
    
    /**
     * Unarchives the magazine issue
     * @throws IllegalStateException if not archived
     */
    public void unarchiveIssue() {
        if (!isArchived) {
            throw new IllegalStateException("Magazine issue " + issueNumber + " is not archived");
        }
        this.isArchived = false;
    }
    
    /**
     * Adds an article to the magazine
     * @param articleTitle Title of the article
     * @throws IllegalArgumentException if article title is invalid
     */
    public void addArticle(String articleTitle) {
        if (articleTitle == null || articleTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Article title cannot be null or empty");
        }
        articles.add(articleTitle.trim());
    }
    
    /**
     * Gets all articles in the magazine
     * @return list of article titles
     */
    public List<String> getArticles() {
        return new ArrayList<>(articles); // Return copy to prevent external modification
    }
    
    /**
     * Gets the number of articles
     * @return number of articles
     */
    public int getArticleCount() {
        return articles.size();
    }
    
    /**
     * Calculates the age of the magazine in days
     * @return age in days
     */
    public long getAgeInDays() {
        return LocalDate.now().toEpochDay() - publicationDate.toEpochDay();
    }
    
    /**
     * Checks if the magazine is recent (published within last 30 days)
     * @return true if recent, false otherwise
     */
    public boolean isRecent() {
        return getAgeInDays() <= 30;
    }
    
    @Override
    public String getItemType() {
        return "E-Magazine";
    }
    
    @Override
    public String getSpecificInfo() {
        return String.format("Issue: %d, Publisher: %s, Category: %s, " +
                           "Published: %s, Pages: %d, Articles: %d, Archived: %s",
                issueNumber, publisher, category, publicationDate, totalPages, 
                getArticleCount(), isArchived);
    }
    
    @Override
    public String toString() {
        return String.format("%s [ID: %d, Title: %s, Author: %s, Issue: %d, Available: %s%s]",
                getItemType(), getItemId(), getTitle(), getAuthor(), issueNumber, isAvailable(),
                !isAvailable() ? String.format(", Borrower: %s, Due: %s", getBorrowerName(), getDueDate()) : "");
    }
}
