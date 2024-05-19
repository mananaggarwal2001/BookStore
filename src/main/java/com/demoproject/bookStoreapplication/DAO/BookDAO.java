package com.demoproject.bookStoreapplication.DAO;

import com.demoproject.bookStoreapplication.databaseClasses.Book;
import com.demoproject.bookStoreapplication.databaseClasses.Register;

import java.util.List;

public interface BookDAO {
    public void addBook(Book book);
    public Book findBook(String title);

    public List<Book> getAllBooks();

    public Book getBookAccrodingToId(int theid);
    public void removeBook(Book thebook);
}
