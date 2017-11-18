package WS;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style=Style.RPC)
public interface wsInter {
	public Integer createRoom(String adminId, int roomNumber, String date, String[] inListOfTimeSlots);
	public Integer deleteRoom(String adminId, int roomNumber, String date, String[] inListOfTimeSlots);
	public String bookRoom(String studentId, int roomNumber, String date, String timeSlot, String campusName);
	public String getAvailableTimeSlot(String studentId, String date);
	public Integer cancelBooking(String studentId, String bookingID);
	public String changeReservation(String studentId, String bookingID, String newCampusName, 
			int newRoomNumber, String newDate, String newTimeSlot);
}
