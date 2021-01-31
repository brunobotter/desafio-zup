package br.com.bruno.banco.service.util;

import java.util.List;

public interface ServiceGenerico <E,K> {
    public E salvar(E entity) ;
    public E atualizar(E entity) ;
    public void remover(K key);
    public E buscar(K key);
    public List<E> listarTodos();

}
