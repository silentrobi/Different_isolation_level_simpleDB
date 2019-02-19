package simpledb.record;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import static java.sql.Types.*;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;

public class TestRecordFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleDB.init("studentdb");
		
		
		
		while(true){
		
		Transaction tx = new Transaction();
		
		System.out.println("Please Enter Database Name or write quit to exit:");
		String dbName=null;
		
		try{
			Scanner sc1= new Scanner(System.in);
			if(sc1.hasNextLine()){
				dbName= sc1.nextLine().toLowerCase();
			}
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		if (dbName.equals("quit")){
			System.out.println("Exit is done !");
			break;
		}
		
		TableInfo ti = SimpleDB.mdMgr().getTableInfo(dbName, tx); //getting existing table
		
		RecordFile rf= new RecordFile(ti, tx);
		Collection<String> fields= ti.schema().fields();
		ArrayList<String> fieldList= new ArrayList<String>(); // field list
		
		for (int i =0 ;i < fields.size();i++){
			fieldList.add((String) fields.toArray()[i]);
		}
		
		
		System.out.println("\nReading STUDENT TABLE file reocords\n");
		
		
		rf.afterLast(); // point to the last block of the file
		while(rf.previous()){ // reverse order
			
			for (int i=0; i<fields.size();i++){
				
				if(ti.schema().type(fieldList.get(i))==INTEGER){ // checking type of each field   
					
					System.out.print(fieldList.get(i)+ ": " + rf.getInt(fieldList.get(i))  );
				}
				if(ti.schema().type(fieldList.get(i))==VARCHAR){
					
					System.out.print( fieldList.get(i)+ ": " + rf.getString(fieldList.get(i))  );
				}
				
				System.out.print("  ");
			}
			System.out.println();
					
		}
		
		tx.commit();
		
		}
		
		
		
	}

}
