package pl.spring.demo.dao;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.to.AuthorTo;
import pl.string.demo.entity.BookEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "CommonServiceTest-context.xml")
public class BookDaoImplTestContext {
	@Autowired
	private BookDao bookDao;

	@Test
	public void testShouldSaveNewBookAndAddToCollection() {
		// given
		BookEntity book = new BookEntity(null, "random title",
				Arrays.asList(new AuthorTo(1L, "rand imie", "rand nazw")));
		int numberOfBooks = bookDao.findAll().size();
		// when
		BookEntity savedBook = bookDao.save(book);
		// then
		assertEquals(numberOfBooks + 1, bookDao.findAll().size());
		assertNotNull(savedBook.getId().longValue());
	}

	@Test(expected = BookNotNullIdException.class)
	public void testShouldThrowBookNotNullIdException() {
		// given
		BookEntity book = new BookEntity(3L, "random title", Arrays.asList(new AuthorTo(1L, "rand imie", "rand nazw")));
		// when
		bookDao.save(book);
		// then
		fail("test should throw BookNotNullIdException");
	}
}
