package module.usuario;

public class UsuarioController {
	private final UsuarioGUI view;
	private final Usuario usuarioLogado;

	public UsuarioController(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
		view = new UsuarioGUI();
	}

	public UsuarioGUI getView() {
		return view;
	}

}
