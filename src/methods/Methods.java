
package methods;
import java.util.Arrays;
import java.util.Random;
public class Methods
{
     static int n=100;
     static  int k=45;
     static float[] a = new float[n+1];
     static float[] p = new float[n+1];
     static float[] q = new float[n+1];
     static   float[] r = new float[n+1];
     static  float[] b = new float[n],c= new float[n];
     static float[] x = new float[n+1];  
public static void main(String[] args) 
    {
        
        float range=10;
        generateSystem(range);        
        
        //Arrays.fill(x, 1);
        x[0]=0;
        generateXInRange(range);
        generateRVector();
     /*   System.out.print(" b= "+Arrays.toString(b)+"\n a= "+Arrays.toString(a)+"\n c= "+Arrays.toString(c)+
                "\n r="+Arrays.toString(r)+"\n p= "+Arrays.toString(p)+"\n q= "+Arrays.toString(q)+"\n x="+Arrays.toString(x));
        */
        try
        {
        //step1
        for (int i = 1; i <= k-1; i++) 
        {
          float revert=1/a[i];
          
          b[i]*=revert; 
          r[i]*=revert;
          
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
       // step2
       for (int i = n; i>=k+2; i--)
        {
            int decI=i-1;
            float revert=1/a[i];
            c[decI]*=revert;
            r[i]*=revert;
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
       //step 3
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
        //step 4
        for (int i = k; i >= 2; i--) 
        {
            int decI=i-1;
            r[decI]-=r[i]*b[decI];
            b[decI]=0;
            r[decI]/=a[decI]; 
            a[decI]=1;
        }      
       //step 5
        for (int i = k+1; i < n; i++) 
        {
          int incI=i+1;
          r[incI]-=r[i]*c[i]; 
          c[i]=0;
          r[incI]/=a[incI];
          a[incI]=1;
        }      
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
       /*System.out.print(" b= "+Arrays.toString(b)+"\n a= "+Arrays.toString(a)+"\n c= "+Arrays.toString(c)+
                "\n r="+Arrays.toString(r)+"\n p= "+Arrays.toString(p)+"\n q= "+Arrays.toString(q));
        */
        float[] solution = new float[n+1];
        for (int i = 1; i < n+1; i++) 
        {
            solution[i]=r[n-i+1];
        }      
        System.out.print(Arrays.toString(solution)+"\n"+Arrays.toString(x)+"\n "+getAvg(solution));
        
    }
    static void generateSystem(float range)
    {
            
       Random r = new Random();
        for (int i = 1; i<n; i++)
        {
            a[i] = (float) r.nextDouble()* 2 * range - range;
            b[i] = (float) r.nextDouble() * 2 * range - range;
            c[i] = (float) r.nextDouble() * 2 * range - range;
        }
        a[n]=(float) r.nextDouble()* 2 * range - range;
        for (int i = 1; i < n+1; i++) 
        {
          p[i]=(float) r.nextDouble()* 2 * range - range;
          q[i]=(float) r.nextDouble()* 2 * range - range;
        }
        p[k]=a[k];
        p[k-1]=c[k-1];
        p[k+1]=b[k];    
        
        q[k+1]=a[k+1];
        q[k]=c[k];
        q[k+2]=b[k+1];
       /* Arrays.fill(a, 2);
        a[1]=1;a[n]=1;
        Arrays.fill(b, 1);
        Arrays.fill(c, 1);
        Arrays.fill(p, 1);
        Arrays.fill(q, 1);
        q[k+1]=2;
        p[k]=2;   */ 
    }    
    static void generateXInRange(float range)
    {  
        Random r = new Random();    
        for (int i = 1; i < x.length; i++)         
            x[i] = (float) r.nextDouble()* 2 * range - range;
        
        
    }
    static void generateRVector()
    {
         for (int i = 2; i < n; i++) 
         {
             r[i]=b[i]*x[n-i]+
                  a[i]*x[n-i+1]+
                  c[i-1]*x[n-i+2];
         }
         r[k]=0;
         r[k+1]=0;
         for (int i = 1; i < n+1; i++) 
         {
           r[k]+=p[i]*x[n-i+1];
           r[k+1]+=q[i]*x[n-i+1];
         }
         r[1]= b[1]*x[n-1]+a[1]*x[n];
         r[n]=a[n]*x[1]+c[n-1]*x[2];
    }
    static float getAvg(float[] solution) // Вычисление погрешности
    {
        float res = 0;
        for (int i = 1; i < n+1; i++)
        {
                if (x[i] != 0)
                        res = Math.max(res, Math.abs(solution[i] - x[i]) / x[i]);
                else
                        res = Math.max(res, Math.abs(solution[i] - x[i]));
        }
        return res;
    }
}

