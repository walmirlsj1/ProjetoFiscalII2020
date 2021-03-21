package global.util.pilha;

public class LinkedQueueque<T> {
	private No<T> inicio, fim;
	private int numElement;

	public LinkedQueueque() {
		clear();
	}

	public T element() {
		return this.inicio.getValue();
	}

	/**
	 * Zera o array
	 */
	public void clear() {
		this.numElement = 0;
		this.inicio = this.fim = null;
	}

	/**
	 * Retorna a quantidade de elementos da inicioa
	 * 
	 * @return
	 */
	public int size() {
		return this.numElement;
	}

	public boolean isEmpty() {
		return this.inicio == null;
	}

	/**
	 * Insere um novo elemento no no fim da lista
	 * 
	 * @param objeto
	 */
	public void add(T objeto) {
		No<T> ponteiro = this.inicio;
		No<T> novoNo = new No<T>(objeto);

		if (isEmpty()) {
			inicio = fim = novoNo;
		} else {
			fim.setNext(novoNo);
			fim = novoNo;
		}
		numElement++;
	}

	/**
	 *
	 * Remove o primeiro elemento da lista
	 * 
	 * @return
	 */
	public T remove() {
		No<T> ponteiro = this.inicio;
		if (!(ponteiro == null)) {
			numElement--;
			this.inicio = this.inicio.getNext();
			return ponteiro.getValue();
		}
		return null;
	}

	public boolean buscar(T id) {
		No<T> ptr = this.inicio;

		if (!this.isEmpty()) {
			while (ptr != null) {
				if (ptr.getValue().equals(id)) {
					return true;
				}
				ptr = ptr.getNext();
			}
		}
		return false;
	}

}
