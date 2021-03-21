package module.versao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import module.usuario.Usuario;

public class VersaoController {
    private final VersaoGUI view;
    private final VersaoHelper helper;
    private final Usuario usuarioLogado;
    private VersaoDAO versaoDAO;

//	private final Usuario usuario;

    public VersaoController(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        this.view = new VersaoGUI();
        this.helper = new VersaoHelper(view, usuarioLogado);

        initialize();
    }

    public void atualizaTabela() {
        helper.preencherTabela(getVersaoDAO().selectAll());
    }

    public void initialize() {

        this.view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    evt.consume();
                    tablePegaID(evt);
                }
            }
        });
        this.view.getBtnExcluir().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                excluir();
            }
        });

        this.view.getBtnNovo().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                limpaTela();
            }
        });
        this.view.getBtnEditar().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                salvar();
            }
        });
        helper.initialize();
        atualizaTabela();
    }

    public void tablePegaID(MouseEvent evt) {
        int id = helper.tablePegaID(evt);
        helper.preencherCampos(getVersaoDAO().selectPerID(id));
    }

    public void limpaTela() {
        helper.limpaTela();
    }

    public void salvar() {
        Versao versao = helper.pegaCampos();
        if (versao == null)
            return;

        if (versao.getId() == -1) {
            // Cadastrar
            getVersaoDAO().insert(versao);
        } else {
            // Editar
            getVersaoDAO().update(versao);
        }
        helper.limpaTela();
        atualizaTabela();
    }

    public void excluir() {
        Versao versao = helper.pegaCampos();
        if (versao == null)
            return;

        getVersaoDAO().delete(versao);
        atualizaTabela();
    }

    public VersaoGUI getView() {
        return view;
    }

    private VersaoDAO getVersaoDAO() {
        if (versaoDAO == null)
            versaoDAO = new VersaoDAO();
        return versaoDAO;
    }
}
