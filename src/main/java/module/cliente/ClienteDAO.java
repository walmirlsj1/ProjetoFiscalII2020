package module.cliente;

import java.util.List;
import javax.persistence.TypedQuery;

import global.GenericoDAO;
import global.service.PersistEngine;

public class ClienteDAO extends GenericoDAO<Cliente> {

    public ClienteDAO() {

    }

    public boolean updateAll(List<Cliente> cliente) {
        if (!PersistEngine.getEntityManager().getTransaction().isActive())
            PersistEngine.beginTransaction();
        try {
            cliente.forEach(c -> PersistEngine.merge(c));

            PersistEngine.commit();
            return true;
        } catch (Exception e) {
            PersistEngine.rollback();
            logger.warn(e.getMessage());
        }
        return false;
    }

    public List<Cliente> selectFilter(String value, String filter) {

        String jpql = "SELECT c FROM Cliente c WHERE c.nome like :value";
        TypedQuery<Cliente> typedQuery = PersistEngine.getEntityManager().createQuery(jpql, Cliente.class);
        typedQuery.setParameter("value", value);

        return typedQuery.getResultList();
    }

}