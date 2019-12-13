package com.yurifernandes.agModeloIlha;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author yuriborghi
 */
public interface Ilha extends Remote {

    public void receberImigrantes(ArrayList<Individuo> individuos) throws RemoteException;
    
    public void evolucaoEmIlha(int numCiclos, int geracoesPorIlha, int qtdMigrantes) throws RemoteException;
}
