/**
 * 
 */
package edu.sjsu.cmpe.library.dto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Review;

/**
 * @author Vijaylakshmi
 *
 */
@JsonPropertyOrder({"review", "links"})
//@JsonPropertyOrder(alphabetic = true)
public class ReviewDto extends LinksDto {

	private Review review;
	
	public ReviewDto (Review review){
		this.setReview(review);
	}

	/**
	 * @return the review
	 */
	public Review getReview() {
		return review;
	}

	/**
	 * @param review the review to set
	 */
	public void setReview(Review review) {
		this.review = review;
	}
}
