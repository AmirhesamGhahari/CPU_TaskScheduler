package CPU_TaskScheduler;

import java.util.LinkedList;

/**
 * test class for evaluating the outcome of the cpu task scheduling based on the
 * nonpreemptive shortest job first method. 
 * this method first create some random process object and then create a Nonpreemptive SJF object and add those processes
 * to this scheduler object and then calls the method to schedule the tasks.
 * then by printing out the proper and related outputs, it evaluates the correctness of scheduling method and
 * at the end it prints out the average turnarount time and average waiting time for all the processes fed to method, which are
 * scheduled and burst by this technique.
 */
public class Test_NSJF {

	public static void main(String[] args) {
		
		
		
		LinkedList<Long> l1 = new LinkedList<Long>();
		l1.add(8l);
		l1.add(4l);
		l1.add(12l);
		l1.add(20l);
		l1.add(3l);
		
		
		LinkedList<Long> l2 = new LinkedList<Long>();
		l2.add(43l);
		l2.add(2l);
		l2.add(11l);
		l2.add(9l);
		l2.add(5l);
		
		
		LinkedList<Long> l3 = new LinkedList<Long>();
		l3.add(4l);
		l3.add(4l);
		l3.add(12l);
		l3.add(49l);
		l3.add(30l);
		
		
		LinkedList<Long> l4 = new LinkedList<Long>();
		l4.add(201l);
		l4.add(30l);
		l4.add(12l);
		l4.add(2l);
		l4.add(3l);
		
		
		LinkedList<Long> l5 = new LinkedList<Long>();
		l5.add(90l);
		l5.add(4l);
		l5.add(19l);
		l5.add(2l);
		l5.add(3l);
		
		
		Nonpreemptive_SJF nsjf1 = new Nonpreemptive_SJF();
		nsjf1.addProcess(1, l1);
		nsjf1.addProcess(2, l2);
		nsjf1.addProcess(3, l3);
		nsjf1.addProcess(4, l4);
		nsjf1.addProcess(5, l5);
		
		
		long[] tr = nsjf1.processScheduling();
		LinkedList<Nonpreemptive_SJF.Process> res = nsjf1.getProcessedlist();
		
		System.out.println(res.size());
		System.out.println(res.get(0).computationSequence.size());
		System.out.println(res.get(0).priority);
		System.out.println(res.get(0).totalComputingTime);
		System.out.println(res.get(0).completionTime);
		System.out.println(res.get(0).waitingTime);
		System.out.println(res.get(0).stat);
		
		System.out.println("--------------------------------");
		System.out.println("--------------------------------");

		
		System.out.println(res.size());
		System.out.println(res.get(3).computationSequence.size());
		System.out.println(res.get(3).priority);
		System.out.println(res.get(3).totalComputingTime);
		System.out.println(res.get(3).completionTime);
		System.out.println(res.get(3).waitingTime);
		System.out.println(res.get(3).stat);
		
		System.out.println("--------------------------------");
		System.out.println("--------------------------------");

		
		System.out.println(res.size());
		System.out.println(res.get(4).computationSequence.size());
		System.out.println(res.get(4).priority);
		System.out.println(res.get(4).totalComputingTime);
		System.out.println(res.get(4).completionTime);
		System.out.println(res.get(4).waitingTime);
		System.out.println(res.get(4).stat);

		System.out.println("--------------------------------");
		System.out.println("--------------------------------");
		
		
		System.out.println("showing averages:");
		System.out.println(tr[0]);
		System.out.println(tr[1]);


		

	}

}
