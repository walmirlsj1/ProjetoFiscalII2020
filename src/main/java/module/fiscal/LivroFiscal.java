package module.fiscal;

import module.fiscal.Fiscal;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.io.File;
import java.sql.Blob;
import java.util.Date;

@Entity
@Table(name = "LIVRO_FISCAL")
public class LivroFiscal {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="ARQUIVO_NOME")
    private String fileName;

    @Basic(fetch=FetchType.LAZY)
    @Lob
    @Column(name = "ARQUIVO")
    private byte[] file;

    @Temporal(TemporalType.DATE)
    @Column(name = "MODIFICADO")
    private Date modificado;

    @ManyToOne
    @JoinColumn(name="FISCAL_ID")
    private Fiscal fiscal;


    public LivroFiscal() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] arquivo) {
        this.file = arquivo;
    }

    public Date getModificado() {
        return modificado;
    }

    public void setModificado(Date modificado) {
        this.modificado = modificado;
    }

    @PrePersist
    protected void onCreate() {
        modificado = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        modificado = new Date();
    }

    public Fiscal getFiscal() {
        return fiscal;
    }

    public void setFiscal(Fiscal fiscal) {
        this.fiscal = fiscal;
    }

    public String toString(){
        return this.fileName;
    }
}
