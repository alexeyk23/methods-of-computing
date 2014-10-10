
package methods;
import java.util.Arrays;
public class Methods
{
    
public static void main(String[] args) 
    {
        int n=8;
        int k=4;
        double[] a = new double[n+1];
        double[] p = new double[n+1];
        double[] q = new double[n+1];
        double[] r = new double[n+1];
        double[] b = new double[n+1],c= new double[n+1];
        generateSystem(n,k,a,b,c,p,q);
        double[] x = new double[n+1];
        Arrays.fill(x, 1);
        generateRVector(n, k, x, a, b, c, p, q, r);
        System.out.print(" r= "+Arrays.toString(r));
        /*
        for (int i = 1; i <= k-1; i++) 
        {
          b[i]/=a[i]; 
          r[i]/=a[i];
          a[i]=1;
          int incI=i+1;
          a[incI]-=b[i]*c[i];
          r[incI]-=r[i]*c[i];
          c[i]=0;
          p[incI]-=p[i]*b[i];
          if(i==k-2)
             c[incI]=p[incI];
          if(i!=k-1)
            r[k]-=r[i]*p[i];
          p[i]=0;          
          q[incI]-=q[i]*b[i];
          r[k+1]-=r[i]*q[i];
          q[i]=0;
        }        
       for (int i = n; i>=k+2; i--)
        {
            int decI=i-1;
            c[decI]/=a[i];
            r[i]/=a[i];
            a[i]=1;
            a[decI]-=c[decI]*b[decI];
            r[decI]-=r[i]*b[decI];
            b[decI]=0;            
            q[decI]-=q[i]*c[decI];
            if(i==k+3)
              b[i-2]=q[decI];
            if(i!=k+2)
              r[k+1]-=r[i]*q[i];           
            q[i]=0;
            p[decI]-=p[i]*c[decI];
            r[k]-=r[i]*p[i];
            p[i]=0;
        }
        int incK=k+1;
        p[incK]/=p[k];
        r[k]/=p[k];
        p[k]=1;
        q[incK]-=q[k]*p[incK];
        r[incK]-=q[k]*r[k];
        q[k]=0;
        r[incK]/=q[incK]; 
        q[incK]=1;
        r[k]-=p[incK]*r[incK]; 
        p[incK]=0;          
        for (int i = k; i >= 2; i--) 
        {
            int decI=i-1;
            r[decI]-=r[i]*b[decI];
            b[decI]=0;
            r[decI]/=a[decI]; 
            a[decI]=1;
        }       
       for (int i = k+1; i < n; i++) 
       {
         int incI=i+1;
         r[incI]-=r[i]*c[i]; 
         c[i]=0;
         r[incI]/=a[incI];
         a[incI]=1;
       }      
        
       System.out.print(" b= "+Arrays.toString(b)+"\n a= "+Arrays.toString(a)+"\n c= "+Arrays.toString(c)+
                "\n r="+Arrays.toString(r)+"\n p= "+Arrays.toString(p)+"\n q= "+Arrays.toString(q));
       double we=0;
       for (int i = 1; i < n+1; i++)        
            we+=r[i];               
       System.out.println("\n"+(we+r[k]));*/
    }
    static void generateSystem(int n,int k, double [] a,double [] b,double [] c,double [] p,double [] q)
    {
            
        Arrays.fill(a, 2);
        a[1]=1;a[n]=1;
        Arrays.fill(b, 1);
        Arrays.fill(c, 1);
        Arrays.fill(p, 1);
        Arrays.fill(q, 1);
        q[k+1]=2;
        p[k]=2;    
    }    
    static void generateRVector(int n,int k,double[] x,double[]a,double [] b,double [] c,double [] p,double [] q,double[] r)
    {
         for (int i = 2; i < n; i++) 
         {
             r[i]=b[i-1]*x[n-i]+a[i]*x[n-i+1]+c[i-1]*x[n-i+2];
         }
         r[k]=0;
         r[k+1]=0;
         for (int i = 1; i < n+1; i++) 
         {
           r[k]+=p[i]*x[n-i+1];
           r[k+1]+=q[i]*x[n-i+1];
         }
         r[1]= b[1]*x[n-1]+a[1]*x[n];
         r[n]=a[n]*x[1]+c[n]*x[2];
    }
}

