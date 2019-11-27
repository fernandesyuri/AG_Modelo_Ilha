package com.yurifernandes.agModeloIlha;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * Classe que representa um indivíduo cujo: (i) cromossomo é um vetor de
 * inteiros, com a primeira posição representando a parte inteira de um valor, e
 * com as outras posições representando a parte decimal; (ii) fenótipo é um
 * valor decimal.
 *
 * @author Yuri Fernandes de Oliveira <yurifernandes.com>
 */
public class IndividuoDecimal extends Individuo<int[], Double> {

    //private double intervalo[];
    private int precisao;
    private static final double MINIMO_FENOTIPO = -10;

    public IndividuoDecimal(int[] cromossomo, /*double[] intervalo,*/ int precisao) {
        super(cromossomo);
        //this.intervalo = intervalo;
        this.precisao = precisao;
    }

    @Override
    public Double getFenotipo() {
        String aux = "";
        for (int i = 0; i < cromossomo.length; i++) {
            aux = aux.concat(Integer.toString(cromossomo[i]));
            if (i == 0) {
                aux = aux.concat(".");
            }
        }
        Locale.setDefault(Locale.ENGLISH);
        double xChapeu = Double.valueOf(aux);

        //f(x) = x × sin(10 × x × π) + 1, x ∈ [−1, 2]
        double fenotipo = xChapeu * Math.sin(10 * xChapeu * Math.PI) + 1;
        fenotipo = Double.valueOf(String.format("%." + precisao + "f", fenotipo));
        //System.out.println(fenotipo);
        return fenotipo;
    }

    /*
    public double[] getIntervalo() {
        return intervalo;
    }
     */
    @Override
    public double getAptidao() {
        BigDecimal modulo;

        double fenotipo = this.getFenotipo();
        if (fenotipo >= 0) {
            modulo = BigDecimal.valueOf(Double.MAX_VALUE).add(BigDecimal.valueOf(fenotipo));
            //return Math.abs(MINIMO_FENOTIPO) + fenotipo;
        } else {
            modulo = BigDecimal.valueOf(Double.MAX_VALUE).subtract(BigDecimal.valueOf(Math.abs(fenotipo)));
            //return MINIMO_FENOTIPO + Math.abs(fenotipo);
        }
        //return 1 / (Math.pow(getFenotipo(), 2) + 0.001);

        System.out.println("Fenótipo: " + fenotipo);
        System.out.println("Modulo: " + modulo.toString());
        System.out.println("Menor positivo: " + Double.MIN_VALUE);

        // Modulo 0 -> Aptidão máxima; Módulo máximo (2 * Double.MAX_VALUE) -> Aptidão 0
        return BigDecimal.valueOf(Double.MIN_VALUE * 1000).divide(modulo).doubleValue();
    }

    // Método para retornar o tamanho do cromossomo.
    public int getTamanhoCromossomo() {
        return cromossomo.length;
    }

    // Método para clonar um indivíduo.
    public IndividuoDecimal clonar() {
        int cromossomoClonado[] = new int[cromossomo.length];
        for (int i = 0; i < cromossomo.length; ++i) {
            cromossomoClonado[i] = cromossomo[i];
        }
        IndividuoDecimal clone = new IndividuoDecimal(cromossomoClonado,
                /*intervalo,*/ precisao);
        return clone;
    }

    // Método para modificar o valor de um gene no cromossomo.
    public void setGene(int pos, int valor) {
        cromossomo[pos] = valor;
    }

    // Retorna o valor do gene em uma determinada posição
    public int getGene(int pos) {
        return cromossomo[pos];
    }

    @Override
    public void mostrarIndividuo() {
        System.out.print("I =");
        for (int i = 0; i < cromossomo.length; ++i) {
            System.out.print(" " + cromossomo[i]);
        }
        System.out.printf(" Fenótipo = %." + precisao + "f", getFenotipo());

        System.out.printf(" Aptidão = %." + precisao + "f", getAptidao());

        System.out.println();
    }
}
