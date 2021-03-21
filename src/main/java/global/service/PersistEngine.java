package global.service;


import org.apache.commons.configuration2.ex.ConfigurationException;

//import org.apache.log4j.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import global.service.config.Config;

public class PersistEngine {

    protected final static Logger logger = LoggerFactory.getLogger(PersistEngine.class.getName());

    private static EntityManagerFactory factory = createFactory();
    private static final ThreadLocal<EntityManager> threadLocal = new ThreadLocal<EntityManager>();

    private static EntityManagerFactory createFactory() {
        Map props = new HashMap<>();
        try {
            String db_host = Config.getConfiguracao().getString("db_host");
            String db_local = Config.getConfiguracao().getString("db_local");
            String db_porta = Config.getConfiguracao().getString("db_porta");

            props.put("javax.persistence.jdbc.url", "jdbc:firebirdsql:" + db_host + "/" + db_porta + ":" + db_local);
            props.put("javax.persistence.jdbc.user", "sysdba");
            props.put("javax.persistence.jdbc.password", "masterkey");

//            props.put("javax.persistence.jdbc.driver", "org.firebirdsql.jdbc.FBDriver");
//            props.put("hibernate.dialect", "org.hibernate.dialect.FirebirdDialect");
            props.put("hibernate.show_sql", "false");
            props.put("hibernate.format_sql", "false");

            return Persistence.createEntityManagerFactory("Teste-01", props);

        } catch (ConfigurationException | IOException e) {
            logger.error("Erro ao conectar ao banco de dados! ");
            logger.debug("DB CONNECT: ", e.getCause());
            System.exit(1);
        }
        return null;
    }

    public static EntityManager getEntityManager() {
        EntityManager em = threadLocal.get();

        if (em == null) {
            em = factory.createEntityManager();
            threadLocal.set(em);
        }
        return em;
    }

    public static void closeEntityManager() {
        EntityManager em = threadLocal.get();
        if (em != null) {
            em.close();
            threadLocal.set(null);
        }
    }

    public static void closeEntityManagerFactory() {
        factory.close();
    }

    public static void beginTransaction() {
        getEntityManager().getTransaction().begin();
    }

    public static void rollback() {
        getEntityManager().getTransaction().rollback();
    }

    public static void commit() {
        getEntityManager().getTransaction().commit();
    }

    public static <T> void persist(T entity) {

        getEntityManager().persist(entity);
    }

    public static <T> void remove(T entity) {

        getEntityManager().remove(entity);
    }

    public static <T> T merge(T entity) {

        return getEntityManager().merge(entity);
    }

    public static <T> Object findById(Class<T> clazz, Integer id) {

        T result = getEntityManager().find(clazz, id);

        return result;
    }
}

