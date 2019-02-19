package simpledb.tx;

import java.sql.Connection;

import simpledb.file.Block;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;


class TestA implements Runnable{
	public void run(){
		try{
			Transaction tx= new Transaction(); // read uncommitted
			
			Block blk1= new Block("dept2.tbl", 0);
			
			tx.pin(blk1);
			tx.getInt(blk1, 0);
			Thread.sleep(1000);
			tx.setInt(blk1, 0, 70); 
			Thread.sleep(2000);
			tx.commit();
			
		}catch(Exception e){}
		
	}
	
}

class TestB implements Runnable{
	public void run(){
		try{
			Transaction tx= new Transaction();
			Block blk1= new Block("dept2.tbl", 0);
			tx.pin(blk1);
			Thread.sleep(1500);
			tx.getInt(blk1, 0);
			
			tx.commit();
			
		}catch(Exception e){}
		
	}
	
}







public class TestDirtyRead {
	public static void main(String[] args) throws InterruptedException {
		SimpleDB.init("studentdb");
		Transaction.setIsolationLevel(Connection.TRANSACTION_READ_UNCOMMITTED);
		System.out.println(Transaction.getIsolationLevel());
		TestA t1=new TestA(); new Thread(t1).start();
		TestB t2=new TestB(); new Thread(t2).start();
	
	
		
	}
}


