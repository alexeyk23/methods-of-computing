package methods;

import java.util.Random;

public class Methods {

  /*  public static void main(String[] args) {
        test();
    }
*/
    static void test() {
        int n = 100;
        int k = n/2;
        double[] a = new double[n + 1];
        double[] p = new double[n + 1];
        double[] q = new double[n + 1];
        double[] r = new double[n + 1];
        double[] b = new double[n], c = new double[n];
        double[] x = new double[n + 1];
        double[] solution = new double[n + 1];
        double range = 10;
        while (n <= 100) {
            range = 10;
            while (range <= 10) {
                double sum = 0;
                double sum2=0;
                for (int i = 0; i < 20; i++) {
                    generateSystem(n, k, a, b, c, p, q, range);
                    generateXInRange(x, range);                    
                    generateRVector(n, k, a, b, c, p, q, r, x);
                    SolveSystem(n, k, a.clone(), b.clone(), c.clone(), p.clone(), q.clone(), r.clone(), solution);                    
                    sum += getAvg(n, solution, x);
                   
//                    Arrays.fill(x,1);                    
//                    generateRVector(n, k, a, b, c, p, q, r, x);
//                    SolveSystem(n, k, a.clone(), b.clone(), c.clone(), p.clone(), q.clone(), r.clone(), solution); 
//                    sum2+=getAvg(n, solution, x);
                }
                sum /= 20;
                sum2/=20;
                System.out.println(String.format("%d | [-%.0f, %.0f] | %e | %e ", n, range, range,sum2, sum));
                range *= 10;
            }
            n *= 10;
            k= n/2;
            a = new double[n + 1];
            p = new double[n + 1];
            q = new double[n + 1];
            r = new double[n + 1];
            b = new double[n];
            c = new double[n];
            x = new double[n + 1];
            solution = new double[n + 1];
        }
    }

    static void SolveSystem(int n, int k, double[] a, double[] b, double[] c, double[] p, double[] q, double[] r, double[] solution) {
        try {
            //step1
            double rK=r[k];
            double rKINC = r[k+1];
            double max=0.0;
            double[] saveP = new double[p.length];
            double[] saveQ = new double[q.length];
                
            for (int i = 1; i <= k - 1; i++) {
                double revert = 1 / a[i];
                b[i] *= revert;
                r[i] *= revert;
                a[i] = 1;
                int incI = i + 1;
                double tmp = -c[i];
                a[incI] += b[i] * tmp;
                r[incI] += r[i] * tmp;
                c[i] = 0;
                tmp = -p[i];
                p[incI] += tmp * b[i];
                if (i == k - 2) 
                    c[incI] = p[incI];   
                if(Math.abs(r[i] * tmp) > max)
                    max=Math.abs(r[i] * tmp);
                saveP[i]=tmp;
               // if (i != k - 1) 
               //     rK += r[i] * tmp;                
                p[i] = 0;
                tmp=-b[i];
                q[incI] += q[i] * tmp;
                tmp=-r[i];
               // rKINC += tmp * q[i];
                q[i] = 0;
            }            
           
            for (int i = 1; i <= k-1; i++) 
            {
               double tmp = r[i]/max* saveP[i];
               rK+=tmp;
            }
            rK*=max;
            // step2
            for (int i = n; i >= k + 2; i--) {
                int decI = i - 1;
                double revert = 1 / a[i];
                c[decI] *= revert;
                r[i] *= revert;
                a[i] = 1;
                double tmp = -b[decI];
                a[decI] += c[decI] * tmp;
                r[decI] += r[i] * tmp;
                b[decI] = 0;
                tmp = -q[i];
                q[decI] += tmp * c[decI];
                if (i == k + 3) {
                    b[i - 2] = q[decI];
                }
                if (i != k + 2) {
                    rKINC += r[i] * tmp;
                }
                q[i] = 0;
                tmp=-p[i];
                p[decI] += tmp * c[decI];
                rK += r[i] * tmp;
                p[i] = 0;
            }
            //step 3            
            int incK = k + 1;
            p[incK] /= p[k];
            r[k] /= p[k];
            p[k] = 1;
            double tmp=-q[k];
            q[incK] += tmp * p[incK];
            r[incK] += tmp * r[k];
            q[k] = 0;
            r[incK] /= q[incK];
            q[incK] = 1;
            tmp=-p[incK];
            r[k] += tmp * r[incK];
            p[incK] = 0;
            //step 4
            for (int i = k; i >= 2; i--) {
                int decI = i - 1;
                tmp=-r[i];
                r[decI] += tmp * b[decI];
                b[decI] = 0;
                r[decI] /= a[decI];
                a[decI] = 1;
            }
            //step 5
            for (int i = k + 1; i < n; i++) {
                int incI = i + 1;
                tmp=-r[i];
                r[incI] += tmp * c[i];
                c[i] = 0;
                r[incI] /= a[incI];
                a[incI] = 1;
            }
        } catch (Exception e) {
            System.out.println("!!!!!!!!!!!!!"+e.getMessage());
        }
        for (int i = 1; i < n + 1; i++) {
            solution[i] = r[n - i + 1];
        }
    }

    static void generateSystem(int n, int k, double[] a, double[] b, double[] c, double[] p, double[] q, double range) {

        Random r = new Random();
        for (int i = 1; i < n; i++) 
        {
            a[i] = (double)( r.nextDouble() * 2 * range - range);
            b[i] = (double)( r.nextDouble() * 2 * range - range)*10;
            c[i] = (double)( r.nextDouble() * 2 * range - range);
        }
        a[n] = (double) r.nextDouble() * 2 * range - range;
        for (int i = 1; i < n + 1; i++) 
        {
            p[i] = (double)(r.nextDouble() * 2 * range - range);
            q[i] = (double) (r.nextDouble() * 2 * range - range);
        }
        p[k] = a[k];
        p[k - 1] = c[k - 1];
        p[k + 1] = b[k];

        q[k + 1] = a[k + 1];
        q[k] = c[k];
        q[k + 2] = b[k + 1];     
    }

    static void generateXInRange(double[] x, double range) {
        Random r = new Random();
        for (int i = 1; i < x.length; i++) {
            x[i] = (double)( r.nextDouble() * 2 * range - range);
        }

    }

    static void generateRVector(int n, int k, double[] a, double[] b, double[] c, double[] p, double[] q, double[] r, double[] x) {
        for (int i = 2; i < n; i++) {
            r[i] = b[i] * x[n - i]
                    + a[i] * x[n - i + 1]
                    + c[i - 1] * x[n - i + 2];
        }
        r[k] = 0;
        r[k + 1] = 0;
        for (int i = 1; i < n + 1; i++) {
            r[k] += p[i] * x[n - i + 1];
            r[k + 1] += q[i] * x[n - i + 1];
        }
        r[1] = b[1] * x[n - 1] + a[1] * x[n];
        r[n] = a[n] * x[1] + c[n - 1] * x[2];
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
}
