import java.rmi.*;
import java.util.List;

public interface rmiinterface extends Remote{
	public String getMsg() throws RemoteException;
	
	/* Admin */
	public Integer createRoom(Integer roomNumber, String date, 
			List<String> listOfTimeSlots) throws RemoteException;
	public Integer deleteRoom(Integer roomNumber, String date, 
			List<String> listOfTimeSlots) throws RemoteException;
	
	/* Student */
	public Integer bookRoom(String campusName, Integer roomNumber, 
			String date, String timeSlot) throws RemoteException;
	public String getAvailableTimeSlot(String date) throws RemoteException;
	public Integer cancelBooking(String bookingID) throws RemoteException;
}
