
package mongodboperation;

import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import org.bson.Document;

public class MongoDBInserter extends Thread 
{
    
     public ArrayList data;
     public ArrayList attribute;
     public MongoCollection<Document> collection;
     public long time=0;
     public int threadnumber;
     
     public void setThreadNumber(int x)
     {
         threadnumber=x;
     }
     
     
     public void setValues(ArrayList data, ArrayList attribute, MongoCollection<Document>collection)
     {
         this.attribute=attribute;
         this.collection=collection;
         this.data=data;
     }
     
     public void run()
     {
         long start=System.currentTimeMillis();
         
         Document d=null;
         
        
         
         for (int i = 0; i <data.size(); i++) 
         {
             d=new Document();
             ArrayList row=(ArrayList) data.get(i);
             for (int j = 0; j <row.size(); j++) 
             {
                 String attri=(String) attribute.get(j);
                 String value= (String) row.get(j);
                 d.append(attri, value);
             }
             collection.insertOne(d);
            //  System.out.println("Working Thread "+threadnumber);
         }
         
         long end=System.currentTimeMillis();
         time=end-start;
         System.out.println("Document inserted successfully "+time+" ms"+ " - "+data.size());  
         
     }
    
}
