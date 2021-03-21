package global.util.pilha;

public class LinkedQueuequeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedQueueque<Integer> lista = new LinkedQueueque<>();
		lista.add(1);
		
		if(lista.buscar(11)) {
			System.out.println("Encontrei");
		} else {
			System.out.println("Nao Encontrei");
		}
	}

}
