package module.versao;

import java.util.List;

import javax.persistence.TypedQuery;

import global.GenericoDAO;
import global.service.PersistEngine;

public class VersaoDAO extends GenericoDAO<Versao> {

	public VersaoDAO() {
	}

	public List<Versao> selectFilter(String value, String filter) {

		String jpql = "SELECT s1 FROM Versao s1";
		TypedQuery<Versao> typedQuery = PersistEngine.getEntityManager().createQuery(jpql, Versao.class);
		List<Versao> lista = typedQuery.getResultList();

		return lista;
	}

}