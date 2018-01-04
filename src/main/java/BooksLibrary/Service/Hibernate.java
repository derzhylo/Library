package BooksLibrary.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Юра on 29.12.2017.
 */
public class Hibernate {

    public static EntityManager createEntityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    public static EntityManagerFactory createEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("library");
    }
}

