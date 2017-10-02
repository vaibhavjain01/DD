import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class rmirpc extends UnicastRemoteObject implements rmiinterface{
	public static final String MSG = "Hello World";
	private static final long serialVersionUID = 1L;
	
	
	public static Map<Integer, List<String>> TimeSlots = new HashMap<Integer, List<String>>(10,10);
	public static Map<String, Integer> RoomSlots = new HashMap<String, Integer>(10, 10);
	
	protected rmirpc() throws RemoteException {
		super(0);
	}
	
	/* Interface Message Implementation */
	public String getMsg() {
		return MSG;
	}
	
	/* Generic */
	Integer addTimeSlots(Integer roomNumber, List<String> listOfTimeSlots)
	{
		Integer rt = new Integer(0);
		// Take care of time slot intersection
		return rt;
	}
	
	Integer deleteTimeSlots(Integer roomNumber, List<String> listOfTimeSlots)
	{
		Integer rt = new Integer(0);
		// Delete time slots
		return rt;	
	}
	
	/* Admin */
	/* Function to create room
	 * Return
	 * 0 Success
	 * -1 Failure
	 * @see rmiinterface#createRoom(java.lang.Integer, java.lang.String, java.util.List)
	 */
	public Integer createRoom(Integer roomNumber, String date, List<String> listOfTimeSlots) 
	{
		Integer rt = new Integer(0);
		
		synchronized (this) {
			if(RoomSlots.containsKey(date))
			{
				if(TimeSlots.containsKey(roomNumber))
				{
					rt = addTimeSlots(roomNumber, listOfTimeSlots);
				}
				else
				{
					TimeSlots.put(roomNumber, listOfTimeSlots);
				}
			}
			else
			{
				RoomSlots.put(date, roomNumber);
				TimeSlots.put(roomNumber, listOfTimeSlots);
			}
		}
		
		return rt;
	}
	
	
	public Integer deleteRoom(Integer roomNumber, String date, List<String> listOfTimeSlots) 
	{
		Integer rt = new Integer(0);
		
		synchronized (this) {
			if(RoomSlots.containsKey(date))
			{
				if(TimeSlots.containsKey(roomNumber))
				{
					rt = deleteTimeSlots(roomNumber, listOfTimeSlots);
				}
				else
				{
					rt = -1;
				}
			}
			else
			{
				rt = -1;
			}
		}
		
		return rt;
	}
	
	/* Student */
	public Integer bookRoom(String campusName, Integer roomNumber, String date, String timeSlot) 
	{
		Integer rt = new Integer(0);
		return rt;
	}
	
	public String getAvailableTimeSlot(String date) 
	{
		String rt = new String("");
		return rt;
	}
	
	public Integer cancelBooking(String bookingID) 
	{
		Integer rt = new Integer(0);
		return rt;
	}
	
	public static void main(String args[]) throws Exception {

		
		/* RMI Port */
		try {
			
			// Exception handler for creating rmi registry
			Registry reg = LocateRegistry.createRegistry(1099); // registered port for rmi
			System.out.println("Rmi port registry created");
		}
		catch(RemoteException e) {
			//e.printStackTrace();
			System.out.println("Rmi registry already exists");
		}
		
		/* Dorval Campus */
		rmirpc rmiDvlObj = new rmirpc();
		Naming.rebind("//localhost/RmiDVLServer", rmiDvlObj);
		
		/* Kirkland Campus */
		rmirpc rmiKklObj = new rmirpc();
		Naming.rebind("//localhost/RmiKkLServer", rmiKklObj);
		
		/* Westmount */
		rmirpc rmiWstObj = new rmirpc();
		Naming.rebind("//localhost/RmiWstServer", rmiWstObj);
	
	}
}
