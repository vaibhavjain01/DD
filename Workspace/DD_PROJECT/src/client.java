import java.rmi.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import drrs.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;

//import HelloApp.Hello;
//import HelloApp.HelloHelper;

import org.omg.CORBA.*;

public class client
{
	static drrsCorba drrsImpl;
	
	public static void main(String args[])
	{
		try
		{
			ORB orb = ORB.init(args, null);
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			/* Find Servers */
			String name = "FE";
			drrsImpl = drrsCorbaHelper.narrow(ncRef.resolve_str(name));
			
			System.out.println("Obtained a handle on server object: " + drrsImpl);
			String a[] = {"12:30-14:30", "15:30-17:30", "18:30-20:30", "21:30-23:30"};
			String b[] = {"10:00-11:00"};
			String c[] = {"11:01-11:30"};
			String d[] = {"08:55-09:55"};
			String aaa[] = {"12:30-14:30", "15:30-17:30", "18:30-20:30", "21:30-23:30"};
			StringHolder outputRt = new StringHolder();
			IntHolder rt = new IntHolder(99);
			
			drrsImpl.createRoom("DVLA1213", 109, "23-10-1990", a, rt);
			if(rt.value == 0) { System.out.println("Room Created"); }
			else { System.out.println("Room not Created"); }
			Thread.sleep(3000);
			
			drrsImpl.createRoom("DVLA1213", 109, "23-10-1990", b, rt);
			if(rt.value == 0) { System.out.println("Room Created"); }
			else { System.out.println("Room not Created"); }
			Thread.sleep(3000);
			
			drrsImpl.createRoom("DVLA1213", 109, "23-10-1990", c, rt);
			if(rt.value == 0) { System.out.println("Room Created"); }
			else { System.out.println("Room not Created"); }
			Thread.sleep(3000);
			
			drrsImpl.createRoom("DVLA1213", 109, "23-10-1990", d, rt);
			if(rt.value == 0) { System.out.println("Room Created"); }
			else { System.out.println("Room not Created"); }
			Thread.sleep(3000);
			
			drrsImpl.getAvailableTimeSlot("DVLS1234", "23-10-1990", outputRt);
			System.out.println(outputRt.value);
			Thread.sleep(3000);
			/*
			drrsImpl.deleteRoom("DVLA1213", 109, "23-10-1990", aaa, rt);
			if(rt.value == 0) { System.out.println("Room Deleted"); }
			else { System.out.println("Room not deleted"); }
			Thread.sleep(3000);
			
			drrsImpl.getAvailableTimeSlot("DVLS1234", "23-10-1990", outputRt);
			System.out.println(outputRt.value);
			Thread.sleep(3000);
			
			drrsImpl.createRoom("DVLA1213", 109, "23-10-1990", a, rt);
			if(rt.value == 0) { System.out.println("Room Created"); }
			else { System.out.println("Room not Created"); }
			Thread.sleep(3000);
			
			drrsImpl.getAvailableTimeSlot("DVLS1234", "23-10-1990", outputRt);
			System.out.println(outputRt.value);
			Thread.sleep(3000);
			*/
			drrsImpl.bookRoom("DVLS1234", 109, "23-10-1990", "12:30-14:30", outputRt, "DVL");
			System.out.println("Booking: " + outputRt.value);
			String bookingId = outputRt.value;
			Thread.sleep(3000);
			
			drrsImpl.getAvailableTimeSlot("DVLS1234", "23-10-1990", outputRt);
			System.out.println(outputRt.value);
			Thread.sleep(3000);
			
			drrsImpl.cancelBooking("DVLS1234", "DVLS1234_23-10-1990_109_12:30-14:30_DVL", rt);
			if(rt.value == -1) { System.out.println("Booking Cancellation Failed"); }
			else { System.out.println("Booking Cancelled"); }
			Thread.sleep(3000);
			
			drrsImpl.getAvailableTimeSlot("DVLS1234", "23-10-1990", outputRt);
			System.out.println(outputRt.value);
			Thread.sleep(3000);
			
			drrsImpl.bookRoom("DVLS1234", 109, "23-10-1990", "12:30-14:30", outputRt, "DVL");
			System.out.println("Booking: " + outputRt.value);
			bookingId = outputRt.value;
			Thread.sleep(3000);
			
			drrsImpl.getAvailableTimeSlot("DVLS1234", "23-10-1990", outputRt);
			System.out.println(outputRt.value);
			Thread.sleep(3000);
			
			StringHolder changeResRt = new StringHolder();
			drrsImpl.changeReservation("DVLS1234", "DVLS1234_23-10-1990_109_12:30-14:30_DVL", 
					"DVL", 109, "23-10-1990", "15:30-17:30", changeResRt);
			System.out.println("Change Result: Booking DVLS1234_23-10-1990_109_12:30-14:30_DVL changed to " + changeResRt.value);
			
			drrsImpl.getAvailableTimeSlot("DVLS1234", "23-10-1990", outputRt);
			System.out.println(outputRt.value);
			Thread.sleep(3000);
			/*
			drrsImpl.bookRoom("DVLS1234", 109, "23-10-1990", "12:30-14:30", outputRt, "DVL");
			System.out.println("Booking: " + outputRt.value);
			bookingId = outputRt.value;
			Thread.sleep(3000);
			
			drrsImpl.getAvailableTimeSlot("DVLS1234", "23-10-1990", outputRt);
			System.out.println(outputRt.value);
			Thread.sleep(3000);
			
			drrsImpl.bookRoom("DVLS1234", 109, "23-10-1990", "15:30-17:30", outputRt, "DVL");
			System.out.println("Booking: " + outputRt.value);
			bookingId = outputRt.value;
			Thread.sleep(3000);
			*/
		} 
		catch (Exception e) 
		{
			System.out.println("ERROR : " + e) ;
			e.printStackTrace(System.out);
		}

	}
}
