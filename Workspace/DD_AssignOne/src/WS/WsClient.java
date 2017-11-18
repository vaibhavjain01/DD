package WS;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.omg.CORBA.IntHolder;
import org.omg.CORBA.StringHolder;

public class WsClient {
	public static void main(String[] args) {
		try {
			URL dvl = new URL("http://localhost/dvl?wsdl");
			URL kkl = new URL("http://localhost/kkl?wsdl");
			URL wst = new URL("http://localhost/wst?wsdl");
			QName qName = new QName("http://WS/", "rmirpcService");
			
			Service serviceDVL = Service.create(dvl, qName);
			wsInter sDVL = serviceDVL.getPort(wsInter.class);
			
			Service serviceKKL = Service.create(kkl, qName);
			wsInter sKKL = serviceKKL.getPort(wsInter.class);
			
			Service serviceWST = Service.create(wst, qName);
			wsInter sWST = serviceWST.getPort(wsInter.class);
			
			String RT = null;
			Integer rt = 99;
			String bookingId;
			String a[] = {"12:30 - 14:30", "15:30 - 17:30", "18:30 - 20:30", "21:30 - 23:30"};
			
			/********** EMPTY AVAILABILITY CHECK ************/
			RT = sKKL.getAvailableTimeSlot("KKLS1234", "23-10-1990");
			System.out.println(RT);
			Thread.sleep(5000);
			/********** EMPTY AVAILABILITY CHECK ************/
			
			
			/**** CREATE ROOM AND DELETE ROOM CHECK ********/
			
			rt = sDVL.createRoom("DVLA1213", 109, "23-10-1990", a);
			if(rt == 0) { System.out.println("Room Created"); }
			else { System.out.println("Room not Created"); }

			rt = sKKL.createRoom("KKLA1213", 109, "23-10-1990", a);
			if(rt == 0) { System.out.println("Room Created"); }
			else { System.out.println("Room not Created"); }
			
			rt = sWST.createRoom("WSTA1213", 109, "23-10-1990", a);
			if(rt == 0) { System.out.println("Room Created"); }
			else { System.out.println("Room not Created"); }
			Thread.sleep(2000);
			
			RT = sKKL.getAvailableTimeSlot("KKLS1234", "23-10-1990");
			System.out.println(RT);
			Thread.sleep(1000);
			
			rt = sWST.deleteRoom("WSTA1213", 109, "23-10-1990", a);
			if(rt == 0) { System.out.println("Room Deleted"); }
			else { System.out.println("Room not deleted"); }
			Thread.sleep(1000);
			
			RT = sKKL.getAvailableTimeSlot("KKLS1234", "23-10-1990");
			System.out.println(RT);
			Thread.sleep(1000);
			
			rt = sWST.createRoom("WSTA1213", 109, "23-10-1990", a);
			if(rt == 0) { System.out.println("Room Created"); }
			else { System.out.println("Room not Created"); }
			Thread.sleep(1000);
			
			RT = sKKL.getAvailableTimeSlot("KKLS1234", "23-10-1990");
			System.out.println(RT);
			Thread.sleep(1000);
			
			/**** CREATE ROOM AND DELETE ROOM CHECK ********/
			
			
			/************* CROSS SERVER BOOK ROOM AND MAX LIMIT CHECK ****************/
			RT = sDVL.bookRoom("DVLS1234", 109, "23-10-1990", "12:30 - 14:30", "KKL");
			System.out.println("Booking: " + RT);
			bookingId = RT;
			Thread.sleep(3000);
			
			RT = sDVL.bookRoom("DVLS1234", 109, "23-10-1990", "15:30 - 17:30", "KKL");
			System.out.println("Booking: " + RT);
			bookingId = RT;
			Thread.sleep(3000);
			
			RT = sKKL.getAvailableTimeSlot("KKLS1234", "23-10-1990");
			System.out.println(RT);
			Thread.sleep(2000);
			
			RT = sDVL.bookRoom("DVLS1234", 109, "23-10-1990", "18:30 - 20:30", "KKL");
			System.out.println("Booking: " + RT);
			bookingId = RT;
			Thread.sleep(3000);
			
			RT = sKKL.getAvailableTimeSlot("KKLS1234", "23-10-1990");
			System.out.println(RT);
			Thread.sleep(2000);
			
			RT = sDVL.bookRoom("DVLS1234", 109, "23-10-1990", "21:30 - 23:30", "KKL");
			System.out.println("Booking: " + RT);
			bookingId = RT;
			Thread.sleep(3000);
			
			RT = sKKL.getAvailableTimeSlot("KKLS1234", "23-10-1990");
			System.out.println(RT);
			Thread.sleep(2000);
			/************* BOOK ROOM AND MAX LIMIT CHECK ****************/
			
			
			/************** CANCEL ROOM CHECK - CANCEL REQUEST FOR ROOM BOOKED ON OTHER SERVER ****************/
			rt = sDVL.cancelBooking("DVLS1234", "DVLS1234_23-10-1990_109_12:30 - 14:30_KKL");
			if(rt == -1) { System.out.println("Booking Cancellation Failed"); }
			else { System.out.println("Booking Cancelled"); }
			Thread.sleep(3000);
		
			RT = sDVL.getAvailableTimeSlot("DVLS1234", "23-10-1990");
			System.out.println(RT);
			Thread.sleep(2000);
			/************** CANCEL ROOM CHECK - CANCEL REQUEST FOR ROOM BOOKED ON OTHER SERVER ****************/
			
			
			/******** BOOK ROOM AFTER CANCEL AT MAX BOOKINGS to check ********************/
			RT = sDVL.bookRoom("DVLS1235", 109, "23-10-1990", "12:30 - 14:30", "DVL");
			System.out.println("Booking: " + RT);
			bookingId = RT;
			Thread.sleep(3000);
		
			RT = sDVL.getAvailableTimeSlot("DVLS1234", "23-10-1990");
			System.out.println(RT);
			Thread.sleep(2000);
			/******** BOOK ROOM AFTER CANCEL AT MAX BOOKINGS to check ********************/
			
			
			/************ CHANGE RESERVATION ************/
			/* Testing change reservation multi client */
			String changeResRt = new String();
			String bookingTrialNew = new String();
			String bookingTrialOld = new String();
			
			bookingTrialOld = sKKL.bookRoom("KKLS1234", 109, "23-10-1990", "15:30 - 17:30", "KKL");
			System.out.println("Old Booking Trial RT: " + bookingTrialOld);
			
			//bookingTrialNew = sKKL.bookRoom("KKLS1234", 109, "23-10-1990", "15:30 - 17:30", "DVL");
			//System.out.println("New Booking Trial RT: " + bookingTrialNew);
			
			/** REAL CHECK **/
			changeResRt = sDVL.changeReservation("DVLS1234", "DVLS1234_23-10-1990_109_15:30 - 17:30_KKL", 
					"DVL", 109, "23-10-1990", "15:30 - 17:30");
			System.out.println("Booking Change Started for DVLS1234_23-10-1990_109_15:30 - 17:30_KKL");
			bookingTrialOld = sKKL.bookRoom("KKLS1234", 109, "23-10-1990", "15:30 - 17:30", "KKL");
			bookingTrialNew = sKKL.bookRoom("KKLS1234", 109, "23-10-1990", "15:30 - 17:30", "DVL");
			/** REAL CHECK **/
			
			Thread.sleep(5000);
			System.out.println("Change Booking RT: " + changeResRt);
			System.out.println("New Booking Trial RT: " + bookingTrialNew);
			System.out.println("Old Booking Trial RT: " + bookingTrialOld);
			
			bookingTrialOld = sKKL.bookRoom("KKLS1234", 109, "23-10-1990", "15:30 - 17:30", "KKL");
			System.out.println("Old Booking Trial RT: " + bookingTrialOld);
			/************ CHANGE RESERVATION ************/
			
			RT = sKKL.bookRoom("KKLS1234", 109, "23-10-1990", "12:30 - 14:30", "WST");
			System.out.println("Booking: " + RT);
			bookingId = RT;
			Thread.sleep(3000);
			
			RT = sDVL.getAvailableTimeSlot("DVLS1234", "23-10-1990");
			System.out.println(RT);
			
			RT = sKKL.getAvailableTimeSlot("KKLS1234", "23-10-1990");
			System.out.println(RT);
			
			RT = sWST.getAvailableTimeSlot("WSTS1234", "23-10-1990");
			System.out.println(RT);

		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
