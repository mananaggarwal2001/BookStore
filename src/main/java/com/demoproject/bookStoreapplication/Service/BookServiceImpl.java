package com.demoproject.bookStoreapplication.Service;

import com.demoproject.bookStoreapplication.DAO.BookDAO;
import com.demoproject.bookStoreapplication.DTO.BookDTO;
import com.demoproject.bookStoreapplication.databaseClasses.Book;
import com.demoproject.bookStoreapplication.databaseClasses.Register;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    BookDAO bookDAO;

    public BookServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public void addBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setAvailableQuantity(bookDTO.getAvailableQuantity());
        book.setPrice(bookDTO.getPrice());
        book.setTitle(bookDTO.getTitle());
        bookDAO.addBook(book);
    }

    @Override
    public Book findBook(String title) {
        return bookDAO.findBook(title);
    }

    @Override
    public List<Book> bookList() {
        return bookDAO.getAllBooks();
    }

    @Override
    public Book findBookById(int theid) {
        return bookDAO.getBookAccrodingToId(theid);
    }

    @Override
    @Transactional
    public void removeBook(Book book) {
        bookDAO.removeBook(book);
    }
}
