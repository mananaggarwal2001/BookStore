package com.demoproject.bookStoreapplication.DAO;

import com.demoproject.bookStoreapplication.databaseClasses.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {

    EntityManager entityManager;

    public BookDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void addBook(Book book) {
        entityManager.persist(book);
    }

    @Override
    public Book findBook(String title) {
        TypedQuery<Book> createQuery = entityManager.createQuery("from Book " + "where title=:booktitle", Book.class);
        createQuery.setParameter("booktitle", title);
        Book resultBook;
        try {
            resultBook = createQuery.getSingleResult();
        } catch (Exception e) {
            resultBook = null;
        }
        return resultBook;
    }

    @Override
    public List<Book> getAllBooks() {
        TypedQuery<Book> bookTypedQuery = entityManager.createQuery("FROM Book", Book.class);
        return bookTypedQuery.getResultList();
    }

    @Override
    public Book getBookAccrodingToId(int theid) {
        return entityManager.find(Book.class, theid);
    }
}
