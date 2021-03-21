package global;

//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.persistence.TypedQuery;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import global.service.PersistEngine;

public class GenericoDAO<T> {
    protected final static Logger logger  = LoggerFactory.getLogger(GenericoDAO.class.getName());

    private final ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
    private Class<T> entity = (Class<T>) (type).getActualTypeArguments()[0];

    public boolean insert(T registro) {
        if (!PersistEngine.getEntityManager().getTransaction().isActive())
            PersistEngine.beginTransaction();

        try {
            PersistEngine.persist(registro);
            PersistEngine.commit();
            return true;
        } catch (Exception e) {
            PersistEngine.rollback();
            logger.debug(e.getMessage());
        }

        return false;
    }

    public boolean update(T registro) {
        if (!PersistEngine.getEntityManager().getTransaction().isActive())
            PersistEngine.beginTransaction();

        try {
            PersistEngine.merge(registro);
            PersistEngine.commit();
            return true;
        } catch (Exception e) {
            PersistEngine.rollback();
            logger.debug(e.getMessage());
        }
        return false;
    }

    public boolean delete(T registro) {
        if (!PersistEngine.getEntityManager().getTransaction().isActive())
            PersistEngine.beginTransaction();
        try {
            PersistEngine.remove(registro);
            PersistEngine.commit();
            return true;
        } catch (Exception e) {
            PersistEngine.rollback();
            logger.debug(e.getMessage());
        }
        return false;
    }

    public T selectPerID(int id) {
        return PersistEngine.getEntityManager().find(entity, id);
    }

    public List<T> selectAll() {
        String jpql = "SELECT z FROM " + entity.getSimpleName() + " z";
        TypedQuery<T> typedQuery = PersistEngine.getEntityManager().createQuery(jpql, entity);
        return typedQuery.getResultList();
    }

}
