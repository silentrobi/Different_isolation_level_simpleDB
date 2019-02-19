package simpledb.record;

import simpledb.file.Block;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;

public class TestRecordPage {

	public static void main(String[] args){
		
		SimpleDB.init("studentdb");
		Transaction tx = new Transaction();
		TableInfo ti = SimpleDB.mdMgr().getTableInfo("student", tx);
		String fileName= ti.fileName();
		Block blk= new Block (fileName, 0); // accessing record of block 0;
		RecordPage  rp= new RecordPage(blk, ti, tx);
		/* iterate through the records*/	
		
		System.out.println("READING STUDENT TABLE RECORD OF A BLOCK ");
		
		rp.goToLastSlot(); // set pointer to the last slot of the block
		while(rp.previous()){
			int sId= rp.getInt("sid"); 	//SId, SName, MajorId, GradYear
			String sName= rp.getString("sname");
			int majorId= rp.getInt("majorid");
			int gradYr= rp.getInt("gradyear");
			System.out.println( "SId: "+sId + "  SName: " + sName+"  MajorId: "+ majorId+ "  GradYear: "+ gradYr);
		}
		
		
		
	}
	
	
}
