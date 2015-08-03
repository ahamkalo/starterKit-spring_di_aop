package pl.spring.demo.dao.impl;

import pl.spring.demo.annotation.SetId;
import pl.spring.demo.common.Sequence;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.to.AuthorTo;
import pl.string.demo.entity.BookEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookDaoImpl implements BookDao {
	
	private static Set<BookEntity> ALL_BOOKS = new HashSet<>();
	@Autowired
	private Sequence sequence;

	public BookDaoImpl() {
		addTestBooks();
	}

	@Override
	public List<BookEntity> findAll() {
		return new ArrayList<>(ALL_BOOKS);
	}

	@Override
	public List<BookEntity> findBookByTitle(String title) {
		List<BookEntity> bookEntities = new ArrayList<BookEntity>();
		for (BookEntity bookEntity : ALL_BOOKS) {
			if (bookEntity.getTitle().toLowerCase().startsWith(title.toLowerCase())) {
				bookEntities.add(bookEntity);
			}
		}
		if(bookEntities.isEmpty()){
			return null;
		}
		return bookEntities;
	}

	@Override
	public List<BookEntity> findBooksByAuthor(String author) {
		List<BookEntity> bookEntities = new ArrayList<BookEntity>();
		String[] names = author.split(" ");

		switch (names.length) {
		case 1:
			for (BookEntity bookEntity : ALL_BOOKS) {
				for (AuthorTo authorTo : bookEntity.getAuthors()) {
					
					if (authorTo.getFirstName().toLowerCase().startsWith(author.toLowerCase())) {
						bookEntities.add(bookEntity);
						continue;
					}
					if (authorTo.getLastName().toLowerCase().startsWith(author.toLowerCase())) {
						bookEntities.add(bookEntity);
						continue;
					}
				}
			}
			break;
		case 2:
			for (BookEntity bookEntity : ALL_BOOKS) {
				for (AuthorTo authorTo : bookEntity.getAuthors()) {
					if (authorTo.getFirstName().toLowerCase().equals(names[0].toLowerCase())
							&& authorTo.getLastName().toLowerCase().startsWith(names[1].toLowerCase())) {
						bookEntities.add(bookEntity);
						continue;
					}
					if (authorTo.getLastName().toLowerCase().equals(names[0].toLowerCase())
							&& authorTo.getFirstName().toLowerCase().startsWith(names[1].toLowerCase())) {
						bookEntities.add(bookEntity);
						continue;
					}
				}
			}
			break;
		default:
			break;
		}
		
		if(bookEntities.isEmpty()){
			return null;
		}

		return bookEntities;
	}

	@Override
	@SetId
	public BookEntity save(BookEntity book) {
		ALL_BOOKS.add(book);
		return book;
	}

	public long getNextSequenceValue() {
		return sequence.nextValue(ALL_BOOKS);
	}

	private void addTestBooks() {
		ALL_BOOKS.add(new BookEntity(1L, "Romeo i Julia", Arrays.asList(new AuthorTo(1L, "Wiliam", "Szekspir"))));
		ALL_BOOKS.add(new BookEntity(2L, "Opium w rosole", Arrays.asList(new AuthorTo(2L, "Hanna", "Ożogowska"))));
		ALL_BOOKS.add(new BookEntity(3L, "Przygody Odyseusza", Arrays.asList(new AuthorTo(3L, "Jan", "Parandowski"))));
		ALL_BOOKS
				.add(new BookEntity(4L, "Awantura w Niekłaju", Arrays.asList(new AuthorTo(4L, "Edmund", "Niziurski"))));
		ALL_BOOKS.add(new BookEntity(5L, "Pan Samochodzik i Fantomas",
				Arrays.asList(new AuthorTo(5L, "Zbigniew", "Nienacki"))));
		ALL_BOOKS.add(new BookEntity(6L, "Zemsta", Arrays.asList(new AuthorTo(6L, "Aleksander", "Fredro"))));
	}
}
