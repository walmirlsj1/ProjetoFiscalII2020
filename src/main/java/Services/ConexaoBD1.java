package Services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConexaoBD1 {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    public static EntityManager getEM2() {
        if (entityManagerFactory == null)
            entityManagerFactory = Persistence.createEntityManagerFactory("Teste-01");
        if (entityManager == null) // || entityManager.isOpen()
            entityManager = entityManagerFactory.createEntityManager();

        return entityManager;
    }

    public void close() {
        entityManager.close();
        entityManagerFactory.close();
        entityManager = null;
        entityManagerFactory = null;
    }
}
