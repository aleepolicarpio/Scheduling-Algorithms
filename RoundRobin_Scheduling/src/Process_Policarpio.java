
public class Process_Policarpio extends Process{

	private int remainingTime;
	private boolean onQueue=false;
	
	public Process_Policarpio(int id, int arrivalTime, int burstTime) {
		super(id, arrivalTime, burstTime);
		remainingTime = burstTime;
	}
	public int getRemainingTime() {
		return remainingTime;
	}
	public boolean isDone(){
		return remainingTime<=0;
	}
	public void subtractTime(){
		remainingTime-=1;
	}
	public boolean isOnQueue() {
		return onQueue;
	}
	public void setOnQueue(boolean onQueue) {
		this.onQueue = onQueue;
	}


}
