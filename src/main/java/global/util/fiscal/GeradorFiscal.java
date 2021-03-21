package global.util.fiscal;

import global.Status;
import module.cliente.Cliente;
import module.cliente.ClienteDAO;
import module.fiscal.Fiscal;
import module.fiscal.FiscalDAO;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;


public class GeradorFiscal {

    public static void gerarFiscal(JProgressBar progress, int mes, int ano) {
        FiscalDAO fiscalDAO = new FiscalDAO();
        Fiscal fiscal;

        ClienteDAO clienteDAO = new ClienteDAO();

        List<Cliente> lstCliente = new ArrayList<>();

        try {
            lstCliente = clienteDAO.selectAll();

            List<Cliente> lstClienteFiscal = fiscalDAO.selectFilterMesAno(mes, ano)
                    .stream()
                    .map(Fiscal::getCliente)
                    .collect(Collectors.toList());

            lstCliente = lstCliente.stream()
                    .filter(c -> (!lstClienteFiscal.contains(c)))
                    .filter(Cliente::getEnviarDadosContador)

//                    .peek(c -> System.out.println(c.getNome() + " " + c.getEnviarDadosContador()))

                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.out.println("Ocorreu ao obter/filtrar lista! " + e.getClass().getSimpleName() + " " + e.getMessage());
        }
        progress.setMaximum(lstCliente.size());

        System.out.println("Listra de cliente para insert: " + lstCliente.size());
        for (Cliente cliente : lstCliente) {

//            fiscalDAO = new FiscalDAO();
            fiscal = new Fiscal();

            fiscal.setCliente(cliente);

            fiscal.setStatus(Status.PENDENTE);

            fiscal.setDataEnvio(null);
            fiscal.setMes(mes);
            fiscal.setAno(ano);
            cliente.getLstFiscal().add(fiscal);
            progress.setValue(progress.getValue() + 1);
        }
        try {
//                fiscalDAO.insert(fiscal);

            clienteDAO.updateAll(lstCliente);
//            System.out.println("Gerado fiscal " + cliente.getNome() + " com sucesso " + mes + " " + ano);
        } catch (Exception e) {
            System.out.println("Falha ao gerar fiscal " + mes + " " + ano + e.getClass().getSimpleName() + " " + e.getMessage());
        }
//        return lstCliente.size();
    }

//    public int getAnoAtual() {
//        return calendario.get(Calendar.YEAR);
//    }
//
//    public int getMesAtual() {
//        return calendario.get(Calendar.MONTH);
//    }

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		
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
//		GeradorFiscal gerFiscal = new GeradorFiscal(lstClientes);
//		System.out.println("Fiscal registrados: " + gerFiscal.gerarFiscal());
//		
//	}

}
