package com.fdr.library.book.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
@Entity
@Data
@Builder
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // ISBN : International Standard Book Number
    // <2007 : 10 digits
    // 2007+ : 13 digits
    @Column(unique = true)
    String isbn;

    String name;

    Integer pages;

    Integer year;

    String description;






}
