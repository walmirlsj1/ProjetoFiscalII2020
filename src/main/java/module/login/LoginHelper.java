package module.login;

import module.usuario.Usuario;

public class LoginHelper {
	
	private final LoginGUI view;
	
	public LoginHelper(LoginGUI view) {
		this.view = view;
	}
	
	public Usuario obterModelo() { //Tipo USuario
		String username = view.getTxtLogin().getText();
		String senha = new String(view.getTxtSenha().getPassword());
		return new Usuario(0, username, senha);
	}
	
	public void setModelo() {
//		String nome = modelo.getNome();
//		String senha = modelo.getSenha();
//		view.getTxtLogin().setText(nome);
//		view.getTxtSenha().setText(senha);
		
	}
	
	public void limparTela() {
		view.getTxtLogin().setText("");
		view.getTxtSenha().setText("");
	}
	
}
