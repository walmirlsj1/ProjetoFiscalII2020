package module.versao;

import module.fiscal.Fiscal;
import module.usuario.Usuario;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


@Entity
public class Versao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "VERSAO")
	private String versao;

	@Column(name = "MODIFICADO")
	private Date modificado;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID")
//	@MapsId("id")
	private Usuario usuario;
	
	public Versao() {
		
	}
	
	public Versao(int id, String versao, Usuario usuario) {
		super();
		this.id = id;
		this.versao = versao;
		this.modificado = new Date();
		this.usuario = usuario;
	}
	
	public Versao(int id, String versao, String modificado, Usuario usuario) {
		super();
		this.id = id;
		this.versao = versao;
		this.usuario = usuario;
		
		try {
			this.modificado = new SimpleDateFormat("dd/MM/yyyy").parse(modificado);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(Fiscal.class.getName()).log(Level.SEVERE, null, e);
//			e.printStackTrace();
		}
	}
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String getVersao() { return versao; }
	public void setVersao(String versao) { this.versao = versao; }
	
	public Date getModificado() { return modificado; }
	public void setModificado(Date modificado) { this.modificado = modificado; }
	
	public Usuario getUsuario() { return usuario; }
	public void setUsuario(Usuario usuario) { this.usuario = usuario; }
	
	public String toString() {
		return this.getVersao();
	}
	
}
