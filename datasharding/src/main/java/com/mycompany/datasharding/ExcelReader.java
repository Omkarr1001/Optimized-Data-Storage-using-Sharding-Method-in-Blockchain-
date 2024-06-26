package com.mycompany.datasharding;


import java.io.*;
import java.util.ArrayList;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
 /* Import declarations to the JExcel JAR file *//* Import declarations to the JExcel JAR file */
public class ExcelReader /* The name of our class file */
{

    public ArrayList getExcelData(String path)
    {
        ArrayList data=new ArrayList();

      try
        {
            Workbook ReadExcel = Workbook.getWorkbook(new File(path));
            Sheet sheet = ReadExcel.getSheet(0);
            int a=sheet.getColumns();
            int b=sheet.getRows();
//            System.out.println("Number of collumns "+Integer.toString(a));
//            System.out.println("Number of rows " +Integer.toString(b));
           
            for (int i=1;i<b;i++)
            {
                ArrayList in=new ArrayList();
                for(int j=0;j<a;j++)
                {

                  //  System.out.println("a is "+a);

                Cell a1 = sheet.getCell(j,i); /* Get the first cell of Column A , 0 maps to A */

                String ed = a1.getContents();
                ed=ed.trim();
                ed=ed.toLowerCase();

              //  System.out.print(ed+" ");
                   // if(!ed.isEmpty())
                in.add(ed);
                }
                data.add(in);
                //System.out.println("");
            }
              ReadExcel.close();
            }
        catch (Exception i)
        {
            System.out.println("Exception in ExcelReader Class in getExcelData():  "+i);
        }

        return data;
        
    }
}