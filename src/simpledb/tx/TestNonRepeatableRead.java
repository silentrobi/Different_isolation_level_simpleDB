package simpledb.tx;

import java.sql.Connection;

import simpledb.file.Block;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;


class TestC implements Runnable{
	public void run(){
		try{
			Transaction tx= new Transaction(); // 1: read uncommitted
			Block blk1= new Block("dept2.tbl", 0);
			
			tx.pin(blk1);
			
			Thread.sleep(1000);
			tx.setInt(blk1, 0, 90); 
			Thread.sleep(1000);
			tx.commit();
			
		}catch(Exception e){}
		
	}
	
}

class TestD implements Runnable{
	public void run(){
		try{
			Transaction tx= new Transaction();
			Block blk1= new Block("dept2.tbl", 0);
			
			tx.pin(blk1);
			
			tx.getInt(blk1, 0);
			Thread.sleep(1300);
			tx.getInt(blk1, 0);
			
			tx.commit();
			
		}catch(Exception e){}
		
	}
	
}




public class TestNonRepeatableRead {
	public static void main(String[] args) throws InterruptedException {
		SimpleDB.init("studentdb");
		Transaction.setIsolationLevel(Connection.TRANSACTION_READ_UNCOMMITTED);
		TestC t1=new TestC(); new Thread(t1).start();
		TestD t2=new TestD(); new Thread(t2).start();
	
	
		
	}
}
