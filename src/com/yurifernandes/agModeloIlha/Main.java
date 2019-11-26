/*
Objetivo: encontrar o valor de x que gera o menor valor de y, na função abaixo:
  f (x) = x × sin(10 × x × π) + 1, x ∈ [−1, 2]
*/

package com.yurifernandes.agModeloIlha;

/**
 *
 * @author Yuri Fernandes de Oliveira <yurifernandes.com>
 */
public class Main {

    public static void main(String args[]) {
        /*int c1[] = new int[]{1,0,0,1,0};
        int c2[] = new int[]{0,0,1,0,1};
        
        IndividuoBinario i1 = new IndividuoBinario(c1);
        IndividuoBinario i2 = new IndividuoBinario(c2);
        
        Operacoes op = new Operacoes();
        
        IndividuoBinario novos[] = op.crossover(i1, i2);
        
        novos[0].mostrarIndividuo();
        //novos[1].mostrarIndividuo();
        
        op.mutacao(novos[0], 7);
        
        novos[0].mostrarIndividuo();*/
        AlgoritmoGenetico ag = new AlgoritmoGenetico(100, 7, new double[]{-2, 2}, 7);
        
        ag.mostrarPopulacao();
        ag.evoluir(200);
        ag.mostrarPopulacao();

    }

}
