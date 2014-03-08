/**
 * 
 */
package edu.sjsu.cmpe.library.domain;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Vijaylakshmi
 *
 */
public class Review {
	private int id;	
	private int rating;
	private String comment;
	private static int reviewID;
	
	public Review(){
		this.id = ++reviewID;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	//Check Rating
	public boolean checkRating(){
		if (rating<1 || rating>5)
			return false;
		else
			return true;
	}
	
	//Check Comment
	public boolean checkComment(){
		if (comment == "" || comment == null)
			return false;
		else
			return true;
	}
}
