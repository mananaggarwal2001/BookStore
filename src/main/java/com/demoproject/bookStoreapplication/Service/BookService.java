package com.demoproject.bookStoreapplication.Service;

import com.demoproject.bookStoreapplication.DTO.BookDTO;
import com.demoproject.bookStoreapplication.databaseClasses.Book;
import com.demoproject.bookStoreapplication.databaseClasses.Register;

import java.util.List;

public interface BookService {
    void addBook(BookDTO bookDTO);
    Book findBook(String title);
    List<Book> bookList();
    Book findBookById(int theid);
}
