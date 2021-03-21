package module.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.persistence.NoResultException;

import module.usuario.Usuario;
import module.usuario.UsuarioDAO;
import module.main.MainWinController;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class LoginController {

    private final LoginGUI view;
    private LoginHelper helper;

    public LoginController() {
        view = new LoginGUI();
        helper = new LoginHelper(view);

        initialize();

        view.setVisible(true);

//		PersistEngine.getEntityManager();
    }

    private void initialize() {

        view.getBtnEntrar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyPressLogin(e);
            }
        });
        view.getBtnEntrar().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        view.getTxtLogin().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                focusSenha(e);
            }
        });
        view.getTxtSenha().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                focusEnter(e);
            }
        });
        /**
         * @FIXME Busca do recurso src/main/resources/ do maven não encontra após criar
         *        jar file.
         */
        ImageIcon tIcon = null;
        try {
            tIcon = new ImageIcon(getClass().getResource("/imagens/FundoAzul.png"));
            view.getImgFundo().setIcon(tIcon);
        } catch (Exception e) {
            tIcon = null;
        }
//		if (tIcon == null) {
//			try {
//				tIcon = new ImageIcon(getClass().getResource("FundoAzul.png"));
//				view.getImgFundo().setIcon(tIcon);
//			} catch (Exception e) {
//
//			}
//		}
        view.getBtnSair().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


    }

    public void login() {
        Usuario usuario = helper.obterModelo();
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuarioAutenticado;

        try {
            usuarioAutenticado = usuarioDAO.findByUsername(usuario);
        } catch (NoResultException e) {
            usuarioAutenticado = null;
        }

        if (usuarioAutenticado != null && bcrypt.matches(usuario.getPasswd(), usuarioAutenticado.getPasswd())) {
            LoggerFactory.getLogger(LoginController.class.getName()).info("Usuario logado - " + usuarioAutenticado.getId());

            new MainWinController(usuarioAutenticado);

            view.dispose();
        } else {
            LoggerFactory.getLogger(LoginController.class.getName()).warn("Login/Senha incorreto!");

            view.exibeMensagem("Usuario ou senha invalidos!");
            view.getTxtSenha().setText("");

            focusLogin();
        }
    }

    public void focusLogin() {
        view.getTxtLogin().requestFocus();
    }

    public void focusSenha(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            view.getTxtSenha().requestFocus();
        }
    }

    public void focusEnter(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
//			view.getBtnEntrar().requestFocus();
            login();
        }
    }

    public void keyPressLogin(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            login();
        }
    }
}
