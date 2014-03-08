/**

 * 
 */
package edu.sjsu.cmpe.library.dto;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Review;

/**
 * @author Vijaylakshmi
 *
 */
@JsonPropertyOrder({"reviews", "links"})
public class ReviewsDto extends LinksDto {
private ArrayList<Review> reviews = new ArrayList<Review>();
	
	public ReviewsDto (ArrayList<Review> reviews){
		this.setReviews(reviews);
	}
	
	public void addReviews (ArrayList<Review> reviews){
		reviews.addAll(reviews);
	}

	/**
	 * @return the reviews
	 */
	public ArrayList<Review> getReviews() {
		return reviews;
	}

	/**
	 * @param reviews the reviews to set
	 */
	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}
}
