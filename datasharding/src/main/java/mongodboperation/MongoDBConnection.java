package mongodboperation;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    
    public MongoDatabase getConnection(String username,String dbname,String password)
{
    
    // Creating a Mongo client 
      MongoClient mongo = new MongoClient( "localhost" , 27017 ); 
   
      // Creating Credentials 
      MongoCredential credential; 
      credential = MongoCredential.createCredential(username, dbname,password.toCharArray()); 
  //    System.out.println("");
     // System.out.println("Connected to the database successfully");  
      
      // Accessing the database 
      MongoDatabase database = mongo.getDatabase(dbname);
    //  System.out.println("");
     // System.out.println("Credentials ::"+ credential); 
      
      return database;
}
    
}
