/*
Objetivo: encontrar o valor de x que gera o menor valor de y, na função abaixo:
  f (x) = x × sin(10 × x × π) + 1, x ∈ [−1, 2]
 */
package com.yurifernandes.agModeloIlha;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 *
 * @author Yuri Fernandes de Oliveira <yurifernandes.com>
 */
public class Main {

    public static void main(String args[]) throws RemoteException, MalformedURLException {

        /*
            AO CRIAR UMA ILHA ESCOLHA QUAL ALGORITMO USAR
                basta instanciar a classe escolhida
        
            OPÇÕES:
            
                1 - AlgoritmoGenetico
                    Crossover de corte de 1 ponto, mutação de 1 gene.
        
                2 - Ilha2Pontos
                    Crossover de corte de 2 pontos, mutação de 1 gene.
        
                3 - IlhaPoliamor
                    Crossover de 3 progenitores, mutação de 1 gene
                    Para cada gene, o que estiver presente em pelo menos 2
                    dos progenitores é escolhido para o indivíduo gerado.
                    Apenas 1 individuo é gerado no crossover.
        
                4 - IlhaUniforme
                    Crossover uniforme, mutação de 1 gene
                    É sorteado para cada gene, qual progenitor vai concedê-lo
                    para qual dos filhos, isoladamente
        
                5 - IlhaTchernobyl
                    Crossover de corte de 1 ponto, mutação de rajada em 5 genes vizinhos
                    Quando a mutação acontece ela afeta até 5 genes consecutivos, mas ela
                    não é uma mudança garantida, ao invés de inverter o valor do gene
                    um novo valor é sorteado, podendo ser igual ao anterior ou não.
                    Cada um dos 5 gentes escolhidos tem aproximadamente 50% de chance de mutar.
                    
            PARAMETROS PARA QUALQUER UM DOS ALGORITMOS:
                
                (Tamanho da população, probabilidade de mutação, intervalo[], precisao)
        
         */
        AlgoritmoGenetico ag = new AlgoritmoGenetico(100, 7, new double[]{-1, 2}, 8);

        ag.mostrarPopulacao();
        ag.evoluir(100);
        ag.mostrarPopulacao();
    }
}
