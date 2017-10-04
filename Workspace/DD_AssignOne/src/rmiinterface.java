import java.rmi.*;
import java.util.List;

public interface rmiinterface extends Remote{
	public String getMsg() throws RemoteException;
	
	/* Admin */
	public Integer createRoom(String adminId, Integer roomNumber, String date, 
			List<String> listOfTimeSlots) throws RemoteException;
	public Integer deleteRoom(String adminId, Integer roomNumber, String date, 
			List<String> listOfTimeSlots) throws RemoteException;
	
	/* Student */
	public Integer bookRoom(String studentId, Integer roomNumber, String date, 
			String timeSlot) throws RemoteException;
	public String getAvailableTimeSlot(String studentId, String date) throws RemoteException;
	public Integer cancelBooking(String studentId, String bookingID) throws RemoteException;
}
