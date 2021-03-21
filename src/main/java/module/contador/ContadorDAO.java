package module.contador;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.TypedQuery;

import global.service.PersistEngine;
import global.GenericoDAO;


public class ContadorDAO extends GenericoDAO<Contador> {

    public ContadorDAO() {

    }

    public List<Contador> selectFilter(String value, String filter) throws SQLException {

        String jpql = "SELECT c1 FROM Contador c1";
        TypedQuery<Contador> typedQuery = PersistEngine.getEntityManager().createQuery(jpql, Contador.class);
        List<Contador> lista = typedQuery.getResultList();

        return lista;
    }

}