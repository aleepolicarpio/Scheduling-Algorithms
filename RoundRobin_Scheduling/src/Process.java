
public class Process
{
	private int id;
	private int arrivalTime;
	private int burstTime;
	private int waitingTime;
	private int turnaroundTime;
	private int endTime;
	
	public Process(int id, int arrivalTime, int burstTime)
	{
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id; 	
	}
	
	public int getArrivalTime(){
		return arrivalTime;
	}
	
	public void setArrivalTime(int arrivalTime){
		this.arrivalTime = arrivalTime;
	}
	
	public int getBurstTime(){
		return burstTime;
	}
	
	public void setBurstTime(int burstTime){
		this.burstTime = burstTime;
	}
	
	public int getWaitingTime(){
		return waitingTime;
	}
	
	public void setWaitingTime(int waitingTime){
		this.waitingTime = waitingTime;
	}
	
	public int getTurnaroundTime(){
		return turnaroundTime;
	}
	
	public void setTurnaroundTime(int turnaroundTime){
		this.turnaroundTime = turnaroundTime;
	}
	
	public int getEndTime(){
		return endTime;
	}
	
	public void setEndTime(int endTime){
		this.endTime = endTime;
	}
}
