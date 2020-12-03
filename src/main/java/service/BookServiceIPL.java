package service;

import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import repository.BookRepository;

public class BookServiceIPL implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findOne(id);
    }

    @Override
    public Book save(Book customer) {
       return bookRepository.save(customer);
    }

    @Override
    public Book remove(Long id) {
        Book book = bookRepository.findOne(id);
        bookRepository.delete(id);
        return book;
    }

    @Override
    public Page<Book> findAllByNameContaining(String name, Pageable pageable) {
        return bookRepository.findAllByNameContaining(name,pageable);
    }
}
