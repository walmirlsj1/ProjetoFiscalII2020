import global.service.PersistEngine;
import module.fiscal.FiscalController;
import module.login.LoginController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.Date;

public class ControleFiscal {
    private static final Logger logger = LoggerFactory.getLogger(ControleFiscal.class.getName());
    private static int i = 0;
    /**
     * Launch the application.
     */
    public static void main(String[] args) throws Exception {

//        EntityManagerFactory entityManagerFactory
//                = Persistence.createEntityManagerFactory("Teste-01");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        if(!entityManager.isOpen())
//            throw new OpenDataException("Falha ao conectar a base de dados!");
//
//        entityManager.close();
//        entityManagerFactory.close();
        logger.info(">>>>> APP Inciado <<<<<");

//        UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
//        for (UIManager.LookAndFeelInfo look : looks) {
//            System.out.println(look.getClassName());
//        javax.swing.plaf.metal.MetalLookAndFeel
//        javax.swing.plaf.nimbus.NimbusLookAndFeel
//        com.sun.java.swing.plaf.motif.MotifLookAndFeel
//        com.sun.java.swing.plaf.windows.WindowsLookAndFeel
//        com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel
//        }


        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
//                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//                     UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                    // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    // javax.swing.UIManager.setLookAndFeel(“com.birosoft.liquid.LiquidLookAndFeel”);
                    new LoginController();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Runnable iniciarConexãoBD = new Runnable() {
            @Override
            public void run() {
                logger.info("Iniciando conexão com BD");

                PersistEngine.getEntityManager();
            }
        };
        iniciarConexãoBD.run();

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                logger.info(">>>>> APP finalizado <<<<<");
            }
        });
    }

}
