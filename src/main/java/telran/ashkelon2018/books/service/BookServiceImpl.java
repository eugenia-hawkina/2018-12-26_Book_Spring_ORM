package telran.ashkelon2018.books.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.books.dao.IRepository;
import telran.ashkelon2018.books.domain.Author;
import telran.ashkelon2018.books.domain.Book;
import telran.ashkelon2018.books.domain.Publisher;
import telran.ashkelon2018.books.dto.AuthorDto;
import telran.ashkelon2018.books.dto.BookDto;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	IRepository repository;

	@Override
	@Transactional
	public boolean addBook(BookDto bookDto) {
		if (repository.findBook(bookDto.getIsbn()) != null) {
			return false;
		}
		
		Publisher publisher = repository.findPublisher(bookDto.getPublisher());
		if (bookDto.getPublisher() == null) {
			publisher = new Publisher(bookDto.getPublisher());
			repository.addPublisher(publisher);
		}
		Set<AuthorDto> authorDtos = bookDto.getAuthors();
		if (authorDtos == null) {
			return false;
		}
		
		Set<Author> authors = new HashSet<>();
		for (AuthorDto authorDto : authorDtos) {
			Author author = repository.findAuthor(authorDto.getName());
			if (author == null) {
				author = new Author(authorDto.getName(), authorDto.getBirthDate());
				repository.addAuthor(author);
			}
			authors.add(author);
		}
		return repository.addBook(new Book(bookDto.getIsbn(), bookDto.getTitle(), 
				authors, publisher));
	}

	@Override
	@Transactional
	public BookDto removeBook(Long isbn) {
		Book book = repository.removeBook(isbn);
		if(book == null) {
			return null;
		}	
		return bookToBookDto(book);
	}

	private BookDto bookToBookDto(Book book) {
		Set<AuthorDto> authors = book.getAuthors().stream().map(this::authorToAuthorDto).collect(Collectors.toSet());
		return new BookDto(book.getIsbn(), book.getTitle(), authors, book.getPublisher().getPublisherName());
	}

	private AuthorDto authorToAuthorDto(Author author) {
		return new AuthorDto(author.getName(), author.getBirthDate());
	}

	@Override
	public BookDto getBookByIsbn(Long isbn) {
		Book book = repository.findBook(isbn);
		if(book == null) {
			return null;
		}
		return bookToBookDto(book);
	}

	@Override
	public Iterable<BookDto> getBooksByAuthor(String authorName) {
		Author author = repository.findAuthor(authorName);
		if (author == null) {
			return null;
		}
		
//		return author.getBooks().stream()
//				.map(this::bookToBookDto)
//				.collect(Collectors.toSet());
		
		Set<Book> books = author.getBooks();
		ArrayList<BookDto> bookDtos = new ArrayList<>();
		for (Book book : books) {
			bookDtos.add(bookToBookDto(book));
		}
		return bookDtos;
	}

	@Override
	public Iterable<BookDto> getBooksByPublisher(String publisherName) {
		Publisher publisher = repository.findPublisher(publisherName);
		if (publisher == null) {
			return null;
		}
		
		return publisher.getBooks().stream()
			.map(this::bookToBookDto)
			.collect(Collectors.toSet());
		
//		Set<Book> books = publisher.getBooks();
//		ArrayList<BookDto> bookDtos = new ArrayList<>();
//		for (Book book : books) {
//			bookDtos.add(bookToBookDto(book));
//		}
//		return bookDtos;
	}

	@Override
	public Iterable<AuthorDto> getBookAuthors(Long isbn) {
		Book book = repository.findBook(isbn);
		if (book == null) {
			return null;
		}
		
//		return book.getAuthors().stream()
//			.map(this::authorToAuthorDto)
//			.collect(Collectors.toSet());
		
		Set<Author> authors = book.getAuthors();
		ArrayList<AuthorDto> authorDtos = new ArrayList<>();
		for (Author author : authors) {
			authorDtos.add(authorToAuthorDto(author));
		}
		return authorDtos;
	}

	@Override
	public Iterable<String> getPublishersByAuthor(String authorName) {
//		Author author = repository.findAuthor(authorName);
//		if (author == null) {
//			return null;
//		}
//		return author.getBooks().stream()
//				.map(b -> b.getPublisher().getPublisherName())
//				.distinct()
//				.collect(Collectors.toList());
		
		return repository.getPublishersByAuthor(authorName).stream()
				.map(p -> p.getPublisherName())
				.collect(Collectors.toSet());
	}

	@Override
	public Iterable<String> getByAnyQuery(String jpql) {
		return repository.getByQuery(jpql);
	}
}
