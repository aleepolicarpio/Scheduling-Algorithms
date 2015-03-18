import java.util.ArrayList;

public class PWOP_Policarpio {
	public static void execute(ArrayList<Process> proc){
		proc = sortList(proc);
		ArrayList<Process> newProc = new ArrayList<Process>();
		
		int endTime = proc.get(0).getArrivalTime();
		
		while(!proc.isEmpty()){
			int nextJob = 0;
			for(int i=0; i<proc.size();i++){
				if(proc.get(i).getArrivalTime()<=proc.get(nextJob).getArrivalTime()){
					if(proc.get(i).getPriorityNum()<proc.get(nextJob).getPriorityNum()){
						nextJob = i;
					}
				}
				else if(proc.get(i).getArrivalTime()<=endTime){
					if(proc.get(i).getPriorityNum()<proc.get(nextJob).getPriorityNum()){
						nextJob = i;
					}
				}
			}

			if(proc.get(nextJob).getArrivalTime()>endTime){
				endTime = proc.get(nextJob).getArrivalTime();
			}
			proc.get(nextJob).setWaitingTime(endTime-proc.get(nextJob).getArrivalTime());

			endTime += proc.get(nextJob).getBurstTime();
			proc.get(nextJob).setTurnaroundTime(proc.get(nextJob).getBurstTime() +
													proc.get(nextJob).getWaitingTime());
			proc.get(nextJob).setEndTime(endTime);
			
			newProc.add(proc.get(nextJob));	
			proc.remove(nextJob);
		}
		
		System.out.println("SORTED ACCORDING TO ID ID:");
		newProc = sortListByID(newProc);
		printProcess(newProc);
		
		printAverages(newProc);
		
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
	
	private static ArrayList<Process> sortListByID(ArrayList<Process> proc){		
		for(int i = 0; i<proc.size();i++){
			for(int j = 0; j<proc.size();j++){
				if(proc.get(i).getId()<proc.get(j).getId()){
					Process process = proc.get(i);
					proc.set(i, proc.get(j));
					proc.set(j, process);
				}
			}
		}		
		return proc;		
	}
	
	private static int getEndTime(ArrayList<Process> proc){	
		int endTime = proc.get(0).getEndTime();
		for(int i = 1; i<proc.size();i++){
			if(proc.get(i).getEndTime()>endTime)
				endTime = proc.get(i).getEndTime();
		}		
		return endTime;		
	}
	private static void printProcess(ArrayList<Process> proc){

		System.out.println("-------------------------------------------");
		System.out.println("ID\tAT\tBT\tP\tWT\tTT\tET");
		System.out.println("-------------------------------------------");
		for(int i = 0; i<proc.size();i++){
			Process process = proc.get(i);
			System.out.println(process.getId()+"\t"+process.getArrivalTime()
					+"\t"+process.getBurstTime()
					+"\t"+process.getPriorityNum()
					+"\t"+process.getWaitingTime()
					+"\t"+process.getTurnaroundTime()
					+"\t"+process.getEndTime());
		}
		System.out.println("-------------------------------------------");
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
							getEndTime(proc) + " = ");
		double size = proc.size();
		System.out.println(size/getEndTime(proc));
	}
	
}
