import module.cliente.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteJPA {
//    public TesteJPA() {
//
//    }

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory
                = Persistence.createEntityManagerFactory("Teste-01");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        Contador contador = new Contador();
////        contador.setId(0);
//        contador.setNome("Arm Feliz");
//        contador.setCnpj("01.142.369/0001-52");
//        contador.setAtivo(T_YESNO.FALSE);
//        contador.setModificado(new Date());
//        contador.setTelefone("5465646");
//        contador.setEmail("teste@teste.com.br");
//        entityManager.getTransaction().begin();
//        entityManager.persist(contador);
//        entityManager.getTransaction().commit();
        System.out.println("teste ok");


        Cliente cliente = entityManager.find(Cliente.class, 34);

        System.out.println(cliente.getId() + " - " + cliente.getContador().getNome() + " - " + cliente.getVersao().getVersao() + " ativo: "+ cliente.getAtivo());

        entityManager.close();
        entityManagerFactory.close();
    }
}
