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
    public  static  void  Solve(double [][] a,double epsLabmda, double epsX,double lam1,double [] x1,int M)
    {
        double[] v2 = new double[N],
                 v1 = new double[N];
        Arrays.fill(v2, 0);
        Arrays.fill(v1, 0);
        v2 = normalise(x1);
        double[] x2 = new double[N];
        Arrays.fill(x2, 0);
        double[][] E = new double[N][N];
        for (int i = 0; i < N; i++) {
            E[i][i]=1;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) 
            {
                E[i][j]-=x1[i]*x1[j];
            }
        }
        double[] f = new double[N];
        Arrays.fill(f, 0);
        for (int i = 0; i < N; i++) 
        {
            for (int j = 0; j < N; j++)
            {
                f[i] += E[i][j]*v2[j];
            }
        }
        countItera=1;
        Haleckiy(a, f, x2);
        double lambda21=0,
               lambda22=1/multiply(x2, v2);
        do
        {
            lambda21=lambda22;
            v1=v2.clone();
            v2=normalise(x2);
            Arrays.fill(f, 0);
            for (int i = 0; i < N; i++) 
            {
                for (int j = 0; j < N; j++)
                {
                    f[i] += E[i][j]*v2[j];
                }
            }
            Haleckiy(a, f, x2);
            lambda22=1/multiply(x2, v2);
            countItera++;
        }while(countItera<=M && (Math.abs(lambda22-lambda21)>epsLabmda || getDelta(v2, v1)>epsX));        
        xSolve=v2.clone();
        lambda=lambda22;     
    }
    public static  double getDelta(double [] a, double [] b)
    {       
        double max=Math.abs(a[0]-b[0]);
        for (int i = 1; i < a.length; i++) 
        {
            max=Math.max(max,Math.abs(a[i]-b[i]));
        }
        return max;
    }
    public static double multiply(double[] a, double[] b)
    {
        double res=0;
        for (int i = 0; i < a.length; i++) {            
            res+=a[i]*b[i];            
        }
        return  res;
    }
    static double[] normalise(double[] x) {
        double[] result = new double[N];
        double norma = Norma(x);
        for (int i = 0; i < N; i++) {
            result[i] = x[i] / norma;
        }
        return result;
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
