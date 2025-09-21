# LibraNet Library Management System

A comprehensive Java-based library management system designed for managing books, audiobooks, and e-magazines with robust error handling, extensibility, and clean architecture.

## Architecture Overview

The system follows object-oriented design principles with:

- **Abstract Base Class**: `LibraryItem` provides common functionality
- **Specialized Classes**: `Book`, `Audiobook`, `EMagazine` with type-specific behaviors
- **Interface Implementation**: `Playable` interface for audiobooks
- **Manager Class**: `LibraryManager` handles operations and fine management
- **Comprehensive Error Handling**: Input validation and state management

## Class Structure

### Core Classes

1. **LibraryItem** (Abstract Base Class)
   - Common attributes: ID, title, author, availability status
   - Common operations: borrowing, returning, fine calculation
   - Duration parsing from strings (e.g., "7 days", "2 weeks")
   - Input validation and error handling

2. **Book** (Concrete Class)
   - Page count, ISBN, genre, publisher, publication year
   - `getPageCount()` method
   - Estimated reading time calculation

3. **Audiobook** (Concrete Class + Playable Interface)
   - Duration, narrator, format, file size, quality
   - Implements `Playable` interface
   - Playback speed options and duration in hours

4. **EMagazine** (Concrete Class)
   - Issue number, publisher, category, publication date
   - `archiveIssue()` method
   - Article management and age calculation

5. **LibraryManager** (Management Class)
   - Centralized operations management
   - Fine collection and tracking
   - Search functionality by title, author, type
   - Statistics and reporting

## Key Features

### Common Operations
- **Borrowing**: Parse duration strings, validate inputs
- **Returning**: State management and fine calculation
- **Availability Checking**: Real-time status tracking
- **Fine Management**: ₹10/day rate, collection tracking

### Specialized Behaviors
- **Books**: `getPageCount()`, reading time estimation
- **Audiobooks**: `Playable` interface implementation
- **E-Magazines**: `archiveIssue()` functionality

### Data Handling
- **Duration Parsing**: String to LocalDate conversion
- **Item IDs**: Integer-based unique identifiers
- **Error Handling**: Comprehensive validation and exception handling

## Design Principles Demonstrated

### 1. Clean, Reusable Design
- Abstract base class eliminates code duplication
- Common operations centralized in `LibraryItem`
- Interface-based design for extensibility

### 2. Extensibility for Future Growth
- Easy to add new item types by extending `LibraryItem`
- `Playable` interface can be implemented by other media types
- Manager class supports additional operations without modification

### 3. Strong Error Handling and Data Management
- Input validation at all levels
- Meaningful exception messages
- State validation for operations
- Null safety and boundary checking

## Usage Examples

### Creating Items
```java
// Create a book
Book book = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald", 
                    180, "978-0-7432-7356-5", "Fiction", "Scribner", 1925);

// Create an audiobook
Audiobook audiobook = new Audiobook(2, "The Hobbit", "J.R.R. Tolkien", 
                                   480, "Rob Inglis", "MP3", 256.5, true, "High", "English");

// Create an e-magazine
EMagazine magazine = new EMagazine(3, "National Geographic", "Susan Goldberg", 
                                   156, "National Geographic Society", "Science", 
                                   LocalDate.of(2024, 1, 15), 120, "cover.jpg");
```

### Library Operations
```java
LibraryManager library = new LibraryManager();

// Add items
library.addItem(book);
library.addItem(audiobook);
library.addItem(magazine);

// Borrow items
library.borrowItem(1, "Alice Johnson", "7 days");
library.borrowItem(2, "Bob Smith", "2 weeks");

// Return items
library.returnItem(1);

// Search
List<LibraryItem> results = library.searchByTitle("Gatsby");
List<LibraryItem> books = library.getItemsByType("Book");
```

### Specialized Operations
```java
// Book-specific
int pages = book.getPageCount();
int readingTime = book.getEstimatedReadingTime();

// Audiobook-specific (Playable interface)
int duration = audiobook.getDuration();
String format = audiobook.getFormat();
boolean downloadable = audiobook.isDownloadable();

// E-Magazine-specific
magazine.archiveIssue();
int articleCount = magazine.getArticleCount();
boolean isRecent = magazine.isRecent();
```

## Running the Demo

Compile and run the demo to see all features in action:

```bash
javac *.java
java LibraNetDemo
```

The demo showcases:
- Item creation and management
- Borrowing and returning operations
- Search functionality
- Fine calculation
- Specialized behaviors
- Error handling
- Library statistics

## Error Handling Features

- **Input Validation**: All constructors validate inputs
- **State Validation**: Operations check item availability
- **Meaningful Exceptions**: Clear error messages for debugging
- **Boundary Checking**: Prevents invalid operations
- **Null Safety**: Handles null and empty inputs gracefully

## Extensibility Examples

### Adding New Item Types
```java
public class EBook extends LibraryItem {
    private String format;
    private double fileSize;
    
    public EBook(int itemId, String title, String author, String format, double fileSize) {
        super(itemId, title, author);
        this.format = format;
        this.fileSize = fileSize;
    }
    
    @Override
    public String getItemType() { return "E-Book"; }
    
    @Override
    public String getSpecificInfo() { 
        return "Format: " + format + ", Size: " + fileSize + " MB"; 
    }
}
```

### Adding New Interfaces
```java
public interface Downloadable {
    boolean isDownloadable();
    String getDownloadUrl();
    long getFileSizeBytes();
}
```

## Conclusion

This LibraNet implementation demonstrates:
- **Clean Architecture**: Well-structured, maintainable code
- **Extensibility**: Easy to add new features and item types
- **Robust Error Handling**: Comprehensive validation and exception management
- **Real-world Applicability**: Practical solution for library management

The system is ready for production use and can be easily extended to meet future requirements.
