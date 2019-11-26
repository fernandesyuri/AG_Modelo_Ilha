package com.yurifernandes.agModeloIlha;

import java.util.ArrayList;
import java.util.Random;

/**
 * Classe com as operações de Algoritmos Genéticos
 *
 * @author Yuri Fernandes de Oliveira <yurifernandes.com>
 */
public class Operacoes {

    // Efetua o crossover entre dois indivíduos
    public IndividuoBinario[] crossover(
            IndividuoBinario i1,
            IndividuoBinario i2) {

        int tam = i1.getTamanhoCromossomo();

        Random r = new Random();
        int pontoCorte = r.nextInt(tam);

        IndividuoBinario ni1 = i1.clonar();
        IndividuoBinario ni2 = i2.clonar();

        for (int i = pontoCorte; i < tam; ++i) {
            ni1.setGene(i, i2.getGene(i));
            ni2.setGene(i, i1.getGene(i));
        }

        return new IndividuoBinario[]{ni1, ni2};

    }

    // Método para efetuar a mutação sobre um indivíduo
    // o parâmetro probabilidade assume um valor [0,100]
    public void mutacao(IndividuoBinario individuo,
            int probabilidade) {

        Random r = new Random();

        int valorAleatorio = r.nextInt(101);
        if (probabilidade >= valorAleatorio) {
            int tam = individuo.getTamanhoCromossomo();
            int pos = r.nextInt(tam);
            int gene = individuo.getGene(pos);
            if (gene == 1) {
                individuo.setGene(pos, 0);
            } else {
                individuo.setGene(pos, 1);
            }

        }

    }

    // Método da Roleta para determinar os indivíduos mais aptos
    public Individuo roleta(ArrayList<Individuo> populacao) {
        double aptidaoTotal = 0.0;
        // Soma todas as aptidões...
        for (Individuo i : populacao) {
            aptidaoTotal += i.getAptidao();
        }

        Random r = new Random();

        double valorAleatorio = r.nextDouble() * aptidaoTotal;

        double aptidaoAcumulada = 0.0;

        for (Individuo i : populacao) {
            aptidaoAcumulada += i.getAptidao();
            if (valorAleatorio <= aptidaoAcumulada) {
                return i;
            }
        }

        return null;

    }

}
