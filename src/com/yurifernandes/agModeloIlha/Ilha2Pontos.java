package com.yurifernandes.agModeloIlha;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Ilha2Pontos extends AlgoritmoGenetico {

    public Ilha2Pontos(int tamanhoPopulacao, //populacao minima = 40
            int probabilidadeMutacao,
            double intervalo[],
            int precisao
    ) throws RemoteException, MalformedURLException {
        super(tamanhoPopulacao, probabilidadeMutacao, intervalo, precisao);
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        AlgoritmoGenetico ag = new Ilha2Pontos(100, 7, new double[]{-1, 2}, 8);
        ag.rmiBind("127.0.0.1", 2022, "Ilha2Pontos");
        ag.setIlhaVizinha("127.0.0.1", 2023, "IlhaPoliamor");
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
                IndividuoBinario filhos[] = op.crossover2pontos(i1, i2);
                for (IndividuoBinario filho : filhos) {
                    op.mutacao(filho, probabilidadeMutacao);
                    novaPopulacao.add(filho);
                }
            }
            populacao = novaPopulacao;
            numGeracoes--;
        }
    }
}
