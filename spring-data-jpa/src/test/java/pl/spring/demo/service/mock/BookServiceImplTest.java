package pl.spring.demo.service.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.mockito.internal.util.reflection.Whitebox;

import pl.spring.demo.common.BookMapper;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.service.impl.BookServiceImpl;
import pl.spring.demo.to.AuthorTo;
import pl.spring.demo.to.BookTo;
import pl.string.demo.entity.BookEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

public class BookServiceImplTest {
	@InjectMocks
	private BookServiceImpl bookService;
	@Mock
	private BookDao bookDao;
	private BookMapper bookMapper;

	@Before
	public void setUpt() {
		MockitoAnnotations.initMocks(this);
		bookMapper = new BookMapper();
		Whitebox.setInternalState(bookService, "bookMapper", bookMapper);
	}

	@Test
	public void testShouldSaveBook() {
		// given
		BookEntity book = new BookEntity(null, "title", Arrays.asList(new AuthorTo(1L, "imie", "nazwisko")));
		Mockito.when(bookDao.save(book))
				.thenReturn(new BookEntity(1L, "title", Arrays.asList(new AuthorTo(1L, "imie", "nazwisko"))));
		// when
		BookTo result = bookService.saveBook(bookMapper.convertToTo(book));
		// then
		Mockito.verify(bookDao).save(book);
		assertEquals(1L, result.getId().longValue());
	}

	@Test
	public void testShouldFindAllBooks() {
		// given
		Mockito.when(bookDao.findAll()).thenReturn(
				Arrays.asList(new BookEntity(1L, "title", Arrays.asList(new AuthorTo(1L, "imie", "nazwisko"))),
						new BookEntity(2L, "title2", Arrays.asList(new AuthorTo(1L, "imie2", "nazwisko2")))));
		// when
		List<BookTo> allBooks = bookService.findAllBooks();
		// then
		Mockito.verify(bookDao).findAll();
		assertNotNull(allBooks);
		assertFalse(allBooks.isEmpty());
		assertEquals(2, allBooks.size());
	}

	@Test
	public void testShouldFindAllBooksByTitle() {
		// given
		final String title = "t";
		Mockito.when(bookDao.findBookByTitle(title)).thenReturn(
				Arrays.asList(new BookEntity(1L, "title", Arrays.asList(new AuthorTo(1L, "imie", "nazwisko"))),
						new BookEntity(2L, "title2", Arrays.asList(new AuthorTo(1L, "imie2", "nazwisko2")))));
		// when
		List<BookTo> booksByTitle = bookService.findBooksByTitle(title);
		// then
		Mockito.verify(bookDao).findBookByTitle(title);
		assertNotNull(booksByTitle);
		assertFalse(booksByTitle.isEmpty());
		assertEquals(2, booksByTitle.size());
	}

	@Test
	public void testShouldFindAllBooksByAuthor() {
		// given
		final String author = "imi";
		Mockito.when(bookDao.findBooksByAuthor(author)).thenReturn(
				Arrays.asList(new BookEntity(1L, "title", Arrays.asList(new AuthorTo(1L, "imie", "nazwisko"))),
						new BookEntity(2L, "title2", Arrays.asList(new AuthorTo(1L, "imie2", "nazwisko2")))));
		// when
		List<BookTo> booksByAuthor = bookService.findBooksByAuthor(author);
		// then
		Mockito.verify(bookDao).findBooksByAuthor(author);
		assertNotNull(booksByAuthor);
		assertFalse(booksByAuthor.isEmpty());
		assertEquals(2, booksByAuthor.size());
	}

	@Test(expected = BookNotNullIdException.class)
	public void testShouldThrowBookNotNullIdException() {
		// given
		BookEntity book = new BookEntity(2L, "title", Arrays.asList(new AuthorTo(1L, "imie", "nazwisko")));
		Mockito.when(bookDao.save(book)).thenThrow(new BookNotNullIdException());
		// when
		bookService.saveBook(bookMapper.convertToTo(book));
		// then
		Mockito.verify(bookDao).save(book);
		fail("test should throw BookNotNullIdException");
	}
}
