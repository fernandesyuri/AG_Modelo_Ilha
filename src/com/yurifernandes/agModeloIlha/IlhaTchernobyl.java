/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yurifernandes.agModeloIlha;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class IlhaTchernobyl extends AlgoritmoGenetico{
    
    //Ilha com mutação em cluster, também é recomendado usar uma taxa de mutação mais alta nela
    
    public IlhaTchernobyl(int tamanhoPopulacao, //populacao minima = 40
            int probabilidadeMutacao,
            double intervalo[],
            int precisao
    ) {
        super(tamanhoPopulacao, probabilidadeMutacao, intervalo, precisao);
    }
    
    @Override
    public void evoluir(int numGeracoes) {
        Operacoes op = new Operacoes();
        while (numGeracoes > 0) {
            //System.out.println("Geração (" + numGeracoes + ")");
            ArrayList<Individuo> novaPopulacao = new ArrayList();
            while (novaPopulacao.size() < populacao.size()) {
                IndividuoBinario i1 = (IndividuoBinario) op.torneio(populacao);
                IndividuoBinario i2 = (IndividuoBinario) op.torneio(populacao);
                //System.out.println(i1 + " " + i2);
                IndividuoBinario filhos[] = op.crossover(i1, i2);
                for (IndividuoBinario filho : filhos) {
                    op.mutacaoEmRajada(filho, probabilidadeMutacao);
                    novaPopulacao.add(filho);
                }
            }
            populacao = novaPopulacao;
            numGeracoes--;
        }
    }
    
}
