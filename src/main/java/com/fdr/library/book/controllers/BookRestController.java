package com.fdr.library.book.controllers;

import com.fdr.library.book.dto.BookDTO;
import com.fdr.library.book.model.BookEntity;
import com.fdr.library.book.model.exception.BookCreationException;
import com.fdr.library.book.service.BookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/book")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String get(@RequestParam String bookName, @RequestParam Integer bookPages) throws BookCreationException {

        //String response = bookService.createBook(bookName, bookPages);

        return "OK get";

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO.PostOutput post(@Valid @RequestBody BookDTO.PostInput input) throws BookCreationException {

        log.info(input.getBookIsbn());
        log.info(input.getBookName());
        log.info(String.valueOf(input.getBookPages()));
        log.info(String.valueOf(input.getBookYear()));
        log.info(input.getBookDescription());

        BookEntity newBook = bookService.createBook(input.getBookIsbn(),input.getBookName(), input.getBookPages(), input.getBookYear(), input.getBookDescription());

        return  BookDTO.PostOutput.builder()
                                  .bookId(newBook.getId())
                                  .bookIsbn(newBook.getIsbn())
                                  .bookName(newBook.getName())
                                  .bookPages(newBook.getPages())
                                  .bookYear(newBook.getYear())
                                  .bookDescription(newBook.getDescription())
                                  .build();
    }
}
