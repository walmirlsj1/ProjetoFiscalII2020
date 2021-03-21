package module.contador;

import global.Pessoa;
import module.cliente.Cliente;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CONTADOR")
public class Contador extends Pessoa {
	@Column(name = "CNPJ")
	private String cnpj;

	@SuppressWarnings("JpaAttributeTypeInspection")
	@OneToMany(mappedBy="contador", targetEntity = Cliente.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Cliente> lstClientes;

	public Contador() {

	}

	public Contador(int id) {
		super(id);

	}
	public Contador(int id, String nome, String cnpj, String telefone, String email, Boolean ativo, String modificado) {
		super(id, nome, telefone, email, ativo, modificado);
		this.cnpj = cnpj;
	}

	public String getCnpj() { return cnpj; }
	public void setCnpj(String cnpj) { this.cnpj = cnpj; }

	public List<Cliente> getLstClientes() {
		return lstClientes;
	}

	public void setLstClientes(List<Cliente> lstClientes) {
		this.lstClientes = lstClientes;
	}
}
