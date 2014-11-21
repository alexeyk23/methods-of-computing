/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author admin
 */
public class lab2 {

    
   /* public static double[][] a =new double[N+1][N+1],//new double[][]{{0, 0, 0, 0}, {0, 2, 10, 0}, {0, 3, 19, 12}, {0, -1, 1, 16}},
            b = new double[N + 1][N + 1],
            c = new double[N + 1][N + 1];*/    
  //  public static double[] array = new double[N * 2 * L + 1];
   // public static double[] arrayB = new double[N * 2 * L + 1];
   // public static double[] arrayC = new double[N * 2 * L + 1];
 //   public static double[] f = new double[N+1];
 //   public static double[] y = new double[N+1];
 //   public static double[] x = new double[N+1];
    static int L = 3;
    static int N = 7;
    public static void main(String[] args) 
    {
        test();
//        for (int i = 1; i <= N; i++) 
//        {
//            for (int j = k0(i); j <= kN(i); j++) 
//            {                       
//               array[getIndex(i, j)]=r.nextDouble()*10;
//               arrayC[getIndex(i, i)]=1;
//            }            
//        }       
//        for (int i = 1; i <= N; i++) {
//            f[i]=r.nextDouble()*10;
//        }
//        
//        double s= 0;
//        int i =1;
//        for (int j = k0(1); j <= kN(1); j++) 
//        {
//            s+=x[j]*array[getIndex(1, j)];
//         //   i++;
//        }
//        System.out.println(s+" "+f[1]);
       
    }
    static void test()
    {
      
        Random r = new Random();      
        double range=10;     
        int countTest=10;   
        double sumAvg=0.0;
        int p=10;
        for (int i = 0; i < 4; i++) 
        {
            if(i>1)
            {
                p=100;
            }
           N=r.nextInt(9*p)+4;
           L=(int)Math.round((double)N/10)+1;
           double[] system = new double[N * 2 * L + 1];      
           double[] f = new double[N+1];      
           double[] solution = new double[N+1];
           double[] x = new double[N+1];
           sumAvg=0;          
           int saveL = L;
            for (int j = 0; j < countTest; j++)                
            {
             generateSystem(N, range, system);
             generateXInRange(x, range);
             generateFVector(N, f, system, x);
             Haleckiy(N, L, system, f, solution); 
             sumAvg+=getAvg(N, solution, x);
            }
            double lDivN = (double)saveL/(double)N;           
            System.out.println(String.format("%d | %f | %e ", N,lDivN,sumAvg/countTest));
            L=2*L+1;
            system = new double[N * 2 * L + 1];      
            f = new double[N+1];      
            solution = new double[N+1];
            x = new double[N+1];
            sumAvg=0;
            for (int j = 0; j < countTest; j++) 
            {             
             generateSystem(N, range, system);
             generateXInRange(x, range);
             generateFVector(N, f, system, x);
             Haleckiy(N, L, system, f, solution);
             sumAvg+=getAvg(N, solution, x);   
            }
           lDivN = (double)L/(double)N; 
           System.out.println(String.format("%d | %f | %e ", N,lDivN,sumAvg/countTest));
        }
        
    }
    static  void Haleckiy(int N,int L,double[] system, double[] f, double[] solution)
    {
      double[] arrayB = new double[N * 2 * L + 1];
      double[] arrayC = new double[N * 2 * L + 1];
      //прямой ход
        for (int j = 1; j <= N; j++) {
            for (int i = j; i <= kN(j); i++) {              
                double s = system[getIndex(i, j)];
                for (int k = k0(i); k <=j-1; k++) {                    
                   s-= arrayB[getIndex(i, k)]*arrayC[getIndex(k, j)];
                }          
                arrayB[getIndex(i, j)]=s;
            }
            for (int i = j + 1; i <= kN(j); i++) {                
                 double s = system[getIndex(j, i)];
                for (int k = k0(i); k <= j - 1; k++) {                    
                    s-=arrayB[getIndex(j,k)]*arrayC[getIndex(k, i)];
                }              
                arrayC[getIndex(j, i)]=s/arrayB[getIndex(j, j)];
            }
        }
        //обратный ход
        double[] y = new double[N+1];
        for (int i = 1; i <= N; i++)
        {
            double s =f[i];
            for (int k = k0(i); k <= i-1; k++) {
               s-=arrayB[getIndex(i, k)]*y[k];
            }
            y[i]=s/arrayB[getIndex(i, i)];
        }
        
        for (int i = N; i >=1; i--)
        {
             double s =y[i];
             for (int k = i+1; k <= kN(i); k++)
             {
                s-=arrayC[getIndex(i, k)]*solution[k];
             }
            solution[i]=s;
        }     
    }
    static void generateSystem(int N,double  range,double[] system)
    {
        Random r = new Random();
        for (int i = 1; i <= N; i++) 
        {
         for (int j = k0(i); j <= kN(i); j++) 
         { 
            system[getIndex(i, j)] = (double)( r.nextDouble() * 2 * range - range);
         }
        }
    }
    static void generateXInRange(double[] x, double range) {
        Random r = new Random();
        for (int i = 1; i < x.length; i++) {
            x[i] = (double)( r.nextDouble() * 2 * range - range);
        }
    }
    static  void  generateFVector(int N,double[] f, double[] system,double[] x)
    {
         Arrays.fill(f, 0); 
         for (int i = 1; i <= N; i++)
         {            
            for (int j = k0(i); j <= kN(i); j++) 
            {
                    f[i]+=system[getIndex(i, j)]*x[j];
            }
         }
    }
    static double getAvg(int n, double[] solution, double[] x) // Вычисление погрешности
    {
        double res = 0;
        for (int i = 1; i < n + 1; i++) {
            if (x[i] != 0) {
                res = Math.max(res, Math.abs(solution[i] - x[i]) / x[i]);
            } else {
                res = Math.max(res, Math.abs(solution[i] - x[i]));
            }
        }
        return res;
    }
    public static int getIndex(int i,int j)
    {
        return i+N*(j-i+L);
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
