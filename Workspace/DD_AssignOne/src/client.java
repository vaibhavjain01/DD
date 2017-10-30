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

/*
class adminClient extends Thread
{
	private String clientTypeID = null;
	private central cenRepoObj = null;
	private Vector<String> bookingId = new Vector<String>(10, 10);
	private Integer threaddelay = 100;
	private Integer operation;
	private Integer RoomNumber;
	private String Date;
	
	private List<String> tmptime = new ArrayList<>(Arrays.asList
			("01:05 - 02:10",
			"02:15 - 03:10",
			"03:15 - 04:10",
			"04:15 - 05-15"));
	private List<String> bookings = new ArrayList<>();

	public adminClient(String inId, central inCenRepoObj, Integer inopration, Integer inRoomNumber, String inDate) 
	{
		super();
		//System.out.printf("Client %s Started", inId);
		clientTypeID = inId;
		cenRepoObj = inCenRepoObj;
		operation = inopration;
		RoomNumber = inRoomNumber;
		Date = inDate;
		start();
	}
	
	public void run()
	{
		rmiinterface rpcObj = getRpcObj();

		if(operation == 0)
		{
			sendCreateRoom(rpcObj);
		}
		else if(operation == 1)
		{
			sendDeleteRoom(rpcObj);
		}
		else if(operation == 2)
		{
			try 
			{
				sendBookRoom(rpcObj);
				Thread.sleep(300);
				sendCheckAvailibility(rpcObj);
				Thread.sleep(300);
				sendCancelBooking(rpcObj);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(operation == 3)
		{
			sendCheckAvailibility(rpcObj);
		}
	}
	
	private String sendCheckAvailibility(rmiinterface rpcObj)
	{
		String rt = null;
		try {
			rt = (rpcObj.getAvailableTimeSlot(clientTypeID, "27-10-2017"));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rt;
	}
	
	private void sendCreateRoom(rmiinterface rpcObj)
	{
		try 
		{
			Integer rt = rpcObj.createRoom(clientTypeID, RoomNumber, Date, tmptime);
			Thread.sleep(1000);
		} 
		catch (RemoteException e) 
		{
			e.printStackTrace();
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendDeleteRoom(rmiinterface rpcObj)
	{
		try 
		{
			Integer rt = rpcObj.deleteRoom(clientTypeID, RoomNumber, Date, tmptime);
		} 
		catch (RemoteException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void sendBookRoom(rmiinterface rpcObj)
	{
		try 
		{
			for(int i = 0; i<tmptime.size(); i++)
			{
				String timeslot = tmptime.get(i);
				String rt = rpcObj.bookRoom(clientTypeID, RoomNumber, Date, timeslot);
				if(rt != null)
				{
					bookings.add(rt);
				}
			}
		} catch (RemoteException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void sendCancelBooking(rmiinterface rpcObj)
	{
		for(int i = 0; i<bookings.size(); i++)
		{
			try {
				Integer rt = rpcObj.cancelBooking(clientTypeID, bookings.get(i));
				bookings.remove(i);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private rmiinterface getRpcObj()
	{
		rmiinterface rpcObj = null;
		try
		{
			if(clientTypeID.contains("DVL"))
			{
				rpcObj = (rmiinterface)Naming.lookup(cenRepoObj.getDVLServer());
			}
			else if(clientTypeID.contains("KKL"))
			{
				rpcObj = (rmiinterface)Naming.lookup(cenRepoObj.getKKLServer());
			}
			else if(clientTypeID.contains("WST"))
			{
				rpcObj = (rmiinterface)Naming.lookup(cenRepoObj.getWSTServer());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return rpcObj;
	}
}
*/
public class client
{
	static drrsCorba drrsImpl;
	static drrsCorba drrsImplKKL;
	static drrsCorba drrsImplWST;
	
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
			String name = "DVL";
			drrsImpl = drrsCorbaHelper.narrow(ncRef.resolve_str(name));
			
			String nameKKL = "KKL";
			drrsImplKKL = drrsCorbaHelper.narrow(ncRef.resolve_str(nameKKL));
			
			String nameWST = "WST";
			drrsImplWST = drrsCorbaHelper.narrow(ncRef.resolve_str(nameWST));
			
			System.out.println("Obtained a handle on server object: " + drrsImpl);
			//System.out.println(drrsImpl.sayHello("VAIBHAV"));
			String a[] = {"12:30 - 14:30", "15:30 - 17:30"};
			
			StringHolder outputRt = new StringHolder();
			IntHolder rt = new IntHolder(99);
			
			drrsImpl.createRoom("DVLA1213", 109, "23-10-1990", a, rt);
			System.out.println(rt.value);
			drrsImplKKL.createRoom("KKLA1213", 109, "23-10-1990", a, rt);
			System.out.println(rt.value);
			
			
			drrsImpl.deleteRoom("DVLA1213", 109, "23-10-1990", a, rt);
			System.out.println(rt.value);
			
			drrsImpl.createRoom("DVLA1213", 109, "23-10-1990", a, rt);
			System.out.println(rt.value);

			drrsImpl.getAvailableTimeSlot("DVLS1234", "23-10-1990", outputRt);
			System.out.println(outputRt.value);
			
			drrsImpl.bookRoom("DVLS1234", 109, "23-10-1990", "12:30 - 14:30", outputRt, "KKL");
			System.out.println(outputRt.value);
			String bookingId = outputRt.value;
			Thread.sleep(3000);
			
			drrsImpl.getAvailableTimeSlot("DVLS1234", "23-10-1990", outputRt);
			System.out.println(outputRt.value);
			Thread.sleep(3000);
			
			drrsImpl.cancelBooking("DVLS1234", bookingId, rt);
			System.out.println(rt.value);
			Thread.sleep(3000);
		
			drrsImpl.getAvailableTimeSlot("DVLS1234", "23-10-1990", outputRt);
			System.out.println(outputRt.value);
			Thread.sleep(3000);
			
			drrsImpl.bookRoom("DVLS1234", 109, "23-10-1990", "12:30 - 14:30", outputRt, "DVL");
			System.out.println(outputRt.value);
			bookingId = outputRt.value;
			Thread.sleep(3000);
		
			drrsImpl.getAvailableTimeSlot("DVLS1234", "23-10-1990", outputRt);
			System.out.println(outputRt.value);
			Thread.sleep(3000);
			
			drrsImpl.changeReservation("DVLS1234", bookingId, "KKL", 109, "23-10-1990", "12:30 - 14:30", outputRt);
			System.out.println(outputRt.value);
			Thread.sleep(3000);
			
			//drrsImpl.cancelBooking("DVLS1234", bookingId, rt);
			//System.out.println(rt.value);
			//Thread.sleep(3000);
			
			drrsImpl.getAvailableTimeSlot("DVLS1234", "23-10-1990", outputRt);
			System.out.println(outputRt.value);
			
			Scanner in = new Scanner(System.in);
			String abd = in.next();
			
			drrsImpl.shutdown();
		} 
		catch (Exception e) 
		{
			System.out.println("ERROR : " + e) ;
			e.printStackTrace(System.out);
		}

	}
}
