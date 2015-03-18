import java.util.ArrayList;



public class FCFS_Policarpio {
	
	public static void execute(ArrayList<Process> proc){
		proc = sortList(proc);
		proc = getWaitingTime(proc);
		proc = getTurnaroundTime(proc);
		proc = getEndTime(proc);
		printProcess(proc);
		printAverages(proc);
		
	}
	
	private static ArrayList<Process> sortList(ArrayList<Process> proc){		
		for(int i = 0; i<proc.size();i++){
			for(int j = 0; j<proc.size();j++){
				if(proc.get(i).getArrivalTime()<proc.get(j).getArrivalTime()){
					Process process = proc.get(i);
					proc.set(i, proc.get(j));
					proc.set(j, process);
				}
			}
		}
		
		return proc;
		
	}

	private static void printProcess(ArrayList<Process> proc){

		System.out.println("-------------------------------------------");
		System.out.println("ID\tAT\tBT\tWT\tTT\tET");
		System.out.println("-------------------------------------------");
		for(int i = 0; i<proc.size();i++){
			Process process = proc.get(i);
			System.out.println(process.getId()+"\t"+process.getArrivalTime()
					+"\t"+process.getBurstTime()
					+"\t"+process.getWaitingTime()
					+"\t"+process.getTurnaroundTime()
					+"\t"+process.getEndTime());
		}
		System.out.println("-------------------------------------------");
	}
	private static ArrayList<Process> getWaitingTime(ArrayList<Process> proc){
		proc.get(0).setWaitingTime(0);
		for(int i = 1; i<proc.size();i++){
			int wtime = proc.get(i-1).getBurstTime() - (proc.get(i).getArrivalTime()-proc.get(i-1).getArrivalTime()) + proc.get(i-1).getWaitingTime();
			proc.get(i).setWaitingTime(wtime);
		}
		return proc;
	}
	private static ArrayList<Process> getTurnaroundTime(ArrayList<Process> proc){
		for(int i = 0; i<proc.size();i++){
			int ttime = proc.get(i).getBurstTime() + proc.get(i).getWaitingTime();
			proc.get(i).setTurnaroundTime(ttime);
		}
		return proc;
	}
	private static ArrayList<Process> getEndTime(ArrayList<Process> proc){
		for(int i = 0; i<proc.size();i++){
			int etime = proc.get(i).getArrivalTime()+proc.get(i).getTurnaroundTime();
			proc.get(i).setEndTime(etime);
		}
		return proc;
	}
	private static void printAverages(ArrayList<Process> proc){
		double wtime = 0;
		double ttime = 0;
		for(int i = 0; i<proc.size();i++){
			wtime += proc.get(i).getWaitingTime();
			ttime += proc.get(i).getTurnaroundTime();
		}
		double averageWT = wtime/proc.size();
		double averageTT = ttime/proc.size();
		System.out.println("Average WT = " + averageWT);
		System.out.println("Average TT = " + averageTT);
		System.out.print("Throughput = "+ proc.size() +"/"+
							proc.get(proc.size()-1).getEndTime() + " = ");
		double size = proc.size();
		System.out.println(size/proc.get(proc.size()-1).getEndTime());
	}
	
}
