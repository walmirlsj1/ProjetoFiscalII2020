package global.util.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.util.ArrayList;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import module.fiscal.LivroFiscal;
import global.service.ServiceCriptografia;
import global.service.config.Config;
import module.cliente.Cliente;
import module.fiscal.Fiscal;
import module.fiscal.FiscalDAO;
import org.apache.commons.configuration2.ex.ConfigurationException;


public class LoadingFile {

    private static String path = "C:\\GDOOR\\Backups\\";
//	private ArrayList<Cliente> lstCliente;

    private static FiscalDAO fiscalDAO = new FiscalDAO();
    private Calendar calendario;

    private static String chave_criptografia = "$#@!RaGnaRoK1!@#$";
    private static ServiceCriptografia servicoCripto;

    public LoadingFile() {

        path = null;
        try {
            path = Config.getConfiguracao().getString("path_clientes");
        } catch (ConfigurationException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        calendario = Calendar.getInstance();
    }

    public static void carregarConfig() {
        try {
            servicoCripto = new ServiceCriptografia(chave_criptografia);
        }catch (InvalidKeyException e){
            System.out.println(e.getMessage() + "\n Falha ao criar servico de criptografia" );
//            return;
        }

        path = null;
        try {
            path = Config.getConfiguracao().getString("path_clientes");
        } catch (ConfigurationException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    //	public LoadingFile(ArrayList<Cliente> lstCliente) {
//		calendario = Calendar.getInstance();
//		this.lstCliente = lstCliente;
//		int i = 0;
//		for (Cliente cliente : lstCliente) {
//			System.out.println("Teste " + (++i) + " " + cliente.getSerial() + " -- " + cliente.getNome() + " Ok: "
//					+ findFolder(cliente.getSerial()));
////			findFolder(cliente);
//		}
//	}

    private static void initMigrarSistemaAntigoPNovo() {
        carregarConfig();

        List<Fiscal> lstFiscal = fiscalDAO.selectAll();

        lstFiscal.forEach(
                f -> {
                    List<String> lista = findFolderListFiles(f);
                    migrarSistemaAntigoPNovo(f, lista);
                }
        );

    }

    private static void migrarSistemaAntigoPNovo(Fiscal fiscal, List<String> lista) {
        int i = 0;


        lista.forEach(file -> {
            System.out.println("To fazendo " + i);

            LivroFiscal livro = new LivroFiscal();
            livro.setFiscal(fiscal);


            System.out.println(fiscal.getCliente().getNome() + " mes "  + fiscal.getMes() + " file " + file);

            File source = Paths.get(file).toFile();
            ByteArrayOutputStream bos;
            FileInputStream fis;

            try {
                fis = new FileInputStream(source);
                bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];

                for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                    bos.write(buf, 0, readNum); //no doubt here is 0
                }

                livro.setFileName(source.getName());
                byte[] blobNew = servicoCripto.criptografa(bos.toByteArray());

//                livro.setFile(BlobProxy.generateProxy(blobNew));
                livro.setFile(blobNew);

                fiscal.getListaLivroFiscal().add(livro);
            } catch (IOException e) {
                System.out.println("Falha na leitura do arquivo " + e.getClass().getSimpleName());
            }
//            catch (Exception e) {
//                System.out.println("Falha ao gravar na base " + e.getClass().getSimpleName() + "\nerror: " + e.getMessage());
//            }

        });

        fiscalDAO.update(fiscal);

    }

    public static List<String> findFolderListFiles(Fiscal fiscal) {
//        String serial, int mes, int ano
        String path_folder = getPathFolder(fiscal.getCliente().getSerial(), fiscal.getMes(), fiscal.getAno());

//		File folder = new File(path_folder);
        File directory = new File(path_folder);

        if (!directory.exists()) {
            directory.mkdirs();
        } else {

            try (Stream<Path> walk = Files.walk(Paths.get(path_folder))) {
                List<String> result = walk.filter(Files::isRegularFile).map(x -> x.toString())
                        .collect(Collectors.toList());

                // result.forEach(System.out::println);
                if (!result.isEmpty()) {
                    return result;
                }

            } catch (IOException e) {
            }
        }
        return new ArrayList<>();
    }

    public boolean findFolder(String serial) {

        String path_folder = getPathFolder(serial);

//		File folder = new File(path_folder);
//		System.out.println(path_folder);
        File directory = new File(path_folder);

        if (!directory.exists()) {
            directory.mkdirs();
        } else {
            try (Stream<Path> walk = Files.walk(Paths.get(path_folder))) {
                List<String> result = walk.filter(Files::isRegularFile).map(x -> x.toString())
                        .collect(Collectors.toList());

                // result.forEach(System.out::println);
                if (!result.isEmpty()) {
                    return true;
                }

            } catch (IOException e) {

                // e.printStackTrace();
            }
        }
        return false;
    }


    public boolean findFolder(String serial, int mes, int ano) {

        String path_folder = getPathFolder(serial, mes, ano);

//		File folder = new File(path_folder);
        File directory = new File(path_folder);

        if (!directory.exists()) {
            directory.mkdirs();
        } else {

            try (Stream<Path> walk = Files.walk(Paths.get(path_folder))) {
                List<String> result = walk.filter(Files::isRegularFile).map(x -> x.toString())
                        .collect(Collectors.toList());

                // result.forEach(System.out::println);
                if (!result.isEmpty()) {
                    return true;
                }

            } catch (IOException e) {

                // e.printStackTrace();
            }
        }
        return false;
    }

    public List<String> getFilesFolder(String serial) {

        String path_folder = getPathFolder(serial);

        try (Stream<Path> walk = Files.walk(Paths.get(path_folder))) {
            List<String> result = walk.filter(Files::isRegularFile).map(x -> x.getFileName().toString())
                    .collect(Collectors.toList());

            // result.forEach(System.out::println);
            if (!result.isEmpty()) {
                return result;
            }

        } catch (IOException e) {

            // e.printStackTrace();
        }
        return null;
    }

    public List<Path> getFilesFolderPath(String serial) {

        String path_folder = getPathFolder(serial);
//		System.out.println("::: Pastas -- >> " + path_folder);
        try (Stream<Path> walk = Files.walk(Paths.get(path_folder))) {
            List<Path> result = walk.filter(Files::isRegularFile).map(x -> x).collect(Collectors.toList());

            // result.forEach(System.out::println);
            if (!result.isEmpty()) {
                return result;
            }

        } catch (IOException e) {

//			 e.printStackTrace();
        }
        return null;
    }

    public boolean criarPastaMensal(Cliente cliente) {
        String path_folder = getPathFolder(cliente.getSerial());
        File folder = new File(path_folder);
        boolean bool = folder.mkdirs();
        if (bool) {
//			Logger.getLogger("Directory created successfully");
            return true;
        } else {
            Logger.getLogger("Sorry couldn't create specified directory");
        }
        return false;
    }

    public String getPathFolder(String serial) {
        String mes = getMesAtual() < 10 ? "0" + String.valueOf(getMesAtual()) : String.valueOf(getMesAtual());
        return path + serial + "/FISCAL/MES_" + mes + "_" + getAnoAtual() + "/";
    }

    public static String getPathFolder(String serial, int mes, int ano) {
        String mes1 = mes < 10 ? "0" + String.valueOf(mes) : String.valueOf(mes);
        return path + serial + "/FISCAL/MES_" + mes1 + "_" + ano + "/";
    }

    public String obterMesString(int mes) {
        String[] meses = {"1JAN", "2FEV", "3MAR", "4ABR", "5MAI", "6JUN", "7JUL", "8AGO", "9SET", "10OUT", "11NOV",
                "12DEZ"};
        return meses[mes];
    }

    public int getAnoAtual() {
        return calendario.get(Calendar.YEAR);
    }

    public int getMesAtual() {
        return calendario.get(Calendar.MONTH);
    }

	public static void main(String[] args) {
        initMigrarSistemaAntigoPNovo();
    }
//		// TODO Auto-generated method stub
//		ClienteDAO clienteDAO = new ClienteDAO();
//
//		ArrayList<Cliente> lstClientes = null;
//
//		try {
//			lstClientes = clienteDAO.selectAll();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		new LoadingFile(lstClientes);
//	}

}
