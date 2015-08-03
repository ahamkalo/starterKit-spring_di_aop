package pl.spring.demo.common;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pl.spring.demo.to.AuthorTo;
import pl.spring.demo.to.BookTo;
import pl.string.demo.entity.BookEntity;

public class BookMapperTest {
	private BookMapper bookMapper = new BookMapper();

	@Test
	public void shouldProperlyConvertToBookTo() {
		// given
		BookEntity book = new BookEntity(1L, "Romeo i Julia", Arrays.asList(new AuthorTo(1L, "Wiliam", "Szekspir")));
		// when
		BookTo bookTo = bookMapper.convertToTo(book);
		// then
		assertNotNull(bookTo);
		assertEquals(1L, bookTo.getId().longValue());
		assertEquals("Romeo i Julia", bookTo.getTitle());
		assertEquals("Wiliam Szekspir", bookTo.getAuthors());
	}

	@Test
	public void shouldReturnNull() {
		// given when then
		assertNull(bookMapper.convertToEntity(null));
		assertNull(bookMapper.convertToTo(null));
		
		assertNull(bookMapper.convertToEntity(new BookTo(null, null, null)));
		assertNull(bookMapper.convertToTo(new BookEntity(null, null, null)));
		
		assertNull(bookMapper.convertToBookToList(null));
		assertNull(bookMapper.convertToBookEntityList(null));
	}

	@Test
	public void shouldProperlyConvertToBookEntity() {
		// given
		BookTo book = new BookTo(1L, "Romeo i Julia", "Wiliam Szekspir");
		// when
		BookEntity bookEntity = bookMapper.convertToEntity(book);
		// then
		assertNotNull(bookEntity);
		assertEquals(1L, bookEntity.getId().longValue());
		assertEquals("Romeo i Julia", bookEntity.getTitle());
		assertEquals("Wiliam", bookEntity.getAuthors().get(0).getFirstName());
		assertEquals("Szekspir", bookEntity.getAuthors().get(0).getLastName());
	}

	@Test
	public void shouldProperlyConvertToBookToList() {
		// given
		List<BookEntity> bookEntities = new ArrayList<BookEntity>(Arrays.asList(
				new BookEntity(1L, "Romeo i Julia", Arrays.asList(new AuthorTo(2L, "Wiliam", "Szekspir"))),
				new BookEntity(2L, "Opium w rosole", Arrays.asList(new AuthorTo(2L, "Hanna", "Ożogowska")))));
		// when
		List<BookTo> bookTos = bookMapper.convertToBookToList(bookEntities);
		// then
		assertNotNull(bookTos);
		assertFalse(bookTos.isEmpty());
		assertEquals(2, bookTos.size());
		assertEquals("Opium w rosole", bookTos.get(1).getTitle());
	}

	@Test
	public void shouldProperlyConvertToBookEntityList() {
		// given
		List<BookTo> bookTos = new ArrayList<BookTo>(Arrays.asList(new BookTo(1L, "Romeo i Julia", "Wiliam Szekspir"),
				new BookTo(2L, "Opium w rosole", "Hanna Ożogowska")));
		// when
		List<BookEntity> bookEntities = bookMapper.convertToBookEntityList(bookTos);
		// then
		assertNotNull(bookEntities);
		assertFalse(bookEntities.isEmpty());
		assertEquals(2, bookEntities.size());
		assertEquals("Opium w rosole", bookEntities.get(1).getTitle());
	}
}
