package CPU_TaskScheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;



/**
 * 
 * Nonpreemptive first come first served scheduling class 
 * 
 */
public class Nonpreemptive_FCFS {
	
	
	/**
	 * linked list object responsible for holding the cpu task burst processes
	 */
	private LinkedList<Nonpreemptive_FCFS.Process> computationList;
	
	/**
	 * linked list object responsible for holding the I/O task burst processes
	 */
	private LinkedList<Nonpreemptive_FCFS.Process> IOList;

	/**
	 * linked list object responsible for holding the already processed and terminated processes
	 */
	private LinkedList<Nonpreemptive_FCFS.Process> processedProcesses;
	
	
	
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
	public Nonpreemptive_FCFS() {
		
		this.computationList =  new LinkedList<Nonpreemptive_FCFS.Process>();
		this.IOList = new LinkedList<Nonpreemptive_FCFS.Process>();
		this.processedProcesses = new LinkedList<Nonpreemptive_FCFS.Process>();
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
	public LinkedList<Nonpreemptive_FCFS.Process> getProcessedlist()
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
			
			Process p11 = this.computationList.getFirst();
			
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
					this.computationList.removeFirst();
					
					
					
				} else {
					p11.stat = Status.Waiting;
					this.IOList.add(p11);
					this.computationList.removeFirst();

					
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
						this.computationList.removeFirst();
						
						
						
					} else {
						p11.stat = Status.Waiting;
						this.IOList.add(p11);
						this.computationList.removeFirst();

						
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
