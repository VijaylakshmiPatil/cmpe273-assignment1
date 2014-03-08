/**
 * 
 */
package edu.sjsu.cmpe.library.dto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import edu.sjsu.cmpe.library.domain.Author;
/**
 * @author Vijaylakshmi
 *
 */
@JsonPropertyOrder (alphabetic = true)
public class AuthorDto extends LinksDto {
private Author author;
	
	public AuthorDto (Author author){
		this.author = author;
	}
	
	public Author getAuthor (){
		return author;
	}
	
	public void setAuthor (Author author){
		this.author = author;
	}
}
