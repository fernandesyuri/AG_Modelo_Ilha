package com.yurifernandes.agModeloIlha;

/**
 *
 * @author Yuri Fernandes de Oliveira <yurifernandes.com>
 */
public class Teste {
    public static void main(String[] args) {
        
        System.out.println(1.95051939703 * Math.sin(10 * 1.95051939703 * Math.PI) + 1);
        
        int[] genotipo = {0, 2, 5};
        IndividuoDecimal individuoDecimal = new IndividuoDecimal(genotipo, 6);
        System.out.println(individuoDecimal.getAptidao());
    }
}
