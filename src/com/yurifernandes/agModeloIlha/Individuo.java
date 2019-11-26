package com.yurifernandes.agModeloIlha;

/**
 * Classe para representar um indivíduo no AG (Algoritmo Genético)
 *
 * @author Yuri Fernandes de Oliveira <yurifernandes.com>
 */
public abstract class Individuo<T, E> {

    protected T cromossomo;

    public Individuo(T cromossomo) {
        this.cromossomo = cromossomo;
    }

    // Para retornar o fenótipo (característica) do indivíduo.
    public abstract E getFenotipo();

    public abstract double getAptidao();

    public abstract void mostrarIndividuo();
}
