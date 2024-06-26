
package mongodboperation;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;


public class DropingMongoDBDatabase {
    
    public void dropMongoDBDatabase()
    {
        //Creating a MongoDB client
      MongoClient mongo = new MongoClient( "localhost" , 27017 );
      MongoDatabase database = mongo.getDatabase("datasharding");
      database.drop();
    }
    
}
