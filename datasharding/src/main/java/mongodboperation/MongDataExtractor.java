package mongodboperation;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.util.ArrayList;



public class MongDataExtractor {
    
    public ArrayList getBlockchaininfo(String collectionname, ArrayList attribute)
    {
         MongoClient mongo = new MongoClient( "localhost" , 27017 );
         DB database = mongo.getDB(Mongoinfo.dbname);
         DBCollection  collection = database.getCollection(collectionname);
         DBCursor cursor = collection.find();
         
                
         ArrayList blockinfodata=new ArrayList();
                  
         while(cursor.hasNext())
         {
            DBObject obj = (DBObject) cursor.next();  
            ArrayList temp=new ArrayList();
            for (int i = 0; i <attribute.size(); i++) 
            {
                String name=(String) attribute.get(i);
                String value=(String) obj.get(name);
                temp.add(value);
            }  
            
            blockinfodata.add(temp);
             
         }
        
        return blockinfodata;
    }
    
}
