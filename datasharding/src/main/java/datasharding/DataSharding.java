
package datasharding;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mycompany.datasharding.ExcelAttributeReader;
import com.mycompany.datasharding.MainFrame;
import java.io.File;
import java.util.ArrayList;
import keygeneration.KeyGen;
import mongodboperation.MongoDBConnection;
import mongodboperation.MongoDBInserter;
import mongodboperation.MongoInserter;
import mongodboperation.Mongoinfo;
import org.bson.Document;

public class DataSharding 
{
    public void insertShardedData(ArrayList shards, String path, int datasize)
    {
        File f=new File(path);
        String collectioname=f.getName();
        collectioname=collectioname.substring(0, collectioname.lastIndexOf("."));
       //  System.out.println("Collection Name: "+collectioname);
       
        
        System.out.println("");
        System.out.println("**************Data Attributes Name************************");
        ArrayList dataattributesname=new ExcelAttributeReader().getAttributesName(path);
        dataattributesname.forEach(name->System.out.println(name));
        
        Mongoinfo.datacollectionname=collectioname;
        Mongoinfo.dataattributename=dataattributesname;
        
        
        MongoDatabase  database= new MongoDBConnection().getConnection("admin", "datasharding", "1234");
        MongoCollection<Document> collection1 = database.getCollection(collectioname);
        
       
        String blockcollectionname= collectioname+"_"+"blockchaininfo";
        MongoCollection<Document> collection2 = database.getCollection(blockcollectionname);
        
        ArrayList blockinfoattributes=new ArrayList();
        blockinfoattributes.add("shard_name");
        blockinfoattributes.add("shard_terminalkey");
        
        
        Mongoinfo.dbname="datasharding";
        Mongoinfo.blockkeycollectionname=blockcollectionname;
        Mongoinfo.blockattributename=blockinfoattributes;
        
        
        
     //   System.out.println("Collection: "+collection1);
        
         ArrayList q_thread=new ArrayList();
         
        for (int i = 0; i <shards.size(); i++) 
        {
            ArrayList temblockKey=new ArrayList();
            ArrayList single=(ArrayList) shards.get(i);
            int n=i+1;
            String str="s"+Integer.toString(n);
            
            for (int j = 0; j <single.size(); j++) 
            {
                if(j==0)
                {
                    String blockinfo="";
                    ArrayList row = (ArrayList) single.get(j);
                    for (int k = 0; k < row.size(); k++)
                    {
                        String rowvalue = (String) row.get(k);
                        blockinfo = blockinfo + rowvalue;
                    }
                    blockinfo=blockinfo.trim();
                    String blockkey= new KeyGen().getKey(blockinfo);
                    temblockKey.add(blockkey);

                }
                else
                {
                    String blockinfo="";
                    ArrayList row = (ArrayList) single.get(j);
                    for (int k = 0; k < row.size(); k++)
                    {
                        String rowvalue = (String) row.get(k);
                        blockinfo = blockinfo + rowvalue;
                    }
                    blockinfo=blockinfo.trim();
                    blockinfo=blockinfo+temblockKey.get(temblockKey.size()-1);
                    String blockkey= new KeyGen().getKey(blockinfo);
                    temblockKey.add(blockkey);
                    
                }
                
            }
            
                      
            ArrayList blocktableinfo=new ArrayList();
            blocktableinfo.add(str);
            blocktableinfo.add(temblockKey.get(temblockKey.size()-1));
            
         //   System.out.println("tempblockinfo: "+templockinfo);
            System.out.println("blockinfoattributes: "+blockinfoattributes);
            MongoInserter m=new MongoInserter();
            m.insertList(blocktableinfo, blockinfoattributes, collection2);
            
            MongoDBInserter mi=new MongoDBInserter();
            mi.setValues(single, dataattributesname, collection1);
            mi.setThreadNumber(i);
            mi.start();
            q_thread.add(mi);
          //  System.out.println("Blocktableinfo: "+blocktableinfo);
        
            
        }
        
        int x=q_thread.size();
     
      while(true)
        {
            int count=0;
          
            for(int i=0;i<q_thread.size();i++)
            {
               MongoDBInserter dt= (MongoDBInserter)q_thread.get(i);
               // System.out.println("dt: "+dt);
               if(!dt.isAlive())
               {
                   count++;
               }
                   
            }
            if(x==count)
            {
                 break;
            }
           
        }
      
      MongoDBInserter dt1= (MongoDBInserter)q_thread.get(0);
              long smallt=dt1.time;
     // long smallt=0;
      for(int i=0;i<q_thread.size();i++)
            {
              MongoDBInserter dt= (MongoDBInserter)q_thread.get(i);
              long t=dt.time;
//                System.out.println("total time is "+t);
              if(t<smallt)
                  smallt=t;
                   
            }
      ArrayList res=new ArrayList();
      res.add(Integer.toString(datasize));
      res.add(Long.toString(smallt));
     
      System.out.println("");
      System.out.println("\n\n TOTAL TIME FOR THE DATA INSERTING USING DATA SHARDING PROCESS IS "+smallt+": ms");
      MainFrame.jTextField3.setText(Long.toString(smallt));
        
        
        
    }
    
    
}
