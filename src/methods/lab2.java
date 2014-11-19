/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

import java.util.Random;

/**
 *
 * @author admin
 */
public class lab2 {

    static int L = 3;
    static int N = 7;
    public static double[][] a =new double[N+1][N+1],//new double[][]{{0, 0, 0, 0}, {0, 2, 10, 0}, {0, 3, 19, 12}, {0, -1, 1, 16}},
            b = new double[N + 1][N + 1],
            c = new double[N + 1][N + 1];
    public static double[][] p = new double[N + 1][2 * L];
    public static double[] array = new double[N * 2 * L + 1];
    
    public static void main(String[] args) {
        Random r = new Random();
        for (int i = 1; i <= N; i++) 
        {
            for (int j = k0(i); j <= kN(i); j++) 
            {
               a[i][j] = r.nextDouble()*10;
               p[i][j-i+L]=a[i][j];
               array[i+N*(j-i+L)]=a[i][j];
            }            
        }
        for (int i = 1; i <= N; i++) 
        {
            c[i][i] = 1;
        }

        for (int j = 1; j <= N; j++) {

            for (int i = j; i <= kN(j); i++) {
                double s = a[i][j];
                for (int k = k0(i); k <=j-1; k++) {
                    s -= b[i][k] * c[k][j];
                }
                b[i][j] = s;
            }
            for (int i = j + 1; i <= kN(j); i++) {
                double s = a[j][i];
                for (int k = k0(i); k <= j - 1; k++) {
                    s -= b[j][k] * c[k][i];
                }
                c[j][i] = s / b[j][j];
            }
        }
        
        
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                System.out.print(String.format("%.3f ",b[i][j]));
            }
            System.out.println();
        }
          System.out.println("\n\n");
          for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                System.out.print(String.format("%.3f ",c[i][j]));
            }
            System.out.println();
        }
//        
//        for (int i = 1; i <=N; i++) 
//        {         
//            for (int j = 1; j < 2*L; j++) 
//            {
//                 System.out.print(array[i+N*j]+" ");
//            }
//            System.out.println();            
//        }
    }

    public static int k0(int index) {
        if (index <= L) {
            return 1;
        }
        return index - L + 1;
    }

    public static int kN(int index) {
        if (index > N - L) {
            return N;
        }
        return index + L - 1;
    }

    public static int jStar(int j, int i) {
        return j - i + L;
    }
}
