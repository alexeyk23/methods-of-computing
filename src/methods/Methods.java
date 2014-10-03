
package methods;

import java.util.Arrays;

/**
 *
 * @author admin
 */
public class Methods {

    /**
     * @param args the command line arguments	
0	0	0	0	0	0	1	1	    	2
0	0	0	0	0	1	2	1	    	4
0	0	0	0	1	2	1	0	    	4
1	1	1	1	2	1	1	1	    	9
1	1	1	2	1	1	1	1	    	9
0	1	2	1	0	0	0	0	    	4
1	2	1	0	0	0	0	0	    	4
1	1	0	0	0	0	0	0	    	2
     */
   
    public static void main(String[] args) 
    {
        int n=10000;
        int k=5;
        double[] a = new double[n+1];
        double[] p = new double[n+1];
        double[] q = new double[n+1];
        double[] r = new double[n+1];
        Arrays.fill(r, 4);
        r[k]=n+1;r[k+1]=n+1;
        r[1]=2;r[n]=2;        
        double[] b = new double[n],c= new double[n];
        Arrays.fill(a, 2); a[1]=1; a[n]=1;
        Arrays.fill(b, 1);
        Arrays.fill(c, 1);
        Arrays.fill(p, 1);
        Arrays.fill(q, 1);
        q[k+1]=2;
        p[k]=2;
        //1
        
        for (int i = 1; i <= k-1; i++) 
        {
          b[i]/=a[i]; 
          r[i]/=a[i];
          a[i]=1;
          a[i+1]-=b[i]*c[i];
          r[i+1]-=r[i]*c[i];
          c[i]=0;
          p[i+1]-=p[i]*b[i];
          if(i==k-2)//пересечение p и c
              c[i+1]=p[i+1];
          if(i!=k-1)
            r[k]-=r[i]*p[i];
          p[i]=0;          
          q[i+1]-=q[i]*b[i];
          r[k+1]-=r[i]*q[i];
          q[i]=0;
        }
        
      for (int i = n; i>=k+2; i--)
        {
            c[i-1]/=a[i];
            r[i]/=a[i];
            a[i]=1;
            a[i-1]-=c[i-1]*b[i-1];
            r[i-1]-=r[i]*b[i-1];
            b[i-1]=0;            
            q[i-1]-=q[i]*c[i-1];
            if(i==k+3)
              b[i-2]=q[i-1];
            if(i!=k+2)
              r[k+1]-=r[i]*q[i];           
            q[i]=0;
            p[i-1]-=p[i]*c[i-1];
            r[k]-=r[i]*p[i];
            p[i]=0;
        }
        p[k+1]/=p[k];
        r[k]/=p[k];
        p[k]=1;
        q[k+1]-=q[k]*p[k+1];
        r[k+1]-=q[k]*r[k];
        q[k]=0;
        r[k+1]/=q[k+1]; 
        q[k+1]=1;
        r[k]-=p[k+1]*r[k+1]; 
        p[k+1]=0;          
        for (int i = k; i >= 2; i--) 
        {
            r[i-1]-=r[i]*b[i-1];
            b[i-1]=0;
            r[i-1]/=a[i-1]; 
            a[i-1]=1;
        }       
       for (int i = k+1; i < n; i++) 
        {
         r[i+1]-=r[i]*c[i]; 
         c[i]=0;
         r[i+1]/=a[i+1];
         a[i+1]=1;
        }
       System.out.print(" b= "+Arrays.toString(b)+"\n a= "+Arrays.toString(a)+"\n c= "+Arrays.toString(c)+
                "\n r="+Arrays.toString(r)+"\n p= "+Arrays.toString(p)+"\n q= "+Arrays.toString(q));
       double we=0;
       for (int i = 1; i < n+1; i++) {
            we+=r[i];
        }
        
        System.out.println("\n"+(we+r[k]));
    }
    
}

