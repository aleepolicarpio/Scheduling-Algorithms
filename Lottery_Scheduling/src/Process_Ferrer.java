
public class Process_Ferrer extends Process{

	private int numberOfTickets;
	
	public Process_Ferrer(int id, int arrivalTime, int burstTime, int priorityNum, int numberOfTickets) {
		super(id, arrivalTime, burstTime, priorityNum);
		this.numberOfTickets = numberOfTickets;
	}
	
	public int getNumberOfTickets(){ return numberOfTickets; }
	public void setNumberOfTickets(int numberOfTickets){ this.numberOfTickets = numberOfTickets; }
}
