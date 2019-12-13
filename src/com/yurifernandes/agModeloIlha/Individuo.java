package com.yurifernandes.agModeloIlha;

import java.io.Serializable;

/**
 * Classe para representar um indivíduo no AG (Algoritmo Genético)
 *
 * @author Yuri Fernandes de Oliveira <yurifernandes.com>
 */
public abstract class Individuo<T, E> implements Comparable, Serializable{

    protected T cromossomo;

    public Individuo(T cromossomo) {
        this.cromossomo = cromossomo;
    }

    // Para retornar o fenótipo (característica) do indivíduo.
    public abstract E getFenotipo();

    public abstract double getAptidao();

    public abstract void mostrarIndividuo();

    @Override
    public abstract int compareTo(Object o);

}
