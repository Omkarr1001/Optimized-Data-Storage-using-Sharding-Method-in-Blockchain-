
package linearclustering;
import java.util.ArrayList;

public class LinearClustering

{
    
    public ArrayList getLinearClusters(ArrayList data, ArrayList indexs)
    {
        ArrayList lclusters=new ArrayList();
        
        for (int i = 0; i < indexs.size(); i++) 
        {
            ArrayList index=(ArrayList) indexs.get(i);
            int min=(int) index.get(0);
            int max=(int) index.get(1);
            
//            System.out.println("min: "+min);
//            System.out.println("max: "+max);
            ArrayList temp=new ArrayList();
            
            int k=1;
            for (int j = 0; j <data.size(); j++) 
            {
                int x=i+1;
                String str="s"+Integer.toString(x)+"_";
                if(j>=min&&j<=max)
                {
                    str=str+Integer.toString(k);
                    ArrayList row=(ArrayList) data.get(j);
                    row.add(str);
                    temp.add(row);
                     k++;
                   
                }
               
            }
            
            lclusters.add(temp);
        }
        
        
        return lclusters;
    }
    
}
