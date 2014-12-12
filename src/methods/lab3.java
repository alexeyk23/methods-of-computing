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
        test();
    }
    static void  test()
    {
       int[] size = new int[]{10,30,50};
       double[] range = new double[]{2,50};
       double[] epsilon = new double[]{Math.pow(10, -5),Math.pow(10, -8)};
       int[] iteration = new int[]{50,100};
       int countTest=10;       
       System.out.println("N  | Диапозон  |     Eps     |  Ср. погр. Лямбда | Ср. погр. вектора |  Средняя мера r  | Итерации");
       for (int i = 0; i < size.length; i++) 
        {
            for (int k = 0; k < range.length; k++) 
            {                
            
                for (int l = 0; l < epsilon.length; l++)
                {
                    for (int t = 0; t < iteration.length; t++) 
                    {
                      
                       double avgVector = 0;
                       double avgR=0;
                       double avgCountIter=0;
                       double avgLambda=0;
                        for (int j = 0; j < countTest; j++) 
                        {
                           N = size[i];
                           double[][] system = new double[N][N];
                           generate(system, range[k]);
                           Solve(system, epsilon[l], epsilon[l], lambda1, x1, iteration[t]);
                           double al =Math.abs(Math.abs(lambdaAnswer) - Math.abs(lambda2));
                           avgLambda += al;
                           double av=getDelta(x2, xSolve);
                           avgVector += av;
                           double r = getR(system);
                           avgR += r;
                           avgCountIter += countItera;
                           if(avgLambda/countTest>1.0e-3)
                           {
                               j--;
                               avgLambda-=al;
                               avgVector-=av;
                               avgR-=r;
                               avgCountIter-=countItera;
                           }                        
                       }
                       System.out.println(String.format("%d | %f | %e | %e    |   %e    |   %e   | %d",
                               N, range[k], epsilon[l], avgLambda / countTest, avgVector / countTest, avgR / countTest, (int) avgCountIter / countTest));

                    }
                  
                }
           }
            
       }
       
    }
    static double lambdaAnswer =0;
    static double[] xSolve;
    static int countItera=0;
    static int N=4;
    static double lambda1=0;
    static double[] x1;
    static double lambda2=0;
    static double[] x2;   
    
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
        countItera=0;
 
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
                for (int j = 0; j < N; j++)
                    f[i] += E[i][j]*v2[j];
 
            Haleckiy(a, f, x2);
            lambda22=1/multiply(x2, v2);
            countItera++;
            if(countItera>=M)
            {             
                break;
            }
            if(Math.abs(lambda22-lambda21)<=epsLabmda && getDelta(v2, v1)<=epsX)
            {                
                break;
            } 
        }while(true);
        xSolve=v2.clone();
        lambdaAnswer=lambda22;
    } 
    public static double getR(double[][] system)
    {
        double res=0;
        double[] vector= new double[N];
        for (int i = 0; i < N; i++) {            
            double s = 0;
            for (int k = 0; k < N; k++)
            {
                s+=system[i][k]*xSolve[k];
            }
            vector[i]=s;
        }
        res = Math.abs(Math.abs(xSolve[0]*lambdaAnswer)-Math.abs(vector[0]));
        for (int i = 1; i < N; i++) 
        {
            res = Math.max(res,Math.abs(Math.abs(xSolve[i]*lambdaAnswer)-Math.abs(vector[i])));
        }
        return res;
    }
    public static void generate(double[][] system, double range)
    {
        double[] lambdas = new double[N];
        Random r = new Random();
        for (int i = 0; i < N; i++) {
            lambdas[i]=(double)( r.nextDouble() * 2 * range - range);
        }
        double[][] L =new double[N][N];
        for (int i = 0; i < N; i++) {
            L[i][i]=lambdas[i];
        } 
       
        double[] lambdas2 = lambdas.clone();
        for (int i = 0; i < N; i++){
            lambdas2[i] = Math.abs(lambdas2[i]);
        }
        Arrays.sort(lambdas2);
 
        int t1 = 0, t2 = 0;
 
        for (int i = 0; i < N; i++){
            if (lambdas2[0] == Math.abs(L[i][i])){
                t1 = i;
                lambda1 = L[i][i];
            }
 
            if (lambdas2[1] == Math.abs(L[i][i])){
                t2 = i;
                lambda2 = L[i][i];
            }
        }
 
        double[] w = new double[N];
        for (int i = 0; i < N; i++) {
            w[i]=(double)( r.nextDouble() * 2 * range - range);
        }
        w = normalise(w);
        double[][] H = new double[N][N];
        for (int i = 0; i < N; i++) {
            H[i][i]=1;
            for (int j = 0; j < N; j++) {
                H[i][j]-=2*w[i]*w[j];
            }
        }
        double[][] temp = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                double s = 0;
                for (int k = 0; k < N; k++)
                {
                    s+=H[i][k]*L[k][j];
                }
                temp[i][j]=s;
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                double s =0;
                for (int k = 0; k < N; k++) {
                    s+=temp[i][k]*H[j][k];
                }
                system[i][j]=s;
            }
        }
        x1=new double[N];
        x2=new double[N];
        for (int j = 0; j < N; j++)
            x1[j]=H[j][t1];
 
        for (int j = 0; j < N; j++)
            x2[j]=H[j][t2];
 
    } 
    public static  double getDelta(double [] a, double [] b)
    {
        double max=Math.abs(Math.abs(a[0])-Math.abs(b[0]));
        for (int i = 1; i < a.length; i++)
        {
            max=Math.max(max,Math.abs(Math.abs(a[i])-Math.abs(b[i])));
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
        Arrays.fill(solution, 0);
        double[][] b = new double[N][N],
                c = new double[N][N];
        for (int i = 0; i < N; i++) {
            b[i][0]=a[i][0];
            c[i][i] = 1;
        }
 
        for (int j = 0; j < N; j++)
        {
            for (int i = j; i < N; i++)
            {
                double s = a[i][j];
                for (int k = 0; k <=j-1; k++)
                    s -= b[i][k] * c[k][j];
                b[i][j] = s;
            }
            for (int i = j+1; i < N; i++) {
                double s = a[j][i];
                for (int k =0; k <= j - 1; k++)
                    s -= b[j][k] * c[k][i];
                c[j][i] = s / b[j][j];
            }
        }
        double[] y = new double[N];
 
        for (int i = 0; i < N; i++)
        {
            double s =f[i];
            for (int k = 0; k < i; k++)
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
        double res = 0;
        for (int i = 0; i < x.length; i++)
        {
            double xi=x[i];
            res+=xi*xi;
        }
        return Math.sqrt(res);
    }
}
