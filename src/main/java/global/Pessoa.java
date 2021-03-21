package global;

//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@MappedSuperclass
public class Pessoa {
	protected static Logger logger;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "TELEFONE")
	private String telefone;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ATIVO")
	private Boolean ativo;

	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFICADO")
	private Date modificado;
	
	public Pessoa() {
		
	}
	
	public Pessoa(int id, String nome, String telefone, String email, Boolean ativo, String modificado) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.ativo = ativo;
		if(modificado.length() < 10) {
			this.modificado = new Date();
		} else {
			try {
				this.modificado = new SimpleDateFormat("dd/MM/yyyy").parse(modificado);
			} catch (ParseException e) {
				logger.debug("Erro data inválida" + e.getMessage());
			}
		}
	}
	
	public Pessoa(int id) {
		this.id = id;
//		this.modificado = new Date();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getModificado() { return modificado; }

	public void setModificado(Date modificado) { this.modificado = modificado; }

	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@PrePersist
	protected void onCreate() {
		modificado = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		modificado = new Date();
	}
}
