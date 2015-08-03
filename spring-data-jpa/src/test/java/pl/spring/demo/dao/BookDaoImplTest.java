package pl.spring.demo.dao;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;

import pl.spring.demo.dao.impl.BookDaoImpl;
import pl.spring.demo.to.AuthorTo;
import pl.string.demo.entity.BookEntity;

public class BookDaoImplTest {
	@Autowired
	private static BookDao bookDao = new BookDaoImpl();
	private static Set<BookEntity> books;

	@BeforeClass
	public static void setUp() {
		books = new HashSet<>(Arrays.asList(
				new BookEntity(1L, "Romeo i Julia", Arrays.asList(new AuthorTo(1L, "Wiliam", "Szekspir"))),
				new BookEntity(2L, "Opium w rosole", Arrays.asList(new AuthorTo(2L, "Hanna", "Ożogowska"))),
				new BookEntity(3L, "Przygody Odyseusza", Arrays.asList(new AuthorTo(3L, "Jan", "Parandowski"))),
				new BookEntity(4L, "Awantura w Niekłaju", Arrays.asList(new AuthorTo(4L, "Edmund", "Niziurski"))),
				new BookEntity(5L, "Pan Samochodzik i Fantomas",Arrays.asList(new AuthorTo(5L, "Zbigniew", "Nienacki"))),
				new BookEntity(6L, "Zemsta", Arrays.asList(new AuthorTo(6L, "Aleksander", "Fredro")))));
		Whitebox.setInternalState(bookDao, "ALL_BOOKS", books);
	}

	@Test
	public void testShouldFindAllBooks() {
		// given when
		List<BookEntity> booksByTitle = bookDao.findAll();
		// then
		assertNotNull(booksByTitle);
		assertFalse(booksByTitle.isEmpty());
		assertEquals(6, booksByTitle.size());
	}

	@Test
	public void testShouldFindAllBooksByTitle() {
		// given
		final String title = "p";
		// when
		List<BookEntity> booksByTitle = bookDao.findBookByTitle(title);
		// then
		assertNotNull(booksByTitle);
		assertFalse(booksByTitle.isEmpty());
		assertEquals(2, booksByTitle.size());
	}

	@Test
	public void testShouldNotFindBookWithTitle() {
		// given
		final String title = "potop";
		// when
		List<BookEntity> booksByTitle = bookDao.findBookByTitle(title);
		// then
		assertNull(booksByTitle);
	}

	@Test
	public void testShouldFindAllBooksByAuthorsLastName() {
		// given
		final String author = "ni";
		// when
		List<BookEntity> booksByAuthor = bookDao.findBooksByAuthor(author);
		// then
		assertNotNull(booksByAuthor);
		assertFalse(booksByAuthor.isEmpty());
		assertEquals(2, booksByAuthor.size());
	}

	@Test
	public void testShouldFindAllBooksByAuthorsFirstName() {
		// given
		final String author = "Hanna";
		// when
		List<BookEntity> booksByAuthor = bookDao.findBooksByAuthor(author);
		// then
		assertNotNull(booksByAuthor);
		assertFalse(booksByAuthor.isEmpty());
		assertEquals(1, booksByAuthor.size());
	}

	@Test
	public void testShouldFindAllBooksByAuthor() {
		// given
		final String author = "Hanna Ożogowska";
		// when
		List<BookEntity> booksByAuthor = bookDao.findBooksByAuthor(author);
		// then
		assertNotNull(booksByAuthor);
		assertFalse(booksByAuthor.isEmpty());
		assertEquals(1, booksByAuthor.size());
	}

	@Test
	public void testShouldFindAllBooksByAuthorWhenLastNameIsFirst() {
		// given
		final String author = "Ożogowska Hanna";
		// when
		List<BookEntity> booksByAuthor = bookDao.findBooksByAuthor(author);
		// then
		assertNotNull(booksByAuthor);
		assertFalse(booksByAuthor.isEmpty());
		assertEquals(1, booksByAuthor.size());
	}

	@Test
	public void testShouldNotFindBookByAuthor() {
		// given
		final String author = "Henryk Sienkiewicz";
		// when
		List<BookEntity> booksByAuthor = bookDao.findBooksByAuthor(author);
		// then
		assertNull(booksByAuthor);
	}
}
