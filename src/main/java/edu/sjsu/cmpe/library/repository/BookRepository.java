package edu.sjsu.cmpe.library.repository;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;

public class BookRepository implements BookRepositoryInterface {
    /** In-memory map to store books. (Key, Value) -> (ISBN, Book) */
    private final ConcurrentHashMap<Long, Book> bookInMemoryMap;

    /** Never access this key directly; instead use generateISBNKey() */
    private long isbnKey;
    private long authorId;
     public BookRepository(ConcurrentHashMap<Long, Book> bookMap) {
	checkNotNull(bookMap, "bookMap must not be null for BookRepository");
	bookInMemoryMap = bookMap;
	isbnKey = 0;
	authorId = 0;
    }

    /**
     * This should be called if and only if you are adding new books to the
     * repository.
     * 
     * @return a new incremental ISBN number
     */
    private final Long generateISBNKey() {
	// increment existing isbnKey and return the new value
	return Long.valueOf(++isbnKey);
    }

    
    /**
     * This will auto-generate unique ISBN for new books.
     */
    @Override
    public Book saveBook(Book newBook) {
	checkNotNull(newBook, "newBook instance must not be null");
	// Generate new ISBN
	Long isbn = generateISBNKey();
	newBook.setIsbn(isbn);
	ArrayList<Author> authors = newBook.getAuthors(); 
	for(int i=0; i < authors.size(); i++){
		authors.get(i).setId(++authorId);
	}
	 
	
	// TODO: create and associate other fields such as author

	// Finally, save the new book into the map
	
	bookInMemoryMap.putIfAbsent(isbn, newBook);

	return newBook;
    }

    /**
     * @see edu.sjsu.cmpe.library.repository.BookRepositoryInterface#getBookByISBN(java.lang.Long)
     */
    @Override
    public Book getBookByISBN(Long isbn) {
	checkArgument(isbn > 0,
		"ISBN was %s but expected greater than zero value", isbn);
	return bookInMemoryMap.get(isbn);
    }
    @Override
    public Book deleteBookByISBN(Long isbn) {
	checkArgument(isbn > 0,
		"ISBN was %s but expected greater than zero value", isbn);
	return bookInMemoryMap.remove(isbn);
    }
    @Override
    public void updateBookByIsbn(Long isbn, String status) {
	checkArgument(isbn > 0,
		"ISBN was %s but expected greater than zero value", isbn);
	Book book = bookInMemoryMap.get(isbn);
	checkNotNull(book, "bookMap must not be null for BookRepository");
	book.setStatus(status);
	bookInMemoryMap.put(isbn, book);
    }
    
    @Override
    public Review getReviewByID(Long isbn, int id) {
		Book book = bookInMemoryMap.get(isbn);
		ArrayList<Review> review = book.getReviews();
		for(int i=0; i<review.size();i++){
			if(review.get(i).getId()==id){
				return review.get(i);
				}
		}
			return null;
		}
}
