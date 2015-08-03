package pl.spring.demo.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import pl.spring.demo.to.AuthorTo;
import pl.spring.demo.to.BookTo;
import pl.string.demo.entity.BookEntity;

@Component
public class BookMapper {
	public BookTo convertToTo(BookEntity bookEntity) {
		if (bookEntity != null && bookEntity.getAuthors() != null) {
			StringBuilder authors = new StringBuilder();
			for (AuthorTo author : bookEntity.getAuthors()) {
				authors.append(author.getFirstName() + " " + author.getLastName() + " ");
			}
			authors.deleteCharAt(authors.length() - 1);
			return new BookTo(bookEntity.getId(), bookEntity.getTitle(), authors.toString());
		}
		return null;
	}

	public BookEntity convertToEntity(BookTo bookTo) {
		if (bookTo != null && bookTo.getAuthors() != null) {
			List<AuthorTo> authors = new ArrayList<AuthorTo>();
			String[] authorsArray = bookTo.getAuthors().split(" ");
			if(authorsArray.length == 1){
				authors.add(new AuthorTo(1L, authorsArray[0], null));
				return new BookEntity(bookTo.getId(), bookTo.getTitle(), authors);
			}
			for (int i = 0; i < authorsArray.length && authorsArray.length > 1; i = i + 2) {
				authors.add(new AuthorTo((long) i / 2, authorsArray[i], authorsArray[i + 1]));
			}
			
			return new BookEntity(bookTo.getId(), bookTo.getTitle(), authors);
		}
		return null;
	}

	public List<BookTo> convertToBookToList(List<BookEntity> bookEntities) {
		if (bookEntities != null) {
			List<BookTo> bookTos = new ArrayList<BookTo>();
			for (BookEntity bookEntity : bookEntities) {
				bookTos.add(convertToTo(bookEntity));
			}
			return bookTos;
		}
		return null;
	}

	public List<BookEntity> convertToBookEntityList(List<BookTo> bookTos) {
		if (bookTos != null) {
			List<BookEntity> bookEntities = new ArrayList<BookEntity>();
			for (BookTo bookTo : bookTos) {
				bookEntities.add(convertToEntity(bookTo));
			}
			return bookEntities;
		}
		return null;
	}
}
