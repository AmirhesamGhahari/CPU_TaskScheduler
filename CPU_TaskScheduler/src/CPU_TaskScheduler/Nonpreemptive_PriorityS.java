package CPU_TaskScheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 
 * Nonpreemptive priority scheduling class 
 * 
 */
public class Nonpreemptive_PriorityS {
	
	/**
	 * linked list object responsible for holding the cpu task burst processes
	 */
	private ArrayList<Nonpreemptive_PriorityS.Process> computationList;
	
	/**
	 * linked list object responsible for holding the I/O task burst processes
	 */
	private LinkedList<Nonpreemptive_PriorityS.Process> IOList;

	/**
	 * linked list object responsible for holding the already processed and terminated processes
	 */
	private LinkedList<Nonpreemptive_PriorityS.Process> processedProcesses;
	
	
	/**
	 * private static enum status as a enum inside the main class to hold the status of each task process 
	 *
	 */
	private static enum Status{
		New,
		Running,
		Ready,
		Waiting,
		Terminated
	}
	
	/**
	 * public static class process as a class inside the main class which creates and initiate the variables of
	 * a cpu burst or I/O burst task process
	 *
	 */
	public static class Process{
		public int priority;
		public LinkedList<Long> computationSequence;
		public LocalDateTime arrivalTime;
		public Status stat;
		public long completionTime;
		public long waitingTime;
		public long totalComputingTime;
		
		/**
		 * @param p1 priority of the process
		 * @param cs linkedlist of the different computation times required to complete this process
		 */
		public Process(int p1, LinkedList<Long> cs)
		{
			this.priority = p1;
			this.computationSequence = cs;
			this.arrivalTime = LocalDateTime.now();
			this.stat = Status.New;
			this.completionTime = 0;
			this.waitingTime = 0;
			
			long sum = 0;
			for(long r1 : cs)
			{
				sum += r1;
			}
			this.totalComputingTime = sum;
		}
		
		
	}
	
	/**
	 * constructor of main class which initiate 3 used linked lists of this class object
	 */
	public Nonpreemptive_PriorityS() {
		
		this.computationList =  new ArrayList<Nonpreemptive_PriorityS.Process>();
		this.IOList = new LinkedList<Nonpreemptive_PriorityS.Process>();
		this.processedProcesses = new LinkedList<Nonpreemptive_PriorityS.Process>();
	}
	



	/**
	 * create a new process with the input arguments and then add it to the cpu burst linked list of the task scheduling class.
	 * @param p1 priority of the process
	 * @param cs linkedlist of the different computation times required to complete this process
	 */
	public void addProcess(int p1, LinkedList<Long> cs)
	{
		Process nP = new Process(p1, cs);
		this.computationList.add(nP);
	}
	
	/**
	 * 
	 * @param pr1 input process
	 * @return the linkedlist of the different computation times required to complete this process
	 */
	public static LinkedList<Long> getComputationSequence(Process pr1)
	{
		return pr1.computationSequence;
	}
	
	/**
	 * @param pr1 input process
	 * @return the priority of that process
	 */
	public static int getPriority(Process pr1) 
	{
		return pr1.priority;
	}
	
	/**
	 * @param pr1 input process
	 * @return the arrival time of that process
	 */
	public static LocalDateTime getArrivalTime(Process pr1) 
	{
		return pr1.arrivalTime;
	}
	
	/**
	 * @param pr1 input process
	 * @return the status of that process
	 */
	public static Status getstat(Process pr1) 
	{
		return pr1.stat;
	}
	
	/**
	 * @param pr1 input process
	 * @return the total duration that it has taken for that process to be completed.
	 */
	public static long getCompletedDuration(Process pr1)
	{
		return pr1.completionTime;
	}
	
	/**
	 * @param pr1 input process
	 * @return the total waiting duration that it process has waited till it gets completed.
	 */
	public static long getWaitingDuration(Process pr1)
	{
		return pr1.waitingTime;
	}
	
	
	
	/**
	 * @return the linked list of already completed and terminated processes for this task scheduler.
	 */
	public LinkedList<Nonpreemptive_PriorityS.Process> getProcessedlist()
	{
		return this.processedProcesses;		
		
	}
	
	
	
	/**
	 * the main method which is responsible for sorting the tasks of cpu computation burst based on the
	 * scheduling technique and run the cpu task burst and I/O task burst based one the sorted scheduling till all the
	 * processes are terminated and add the to the list of already processed tasks.
	 * this method calculates the total waitnig and turnaround time of each process when it is terminated and
	 * at the end it calculates the average turnaround and waiting time of processes scheduled and sorted based on this scheduling methods
	 * @return the average turnaround and waiting time of processes scheduled and sorted based on this scheduling methods
	 */
	public long[] processScheduling()
	{
		
		LocalDateTime startingTime = LocalDateTime.now();
		LocalDateTime currenTime = startingTime;
		this.processedProcesses.clear();
		
		for(Process p1 : this.computationList)
		{
			p1.stat = Status.Ready;
		}
		
		
		while(this.computationList.size() != 0 || this.IOList.size() != 0) 
		{
			if(this.computationList.size() != 0) 
			{
				long pless = this.computationList.get(0).priority;
				for(Process pr : this.computationList)
				{
					if(pr.priority < pless)
					{
						pless = pr.priority;
					}
				}
				int sz1 = this.computationList.size();
				int ind = -1;
				for(int j = sz1 - 1; j >= 0; j--)
				{
					if(this.computationList.get(j).priority == pless)
					{
						ind = j;
					}
				}
				
			
			Process p11 = this.computationList.get(ind);
			
			if(this.IOList.size() == 0)
			{
				p11.stat = Status.Running;
				long l1 = p11.computationSequence.getFirst();
				currenTime = currenTime.plusSeconds(l1);
				p11.computationSequence.removeFirst();
				
				if(p11.computationSequence.size() == 0)
				{
					p11.stat = Status.Terminated;
					p11.completionTime = ChronoUnit.SECONDS.between(startingTime, currenTime);
					p11.waitingTime = p11.completionTime - p11.totalComputingTime;
					this.processedProcesses.add(p11);
					this.computationList.remove(ind);
					
					
					
				} else {
					p11.stat = Status.Waiting;
					this.IOList.add(p11);
					this.computationList.remove(ind);

					
				} 
				
				
			}else {
				
				Process p22 = this.IOList.getFirst();
				
				if(p11.computationSequence.getFirst() < p22.computationSequence.getFirst())
				{
					p11.stat = Status.Running;
					long l1 = p11.computationSequence.getFirst();
					currenTime = currenTime.plusSeconds(l1);
					p11.computationSequence.removeFirst();
					
					if(p11.computationSequence.size() == 0)
					{
						p11.stat = Status.Terminated;
						p11.completionTime = ChronoUnit.SECONDS.between(startingTime, currenTime);
						p11.waitingTime = p11.completionTime - p11.totalComputingTime;
						this.processedProcesses.add(p11);
						this.computationList.remove(ind);
						
						
						
					} else {
						p11.stat = Status.Waiting;
						this.IOList.add(p11);
						this.computationList.remove(ind);

						
					} 
					
					long t22 = p22.computationSequence.getFirst();
					p22.computationSequence.removeFirst();
					t22 -= l1;
					p22.computationSequence.addFirst(t22);
					
				} else {
					
					long l1 = p22.computationSequence.getFirst();
					currenTime = currenTime.plusSeconds(l1);
					p22.stat = Status.Ready;
					p22.computationSequence.removeFirst();
					
					this.computationList.add(p22);
					this.IOList.removeFirst();
					
					p11.stat = Status.Running;
					long t11 = p11.computationSequence.getFirst();
					p11.computationSequence.removeFirst();
					t11 -= l1;
					p11.computationSequence.addFirst(t11);
					
					
					if(this.IOList.size() == 0)
					{
						long l11 = p11.computationSequence.getFirst();
						currenTime = currenTime.plusSeconds(l11);
						p11.computationSequence.removeFirst();
						
						if(p11.computationSequence.size() == 0)
						{
							p11.stat = Status.Terminated;
							p11.completionTime = ChronoUnit.SECONDS.between(startingTime, currenTime);
							p11.waitingTime = p11.completionTime - p11.totalComputingTime;
							this.processedProcesses.add(p11);
							this.computationList.remove(ind);
							
							
							
						} else {
							p11.stat = Status.Waiting;
							this.IOList.add(p11);
							this.computationList.remove(ind);

							
						} 
					
					
				} else {
					long l11 = p11.computationSequence.getFirst();
					int sz22 = this.IOList.size();
					int j22 = 0;
					long sum22 = 0;
					while(sum22 < l11)
					{
						if(j22 < sz22) { 
						sum22 += this.IOList.get(j22).computationSequence.getFirst();
						j22++;
						} else {
							sum22 = 100000000;
							j22++;
						}
					}
					int numOfElem = j22 - 1;
					for( int rw1 = 0; rw1 < numOfElem; rw1++)
					{
						Process p44 = this.IOList.getFirst();
						long f44 = p44.computationSequence.getFirst();
						currenTime = currenTime.plusSeconds(f44);
						p44.computationSequence.removeFirst();
						p44.stat = Status.Ready;
						this.IOList.removeFirst();
						this.computationList.add(p44);
						
						long f11 = p11.computationSequence.getFirst();
						f11 -= f44;
						p11.computationSequence.removeFirst();
						p11.computationSequence.addFirst(f11);
					
					}
					
					if(this.IOList.size() == 0)
					{
						p11.stat = Status.Running;
						long l111 = p11.computationSequence.getFirst();
						currenTime = currenTime.plusSeconds(l111);
						p11.computationSequence.removeFirst();
						
						if(p11.computationSequence.size() == 0)
						{
							p11.stat = Status.Terminated;
							p11.completionTime = ChronoUnit.SECONDS.between(startingTime, currenTime);
							p11.waitingTime = p11.completionTime - p11.totalComputingTime;
							this.processedProcesses.add(p11);
							this.computationList.remove(ind);
							
							
							
						} else {
							p11.stat = Status.Waiting;
							this.IOList.add(p11);
							this.computationList.remove(ind);

							
						} 
						
						
					} else {
						
						long l1111 = p11.computationSequence.getFirst();
						currenTime = currenTime.plusSeconds(l1111);
						p11.computationSequence.removeFirst();
						
						if(p11.computationSequence.size() == 0)
						{
							p11.stat = Status.Terminated;
							p11.completionTime = ChronoUnit.SECONDS.between(startingTime, currenTime);
							p11.waitingTime = p11.completionTime - p11.totalComputingTime;
							this.processedProcesses.add(p11);
							this.computationList.remove(ind);
							
							
							
						} else {
							p11.stat = Status.Waiting;
							this.IOList.add(p11);
							this.computationList.remove(ind);

							
						} 
						
						long t22 = p22.computationSequence.getFirst();
						p22.computationSequence.removeFirst();
						t22 -= l1;
						p22.computationSequence.addFirst(t22);
					}
					
					
					
				}
				
				
			}
			}
		
		} else
		{
			Process p22 = this.IOList.getFirst();
			long l1 = p22.computationSequence.getFirst();
			currenTime = currenTime.plusSeconds(l1);
			p22.computationSequence.removeFirst();
			
			p22.stat = Status.Ready;
			this.computationList.add(p22);
			this.IOList.removeFirst();
			
			
		}
		
	
		
	}
		
		int s1 = this.processedProcesses.size();
		long sumOfWaitings = 0;
		for(Process b1 : this.processedProcesses)
		{
			sumOfWaitings += b1.waitingTime;
		}
		long averageWaitingTime = sumOfWaitings / s1;
		
		long sumOfTurnaround = 0;
		for(Process b1 : this.processedProcesses)
		{
			sumOfTurnaround += b1.completionTime;
		}
		long averageTurnaroundTime = sumOfTurnaround / s1;
	
		long[] result = new long[2];
		result[0] = averageTurnaroundTime;
		result[1] = averageWaitingTime;
		return result;
	
	}
	
	

}
