package module.usuario;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import global.GenericoDAO;
import global.service.PersistEngine;

public class UsuarioDAO extends GenericoDAO<Usuario> {

	public UsuarioDAO() {
	}

	public List<Usuario> selectFilter(String value, String filter) {

		String jpql = "SELECT s1 FROM Usuario s1";
		TypedQuery<Usuario> typedQuery = PersistEngine.getEntityManager().createQuery(jpql, Usuario.class);
		List<Usuario> lista = typedQuery.getResultList();

		return lista;
	}

	public Usuario selectPorNomeESenha(Usuario usuario1) throws NoResultException {
//		String query = "SELECT * FROM USUARIO WHERE USERNAME=? and PASSWD=?";
		CriteriaBuilder criteriaBuilder = PersistEngine.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
		Root<Usuario> root = criteriaQuery.from(Usuario.class);

		criteriaQuery.select(root);
		criteriaQuery.where(
				criteriaBuilder.equal(root.get("username"), usuario1.getUsername()),
				criteriaBuilder.equal(root.get("passwd"), usuario1.getPasswd())
		);

		TypedQuery<Usuario> typedQuery = PersistEngine.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getSingleResult();
	}
	public Usuario findByUsername(Usuario usuario1) throws NoResultException {
//		String query = "SELECT * FROM USUARIO WHERE USERNAME=? and PASSWD=?";
		CriteriaBuilder criteriaBuilder = PersistEngine.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
		Root<Usuario> root = criteriaQuery.from(Usuario.class);

		criteriaQuery.select(root);
		criteriaQuery.where(
				criteriaBuilder.equal(root.get("username"), usuario1.getUsername())
//				criteriaBuilder.equal(root.get("passwd"), usuario1.getPasswd())
		);

		TypedQuery<Usuario> typedQuery = PersistEngine.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getSingleResult();
	}
}