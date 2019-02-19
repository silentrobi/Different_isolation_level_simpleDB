package simpledb.buffer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import simpledb.file.*;

/**
 * Manages the pinning and unpinning of buffers to blocks.
 * @author Edward Sciore
 *
 */
class ExerciseBasicBufferMgr {
   private ExerciseBuffer[] bufferpool;
   private int numAvailable;
   private ArrayList<ExerciseBuffer> unpinnedBufferList; 
   private Map<Block, ExerciseBuffer> bufferHashTable;  // keep track of allocated buffer blocks n bufferpool
   
   
   
   /**
    * Creates a buffer manager having the specified number 
    * of buffer slots.
    * This constructor depends on both the {@link FileMgr} and
    * {@link simpledb.log.LogMgr LogMgr} objects 
    * that it gets from the class
    * {@link simpledb.server.SimpleDB}.
    * Those objects are created during system initialization.
    * Thus this constructor cannot be called until 
    * {@link simpledb.server.SimpleDB#initFileAndLogMgr(String)} or
    * is called first.
    * @param numbuffs the number of buffer slots to allocate
    */
     ExerciseBasicBufferMgr(int numbuffs) {
      bufferpool = new ExerciseBuffer[numbuffs];
      
      bufferHashTable= new Hashtable<Block,ExerciseBuffer >();
      unpinnedBufferList= new ArrayList<ExerciseBuffer>();
      
      numAvailable = numbuffs;
      for (int i=0; i<numbuffs; i++){
         bufferpool[i] = new ExerciseBuffer(i);
         unpinnedBufferList.add(bufferpool[i]); // initially all buffers are unpinned
         }
       
         
   }
   
   /**
    * Flushes the dirty buffers modified by the specified transaction.
    * @param txnum the transaction's id number
    */
   synchronized void flushAll(int txnum) {
	   
      for (ExerciseBuffer buff : bufferpool)
         if (buff.isModifiedBy(txnum))
         buff.flush();
   }
   
   /**
    * Pins a buffer to the specified block. 
    * If there is already a buffer assigned to that block
    * then that buffer is used;  
    * otherwise, an unpinned buffer from the pool is chosen.
    * Returns a null value if there are no available buffers.
    * @param blk a reference to a disk block
    * @return the pinned buffer
    */
   synchronized ExerciseBuffer pin(Block blk) {
	   ExerciseBuffer buff = findExistingBuffer(blk);
      if (buff == null) {
         buff = chooseUnpinnedBuffer();
         if (buff == null)
            return null;
         buff.assignToBlock(blk);
         
        
      }
      if (!buff.isPinned()) numAvailable--;
      
      buff.pin(); //   
      
      if(bufferHashTable.containsValue(buff)){  //if value (buffer) is already exist then remove that old key (blk) 
          bufferHashTable.remove(blk);  
      
      }
      bufferHashTable.put(blk, buff);  // adding new key (blk) to HashTable
       
      
      return buff;
   }
   
   /**
    * Allocates a new block in the specified file, and
    * pins a buffer to it. 
    * Returns null (without allocating the block) if 
    * there are no available buffers.
    * @param filename the name of the file
    * @param fmtr a pageformatter object, used to format the new block
    * @return the pinned buffer
    */
   synchronized ExerciseBuffer pinNew(String filename, PageFormatter fmtr) {
	   ExerciseBuffer buff = chooseUnpinnedBuffer();
      if (buff == null)
         return null;
      Block blk=buff.assignToNew(filename, fmtr); // return the newly created block
      numAvailable--;
      buff.pin();
      
      if(bufferHashTable.containsValue(buff)){  //if value (buffer) is already exist then remove that key (blk) 
          bufferHashTable.remove(blk);  
      
      }
      bufferHashTable.put(blk, buff);  // adding new key (blk) to HashTable
       
      return buff;
   }
   
   /**
    * Unpins the specified buffer.
    * @param buff the buffer to be unpinned
    */
   synchronized void unpin(ExerciseBuffer buff) {
      buff.unpin();
      if (!buff.isPinned() && (unpinnedBufferList.size() < bufferpool.length)){ // unpinnedBufferList length should not exceed the bufferpool length 
         numAvailable++;
         unpinnedBufferList.add(buff);   // adding to the tail
         
      }
   }
   
   /**
    * Returns the number of available (i.e. unpinned) buffers.
    * @return the number of available buffers
    */
   int available() {
      return numAvailable;
   }
   
   private ExerciseBuffer findExistingBuffer(Block blk) {
	   ExerciseBuffer buff= bufferHashTable.get(blk); // return Buffer object which holding the blk 
	                                                  // return null if not found 
	   return buff;   
	   
     
   }
   
   private ExerciseBuffer chooseUnpinnedBuffer() {
      ExerciseBuffer buff=null;
	   if (!unpinnedBufferList.isEmpty()) {
    	  buff= unpinnedBufferList.get(0);
    	  unpinnedBufferList.remove(0); // removing the head of the list
      }
         
      return buff;
     
   } 
   public String toString(){ // print buffers info
		String buffers="";
	    for (int i=0; i< bufferpool.length;i++){
	    	buffers += bufferpool[i].toString(); 
	    }
	    return buffers;
	}

}
