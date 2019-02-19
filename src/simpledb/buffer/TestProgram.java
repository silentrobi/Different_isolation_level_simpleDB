package simpledb.buffer;

import simpledb.file.Block;
import simpledb.server.SimpleDB;

public class TestProgram {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        SimpleDB.initFileAndLogMgr("studentdb");
        ExerciseBufferMgr bm= new ExerciseBufferMgr(3); // 3 buffers in the bufferpool
        
        //test:  pin(10), pin(20), pin(30), unpin(20), unpin(10), pin(40), unpin (30), pin(60)
        
        /* BufferMgr class test */
        System.out.println("'ExerciseBufferMgr' class test:\n");
        
        ExerciseBuffer buff1=bm.pin(new Block("student.tbl",10)); //buffer 0 is pinned
        ExerciseBuffer buff2 =bm.pin(new Block("student.tbl",20)); // buffer 1 is pinned
        ExerciseBuffer buff3 =bm.pin(new Block("student.tbl",30)); // buffer 2 is pinned
        bm.unpin(buff2);   // buffer 1 is unpinned
        bm.unpin(buff1); // buffer 0 is unpinned
        
        bm.pin(new Block("student.tbl",40)); // buffer 1  is pinned
        
        bm.unpin(buff3); //buffer 2 is unpinned
        
        bm.pin(new Block("student.tbl",60)); // buffer 0 is pinned
        
        System.out.println( bm.toString());
        
        System.out.println("'ExerciseBasicBufferMgr' class test:\n");
        
        /*BasicBufferMgr class test*/
       
        ExerciseBasicBufferMgr basicBufferMgr= new ExerciseBasicBufferMgr(3); // 3 buffers in the bufferpool
        
        ExerciseBuffer buff01=basicBufferMgr.pin(new Block("student.tbl",10)); //buffer 0 is pinned
        ExerciseBuffer buff02 =basicBufferMgr.pin(new Block("student.tbl",20)); // buffer 1 is pinned
        ExerciseBuffer buff03 =basicBufferMgr.pin(new Block("student.tbl",30)); // buffer 2 is pinned
        basicBufferMgr.unpin(buff02);   // buffer 1 is unpinned
        basicBufferMgr.unpin(buff01); // buffer 0 is unpinned
        
        basicBufferMgr.pin(new Block("student.tbl",40)); // buffer 1  is pinned
        
        basicBufferMgr.unpin(buff03); //buffer 2 is unpinned
        
        basicBufferMgr.pin(new Block("student.tbl",60)); // buffer 0 is pinned
        
        System.out.println( basicBufferMgr.toString());
        
        
	}

}
