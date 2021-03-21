package module.cliente;

//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.persistence.*;
import java.util.List;

import module.contador.Contador;
import global.Pessoa;
import module.fiscal.Fiscal;
import module.versao.Versao;

@Entity
public class Cliente extends Pessoa {

    @Column(name = "CNPJ")
    private String cnpj;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VERSAO_ID", referencedColumnName = "ID")
    private Versao versao;

    @Column(name = "SERIAL")
    private String serial;

    @Column(name = "IDREMOTO")
    private String idRemoto;

    @Column(name = "ENVIAR_DADOS_CONTADOR")
    private Boolean enviarDadosContador;

    @SuppressWarnings("JpaAttributeTypeInspection")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "CONTADOR_ID", referencedColumnName = "ID")
    private Contador contador;

    @SuppressWarnings("JpaAttributeTypeInspection")
    @OneToMany(mappedBy = "cliente", targetEntity = Fiscal.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fiscal> lstFiscal;

    public Cliente() {
        logger  = LoggerFactory.getLogger(Cliente.class.getName());
    }

//    public Cliente(int id) {
//        super(id);
//    }

    public Cliente(int id, String nome, String cnpj, String telefone, String email, Versao versao, String serial,
                   Contador contador, String idRemoto, Boolean enviarDadosContador, Boolean ativo, String modificado) {
        super(id, nome, telefone, email, ativo, modificado);
        this.cnpj = cnpj;
        this.versao = versao;
        this.serial = serial;
        this.contador = contador;
        this.idRemoto = idRemoto;
        this.enviarDadosContador = enviarDadosContador;
    }

    private String limpaCNPJ(String cnpj) {
        // Remove tudo que não for letra a-zA-Z
        cnpj = cnpj.replaceAll("\\W", "");

        // Remove tudo que não for digito 1-9
        cnpj = cnpj.replaceAll("\\D", "");
        return cnpj;
    }

    public String getCnpj() {
        return limpaCNPJ(cnpj);
    }

    public void setCnpj(String cnpj) {
        this.cnpj = limpaCNPJ(cnpj);
    }

    public Versao getVersao() {
        return versao;
    }

    public void setVersao(Versao versao) {
        this.versao = versao;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Contador getContador() {
        return contador;
    }

    public void setContador(Contador contador) {
        this.contador = contador;
    }

    public String getIdRemoto() {
        return idRemoto;
    }

    public void setIdRemoto(String idRemoto) {
        this.idRemoto = idRemoto;
    }

    public Boolean getEnviarDadosContador() {
        return enviarDadosContador;
    }

    public void setEnviarDadosContador(Boolean enviarDadosContador) {
        this.enviarDadosContador = enviarDadosContador;
    }

    public List<Fiscal> getLstFiscal() {
        return this.lstFiscal;
    }

    public void setLstFiscal(List<Fiscal> lstFiscal) {
        this.lstFiscal = lstFiscal;
    }

    @Override
    public String toString() {
        return "Codigo: " + this.getId() + " Nome: " + this.getId() + " Sistema: " + versao.getVersao() + " Serial: "
                + this.getSerial() + " Contador: " + this.getContador().getNome() + " RemotoID: " + this.getIdRemoto()
                + " EnviarDadosContador: " + this.getEnviarDadosContador();
    }
}
