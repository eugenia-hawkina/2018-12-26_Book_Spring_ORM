package telran.ashkelon2018.books.dto;

import lombok.Getter;

@Getter
public class QueryDto {

	String query;
}

// publisher of book with title3
// 	"query" : "select p.publisherName from Book b join b.publisher p where b.title='Title3' "

// books with 2 authors 
// "query" : "select b.title from Book b join b.authors a group by b.title having count(b)=2"

// birthday and author-name of book Title
// "query" : "select concat (a.birthDate, ' - ', a.name) from Book b join b.authors a where b.title='Title2'"


	//"query": "select b.title from Book b"
	//"query": "select a from Author a"
	//"query": "select a.name from Book b join b.authors a where b.title='Title1'"
	//"query": "select b.title from Book b join b.authors a"
	//"query": "select b.publisher.name from Book b join b.authors a"  ???
	/*"query": "select b.title from Book b 
	 * 			join b.authors a grouping by b.title having count(b)=2"*/
	/*"query": "select concat(b.title,' - ', a.name) from Book b 
	 * 			join b.authors a"
	 */
	//"query": "select concat(count(*),'') from Book b join b.authors a"
	/*"query": "select concat(count(*),'',b.title) from Book b join 
	   b.authors a groupe by b.title"
	 */
	/*"query": "select concat(count(*),'',b.title) from Book b join 
	   b.authors a groupe by b.title having count(*)=2"
	 */
	/*"query": "select concat(a.birthDate,' - ',a.name) 
	 * 			from Book b join b.authors a where b.title='Title6'"
	 */
	/*"query": "select concat(a.title,' - ',a.name,p.publisherName) 
	 * 			from Book b join b.authors a join b.publisher p"
	 */
	/*"query": "select concat(a.title,' - ',a.name,' - ',p.publisher.publisherName) 
	 * 			from Book b join b.authors a"
	 */
	/*"query": "select concat(a.title,' - ',a,' - ',p.publisher) 
	 * 			from Book b join b.authors a"
	 */