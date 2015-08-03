package pl.spring.demo.entity;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import pl.spring.demo.to.AuthorTo;
import pl.string.demo.entity.BookEntity;

public class BookEntityTest {
	@Test
	public void shouldProperlyCompareBookEntitiesById() {
		//given
		BookEntity book = new BookEntity(1L, "Romeo i Julia", Arrays.asList(new AuthorTo(2L,"Wiliam","Szekspir")));
		BookEntity otherBook = new BookEntity(1L, "Opium w rosole", Arrays.asList(new AuthorTo(2L, "Hanna", "Ożogowska")));
		BookEntity otherBook2 = new BookEntity(2L, "Romeo i Julia", Arrays.asList(new AuthorTo(2L,"Wiliam","Szekspir")));
		
		//when then
		assertTrue(book.equals(otherBook));
		assertFalse(book.equals(otherBook2));
	}
}
