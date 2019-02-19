package simpledb.tx.recovery;

import simpledb.file.Block;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;


class TestA implements Runnable{
	public void run(){
		try{
			Transaction tx= new Transaction();
			Block blk1= new Block("junk.tbl", 1);
			Block blk2= new Block("junk.tbl", 2);
			tx.pin(blk1);
			tx.pin(blk2);
			Thread.sleep(2500);
			tx.setInt(blk1, 0, 5);
			tx.setString(blk2, 0, "hi");
			tx.commit();
			
		}catch(Exception e){}
		
	}
	
}

class TestB implements Runnable{
	public void run(){
		try{
			Transaction tx= new Transaction();
			Block blk1= new Block("junk.tbl", 4);
			Block blk2= new Block("junk.tbl", 20);
			tx.pin(blk1);
			tx.pin(blk2);
			tx.setInt(blk1, 0, 5);
			Thread.sleep(1000);
			tx.setString(blk2, 0, "hi");
			tx.commit();
			
		}catch(Exception e){}
		
	}
	
}


class TestC implements Runnable{
	public void run(){
		try{
			Transaction tx= new Transaction();
			Block blk1= new Block("junk.tbl", 5);
			Block blk2= new Block("junk.tbl", 10);
			tx.pin(blk1);
			tx.pin(blk2);
			tx.setInt(blk1, 0, 5);
			tx.setString(blk2, 0, "hi");
			tx.commit();
			
		}catch(Exception e){}
		
	}
	
}

class TestD implements Runnable{
	public void run(){
		try{
			Transaction tx= new Transaction();
			Block blk1= new Block("junk.tbl", 1);
			Block blk2= new Block("junk.tbl", 2);
			tx.pin(blk1);
			tx.pin(blk2);
			tx.setInt(blk1, 0, 5);
			Thread.sleep(1500);
			tx.setString(blk2, 0, "hi");
			tx.commit();
			
		}catch(Exception e){System.out.println(e);}
		
	}
	
}



public class TestReovery {
	public static void main(String[] args) throws InterruptedException {
		SimpleDB.init("sampledb");
	TestA t1=new TestA(); new Thread(t1).start();
	TestB t2=new TestB(); new Thread(t2).start();
	TestC t3=new TestC(); new Thread(t3).start();
	TestD t4=new TestD(); new Thread(t4).start();
	
		
	}
}


