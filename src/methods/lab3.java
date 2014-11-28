/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package methods;

import java.util.Arrays;
import java.util.Random;

public class lab3 
{
    public static void main(String[] args)
    {
        Random r = new Random();
        double [][] a = new double[N][N];
        double[] f = new double[N],solution = new double[N];
          for (int i = 0; i < N; i++) 
        {
            for (int j = i; j < N; j++) 
            {
                a[i][j]=r.nextDouble()*10;
               
            }
            f[i] =r.nextDouble()*10;
        }
          Haleckiy(a, f, solution);
          System.out.println(Arrays.toString(solution));
    }
    static double lambda =0;
    static double[] xSolve;
    static int countItera=0;
    static int N=4;
    public  static  void  Solve(double [][] a,double eps,double lam1,double [] x1,int M)
    {
        
    }
    public static void Haleckiy(double [][]a, double [] f,double [] solution)
    {
        double[][] b = new double[N][N],
                   c = new double[N][N];   
         for (int i = 0; i < N; i++)         
            c[i][i] = 1;
        for (int j = 0; j < N; j++) 
        {
            for (int i = j; i < N; i++) 
            {
                double s = a[i][j];
                for (int k = 0; k <j-1; k++)                 
                    s -= b[i][k] * c[k][j];                
                b[i][j] = s;
            }
            for (int i = j + 1; i < N; i++) {
                double s = a[j][i];
                for (int k =0; k < j - 1; k++)               
                    s -= b[j][k] * c[k][i];                
                c[j][i] = s / b[j][j];
            }
        }
        double[] y = new double[N+1];
       
        for (int i = 0; i < N; i++)
        {
            double s =f[i];
            for (int k = 0; k < i-1; k++)
            {
               s-=b[i][k]*y[k];
            }
            y[i]=s/b[i][i];
        }        
        for (int i = N-1; i >=0; i--)
        {
             double s =y[i];
             for (int k = i+1; k < N; k++)
             {
                s-=c[i][k]*solution[k];
             }
            solution[i]=s;
        }     
        
    }
    public static double Norma(double[] x)
    {
        double res =0;
        for (int i = 0; i < x.length; i++) 
        {
            double xi=x[i];
            res+=xi*xi;
        }
        return Math.sqrt(res);
    }
}
