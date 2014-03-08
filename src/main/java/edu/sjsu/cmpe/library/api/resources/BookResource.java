package edu.sjsu.cmpe.library.api.resources;

import java.util.ArrayList;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.validator.constraints.NotEmpty;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.BookDetails;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.AuthorsDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    /** bookRepository instance */
    private final BookRepositoryInterface bookRepository;

    /**
     * BookResource constructor
     * 
     * @param bookRepository
     *            a BookRepository instance
     */
    public BookResource(BookRepositoryInterface bookRepository) {
	this.bookRepository = bookRepository;
    }
   //2. View Book API
    @GET
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public BookDto getBookByIsbn(@PathParam("isbn") LongParam isbn) {
	Book book = bookRepository.getBookByISBN(isbn.get());
	
	BookDetails bookDetails = new BookDetails(book);
	BookDto bookResponse = new BookDto(bookDetails);
	bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),
		"GET"));
	bookResponse.addLink(new LinkDto("update-book",
		"/books/" + book.getIsbn(), "PUT"));
	bookResponse.addLink(new LinkDto("delete-book", "/books/" + book.getIsbn(),
			"DELETE"));
	bookResponse.addLink(new LinkDto("create-review",
			"/books/" + book.getIsbn() + "/reviews/" , "POST"));
	
	if(bookDetails.getReviews().size() > 0){
		bookResponse.addLink(new LinkDto("view-all-reviews", "/books/"
				+ book.getIsbn() + "/reviews", "GET"));
	}
		
	// add more links

	return bookResponse;
    }
// 1. Create Book API
    @POST
    @Timed(name = "create-book")
    public Response createBook(@Valid Book request) {
	// Store the new book in the BookRepository so that we can retrieve it.
	Book savedBook = bookRepository.saveBook(request);

	
	String location = "/books/" + savedBook.getIsbn();
	LinksDto bookResponse = new LinksDto();
	bookResponse.addLink(new LinkDto("view-book", location, "GET"));
	bookResponse.addLink(new LinkDto("update-book", location, "PUT"));
	bookResponse.addLink(new LinkDto("delete-book", location, "DELETE"));
	bookResponse.addLink(new LinkDto("create-review", location, "POST"));
	// Add other links if needed

	return Response.status(201).entity(bookResponse).build();
    }
    
    //3. Delete Book API
    @DELETE
    @Path("/{isbn}")
    @Timed (name = "delete-book")
    public Response deleteBookByISBN(@PathParam ("isbn") LongParam isbn){
    	
    	bookRepository.deleteBookByISBN(isbn.get());
    	LinksDto bookResponse = new LinksDto();
    	bookResponse.addLink(new LinkDto("create-book",
    			"/books" , "POST"));
    	return Response.status(200).entity(bookResponse).build(); 
    }
    
    //4. Update Book API
    @PUT
    @Path("/{isbn}")
    @Timed(name = "update-book")
    public Response updateBook (@PathParam("isbn") LongParam isbn, @QueryParam("status") String status){
    	
    	Book book = bookRepository.getBookByISBN(isbn.get());
    	
    	if(book.checkStatus(status))
    	{
    		bookRepository.updateBookByIsbn(isbn.get(), status);
    		String location = "/books/" + book.getIsbn();
        	LinksDto bookResponse = new LinksDto();
        	
        	bookResponse.addLink(new LinkDto("view-book", location, "GET"));
        	bookResponse.addLink(new LinkDto("update-book", location, "PUT"));
        	bookResponse.addLink(new LinkDto("delete-book", location, "DELETE"));
        	bookResponse.addLink(new LinkDto("create-review",
        			"/books/" + book.getIsbn() + "/reviews/" , "POST"));
        	BookDetails bookDetails = new BookDetails(book);
        	if(bookDetails.getReviews().size() > 0){
        		bookResponse.addLink(new LinkDto("view-all-reviews", "/books/"
        				+ book.getIsbn() + "/reviews", "GET"));
        	}
    		
        	return Response.status(200).entity(bookResponse).build();
    	}
    	else{
    		
    		return Response.status(400).entity("Status value is not valid").build();
    	}
    	
    }
    
   //8. View Book Author APi
    @GET
    @Path ("{isbn}/authors/{id}")
    @Timed (name="view-author")
	public AuthorDto getAuthorById (@PathParam ("isbn") LongParam isbn, @PathParam ("id") long id){
		
		Book book = bookRepository.getBookByISBN(isbn.get());
		ArrayList<Author> authors = book.getAuthors();
		int authorIndex = 0;
		//Author author;
		for(int i = 0; i <authors.size(); i++){
			if(id == authors.get(i).getId()){
				authorIndex = i;
				break;
			}
		}
		
		AuthorDto authorResponse = new AuthorDto(authors.get(authorIndex));
		String location = "/books/" + book.getIsbn() + "/authors/" + authors.get(authorIndex).getId();
		authorResponse.addLink(new LinkDto("view-author", location, "GET"));
		return authorResponse;
	}
 // 9.View All Authors of the Book API
	@GET
    @Path ("{isbn}/authors")
    @Timed (name="view-author")
	public AuthorsDto getAuthors (@PathParam ("isbn") LongParam isbn){
		
		Book book = bookRepository.getBookByISBN(isbn.get());
		ArrayList<Author> authors = book.getAuthors();
				
		AuthorsDto authorResponse = new AuthorsDto(authors);
		return authorResponse;
	}
	
	

	
     
}

