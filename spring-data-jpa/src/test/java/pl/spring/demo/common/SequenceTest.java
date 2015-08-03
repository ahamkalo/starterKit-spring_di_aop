package pl.spring.demo.common;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import pl.spring.demo.to.AuthorTo;
import pl.string.demo.entity.BookEntity;

public class SequenceTest {
	private static final Set<BookEntity> ALL_BOOKS = new HashSet<>();
	Sequence sequence = new Sequence();

	@Test
	public void testShouldReturnNextSequenceValue() {
		// given
		ALL_BOOKS.add(new BookEntity(1L, "Romeo i Julia", Arrays.asList(new AuthorTo(1L, "Wiliam", "Szekspir"))));
		ALL_BOOKS.add(new BookEntity(2L, "Opium w rosole", Arrays.asList(new AuthorTo(2L, "Hanna", "Ożogowska"))));
		// when then
		assertEquals(3L, sequence.nextValue(ALL_BOOKS));
	}

}
