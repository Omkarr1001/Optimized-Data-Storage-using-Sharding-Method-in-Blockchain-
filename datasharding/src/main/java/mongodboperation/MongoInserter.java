
package mongodboperation;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import org.bson.Document;


public class MongoInserter
{
    public void insertList(ArrayList data, ArrayList attribute, MongoCollection<Document>collection)
    {
     
        Document d = new Document();;
        for (int i = 0; i < data.size(); i++)
        {
         
            String attri=(String)attribute.get(i);
            String value=(String)data.get(i);
            d.append(attri, value); 
       //     System.out.println("Document: "+d);  
        }
        
         collection.insertOne(d);
         
      System.out.println("Document inserted successfully");  
      

     
    }
}
