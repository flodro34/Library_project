package com.fdr.library.book.service;

import com.fdr.library.book.persistence.BookRepository;
import com.fdr.library.book.model.BookEntity;
import io.micrometer.common.util.StringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public String createBook(String bookName, Integer bookPages) throws BadRequestException {

        if(bookName == null || StringUtils.isBlank(bookName)) {
            //return "Le bookName ne peut être null ou vide";
            throw new BadRequestException("Book name is null or empty");
        }

        if(bookPages == null || bookPages <= 0) {
            //return "Le nombre de pages ne peut être null ou vide";
            throw new BadRequestException("Book pages is null or empty");
        }

        BookEntity existingBook = bookRepository.findByNameAndPages(bookName, bookPages);

        if (existingBook == null) {
            BookEntity newBook = BookEntity.builder()
                    .name(bookName)
                    .pages(bookPages)
                    .build();

            bookRepository.save(newBook);
            return "Book Created";
        }
        else {
            throw new BadRequestException ("Book existed") ;
        }
    }
}
