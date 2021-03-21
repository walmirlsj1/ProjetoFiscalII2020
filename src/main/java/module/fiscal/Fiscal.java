package module.fiscal;

import global.Status;
import module.cliente.Cliente;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Entity
public class Fiscal {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @SuppressWarnings("JpaAttributeTypeInspection")
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENTE_ID", referencedColumnName = "ID")
//    @MapsId("CLIENTE_ID")
    private Cliente cliente;

    @Column(name = "ANO")
    private int ano;

    @Column(name = "MES")
    private int mes;

    @Column(name = "STATUS")
    private Status status;

    @Column(name = "DATA_ENVIO")
    private Date dataEnvio;

    @Temporal(TemporalType.DATE)
    @Column(name = "MODIFICADO")
    private Date modificado;

    /**
     * Se mudar para FetchType.LAZY da falha failed to lazily initialize a collection of role
     * utilize FetchType.EAGER
     */
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    @JoinColumn(name = "FISCAL_ID")
    @OneToMany(mappedBy="fiscal", targetEntity = LivroFiscal.class, orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LivroFiscal> listaLivroFiscal = new ArrayList<>();

    public Fiscal() {

    }

    public Fiscal(int id, Cliente cliente, int ano, int mes, Status status, String dataEnvio, String modificado) {

        this.id = id;
        this.cliente = cliente;
        this.ano = ano;
        this.mes = mes;
        this.status = status;
        try {
            this.modificado = new SimpleDateFormat("dd/MM/yyyy").parse(modificado);
            this.dataEnvio = new SimpleDateFormat("dd/MM/yyyy").parse(dataEnvio);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(Fiscal.class.getName()).log(Level.SEVERE, null, e);
//			e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Date getModificado() {
        return modificado;
    }

    public void setModificado(Date modificado) {
        this.modificado = modificado;
    }


    public List<LivroFiscal> getListaLivroFiscal() {
        return listaLivroFiscal;
    }

    public void setListaLivroFiscal(List<LivroFiscal> listaLivroFiscal) {
        this.listaLivroFiscal = listaLivroFiscal;
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
