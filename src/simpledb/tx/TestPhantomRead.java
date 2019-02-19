package simpledb.tx;

import java.sql.Connection;

import simpledb.file.Block;
import simpledb.record.RecordPage;
import simpledb.record.TableInfo;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;


class TestE implements Runnable{
	public void run(){
		try{
			
			SimpleDB.init("studentdb");
			Transaction tx = new Transaction();
			TableInfo ti = SimpleDB.mdMgr().getTableInfo("dept", tx);
			String fileName= ti.fileName();
			Block blk= new Block (fileName,7); // accessing record of block 0;
			RecordPage  rp= new RecordPage(blk, ti, tx);
			/* iterate through the records*/	
			
			
			boolean f= false;
			tx.size(fileName);
			while(rp.next()){
				
				int dId= rp.getInt("did"); 	//SId, SName, MajorId, GradYear
				if(dId == 80){
					String dName= rp.getString("dname");
					
					System.out.println( "DId: "+dId + "  DName: " + dName);
					f= true;
					break;
				}
				
				
			}
			
			tx.commit();
			
			Thread.sleep(5000);
			Transaction tx1 = new Transaction();
			TableInfo ti1 = SimpleDB.mdMgr().getTableInfo("dept", tx1);
			String fileName1= ti1.fileName();
			tx.size(fileName);
			Block blk1= new Block (fileName1,7); // accessing record of block 0;
			RecordPage  rp2= new RecordPage(blk1, ti1, tx1);
			
			while(rp2.next()){
				
				int dId= rp2.getInt("did"); 	//SId, SName, MajorId, GradYear
				if(dId == 80){
					String dName= rp2.getString("dname");
					f= true;
					System.out.println( "DId: "+dId + "  DName: " + dName);
					break;
				}
				
				
				
			}
			tx1.commit();
			
			
			
		}catch(Exception e){}
		
	}
	
}

class TestF implements Runnable{
	public void run(){
		try{
			
			SimpleDB.init("studentdb");
			Transaction tx = new Transaction();
			TableInfo ti = SimpleDB.mdMgr().getTableInfo("dept", tx);
			String fileName= ti.fileName();
			Block blk= new Block (fileName, 7); // accessing record of block 0;
			RecordPage  rp= new RecordPage(blk, ti, tx);
			/* iterate through the records*/	
			
			
			rp.insert();
			rp.setInt("did", 80);//SId, SName, MajorId, GradYear
			rp.setString("dname", "Z");
			
			Thread.sleep(5000);
			tx.commit();
		
			
		}catch(Exception e){}
		
	}
	
}




public class TestPhantomRead {
	public static void main(String[] args) throws InterruptedException {
		SimpleDB.init("studentdb");
		Transaction.setIsolationLevel(Connection.TRANSACTION_READ_COMMITTED);
		
		TestF t2=new TestF(); new Thread(t2).start();
		TestE t1=new TestE(); new Thread(t1).start();
	
		
	}
}
