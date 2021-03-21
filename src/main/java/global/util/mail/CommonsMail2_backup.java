package global.util.mail;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

public class CommonsMail2_backup {
	private String emitente_email = "walmirluizdasilvajunior@gmail.com";
	private String emitente_nome = "Walmir2";
	private String emitente_passwd = "password";
	private String destinatario_email = "walmir004@gmail.com";
	private String destinatario_nome = "Walmir Luiz";
	private String server_smtp = "smtp.gmail.com";
	
	private String titulo = "Teste de envio";
	private String msg = "Teste xD";
	
	private ArrayList<EmailAttachment> listAnexo;
	
	public CommonsMail2_backup() throws EmailException, MalformedURLException {
		listAnexo = new ArrayList<EmailAttachment>();
	}
	
	public void enviaEmailSimples() throws EmailException {
		
		SimpleEmail email = new SimpleEmail();
		email.setHostName(server_smtp); // o servidor SMTP para envio do e-mail
		email.addTo(destinatario_email, destinatario_nome); // destinat�rio
		email.setFrom(emitente_email, emitente_nome); // remetente
		email.setSubject(titulo); // assunto do e-mail
		email.setMsg(msg); // conteudo do e-mail
		email.setAuthentication(emitente_email, emitente_passwd);
		email.setSmtpPort(465);
		email.setSSL(true);
		email.setTLS(true);
		email.send();
	}

	
	public void enviaEmailComAnexo() throws EmailException {

//		// cria o anexo 1.
//		EmailAttachment anexo1 = new EmailAttachment();
//		anexo1.setPath("C:\\Users\\Ghost\\eclipse-workspace\\projeto-fiscal-loja\\utilitarios_Projeto\\cache_email\\teste.txt"); // caminho do arquivo (RAIZ_PROJETO/teste/teste.txt)
//		anexo1.setDisposition(EmailAttachment.ATTACHMENT);
//		anexo1.setDescription("Exemplo de arquivo anexo");
//		anexo1.setName("teste.txt");
//
//		// cria o anexo 2.
//		EmailAttachment anexo2 = new EmailAttachment();
//		anexo2.setPath("C:\\Users\\Ghost\\eclipse-workspace\\projeto-fiscal-loja\\utilitarios_Projeto\\cache_email\\teste2.txt"); // caminho do arquivo (RAIZ_PROJETO/teste/teste2.jsp)
//		anexo2.setDisposition(EmailAttachment.ATTACHMENT);
//		anexo2.setDescription("Exemplo de arquivo anexo");
//		anexo2.setName("teste2.txt");

		// configura o email
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(server_smtp); // o servidor SMTP para envio do e-mail
		email.addTo(destinatario_email, destinatario_nome); // destinat�rio
		email.setFrom(emitente_email, emitente_nome); // remetente
		email.setSubject(titulo); // assunto do e-mail
		email.setMsg(msg); // conteudo do e-mail
		email.setAuthentication(emitente_email, emitente_passwd);
		email.setSmtpPort(465);
		email.setSSL(true);
		email.setTLS(true);

		// adiciona arquivo(s) anexo(s)
//		email.attach(anexo1);
//		email.attach(anexo2);
		for (EmailAttachment emailAttachment : listAnexo) {
			email.attach(emailAttachment);
		}
		// envia o email
		email.send();
	}

	public void enviaEmailFormatoHtml() throws EmailException, MalformedURLException {

		HtmlEmail email = new HtmlEmail();

		// adiciona uma imagem ao corpo da mensagem e retorna seu id
		URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
		String cid = email.embed(url, "Apache logo");

		// configura a mensagem para o formato HTML
		email.setHtmlMsg("<html>Logo do Apache - <img ></html>");

		// configure uma mensagem alternativa caso o servidor n�o suporte HTML
		email.setTextMsg("Seu servidor de e-mail n�o suporta mensagem HTML");

		email.setHostName(server_smtp); // o servidor SMTP para envio do e-mail
		email.addTo(destinatario_email, destinatario_nome); // destinat�rio
		email.setFrom(emitente_email, emitente_nome); // remetente
		email.setSubject(titulo); // assunto do e-mail
		email.setMsg(msg); // conteudo do e-mail
		email.setAuthentication(emitente_email, emitente_passwd);
		email.setSmtpPort(465);
		email.setSSL(true);
		email.setTLS(true);
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

	public ArrayList<EmailAttachment> getListAnexo() {
		return listAnexo;
	}

	public void setAnexo(ArrayList<EmailAttachment> listAnexo) {
		this.listAnexo = listAnexo;
	}

	public void addAnexo(ArrayList<String> lstAnexo) {
		EmailAttachment anexo1 = new EmailAttachment();
		for (String anexo : lstAnexo) {
			anexo1.setPath(anexo); // caminho do arquivo (RAIZ_PROJETO/teste/teste.txt)
			anexo1.setDisposition(EmailAttachment.ATTACHMENT);
			anexo1.setDescription("Exemplo de arquivo anexo");
			anexo1.setName("teste.txt");
			this.listAnexo.add(anexo1);
		}
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
		new CommonsMail2_backup();
	}
}
