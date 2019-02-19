package simpledb.planner;

import simpledb.query.Plan;
import simpledb.query.ProductPlan;
import simpledb.query.Scan;
import simpledb.query.TablePlan;
import simpledb.record.TableInfo;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleDB.init("studentdb");
		Transaction tx= new Transaction();
		
		TableInfo ti= SimpleDB.mdMgr().getTableInfo("student", tx);
		Plan p = new TablePlan("student", tx);
		Plan p2= new ProductPlan(p, p);
		Scan s=p2.open();
		int count =0;
		while(s.next()){
			System.out.println(s.getInt("sid"));
			count++;
		}
		System.out.println(count);
	}

}
