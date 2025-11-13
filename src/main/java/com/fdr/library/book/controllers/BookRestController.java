package com.fdr.library.book.controllers;

import com.fdr.library.book.dto.BookDTO;
import com.fdr.library.book.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
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
    public String get(@RequestParam String bookName, @RequestParam Integer bookPages) throws BadRequestException {

        String response = bookService.createBook(bookName, bookPages);

        return "OK get";

    }

    @PostMapping
    public String post(@RequestBody BookDTO.PostInput input) throws BadRequestException {
        String response = bookService.createBook(input.getBookName(), input.getBookPages());
        return response;
    }
}
