import java.util.ArrayList;

public class LWP_Ferrer {
	
	public static void execute(ArrayList<Process_Ferrer> list, int timeQuantum) {
		double length = list.size();
		ArrayList<Process_Ferrer> inputList = sortByID(list, length);
		
		System.out.println("Lottery Scheduling:");
		System.out.println("Time Quantum: " + timeQuantum);
		System.out.println("Table 1: Given Processes");
		System.out.println("ID\tAT\tBT\tTN");
		for(int i = 0; i < length; i++){
			System.out.print(inputList.get(i).getId() + "\t");
			System.out.print(inputList.get(i).getArrivalTime() + "\t");
			System.out.print(inputList.get(i).getBurstTime() + "\t");
			System.out.println(inputList.get(i).getNumberOfTickets());
		}
		
		int[] initialBurstTime = new int[(int) length];
		for(int i = 0; i < length; i++){
			initialBurstTime[i] = inputList.get(i).getBurstTime();
		}
		
		ArrayList<Process_Ferrer> sortedList = sortByAT(inputList, length),
				processedList = new ArrayList<Process_Ferrer>(),
				outputList = new ArrayList<Process_Ferrer>(),
				finalOutputList = new ArrayList<Process_Ferrer>();
		
		int currentTime = sortedList.get(0).getArrivalTime(),
				currentTimeSlice = 0,
				currentProcess = -1,
				endedProcess = -1,
				stoppedProcess = -1;
		
		int startingTime = sortedList.get(0).getArrivalTime();
		int[] numberOfTickets;
		
		do{
			System.out.println("\nTime = " + currentTime + ".." + (currentTime + 1) +",");
			if(endedProcess != -1){
				System.out.println("Burst Time of Process " + endedProcess + " is equal to 0. Thus, it has ended.");
				endedProcess = -1;
			}
			
			if(stoppedProcess != -1){
				System.out.println("Time Slice = " + timeQuantum + "; thus, Process " + stoppedProcess + " is temporarily stopped.");
				stoppedProcess = -1;
			}
			
			while(sortedList.size() > 0){
				if(sortedList.get(0).getArrivalTime() == currentTime){
					System.out.println("Process " + sortedList.get(0).getId() + " arrives.");
					processedList.add(sortedList.get(0));
					sortedList.remove(0);
				}else{
					break;
				}
			}
			
			if(processedList.size() > 0){
				System.out.println("Time Slice = " + currentTimeSlice + ".." + (currentTimeSlice + 1));
				if(currentProcess == -1 || currentTimeSlice == 0){
					numberOfTickets = new int[processedList.size()];
					for(int i = 0; i < numberOfTickets.length; i++){
						numberOfTickets[i] = processedList.get(i).getNumberOfTickets();
					}
					currentProcess = chooseRandomProcess(numberOfTickets);
					System.out.println("Process " + processedList.get(currentProcess).getId() + " is randomly chosen.");
				}
				System.out.println("Process " + processedList.get(currentProcess).getId() + " is executed.");
				
				for(int i = 0; i < processedList.size(); i++){
					if(i != currentProcess){
						System.out.println("Process " + processedList.get(i).getId() + " waits.");
						processedList.get(i).setWaitingTime(processedList.get(i).getWaitingTime() + 1);
					}
				}
				processedList.get(currentProcess).setBurstTime(processedList.get(currentProcess).getBurstTime() - 1);
				if(processedList.get(currentProcess).getBurstTime() == 0){
					endedProcess = processedList.get(currentProcess).getId();
					outputList.add(processedList.get(currentProcess));
					processedList.remove(currentProcess);
					currentProcess = -1;
					currentTimeSlice = 0;
				}else{
					currentTimeSlice = (currentTimeSlice + 1) % timeQuantum;
					if(currentTimeSlice == 0)
						stoppedProcess = processedList.get(currentProcess).getId();
				}
			}else{
				System.out.println("Nothing happens.");
			}
			
			currentTime++;
		}while(processedList.size() > 0 || sortedList.size() > 0);
		if(endedProcess != -1){
			System.out.println("\nTime = " + currentTime + ",");
			System.out.println("Time Slice = " + currentTimeSlice + "");
			System.out.println("Burst Time of Process " + endedProcess + " is equal to 0. Thus, it has ended.");
		}
		
		finalOutputList = sortByID(outputList, length);
		double averageWaitingTime = 0, averageTurnaroundTime = 0;
		for(int i = 0; i < length; i++){
			finalOutputList.get(i).setBurstTime(initialBurstTime[i]);
			finalOutputList.get(i).setTurnaroundTime(finalOutputList.get(i).getBurstTime() + 
					finalOutputList.get(i).getWaitingTime());
			finalOutputList.get(i).setEndTime(finalOutputList.get(i).getArrivalTime() + 
					finalOutputList.get(i).getTurnaroundTime());
			averageWaitingTime += finalOutputList.get(i).getWaitingTime();
			averageTurnaroundTime += finalOutputList.get(i).getTurnaroundTime();
		}
		
		System.out.println("\nTable 2: Computation of Other Time Details");
		System.out.println("ID\tAT\tBT\tTN\tWT\tTT\tET");
		for(int i = 0; i < length; i++){
			System.out.print(finalOutputList.get(i).getId() + "\t");
			System.out.print(finalOutputList.get(i).getArrivalTime() + "\t");
			System.out.print(finalOutputList.get(i).getBurstTime() + "\t");
			System.out.print(finalOutputList.get(i).getNumberOfTickets() + "\t");
			System.out.print(finalOutputList.get(i).getWaitingTime() + "\t");
			System.out.print(finalOutputList.get(i).getTurnaroundTime() + "\t");
			System.out.println(finalOutputList.get(i).getEndTime() + "\t");
		}
		System.out.println();
		System.out.print("Number of Processes: " + length + "\t");
		System.out.println("Total Time: " + (currentTime - startingTime));
		double throughput = length / (currentTime - startingTime);
		System.out.println("Throughput: " + length + "/" + (currentTime - startingTime) + " = " + throughput);
		System.out.println("Average Waiting Time: " + averageWaitingTime + "/" + length + " = " + (averageWaitingTime / length));
		System.out.println("Average Turnaround Time: " + averageTurnaroundTime + "/" + length + " = " + (averageTurnaroundTime / length));
	}
	
	public static int chooseRandomProcess(int[] numberOfTickets){
		int process = 0;
		int totalTickets = 0;
		for(int i = 0; i < numberOfTickets.length; i++){
			totalTickets += numberOfTickets[i];
		}
		
		int randomTicket = (int) Math.round((Math.random() * (totalTickets - 1)) + 1);
		for(int i = 0; i < numberOfTickets.length; i++){
			if(randomTicket <= numberOfTickets[i]){
				process = i;
				break;
			}else{
				randomTicket -= numberOfTickets[i];
			}
		}
		
		return process;
	}
	
	public static ArrayList<Process_Ferrer> sortByAT(ArrayList<Process_Ferrer> inputList, double length) {
		ArrayList<Process_Ferrer> sortedList = new ArrayList<Process_Ferrer>();
		int minIndex, minArrivalTime;
		
		for(int i = 0; i < length; i++){
			minIndex = 0;
			minArrivalTime = inputList.get(0).getArrivalTime();
			for(int j = 1; j < inputList.size(); j++){
				if(minArrivalTime > inputList.get(j).getArrivalTime()){
					minIndex = j;
					minArrivalTime = inputList.get(j).getArrivalTime();
				}
			}
			sortedList.add(inputList.get(minIndex));
			inputList.remove(minIndex);
		}
		
		return sortedList;
	}
	
	public static ArrayList<Process_Ferrer> sortByID(ArrayList<Process_Ferrer> inputList, double length) {
		ArrayList<Process_Ferrer> sortedList = new ArrayList<Process_Ferrer>();
		int minIndex, minId;
		
		for(int i = 0; i < length; i++){
			minIndex = 0;
			minId = inputList.get(0).getId();
			for(int j = 1; j < inputList.size(); j++){
				if(minId > inputList.get(j).getId()){
					minIndex = j;
					minId = inputList.get(j).getId();
				}
			}
			sortedList.add(inputList.get(minIndex));
			inputList.remove(minIndex);
		}
		return sortedList;
	}
}
