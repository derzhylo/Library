package BooksLibrary.Database;

import BooksLibrary.Entity.Book;
import BooksLibrary.Service.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by Юра on 29.12.2017.
 */
public class BookDAO {
    private static EntityManagerFactory entityManagerFactory = null;
    private static EntityManager entityManager = null;
    private static EntityTransaction transaction = null;

    public static boolean addBook(Book book) {
        entityManagerFactory = Hibernate.createEntityManagerFactory();
        entityManager = Hibernate.createEntityManager(entityManagerFactory);
        transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist( book );
            transaction.commit();
        } catch ( PersistenceException e ) {
            if ( transaction.isActive() )
                transaction.rollback();

            e.printStackTrace();
            return false;
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

        return true;
    }

    public static boolean removeBook(Book book) {
        entityManagerFactory = Hibernate.createEntityManagerFactory();
        entityManager = Hibernate.createEntityManager(entityManagerFactory);
        transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Book b = entityManager.merge( book );
            entityManager.remove( b );
            transaction.commit();
        } catch ( PersistenceException e ) {
            if ( transaction.isActive() )
                transaction.rollback();

            e.printStackTrace();
            return false;
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

        return true;
    }

    public static List<Book> getBooksByName(String name) {
        entityManagerFactory = Hibernate.createEntityManagerFactory();
        entityManager = Hibernate.createEntityManager(entityManagerFactory);
        transaction = entityManager.getTransaction();
        List<Book> books = null;
        try {
            transaction.begin();
            books = entityManager.createQuery( "SELECT b FROM Book b WHERE b.name = :name", Book.class )
                    .setParameter( "name", name )
                    .getResultList();
            transaction.commit();
        } catch ( PersistenceException e ) {
            if ( transaction.isActive() )
                transaction.rollback();

            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

        return books;
    }

    public static List<Book> getAllBooks() {
        entityManagerFactory = Hibernate.createEntityManagerFactory();
        entityManager = Hibernate.createEntityManager(entityManagerFactory);
        transaction = entityManager.getTransaction();
        List<Book> books = null;
        try {
            transaction.begin();
            books = entityManager.createQuery( "SELECT b FROM Book b", Book.class ).getResultList();
            transaction.commit();
        } catch ( PersistenceException e ) {
            if ( transaction.isActive() )
                transaction.rollback();

            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

        return books;
    }

    public static boolean editBook(Book book, String name) {
        entityManagerFactory = Hibernate.createEntityManagerFactory();
        entityManager = Hibernate.createEntityManager(entityManagerFactory);
        transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            book.setName( name );
            entityManager.merge( book );
            transaction.commit();
        } catch ( PersistenceException e ) {
            if ( transaction.isActive() )
                transaction.rollback();

            e.printStackTrace();
            return false;
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

        return true;
    }

    public static boolean checkAvailability(String name, String author) {
        entityManagerFactory = Hibernate.createEntityManagerFactory();
        entityManager = Hibernate.createEntityManager(entityManagerFactory);
        transaction = entityManager.getTransaction();
        List<Book> books = null;
        try {
            transaction.begin();
            books = entityManager.createQuery("SELECT b FROM Book b WHERE b.name = :name AND b.author = :author", Book.class)
                    .setParameter( "name", name )
                    .setParameter( "author", author )
                    .getResultList();
            transaction.commit();
        } catch ( PersistenceException e ) {
            if ( transaction.isActive() )
                transaction.rollback();

            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

        return !books.isEmpty();
    }
}


