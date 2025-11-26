package com.fdr.library.book.service;

import com.fdr.library.book.model.exception.BookCreationException;
import com.fdr.library.book.persistence.BookRepository;
import com.fdr.library.book.model.BookEntity;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.time.Year;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookEntity createBook(String isbn,String bookName, Integer bookPages, Integer year, String description) throws BookCreationException {

        if(isbn == null || StringUtils.isBlank(isbn)) {
            throw new BookCreationException("isbn is null or empty");
        }

        if(!BookService.isValidIsbn13(isbn)) {
            throw new BookCreationException("isbn is invalid");
        }

        if(bookName == null || StringUtils.isBlank(bookName)) {
            //return "Le bookName ne peut être null ou vide";
            throw new BookCreationException("Book name is null or empty");
        }

        if(bookPages == null || bookPages <= 0) {
            //return "Le nombre de pages ne peut être null ou vide";
            throw new BookCreationException("Book pages is null or empty");
        }

        if(year == null || year > Year.now().getValue()) {
            throw new BookCreationException("L'année de parution ne peut être postériure à l'année en cours");
        }

        BookEntity existingBook = bookRepository.findByIsbn(isbn);

        //BookEntity existingBook = bookRepository.findByNameAndPages(bookName, bookPages);

//        if (existingBook != null) {
//            throw new BookCreationException ("Book existed") ;
//        }

        BookEntity newBook = BookEntity.builder()
                .isbn(isbn)
                .name(bookName)
                .pages(bookPages)
                .year(year)
                .description(description)
                .build();
        bookRepository.save(newBook);

        return newBook;
    }

    public static boolean isValidIsbn13(String rawIsbn) {

        if (rawIsbn == null) {
            return false;
        }

        // 1. Nettoyage : on enlève les espaces et tirets
        String isbn = rawIsbn.replaceAll("[\\s-]+", "");

        // 2. Vérif de la longueur et que tout est bien numérique
        if (!isbn.matches("\\d{13}")){
            return false;
        }

        return true;

        //TODO : Uncomment algo check
//        // 3. Calcul du check digit
//        int sum = 0;
//        for(int i = 0; i < 12; i++) {
//            int digit = isbn.charAt(i) - '0';
//
//            // positions paires/impaires en base 0
//            if(i % 2 == 0) {
//                // poids 1 pour d1, d3, d5 ... => i = 0, 2, 4 ...
//                sum += digit;
//            } else {
//                // poids 3 pour d2, d4, d6 ... => i = 1, 3, 5 ...
//                sum += 3 * digit;
//            }
//        }
//
//        int modulo = sum % 10;
//        int expectedCheckDigit = (10 - modulo) % 10;
//
//        int actualCheckDigit = isbn.charAt(12) - '0';
//
//        return expectedCheckDigit == actualCheckDigit;
    }
}