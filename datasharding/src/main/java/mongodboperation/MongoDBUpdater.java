package mongodboperation;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import java.util.ArrayList;



public class MongoDBUpdater {
    
    
    public boolean getupdate(String collecname, ArrayList prev, String id, ArrayList att, int x)
    {
        collecname=collecname.trim();
        id=id.trim();
        System.out.println("Inside Updater ");
        System.out.println("collecname "+collecname);
        System.out.println("prev "+prev);
        System.out.println("id "+id);
        System.out.println("att "+att);
        boolean flag=false;
        
        try
        {
            
        
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("datasharding");
        DBCollection userCollection = db.getCollection(collecname);
   
            String str2 = (String) att.get(2);
            String value = (String) prev.get(x);
            
//             System.out.println("Attribute Name: "+str2);
//             System.out.println("Original Value: "+value);
//        

            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("sr_no", id);

            BasicDBObject updateQuery = new BasicDBObject();
            updateQuery.put("$set", new BasicDBObject().append(str2, value));

            userCollection.update(searchQuery, updateQuery);

           
         mongoClient.close();
        flag=true;
        }
        
         catch(Exception e)
        {
            e.printStackTrace(); 
            flag=false;
        }
        
        System.out.println("Flag: "+flag);
        return flag;
    }
}
