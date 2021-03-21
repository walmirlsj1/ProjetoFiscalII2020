package module.fiscal;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.TypedQuery;

import global.GenericoDAO;
import module.cliente.Cliente;

import global.service.PersistEngine;

public class FiscalDAO extends GenericoDAO<Fiscal> {

    public List<Fiscal> selectFilterClienteMesAno(Cliente cliente, int mes, int ano) {
//		String jpql = "SELECT * FROM FISCAL WHERE CLIENTE_ID=? AND MES=? AND ANO=?";
        CriteriaBuilder criteriaBuilder = PersistEngine.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Fiscal> criteriaQuery = criteriaBuilder.createQuery(Fiscal.class);
        Root<Fiscal> root = criteriaQuery.from(Fiscal.class);

        criteriaQuery.select(root);
        criteriaQuery.where(
                criteriaBuilder.equal(root.get("cliente"), cliente),
                criteriaBuilder.equal(root.get("mes"), mes),
                criteriaBuilder.equal(root.get("ano"), ano)
        );

        TypedQuery<Fiscal> typedQuery = PersistEngine.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    public List<Fiscal> selectFilterMesAno(int mes, int ano) {

        CriteriaBuilder criteriaBuilder = PersistEngine.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Fiscal> criteriaQuery = criteriaBuilder.createQuery(Fiscal.class);
        Root<Fiscal> root = criteriaQuery.from(Fiscal.class);

        criteriaQuery.select(root);
        criteriaQuery.where(
                criteriaBuilder.equal(root.get("mes"), mes),
                criteriaBuilder.equal(root.get("ano"), ano)
        );

        TypedQuery<Fiscal> typedQuery = PersistEngine.getEntityManager().createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

}
