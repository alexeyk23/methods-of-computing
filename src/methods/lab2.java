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
    static int L = 2;
    static int N = 4;
    public static void main(String[] args) 
    {
       System.out.println("Тесты ленточных матриц");
       test();  
       int i=10;
       System.out.println("\nТесты хорошо обусловленных матриц");
       goodMatrixTest();
        
       System.out.println("\nТесты плохо обусловленных матриц");
       badMatrixTest();
    }
    static void test()    
    {      
        Random r = new Random();      
        double range=10;     
        int countTest=10;   
        double sumAvg=0.0;
        int p=10;
        for (int i = 0; i < 2; i++) 
        {
            if(i==1)
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
    static void goodMatrixTest()
    {
        Random r = new Random();        
        int countTest=10;
        int p = 10;
        for (int j = 0; j < 4; j++) 
        {    
            double sum = 0;
            if(j>1)
                p=100;
            for (int i = 0; i < countTest; i++) 
            {
                N=r.nextInt(9*p)+p;                
                L=N;
                double range = 10;
                double[] system = new double[N *2*L + 1];      
                double[] f = new double[N+1];      
                double[] solution = new double[N+1];
                double[] x = new double[N+1];
                generateSystem(N, range, system);
                generateXInRange(x, range);
                generateFVector(N, f, system, x);
                Haleckiy(N, L, system, f, solution);
                sum+=getAvg(N, solution, x);
            }           
            System.out.println(String.format("%d | %e",N,sum/countTest));
        }
    }
    static void badMatrixTest()
    {
        N=10;
        L=N;
        Random r= new Random();
        for (int i = 0; i < 2; i++) {
            N = r.nextInt(90)+10;
            L=N;            
            for (int pow = 2; pow < 8; pow+=2) 
            {
                double[] system=genBadMatrix(N, pow);
                double[] f = new double[N+1];      
                double[] solution = new double[N+1];
                double[] x = new double[N+1];
                generateXInRange(x, 10);
                generateFVector(N, f, system, x);
                Haleckiy(N, L, system, f, solution);
                System.out.println(String.format("%d | %d | %e", pow,N,getAvg(N, solution, x)));
            }
        }
        
    }
    static double[] genBadMatrix(int N, int pow)
    {
        double[] badL = new double[N*N*2+1];
        double[] badV = new double[N*N*2+1];
        double[] badM = new double[N*N*2+1];
        double range =10;
        Random r = new Random();
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= i; j++) {
                badL[getIndex(i, j)] = (double)(r.nextDouble() * 2 * range - range);
                badV[getIndex(j, i)] = (double)(r.nextDouble() * 2 * range - range);
            }
        }
        double p = Math.pow(10, -pow);
        for (int i = 1; i <= N; i++) 
        {
            badL[getIndex(i, i)]*=p;
            badV[getIndex(i, i)]*=p;
        }
        for (int i = 1; i <= N; i++)
            {
                for (int j = 1; j <= N; j++)
                {
                    double s =0;
                    for (int k = 1; k <= N; k++)
                           s+=badL[getIndex(i, k)]*badV[getIndex(k, j)];
                    badM[getIndex(i, j)]=s;
                }
            }
        return badM;
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
