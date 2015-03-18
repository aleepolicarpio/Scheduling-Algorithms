import java.util.ArrayList;


public class SRTF_Policarpio {

	public static void execute(ArrayList<Process_Policarpio> proc){
		proc = sortList(proc);
		ArrayList<Process_Policarpio> list = (ArrayList<Process_Policarpio>) proc.clone();
		int time = 0;
		int endTime = 0;
		int job = 0;
		for(time = 0; !list.isEmpty();time++){
			job = 0;
			for(int i = 0; i < list.size();i++){
				if(list.get(i).getArrivalTime()<=time&&
						list.get(i).getRemainingTime()<list.get(job).getRemainingTime()&&
						list.get(i).getRemainingTime()>0){
	                job=i;
	            }
			}
			list.get(job).subtractTime();
			
			if(list.get(job).getRemainingTime()==0){
				
				endTime = time + 1;
						
				if(endTime<list.get(job).getArrivalTime()){
					endTime = list.get(job).getArrivalTime()+list.get(job).getBurstTime();
				}
				System.out.println(list.get(job).getId()+"\t"+endTime);
				list.get(job).setEndTime(endTime);
				list.remove(job);
			}
		}
		
		
		
		for(int i=0;i<proc.size();i++){
			proc.get(i).setWaitingTime(proc.get(i).getEndTime()-proc.get(i).getBurstTime()-proc.get(i).getArrivalTime());
			proc.get(i).setTurnaroundTime(proc.get(i).getBurstTime()+proc.get(i).getWaitingTime());
		}
		
		proc = sortListByID(proc);
		printProcess(proc);
		printAverages(proc);
	}
	private static ArrayList<Process_Policarpio> sortList(ArrayList<Process_Policarpio> proc){		
		for(int i = 0; i<proc.size();i++){
			for(int j = 0; j<proc.size();j++){
				if(proc.get(i).getArrivalTime()<proc.get(j).getArrivalTime()){
					Process_Policarpio process = proc.get(i);
					proc.set(i, proc.get(j));
					proc.set(j, process);
				}
			}
		}		
		return proc;		
	}
	
	private static ArrayList<Process_Policarpio> sortListByID(ArrayList<Process_Policarpio> proc){		
		for(int i = 0; i<proc.size();i++){
			for(int j = 0; j<proc.size();j++){
				if(proc.get(i).getId()<proc.get(j).getId()){
					Process_Policarpio process = proc.get(i);
					proc.set(i, proc.get(j));
					proc.set(j, process);
				}
			}
		}		
		return proc;		
	}
	
	private static int getEndTime(ArrayList<Process_Policarpio> proc){	
		int endTime = proc.get(0).getEndTime();
		for(int i = 1; i<proc.size();i++){
			if(proc.get(i).getEndTime()>endTime)
				endTime = proc.get(i).getEndTime();
		}		
		return endTime;		
	}
	private static void printProcess(ArrayList<Process_Policarpio> proc){

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
	private static void printAverages(ArrayList<Process_Policarpio> proc){
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
