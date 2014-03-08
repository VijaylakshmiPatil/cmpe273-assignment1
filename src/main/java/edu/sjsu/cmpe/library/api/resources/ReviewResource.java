package edu.sjsu.cmpe.library.api.resources;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import java.util.ArrayList;

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.dto.ReviewsDto;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;


@Path("/v1/books/{isbn}/reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class ReviewResource {
	
 private final BookRepositoryInterface bookRepository;
 
 public ReviewResource(BookRepositoryInterface bookRepository) {
		this.bookRepository = bookRepository;
	    }
//5.Create Book Review API
   	    @POST
	    @Timed(name = "create-review")
	    public Response createReviewByISBN(@PathParam ("isbn") LongParam isbn, @Valid Review request) {
   	    
   	    	if (!request.checkRating()  ){
   	    		if(request.getRating()> 5 || request.getRating() < 1)
   	   	    	{
   	   	    		return Response.status(420).entity("Rating should be in 1 to 5 range").build();
   	   	    	}
   	    		else
   				return Response.status(420).entity("Rating should not be Null").build();
   	    		}
   	    	
   			if (!request.checkComment()){
   				return Response.status(420).entity("Comment should not be Null").build();
   			}
		Book book = bookRepository.getBookByISBN(isbn.get());
		ArrayList<Review> allreview = book.getReviews();
		allreview.add(request);

		String location = "/books/" + book.getIsbn() + "/reviews/" + request.getId();
		LinksDto bookResponse = new LinksDto();
		
		bookResponse.addLink(new LinkDto("view-review", location, "GET"));
		

		return Response.status(201).entity(bookResponse).build();
	    }
   	
   	    // 6.View Book Review API 
	    @GET
	    @Path("/{id}/")
	    @Timed(name = "view-review")
	    public Response getReviewByIsbn(@PathParam("isbn") LongParam isbn,@PathParam("id") int id) {
		Review review = bookRepository.getReviewByID(isbn.get(),id);
		Book book = bookRepository.getBookByISBN(isbn.get());
		ReviewDto bookResponse = new ReviewDto(review);
		bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn() + "/review/"+ review.getId(),
			"GET"));
		return Response.status(201).entity(bookResponse).build();
	    }
	    
	 // 7.View All Reviews of the Book API 
	    @GET
	    @Timed(name = "view-book")
	    public Response viewAllReviews(@PathParam("isbn") LongParam isbn)
	    {
	        Book book= bookRepository.getBookByISBN(isbn.get());
	    	ArrayList<Review> review =  book.getReviews();
	    	ReviewsDto bookResponse = new ReviewsDto(review);
	    	
	    	return Response.status(201).entity(bookResponse).build();
	    }
}