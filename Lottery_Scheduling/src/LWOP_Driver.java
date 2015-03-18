import java.util.ArrayList;

public class LWOP_Driver {
	
	public static void main( String[] args)
    {
    	Process_Ferrer p1 = new Process_Ferrer(1,0,20,0,4);
    	Process_Ferrer p2 = new Process_Ferrer(2,5,15,0,2);
    	Process_Ferrer p3 = new Process_Ferrer(3,10,10,0,3);
    	Process_Ferrer p4 = new Process_Ferrer(4,50,5,0,1);
    	
    	ArrayList<Process_Ferrer> list= new ArrayList<Process_Ferrer>();
    	list.add(p1);
    	list.add(p2);
    	list.add(p3);
    	list.add(p4);
    	
    	LWOP_Ferrer.execute(list);
    }
}
