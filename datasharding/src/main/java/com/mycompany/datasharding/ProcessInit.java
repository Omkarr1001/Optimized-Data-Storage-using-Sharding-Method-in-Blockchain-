package com.mycompany.datasharding;
import datasharding.DataSharding;
import java.util.ArrayList;
import linearclustering.LinearClustering;
import mongodboperation.DropingMongoDBDatabase;



public class ProcessInit implements ShardInterface {
    
    public void initProcess(String path, int shardsnum)
    {
       
        ArrayList data=new ExcelReader().getExcelData(path);
        displayOriginalData(data);
        
        ArrayList indexs=new IndexGeneration().getIndex(data.size(), shardsnum);
        displayIndex(indexs);
        
        ArrayList shards=new LinearClustering().getLinearClusters(data, indexs);
        displayLinearClusters(shards);
        
        new DropingMongoDBDatabase().dropMongoDBDatabase();
        new DataSharding().insertShardedData(shards, MainFrame.filepath, data.size());
            
        
    }

    @Override
    public void displayOriginalData(ArrayList data) {
        System.out.println("");
        System.out.println("**************Original Dataset**************************");    
        System.out.println("");
      //  data.forEach(row->System.out.println(row));
        for (int i = 0; i <data.size(); i++) 
        {
            ArrayList row=(ArrayList) data.get(i);
            System.out.println(row);
        }
    }

    @Override
    public void displayIndex(ArrayList index) {
        System.out.println("");
        System.out.println("**************Index**************************");    
        System.out.println("");
        index.forEach(row->System.out.println(row));
    }

    @Override
    public void displayLinearClusters(ArrayList shards) {
        System.out.println("");
        System.out.println("*****************Shards**************************");    
        System.out.println("");
        for (int i = 0; i <shards.size(); i++) 
        {
            ArrayList single =(ArrayList) shards.get(i);
            int x=i+1;
            System.out.println("");
            System.out.println("Shard Number : "+x);
            System.out.println("");
            single.forEach(row->System.out.println(row));
        }
    }

    
}
