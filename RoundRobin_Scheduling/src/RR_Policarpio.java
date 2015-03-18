import java.util.ArrayList;


public class RR_Policarpio {
	static int time = 0;
	
	public static void execute(ArrayList<Process_Policarpio> list, int timeSlice){
		
		list = sortList(list);
		
		int quantum = 0;
		int processDone = 0;
		
		ArrayList<Process_Policarpio> q = new ArrayList<Process_Policarpio>();
		
		while(processDone!=list.size()){			
			for(int i = 0; i<list.size();i++){
				if(time==list.get(i).getArrivalTime()&&!list.get(i).isDone()&&!list.get(i).isOnQueue()){
					q.add(list.get(i));
					list.get(i).setOnQueue(true);
				}
			}

			if(q.isEmpty()){
					time++;
			}
			else{
				Process_Policarpio proc = q.remove(0);	
				for(int i=0; i<q.size();i++){
						q.get(i).setWaitingTime(q.get(i).getWaitingTime()+1);
				}
					time++;
					for(int i = 0; i<list.size();i++){
						if(time==list.get(i).getArrivalTime()&&!list.get(i).isDone()&&!list.get(i).isOnQueue()){
							q.add(list.get(i));
							list.get(i).setOnQueue(true);
						}
					}
					quantum++;
					proc.subtractTime();
					if(proc.getRemainingTime()==0){
						proc.setTurnaroundTime(proc.getBurstTime()+proc.getWaitingTime());
						proc.setEndTime(time);
						quantum=0;
						processDone++;
					}
					else if(quantum==timeSlice && proc.getRemainingTime()>0){
						quantum=0;
						q.add(proc);
					}
					else if(quantum<timeSlice && proc.getRemainingTime()>0){
						q.add(0,proc);
					}


				}

			
		}
		list = sortListByID(list); 
		printProcess(list);
		printAverages(list);
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
							time + " = ");
		double size = proc.size();
		System.out.println(size/proc.get(proc.size()-1).getEndTime());
	}
}
