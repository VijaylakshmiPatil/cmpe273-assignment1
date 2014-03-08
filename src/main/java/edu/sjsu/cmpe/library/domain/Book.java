package edu.sjsu.cmpe.library.domain;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
public class Book {
	
    private long isbn;
    @NotEmpty
	@NotNull
    @JsonProperty ("title")
    private String title;
    @NotEmpty
	@NotNull
    @JsonProperty ("publication-date")
    private String publicationDate;
    @JsonProperty ("language")
    private String language;
    @JsonProperty ("num-pages")
    private int noOfPages;
    @JsonProperty ("status")
    private String status;
    @NotEmpty
	@NotNull
    @JsonProperty ("authors")
    ArrayList<Author> authors;
    @JsonProperty ("reviews")
    ArrayList<Review> review = new ArrayList<Review>();
    
    /**
     * @return the isbn
     */
    public long getIsbn() {
	return isbn;
    }

    /**
     * @param isbn
     *            the isbn to set
     */
    public void setIsbn(long isbn) {
	this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }

	/**
	 * @return the publicationDate
	 */
	public String getPublicationDate() {
		return publicationDate;
	}

	/**
	 * @param publicationDate the publicationDate to set
	 */
	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the noOfPages
	 */
	public int getNoOfPages() {
		return noOfPages;
	}

	/**
	 * @param noOfPages the noOfPages to set
	 */
	public void setNoOfPages(int noOfPages) {
		this.noOfPages = noOfPages;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
	
		if (status.isEmpty()){
    		this.status="available";
    	}
    	else{
    		this.status = status;   		
    	}
	}
    
	public boolean checkStatus(String status){
    	String bookStatus=status.toLowerCase();
    	if (bookStatus.equals("lost") || bookStatus.equals("checked-out")|| bookStatus.equals("in-queue") || bookStatus.equals("available"))
    		return true;    		
    	else if (status.isEmpty()) 
    		return true;
    	else
    		return false;
    }
    //get the authors of the book
    public ArrayList<Author> getAuthors(){
    	return authors;
    }
    
    //set the authors of the book
    public void setAuthors(ArrayList<Author> authors){
    	this.authors = authors;
     		
     }
    
    //get the reviews for a book
    public ArrayList<Review> getReviews(){
    	return review;
    }
    
  //set the reviews for a book
    public void setReviews (ArrayList<Review> reviews){
    	this.review = review;
    }
    
    
    
}
