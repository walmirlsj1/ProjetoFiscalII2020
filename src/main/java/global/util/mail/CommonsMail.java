package global.util.mail;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import global.service.config.Config;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

public class CommonsMail {
    private String emitente_email;
    private String emitente_nome;
    private String emitente_passwd;
    private String destinatario_email;
    private String destinatario_nome;
    private String server_smtp;
    private int server_smtp_porta;
    private boolean server_smtp_ssl;
    private boolean server_smtp_tls;
    private String titulo = "Teste de envio";
    private String msg = "Teste xD";

    private ArrayList<EmailAttachment> listAnexo;

    private ArrayList<FileNameUtil> listaAnexo;

    public CommonsMail() {
        listAnexo = new ArrayList<>();
        listaAnexo = new ArrayList<>();

        try {
            emitente_email = Config.getConfiguracao().getString("email_emitente_email");
            emitente_nome = Config.getConfiguracao().getString("email_emitente_nome");
            emitente_passwd = Config.getConfiguracao().getString("email_emitente_passwd");
            server_smtp = Config.getConfiguracao().getString("email_server_smtp");
            server_smtp_porta = Config.getConfiguracao().getInt("email_server_smtp_porta");
            server_smtp_ssl = Config.getConfiguracao().getBoolean("email_server_smtp_ssl");
            server_smtp_tls = Config.getConfiguracao().getBoolean("email_server_smtp_tls");
        } catch (ConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


//		email_emitente_email = "walmirluizdasilvajunior@gmail.com";
//		email_emitente_nome = "Walmir2";
//		email_emitente_passwd = "password";
//		server_smtp = "smtp.gmail.com";
//		server_smtp_porta = 465
//		server_smtp_ssl = true
//		server_smtp_tls = true
    }

    public void enviaEmailSimples() throws EmailException {

        SimpleEmail email = new SimpleEmail();
        email.setHostName(server_smtp); // o servidor SMTP para envio do e-mail
        email.addTo(destinatario_email, destinatario_nome); // destinatçrio
        email.setFrom(emitente_email, emitente_nome); // remetente
        email.setSubject(titulo); // assunto do e-mail
        email.setMsg(msg); // conteudo do e-mail
        email.setAuthentication(emitente_email, emitente_passwd);
        email.setSmtpPort(server_smtp_porta);
//		email.setSSL(server_smtp_ssl);
//		email.setTLS(server_smtp_tls);
        email.setSSLOnConnect(server_smtp_ssl);
        email.send();
    }

    public void enviaEmailComAnexo() throws EmailException {
        // configura o email
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName(server_smtp); // o servidor SMTP para envio do e-mail
        email.addTo(destinatario_email, destinatario_nome); // destinatçrio
        email.setFrom(emitente_email, emitente_nome); // remetente
        email.setSubject(titulo); // assunto do e-mail
        email.setMsg(msg); // conteudo do e-mail
        email.setAuthentication(emitente_email, emitente_passwd);
        email.setSmtpPort(server_smtp_porta);
//		email.setSSL(server_smtp_ssl);
//		email.setTLS(server_smtp_tls);
        email.setSSLOnConnect(server_smtp_ssl);
        // adiciona arquivo(s) anexo(s)
//		email.attach(anexo1);
//		email.attach(anexo2);
//		email.setDebug(true);

        listaAnexo.forEach(i -> {
            try {
                email.attach(i.getSource(), i.getNome(), "");
            } catch (EmailException e) {
                e.printStackTrace();
            }

        });
//		for (EmailAttachment emailAttachment : listAnexo) {
//			email.attach(emailAttachment);
//		}
        // envia o email
        System.out.println("enviando msg");
        email.send();
        System.out.println("concluido msg");
    }

    public void enviaEmailFormatoHtml() throws EmailException, MalformedURLException {

        HtmlEmail email = new HtmlEmail();

        // adiciona uma imagem ao corpo da mensagem e retorna seu id
        URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
        String cid = email.embed(url, "Apache logo");

        // configura a mensagem para o formato HTML
        email.setHtmlMsg("<html>Logo do Apache - <img ></html>");

        // configure uma mensagem alternativa caso o servidor nço suporte HTML
        email.setTextMsg("Seu servidor de e-mail nço suporta mensagem HTML");

        email.setHostName(server_smtp); // o servidor SMTP para envio do e-mail
        email.addTo(destinatario_email, destinatario_nome); // destinatçrio
        email.setFrom(emitente_email, emitente_nome); // remetente
        email.setSubject(titulo); // assunto do e-mail
        email.setMsg(msg); // conteudo do e-mail
        email.setAuthentication(emitente_email, emitente_passwd);
        email.setSmtpPort(server_smtp_porta);
//		email.setSSL(server_smtp_ssl);
//		email.setTLS(server_smtp_tls);
        email.setSSLOnConnect(server_smtp_ssl);
        // envia email
        email.send();
    }

    public String getEmitente_email() {
        return emitente_email;
    }

    public void setEmitente_email(String emitente_email) {
        this.emitente_email = emitente_email;
    }

    public String getEmitente_nome() {
        return emitente_nome;
    }

    public void setEmitente_nome(String emitente_nome) {
        this.emitente_nome = emitente_nome;
    }

    public String getEmitente_passwd() {
        return emitente_passwd;
    }

    public void setEmitente_passwd(String emitente_passwd) {
        this.emitente_passwd = emitente_passwd;
    }

    public String getDestinatario_email() {
        return destinatario_email;
    }

    public void setDestinatario_email(String destinatario_email) {
        this.destinatario_email = destinatario_email;
    }

    public String getDestinatario_nome() {
        return destinatario_nome;
    }

    public void setDestinatario_nome(String destinatario_nome) {
        this.destinatario_nome = destinatario_nome;
    }

    public String getServer_smtp() {
        return server_smtp;
    }

    public void setServer_smtp(String server_smtp) {
        this.server_smtp = server_smtp;
    }

    public int getServer_smtp_porta() {
        return server_smtp_porta;
    }

    public void setServer_smtp_porta(int server_smtp_porta) {
        this.server_smtp_porta = server_smtp_porta;
    }

    public boolean isServer_smtp_ssl() {
        return server_smtp_ssl;
    }

    public void setServer_smtp_ssl(boolean server_smtp_ssl) {
        this.server_smtp_ssl = server_smtp_ssl;
    }

    public boolean isServer_smtp_tls() {
        return server_smtp_tls;
    }

    public void setServer_smtp_tls(boolean server_smtp_tls) {
        this.server_smtp_tls = server_smtp_tls;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

//	public ArrayList<EmailAttachment> getListAnexo() {
//		return listAnexo;
//	}
//
//	public void setAnexo(ArrayList<EmailAttachment> listAnexo) {
//		this.listAnexo = listAnexo;
//	}

//	public void addAnexo(List<Path> lstAnexo) {
//		EmailAttachment anexo1;
//		int c = 0;
//		for (Path anexo : lstAnexo) {
//			anexo1 = new EmailAttachment();
////			org.apache.commons.mail.resolver.DataSourceFileResolver
//			anexo1.setPath(anexo.toString()); // caminho do arquivo (RAIZ_PROJETO/teste/teste.txt)
//			anexo1.setDisposition(EmailAttachment.ATTACHMENT);
//			anexo1.setDescription("Anexo " + ++c);
//			anexo1.setName(anexo.getFileName().toString());
//			this.listAnexo.add(anexo1);
//		}
//	}

    public void addAnexo(String nome, byte[] anexo) {
		
//			String mimeType = new MimetypesFileTypeMap().getContentType( nome );
//			InputStream temp = new ByteArrayInputStream(anexo);
//			String mimeType = URLConnection.guessContentTypeFromStream(temp);
//			temp.close();

        DataSource source = new ByteArrayDataSource(anexo, "application/zip");


        FileNameUtil file = new FileNameUtil();
        file.setNome(nome);
        file.setSource(source);
        this.listaAnexo.add(file);


    }

    public void clearAnexo() {
        this.listAnexo = new ArrayList<EmailAttachment>();
    }

    /**
     * @param args
     * @throws EmailException
     * @throws MalformedURLException
     */
    public static void main(String[] args) throws EmailException, MalformedURLException {
        new CommonsMail();
    }
}

class FileNameUtil {
    private String nome;
    private DataSource source;

    public FileNameUtil() {
    }

    public FileNameUtil(String nome, DataSource source) {
        this.nome = nome;
        this.source = source;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public DataSource getSource() {
        return source;
    }

    public void setSource(DataSource source) {
        this.source = source;
    }
}
