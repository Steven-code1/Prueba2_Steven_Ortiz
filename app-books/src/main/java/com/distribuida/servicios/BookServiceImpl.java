package com.distribuida.servicios;

import com.distribuida.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class BookServiceImpl implements BookService {
    @Inject
    private EntityManager entityManager;

    @Override
    public List<Book> getBooks()  {
        return entityManager
                .createNamedQuery("Book.findAll",Book.class)
                .getResultList();
    }

    @Override
    public Book getBookById(Integer id){
        return entityManager.find(Book.class,id);
    }

    @Override
    public void delete(Integer id) {
        //entityManager.getTransaction().begin();
        //entityManager.remove(getBookById(id));
        //entityManager.getTransaction().commit();
        Book book = entityManager.find(Book.class, id);
        entityManager.getTransaction().begin();
        if (book != null) {
            entityManager.remove(book);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public void creteBook(Book book) {
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void updateBook(Integer id, Book book){
        var GB=this.getBookById(id);
        GB.setIsbn(book.getIsbn());
        GB.setAuthor(book.getAuthor());
        GB.setTitle(book.getTitle());
        GB.setPrice(book.getPrice());
        entityManager.persist(GB);
    }



    @Override
    public List<Book> getBookByAuthor(Integer authorId){
        return entityManager.createNamedQuery("Book.findByAuthor",Book.class).setParameter("authorId",authorId)
                .getResultList();

    }
}
