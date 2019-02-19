package simpledb.file;

import simpledb.server.SimpleDB;

public class FileTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	    SimpleDB.initFileMgr("studentdb");
	    
	    /* reading/ writing/ appending from junk.tbl file */
		Block blk= new Block ("junk.tbl",0); // Block (fileName, logical block number)
		Task2 p1 = new Task2();
		p1.read(blk);
		int n = p1.getInt(1); 
		p1.setInt(1, n+1); // increment value by 1 at offset 1;
		p1.write(blk);
		System.out.println("Value in offset 1 is:   "+p1.getInt(1)); // print value in that offset
		
		
		Task2 p2= new Task2();
		p2.setString(2,"hello"); // setString(offset, String) at offset 2s
		blk = p2.append("junk.tbl"); //return reference to the newly created block.
		
		
		Task2 p3= new Task2();
		p3.read(blk);
		String s= p3.getString(2); // get String at offset 2
		System.out.println("Block "+ blk.number() + " contains "+ s);
		
		
		System.out.println("\n\nSet Name and grad Year in Student Table ");
		
		/*Operations from student.tbl file */
		
		Task2 p4= new Task2();
		Block blk2 = new Block ("student.tbl",0);
		System.out.println(blk2.number());
		
		// setting sName and gradYear
		p4.setInt( 8,2012);
		p4.setString(16, "tim");
		
		System.out.println("Name:  "+ p4.getString(16));
		System.out.println("Graduation Year:  "+ p4.getInt(8));
		
		

		

	}

}
