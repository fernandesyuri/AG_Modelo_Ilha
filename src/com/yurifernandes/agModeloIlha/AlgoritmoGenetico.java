package com.yurifernandes.agModeloIlha;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Yuri Fernandes de Oliveira <yurifernandes.com>
 */
public class AlgoritmoGenetico extends UnicastRemoteObject implements Ilha {

    protected ArrayList<Individuo> populacao;
    protected int probabilidadeMutacao;

    protected int numeroDoCiclo = 0;
    protected String ipVizinho;
    protected int portaVizinho;
    protected String nomeVizinho;

    public AlgoritmoGenetico(int tamanhoPopulacao, //populacao minima = 40
            int probabilidadeMutacao,
            double intervalo[],
            int precisao
    ) throws RemoteException, MalformedURLException {
        populacao = new ArrayList<>();
        this.probabilidadeMutacao = probabilidadeMutacao;
        if (tamanhoPopulacao < 40) {
            tamanhoPopulacao = 40;
        }
        inicializarPopulacao(tamanhoPopulacao, intervalo, precisao);
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        AlgoritmoGenetico ag = new AlgoritmoGenetico(100, 7, new double[]{-1, 2}, 8);
        ag.rmiBind("127.0.0.1", 2021, "AlgoritmoGenetico");
        ag.setIlhaVizinha("127.0.0.1", 2022, "Ilha2Pontos");

        /**
         * Aqui se inicia a evolução em ilha.
         *
         * O primeiro parâmetro é o número de ciclos, ou seja, quantas voltas
         * serão dadas no total no círculo de ilhas.
         *
         * O segundo parâmetro é a quantidade de gerações por ilha, ou seja, a
         * cada quantas gerações que ocorrerá uma migração.
         *
         * O último parâmetro se refere a quantidade de indivíduos que serão
         * enviados por migração para a ilha seguinte.
         *
         * Para que a evolução em ilha ocorra, é necessário inicializar
         * manualmente cada ilha.
         */
        ag.evolucaoEmIlha(5, 100, 5);
    }

    public void rmiBind(String ip, int porta, String nome) throws RemoteException, MalformedURLException {
        LocateRegistry.createRegistry(porta);
        Naming.rebind("rmi://" + ip + ":" + porta + "/" + nome, this);
    }

    public void setIlhaVizinha(String ip, int porta, String nome) {
        this.ipVizinho = ip;
        this.portaVizinho = porta;
        this.nomeVizinho = nome;
    }

    public void evoluir(int numGeracoes) {

        Operacoes op = new Operacoes();
        while (numGeracoes > 0) {
            //System.out.println("Geração (" + numGeracoes + ")");
            ArrayList<Individuo> novaPopulacao = new ArrayList<Individuo>();
            while (novaPopulacao.size() < populacao.size()) {
                IndividuoBinario i1 = (IndividuoBinario) op.torneio(populacao);
                IndividuoBinario i2 = (IndividuoBinario) op.torneio(populacao);
                //System.out.println(i1 + " " + i2);
                IndividuoBinario filhos[] = op.crossover(i1, i2);
                for (IndividuoBinario filho : filhos) {
                    op.mutacao(filho, probabilidadeMutacao);
                    novaPopulacao.add(filho);
                }
            }
            populacao = novaPopulacao;
            numGeracoes--;
        }
    }

    public void mostrarPopulacao() {
        for (Individuo i : populacao) {
            i.mostrarIndividuo();
        }
    }

    void inicializarPopulacao(int tamanhoPopulacao, double intervalo[],
            int precisao) {
        Random r = new Random();

        double tamanhoIntervalo = intervalo[1] - intervalo[0];
        int log = (int) log2(tamanhoIntervalo * Math.pow(10, precisao));
        int tamanhoCromossomo = log + 1;

        for (int i = 0; i < tamanhoPopulacao; ++i) {
            int cromossomo[] = new int[tamanhoCromossomo];
            for (int j = 0; j < tamanhoCromossomo; ++j) {
                cromossomo[j] = r.nextInt(2);
            }
            IndividuoBinario individuo = new IndividuoBinario(cromossomo, intervalo, precisao);
            populacao.add(individuo);
        }
    }

    private double log2(double valor) {
        return Math.log10(valor) / Math.log10(2);
    }

    public ArrayList<Individuo> melhoresIndividuos(int quantidade) {

        Collections.sort(populacao); // talvez dê problema
        ArrayList<Individuo> melhoresIndividuos = new ArrayList<>();
        for (int i = 0; i < quantidade/*(int) (populacao.size() * 0.1)*/; i++) {
            melhoresIndividuos.add(populacao.get(i));
        }

        return melhoresIndividuos;
    }

    @Override
    public void receberImigrantes(ArrayList<Individuo> individuos) throws RemoteException {

        Collections.sort(populacao);
        for (int i = 0; i < individuos.size(); i++) {
            populacao.remove(populacao.size() - 1);
        }
        for (Individuo i : individuos) {
            populacao.add(i);
        }
        System.out.println("Imigrantes recebidos");
    }

    @Override
    public void evolucaoEmIlha(int numCiclos, int geracoesPorIlha, int qtdMigrantes) throws RemoteException {

        if (this.numeroDoCiclo <= numCiclos) {
            evoluir(geracoesPorIlha);
            this.numeroDoCiclo++;

            boolean conectado = false;
            Ilha i = null;
            while (!conectado) {
                try {
                    i = (Ilha) Naming.lookup("rmi://" + ipVizinho + ":" + portaVizinho + "/" + nomeVizinho);
                    conectado = true;
                } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                    //
                }

            }

            //System.out.println("Conectado a " + nomeVizinho);
            i.receberImigrantes(melhoresIndividuos(qtdMigrantes));
            i.evolucaoEmIlha(numCiclos, geracoesPorIlha, qtdMigrantes);
        } else {
            mostrarPopulacao();
        }
    }
}
