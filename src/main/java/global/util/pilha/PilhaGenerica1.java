package global.util.pilha;

public class PilhaGenerica1<T> {
	private T[] pilha;
	private int topo;
	private final int MAX = 10;

	public PilhaGenerica1() {
		this.pilha = (T[]) new Object[MAX];
		this.topo = -1;

	}

	public void empilha(T valor) {
		if (topo + 1 > pilha.length)
			aumentarVetor();

		topo++;
		pilha[topo] = valor;
	}

	public boolean estaVazia() {
		return (topo == -1);
	}

	public T desempilha() {
		if (estaVazia())
			return null;
		T conteudoTopo = pilha[topo];
		topo--;
		return conteudoTopo;
	}

	private void aumentarVetor() {
		T[] novaPilha = (T[]) new Object[this.pilha.length + MAX];

		for (int i = 0; i < pilha.length; i++) {
			novaPilha[i] = pilha[i];
		}

		pilha = novaPilha;
	}

	public T getTopo() {
		return pilha[topo];
	}

	public int getTamanho() {
		return topo + 1;
	}

	public void imprimePilha() {
		for (int i = topo; i >= 0; i--) {
			System.out.println(pilha[i]);
		}
		System.out.println("");
	}
}
