package global.util.pilha;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package estruturasdedadosiaula.ListaLigada;

/**
 *
 * @author Glasielly
 * @param <T>
 */
public class No<T> {
    private T value;
    private No<T> next;
    
    public No(T chave){
        this.value = chave;
        this.next = null;
    }

    /**
     * @return the chave
     */
    public T getValue() {
        return value;
    }

    /**
     * @param chave the chave to set
     */
    public void setValue(T value) {
        this.value = value;
    }
    
    public No<T> getNext() {
    	return next;
    }
    
    /**
     * @return the proximo
     */
    public boolean hasNext() {
        return next != null ;
    }

    /**
     * @param proximo the proximo to set
     */
    public void setNext(No<T> next) {
        this.next = next;
    }
    
}