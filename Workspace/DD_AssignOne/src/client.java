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
		    // create and initialize the ORB
			ORB orb = ORB.init(args, null);
			// get the root naming context
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// Use NamingContextExt instead of NamingContext. This is part of the Interoperable naming        	Service.  
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			
			/* Find Servers */
			String name = "FE";
			drrsImpl = drrsCorbaHelper.narrow(ncRef.resolve_str(name));
			
			System.out.println("Obtained a handle on server object: " + drrsImpl);
			//System.out.println(drrsImpl.sayHello("VAIBHAV"));
			String a[] = {"12:30 - 14:30", "15:30 - 17:30", "18:30 - 20:30", "21:30 - 23:30"};
			
			StringHolder outputRt = new StringHolder();
			IntHolder rt = new IntHolder(99);
			
			drrsImpl.createRoom("DVLA1213", 109, "23-10-1990", a, rt);
			if(rt.value == 0)
			{
				System.out.println("Room Created");
			}
			else
			{
				System.out.println("Room not Created");
			}
			
			drrsImpl.deleteRoom("DVLA1213", 109, "23-10-1990", a, rt);
			if(rt.value == 0)
			{
				System.out.println("Room Deleted");
			}
			else
			{
				System.out.println("Room not deleted");
			}
			
			drrsImpl.getAvailableTimeSlot("DVLS1234", "23-10-1990", outputRt);
			System.out.println(outputRt.value);
			
			drrsImpl.bookRoom("DVLS1234", 109, "23-10-1990", "12:30 - 14:30", outputRt, "KKL");
			System.out.println("Booking: " + outputRt.value);
			String bookingId = outputRt.value;
			Thread.sleep(3000);
			
			drrsImpl.cancelBooking("DVLS1234", "DVLS1234_23-10-1990_109_12:30 - 14:30_KKL", rt);
			if(rt.value == -1)
			{
				System.out.println("Booking Cancellation Failed");
			}
			else
			{
				System.out.println("Booking Cancelled");
			}
			Thread.sleep(3000);
		
			StringHolder changeResRt = new StringHolder();
			
			drrsImpl.changeReservation("DVLS1234", "DVLS1234_23-10-1990_109_15:30 - 17:30_KKL", "DVL", 109, "23-10-1990", "15:30 - 17:30", changeResRt);
			/*--------------------------------------------*/
			
			drrsImpl.shutdown();
		} 
		catch (Exception e) 
		{
			System.out.println("ERROR : " + e) ;
			e.printStackTrace(System.out);
		}

	}
}
