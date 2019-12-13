package com.yurifernandes.agModeloIlha;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class IlhaTchernobyl extends AlgoritmoGenetico {

    //Ilha com mutação em cluster, também é recomendado usar uma taxa de mutação mais alta nela
    public IlhaTchernobyl(int tamanhoPopulacao, //populacao minima = 40
            int probabilidadeMutacao,
            double intervalo[],
            int precisao
    ) throws RemoteException, MalformedURLException {
        super(tamanhoPopulacao, probabilidadeMutacao, intervalo, precisao);
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        AlgoritmoGenetico ag = new IlhaTchernobyl(100, 7, new double[]{-1, 2}, 8);
        ag.rmiBind("127.0.0.1", 2025, "IlhaTchernobyl");
        ag.setIlhaVizinha("127.0.0.1", 2021, "AlgoritmoGenetico");
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
