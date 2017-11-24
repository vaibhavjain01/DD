import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.rmi.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.omg.CORBA.IntHolder;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;

/* This class contains code to create a UDP listen thread for each server.
 */
class listenThread extends Thread
{
	private Integer serverPort = null;
	
	/* This hashmap is updated after every client operation in RMIInterface thread */
	public HashMap<String, String> bookingRecords = null;
	private String response = null;
	
	public listenThread(Integer port, HashMap<String, String> inBookingRecords) 
	{
		super();
		//System.out.println("Thread for UDP connection created");
		serverPort = port;
		bookingRecords = inBookingRecords;
		start();
	}

	public void run()
	{
		DatagramSocket serverSocket = null;
		try
		{
	         byte[] receiveData = new byte[1024];
	         byte[] sendData = new byte[1024];
	         
	         while(true)
             {
	        	serverSocket = new DatagramSocket(serverPort);
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                //System.out.printf("\nListening on port %d\n", serverPort);
                serverSocket.receive(receivePacket);
                
                String request = new String( receivePacket.getData());
                if(request.contains("_CheckRoom") == false)
                {
                	System.out.println("Unauthenticated Request Discarded");
                	continue;
                }
                
                //System.out.println("RECEIVED: " + request);
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                
                response = getDateAvailRecord(request.substring(0, request.indexOf("_")));
                if(response != null)
                {
                	sendData = response.getBytes();
                }
                
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
                serverSocket.close();
                
             }
		}
		catch(SocketException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
 		{
			if(serverSocket != null)
			{
				serverSocket.close();
			}
 		}
	}
	
	private String getDateAvailRecord(String date)
	{
		return bookingRecords.get(date);
	}
}


public class RMVJ {
	public static final String MSG = "Hello World";
	private static final long serialVersionUID = 1L;
	
	/* StudentName, Number of bookings */
	private Map<String, Integer> StudentRecord = new HashMap<String, Integer>(10, 10);
	
	/* Initialized with 1, and incremented till server lifetime */
	private Integer BookingCounter = new Integer(1);
	
	/* BookingInfo contains key as date_roomnumber_timeslot */
	/* BookingInfo contains value as StudentID_BookingCounter */
	private Map<String, String> BookingInfo = new HashMap<String, String>(10, 10);
	
	/* RoomNumber(Integer) as key, List of timeslots (Stirngs) as value */
	//private Map<Integer, List<String>> RoomTimeSlots = new HashMap<Integer, List<String>>(10,10);
	
	/* Date(String) as key, hashmap RoomTimeSlots as value */
	private Map<String, HashMap<Integer, ArrayList<String>>> DateRoomSlots = 
			new HashMap<String, HashMap<Integer, ArrayList<String>>>(10, 10);
	
	private HashMap<String, String> bookingAvailRecords = new HashMap<String, String>(10, 10);
	
	private String serverName = null;
	listenThread udpListenThread = null;
	central cenRepoObj = null;
	
	/* Log File Output */
	private FileOutputStream userLog = null;
	
	private ORB orb;
	
	private PrintWriter out;
	
	//protected rmirpcImpl(String inServerName, Integer udpPort, central inCenRepoObj)
	protected RMVJ(String inServerName, Integer udpPort, central inCenRepoObj)
	{
		serverName = inServerName;
		cenRepoObj = inCenRepoObj;
		udpListenThread = new listenThread(udpPort, bookingAvailRecords);
		listenToUDP.start();
		resetCounter.start();
	}
	
	public void setORB(ORB orb_val) 
	{
		orb = orb_val; 
	}
	
	/* Generic */
	private String genBookingId(String studentId, String date, Integer roomNumber, String timeSlot, String serverName)
	{
		return (studentId + "_" + date + "_" + roomNumber.toString() + "_" + timeSlot + "_" + serverName);
	}
	
	Thread resetCounter = new Thread()
	{
		public void run()
		{
			int counter = 1;
			while(true)
			{
				if(counter == (1 * 60 * 60 * 24 * 7))	// 1 second * 60seconds * 60 minutes * 24 hours * 7 days
				{
					counter = 1;
					StudentRecord.clear();
				}
			}
		}
	};
	
	Thread listenToUDP = new Thread()
	{
	  public void run() 
	  {
		  DatagramSocket serverSocket = null;
		  String response = null;
			try
			{
		         byte[] receiveData = new byte[1024];
		         byte[] sendData = new byte[1024];
		         Integer serverPort = 0;
		         
		         if(serverName == "DVL")
		         {
		        	 serverPort = 9853;
		         }
		         else if(serverName == "KKL")
		         {
		        	 serverPort = 9753;
		         }
		         else if(serverName == "WST")
		         {
		        	 serverPort = 9653;
		         }
		         
		         while(true)
	             {
		        	serverSocket = new DatagramSocket(serverPort);
	                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	                //System.out.printf("\nListening on port %d\n", serverPort);
	                serverSocket.receive(receivePacket);
	                
	                String request = new String( receivePacket.getData());
	                if((request.contains("ROOMBOOK") == false) &&
	                		(request.contains("ROOMCANCEL") == false))
	                {
	                	System.out.println("Unauthenticated Request Discarded");
	                	continue;
	                }
	                
	                //System.out.println("RECEIVED: " + request);
	                InetAddress IPAddress = receivePacket.getAddress();
	                int port = receivePacket.getPort();     
	                if(request.contains("ROOMBOOK") == true)
	                {
	                	String params[] = request.split(",");
	                	response = roomBookerFun(params[1], Integer.parseInt(params[2]), params[3], params[4].substring(0, 13));
	                }
	                else if(request.contains("ROOMCANCEL") == true)
	                {
	                	String params[] = request.split(",");
	                	Integer tmpRt = (bookingCanceller(params[1], params[2]));
	                	response = tmpRt.toString();
	                }
	                 
	                if(response != null)
	                {
	                	sendData = response.getBytes();
	                }
	                else
	                {
	                	sendData = "FAILED".getBytes();
	                }
	                
	                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
	                serverSocket.send(sendPacket);
	                serverSocket.close();
	                
	             }
			}
			catch(SocketException e)
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			finally
	 		{
				if(serverSocket != null)
				{
					serverSocket.close();
				}
	 		}
	  }
	};
	
	/* Admin */
	/* Function to create room
	 * Return
	 * 0 Success
	 * -1 Failure
	 * @see rmiinterface#createRoom(java.lang.Integer, java.lang.String, java.util.List)
	 */
	public void createRoom(String adminId, int roomNumber, String date, String[] inListOfTimeSlots, IntHolder rt)
	{
		rt.value = 0;
		//Integer rt = new Integer(0);
		String fileName = "D:\\Test\\".concat(serverName);
		fileName = fileName.concat("\\");
		fileName = fileName.concat(adminId);
		fileName = fileName.concat(".txt");
		File logFile = new File(fileName);
		PrintWriter out = null;
		//ArrayList<String> listOfTimeSlots = (ArrayList<String>) Arrays.asList(inListOfTimeSlots);
		ArrayList<String> listOfTimeSlots = new ArrayList<String>( Arrays.asList(inListOfTimeSlots));
		
		try
		{
			if(logFile.exists() && !logFile.isDirectory())
			{
				synchronized(this)
				{
					out = new PrintWriter(new FileOutputStream(new File(fileName), true));
				}
			}
			else
			{
				out = new PrintWriter(fileName);
			}
			synchronized(this)
			{
			    out.printf("\n\nDate: %s\nTime: %s\nRequestType: CreateRoom"
			    		+ "\nRequestParam: \n(AdminId = %s)\n(RoomNumber = %d)\n(Date = %s)", 
			    		LocalDate.now(), LocalTime.now(), adminId, roomNumber, date);
		    	
			    out.println("\n(TimeSlots: ");
				    
			    for(int i = 0; i < listOfTimeSlots.size(); i++)
			    {
			    	out.printf("%s, ", listOfTimeSlots.get(i));
			    }
			    
			    out.println(")");
		    
				if(validateAdminId(adminId) == -1)
				{
					out.printf("\nRequest Failed\n Server Response: -1");
					rt.value = -1;
					return;
				}
			}
			synchronized(DateRoomSlots)
			{
				if(DateRoomSlots.containsKey(date))
				{
					if((DateRoomSlots.get(date)).containsKey(roomNumber))
					{
						List<String> cTimeSlots = ((DateRoomSlots.get(date)).get(roomNumber));
						for(int i=0; i<cTimeSlots.size(); i++)
						{
							for(int j=0; j<listOfTimeSlots.size(); j++)
							{
								rt.value = checkIfConflict(cTimeSlots.get(i), listOfTimeSlots.get(j));
								if(rt.value == -1)
								{
									//System.out.printf("\nTimeslot %s already exists and conflicts with %s.", cTimeSlots.get(i), listOfTimeSlots.get(j));
									listOfTimeSlots.remove(j);
									continue;
								}
								else
								{
									((DateRoomSlots.get(date)).get(roomNumber)).add(listOfTimeSlots.get(j));
								}
							}
						}
						
					}
					else
					{
						(DateRoomSlots.get(date)).put(roomNumber, listOfTimeSlots);
						synchronized(this)
						{
							out.printf("\nRequest Success for all timeslots");
						}
					}
				}
				else
				{
					HashMap<Integer, ArrayList<String>> tmp = new HashMap<Integer, ArrayList<String>>(10,10);
					tmp.put(roomNumber, listOfTimeSlots);
					DateRoomSlots.put(date, tmp);
					synchronized(this)
					{
						out.printf("\nRequest Success for all timeslots");
					}
				}
			}
		
			System.out.printf("\nTimeslot Addition Procedure Completed");
			rt.value = 0;
			bookingAvailRecords = updateBookingAvailRecord();
			synchronized(this)
			{
				out.printf("\nRequest Completed for Non Conflicted Timeslots\nServer Response %d", rt.value);
				out.close();
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		return;
	}
	
	private Integer checkIfConflict(String timeOne, String timeTwo)
	{
		Integer rt = new Integer(-1);
		try
		{
			Integer startOneHours = 0;
			Integer startOneMins = 0;
			Integer endOneHours = 0;
			Integer endOneMins = 0;
			Integer startTwoHours = 0;
			Integer startTwoMins = 0;
			Integer endTwoHours = 0;
			Integer endTwoMins = 0;
			
			String[] result = timeOne.split("-");
			String[] startHoursMins = result[0].split(":");
			String[] endHoursMins = result[1].split(":");
			
			startOneHours = Integer.parseInt(startHoursMins[0].trim());
			startOneMins = Integer.parseInt(startHoursMins[1].trim());
			endOneHours = Integer.parseInt(endHoursMins[0].trim());
			endOneMins = Integer.parseInt(endHoursMins[1].trim());
			
			result = timeTwo.split("-");
			startHoursMins = result[0].split(":");
			endHoursMins = result[1].split(":");
			startTwoHours = Integer.parseInt(startHoursMins[0].trim());
			startTwoMins = Integer.parseInt(startHoursMins[1].trim());
			endTwoHours = Integer.parseInt(endHoursMins[0].trim());
			endTwoMins = Integer.parseInt(endHoursMins[1].trim());
			
			if(endTwoHours < startOneHours)
			{
				return 0;
			}
			else if(endTwoHours == startOneHours)
			{
				if(endTwoMins < startOneMins)
				{
					return 0;
				}
			}
			else if(startTwoHours > endOneHours)
			{
				return 0;
			}
			else if(startTwoHours == endOneHours)
			{
				if(startTwoMins > endOneMins)
				{
					return 0;
				}
			}
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
			rt = -1;
		}
		return rt;
	}
	
	private String genBookingInfoKey(String date, Integer roomNumber, String timeSlot)
	{
		return (date + "#" + Integer.toString(roomNumber) + "#" + timeSlot);
	}
	
	public void deleteRoom(String adminId, int roomNumber, String date, String[] inListOfTimeSlots, IntHolder rt)
	{
		rt.value = 0;
		//Integer rt = new Integer(0);
		String bookingName = null;
		String studentId = null;
		List<String> listOfTimeSlots = Arrays.asList(inListOfTimeSlots);
		
		String fileName = "D:\\Test\\".concat(serverName);
		fileName = fileName.concat("\\");
		fileName = fileName.concat(adminId);
		fileName = fileName.concat(".txt");
		File logFile = new File(fileName);
		PrintWriter out = null;
		
		try
		{
			if(logFile.exists() && !logFile.isDirectory())
			{
				out = new PrintWriter(new FileOutputStream(new File(fileName), true));
			}
			else
			{
				out = new PrintWriter(fileName);
			}
			
		    out.printf("\\n\nDate: %s\nTime: %s\nRequestType: DeleteRoom"
		    		+ "\nRequestParam: \n(AdminId = %s)\n(RoomNumber = %d)\n(Date = %s)", 
		    		LocalDate.now(), LocalTime.now(), adminId, roomNumber, date);
	    	
		    out.println("\n(TimeSlots: ");
	    	
		    for(int i = 0; i < listOfTimeSlots.size(); i++)
		    {
		    	out.printf("%s, ", listOfTimeSlots.get(i));
		    }
		    
		    out.println(")");
		    
		
			if(validateAdminId(adminId) == -1)
			{
				out.printf("\nRequest Failed\n Server Response: -1");
				rt.value = -1;
				return;
			}
			
			if(DateRoomSlots.containsKey(date))
			{
				if((DateRoomSlots.get(date)).containsKey(roomNumber))
				{
					ListIterator<String> li = listOfTimeSlots.listIterator();
					List<String> tmp = ((DateRoomSlots.get(date)).get(roomNumber));
					
				    while(li.hasNext())
				    {
				    	String tmpTimeSlot = li.next();
				    	bookingName = genBookingInfoKey(date, roomNumber, tmpTimeSlot);
				    	if(BookingInfo.containsKey(bookingName))
				    	{
					    	studentId = BookingInfo.get(bookingName);
					    	if(studentId != null)
					    	{			    		
					    		studentId = studentId.substring(0, studentId.indexOf("_"));
					    		BookingInfo.remove(bookingName);
					    		StudentRecord.put(studentId, StudentRecord.get(studentId) - 1);
					    	}
				    	}
				    	((DateRoomSlots.get(date)).get(roomNumber)).remove(tmpTimeSlot);
					}
				    
				    if(((DateRoomSlots.get(date)).get(roomNumber)).isEmpty())
				    {
				    	(DateRoomSlots.get(date)).remove(roomNumber);
				    }
				    
				    if((DateRoomSlots.get(date)).size() == 0)
				    {
				    	DateRoomSlots.remove(date);
				    }
				    
				    System.out.println("TimsSlot successfully Deleted");
				    out.printf("\nRequest Success");
				}
				else
				{
					System.out.println("No record could be found matching input details");
					out.printf("\nRequest Failed");
					rt.value = -1;
					return;
				}
			}
			else
			{
				System.out.println("No record could be found matching input details");
				out.printf("\nRequest Failed");
				rt.value = -1;
				return;
			}
			bookingAvailRecords = updateBookingAvailRecord();
			out.printf("\nServer Response: %d", rt.value);
			out.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	
	/* Student */
	public void bookRoom(String studentId, int roomNumber, String date, String timeSlot, StringHolder rt, String campusName)
	{
		//String rt = null;
		rt.value = "FAILED";
		String fileName = "D:\\Test\\".concat(serverName);
		fileName = fileName.concat("\\");
		fileName = fileName.concat(studentId);
		fileName = fileName.concat(".txt");
		File logFile = new File(fileName);
		PrintWriter out = null;
		
		System.out.println("Room Book: New Booking Request Received");
		System.out.println("Room Book: New booking: " + campusName + "_" + roomNumber + "_" + date + "_" + timeSlot);
		
		try
		{
			synchronized(this)
			{
				if(logFile.exists() && !logFile.isDirectory())
				{
					out = new PrintWriter(new FileOutputStream(new File(fileName), true));
				}
				else
				{
					out = new PrintWriter(fileName);
				}
				
			    out.printf("\n\nDate: %s\nTime: %s\nRequestType: BookRoom"
			    		+ "\nRequestParam: \n(StudentId = %s)\n(RoomNumber = %d)\n(Date = %s)", 
			    		LocalDate.now(), LocalTime.now(), studentId, roomNumber, date);
		    	
			    out.printf("\n(TimeSlots: %s)", timeSlot);
			
				if(validateStudentId(studentId) == -1)
				{
					out.printf("\nRequest Failed");
					out.printf("\nServer Response: null");
					System.out.println("Student Validation Failed");
					rt.value = "FAILED";
					return;
				}
			}

			if(StudentRecord.containsKey(studentId))
			{
				if(StudentRecord.get(studentId) == 3)
				{
					System.out.println("Maximum Booking Count Reached for this Student ID");
					rt.value = "FAILED";
					return;
				}
			}
			
			if((serverName == "DVL") && (((campusName).equals("DVL")) == false))
			{
				if(((campusName).equals("KKL")) == true)
				{
					rt.value = roomBookForwarder(studentId, roomNumber, date, timeSlot, central.udpPortKKLRM1);
					this.out = out;
				}
				else if(((campusName).equals("WST")) == true)
				{
					rt.value = roomBookForwarder(studentId, roomNumber, date, timeSlot, central.udpPortWSTRM1);
					this.out = out;
				}
			}
			else if((serverName == "KKL") && (((campusName).equals("KKL")) == false))
			{
				if(((campusName).equals("DVL")) == true)
				{
					rt.value = roomBookForwarder(studentId, roomNumber, date, timeSlot, central.udpPortDVLRM1);
					this.out = out;
				}
				else if(((campusName).equals("WST")) == true)
				{
					rt.value = roomBookForwarder(studentId, roomNumber, date, timeSlot, central.udpPortWSTRM1);
					this.out = out;
				}
			}
			else if((serverName == "WST") && (((campusName).equals("WST")) == false))
			{
				if(((campusName).equals("KKL")) == true)
				{
					rt.value = roomBookForwarder(studentId, roomNumber, date, timeSlot, central.udpPortKKLRM1);
					this.out = out;
				}
				else if(((campusName).equals("DVL")) == true)
				{
					rt.value = roomBookForwarder(studentId, roomNumber, date, timeSlot, central.udpPortDVLRM1);
					this.out = out;
				}
			}
			else
			{
				rt.value = roomBookerFun(studentId, roomNumber, date, timeSlot);
			}
			synchronized(this)
			{
				out.printf("\nServer Response: %s", rt.value);
				out.close();
				
				if(rt.value != null)
				{
					if(StudentRecord.containsKey(studentId))
					{
						StudentRecord.put(studentId, StudentRecord.get(studentId) + 1);
						BookingCounter = StudentRecord.get(studentId);
					}
					else
					{
						BookingCounter = 1;
						StudentRecord.put(studentId, 1);
					}
					out.printf("\nRequest Success");
				}
				else
				{
					System.out.println("roomBooker function failed");
					rt.value = "FAILED";
					out.printf("\nRequest Failed");
				}
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	
	private String roomBookForwarder(String studentId, Integer roomNumber, String date, String timeSlot, Integer inUdpPort)
	{
		String rt = null;
		
		DatagramSocket clientSocket = null;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		inUdpPort++;
		try
		{
			clientSocket = new DatagramSocket();
		    InetAddress IPAddress = InetAddress.getByName("localhost"); //InetAddress.getByName(inUrl);
		    byte[] sendData = new byte[1024];
		    byte[] receiveData = new byte[1024];
		    int i = 0;
		    String request = "ROOMBOOK," + studentId + "," + roomNumber.toString() + "," + date + "," + timeSlot;
		    
		    String sentence = request;
		    sendData = sentence.getBytes();
		    // System.out.printf("\nSending on address %s, port %d\n", IPAddress, inUdpPort);
		    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, inUdpPort);
		    clientSocket.send(sendPacket);
		    
		    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		    clientSocket.receive(receivePacket);
		    if(receiveData[0] == 0)
		    {
		    	return null;
		    }
		    for(i = 0; i < receiveData.length; i++)
		    {
		    	if(receiveData[i] == 0)
		    	{
		    		break;
		    	}
		    }
		    receiveData.toString();
		    String response = new String(Arrays.copyOf(receiveData, i), "UTF-8");
		    
		    //System.out.println("FROM SERVER:" + response);
		    rt = response;
		}
		catch(SocketException e)
		{
			e.printStackTrace();
		}
		catch(UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(NegativeArraySizeException e)
		{
			// Handled
		}
		finally
		{
			if(clientSocket != null)
			{
				clientSocket.close();
			}
		}
		
		return rt;
	}
	
	private String roomBookerFun(String studentId, int roomNumber, String date, String timeSlot)
	{
		StringHolder rt = new StringHolder(null);
		
		synchronized(DateRoomSlots)
		{
			if(DateRoomSlots.containsKey(date))
			{
				if((DateRoomSlots.get(date)).containsKey(roomNumber))
				{
					if((DateRoomSlots.get(date)).get(roomNumber).contains(timeSlot))
					{
						String tmpBookingKey = genBookingInfoKey(date, roomNumber, timeSlot);
						if(BookingInfo.containsKey(tmpBookingKey))
						{
							System.out.printf("Booking already exists with %s Booking ID", BookingInfo.get(tmpBookingKey));
							rt.value = null;
						}
						else
						{
							rt.value = genBookingId(studentId, date, roomNumber, timeSlot, this.serverName);
							BookingCounter = 1;
							BookingInfo.put(tmpBookingKey, rt.value);
							System.out.println("Booking Procedure Successfully Completed");
							bookingAvailRecords = updateBookingAvailRecord();
						}
					}
				}
			}
		}
		return rt.value;
	}
	
	public void getAvailableTimeSlot(String studentId, String date, StringHolder outputRt) 
	{
		String recordOne = null;
		String recordTwo = null;
		String request = date + "_CheckRoom";
		String rt = new String("");
		
		String fileName = "D:\\Test\\".concat(serverName);
		fileName = fileName.concat("\\");
		fileName = fileName.concat(studentId);
		fileName = fileName.concat(".txt");
		
		try(FileWriter fw = new FileWriter(fileName, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw))
		{
			synchronized(out)
			{
			    out.printf("\n\nDate: %s\nTime: %s\nRequestType: AvailableRooms"
			    		+ "\nRequestParam: \n(studentId = %s)\n(Date = %s)", 
			    		LocalDate.now(), LocalTime.now(), studentId, date);
			
				if(validateStudentId(studentId) == -1)
				{
					out.println("\nRequest Failed\nServer Response: null");
					outputRt.value = "FAILED";
					return;
					// vj return null;
				}
				
				if(serverName == "DVL")
				{
					//recordOne = getAvailableRecordFromServer(cenRepoObj.getKKLServer(), cenRepoObj.getUdpPortKKL(), request);
					//recordTwo = getAvailableRecordFromServer(cenRepoObj.getWSTServer(), cenRepoObj.getUdpPortWST(), request);
				}
				else if(serverName == "KKL")
				{
					//recordOne = getAvailableRecordFromServer(cenRepoObj.getDVLServer(), cenRepoObj.getUdpPortDVL(), request);
					//recordTwo = getAvailableRecordFromServer(cenRepoObj.getWSTServer(), cenRepoObj.getUdpPortWST(), request);
				}
				else if(serverName == "WST")
				{
					//recordOne = getAvailableRecordFromServer(cenRepoObj.getDVLServer(), cenRepoObj.getUdpPortDVL(), request);
					//recordTwo = getAvailableRecordFromServer(cenRepoObj.getKKLServer(), cenRepoObj.getUdpPortKKL(), request);
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(recordOne != null)
				{
					rt = rt.concat(recordOne);
				}
				if(recordTwo != null)
				{
					rt = rt.concat(", ");
					rt = rt.concat(recordTwo);
				}
				rt = rt.concat(", ");
				if(bookingAvailRecords.size() > 0)
				{
					if(date == null)
					{
						rt = "FAILED";
						outputRt.value = rt;
						return;
					}
					String tmpRec = bookingAvailRecords.get(date);
					if(tmpRec == null)
					{
						rt = "FAILED";
						outputRt.value = rt;
						return;
					}
					rt = rt.concat(tmpRec);
				}
				out.printf("\nRequest Success\nServer Response: %s", rt);
				out.close();
			}
		} 
		catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		outputRt.value = rt;
		//return rt;
	}
	
	private String getAvailableRecordFromServer(String inUrl, Integer inUdpPort, String request)
	{
		String record = null;
		DatagramSocket clientSocket = null;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		
		try
		{
			clientSocket = new DatagramSocket();
		    InetAddress IPAddress = InetAddress.getByName("localhost"); //InetAddress.getByName(inUrl);
		    byte[] sendData = new byte[1024];
		    byte[] receiveData = new byte[1024];
		    int i = 0;
		    
		    String sentence = request;
		    sendData = sentence.getBytes();
		    // System.out.printf("\nSending on address %s, port %d\n", IPAddress, inUdpPort);
		    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, inUdpPort);
		    clientSocket.send(sendPacket);
		    
		    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		    clientSocket.receive(receivePacket);
		    if(receiveData[0] == 0)
		    {
		    	return null;
		    }
		    for(i = 0; i < receiveData.length; i++)
		    {
		    	if(receiveData[i] == 0)
		    	{
		    		break;
		    	}
		    }
		    receiveData.toString();
		    String response = new String(Arrays.copyOf(receiveData, i), "UTF-8");
		    
		    //System.out.println("FROM SERVER:" + response);
		    record = response;
		}
		catch(SocketException e)
		{
			e.printStackTrace();
		}
		catch(UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(NegativeArraySizeException e)
		{
			// Handled
		}
		finally
		{
			if(clientSocket != null)
			{
				clientSocket.close();
			}
		}
		
		return record;
	}

	public void cancelBooking(String studentId, String bookingID, IntHolder rt)
	{
		//Integer rt = new Integer(0);
		rt.value = 0;
		String fileName = "D:\\Test\\".concat(serverName);
		fileName = fileName.concat("\\");
		fileName = fileName.concat(studentId);
		fileName = fileName.concat(".txt");
		File logFile = new File(fileName);
		PrintWriter out = null;
		
		try
		{
			if(logFile.exists() && !logFile.isDirectory())
			{
				synchronized(this)
				{
					out = new PrintWriter(new FileOutputStream(new File(fileName), true));
				}
			}
			else
			{
				out = new PrintWriter(fileName);
			}
			
			synchronized(out)
			{
			    out.printf("\n\nDate: %s\nTime: %s\nRequestType: CancelBooking"
			    		+ "\nRequestParam: \n(studentId = %s)\n(bookingID = %s)", 
			    		LocalDate.now(), LocalTime.now(), studentId, bookingID);
				    
				if(validateStudentId(studentId) == -1)
				{
					out.printf("\nRequest Failed\nServer Response: null");
					rt.value = -1;
					return;
				}
			}
			
			if(bookingID.length() < 14)
			{
				rt.value = -1;
				return;
			}
			
			if((serverName == "DVL") && (((bookingID.substring(bookingID.length() - 3, bookingID.length())).equals("DVL")) == false))
			{
				if(((bookingID.substring(bookingID.length() - 3, bookingID.length())).equals("KKL")) == true)
				{
					rt.value = roomCancelForwarder(studentId, bookingID, central.udpPortKKLRM1);
					this.out = out;
				}
				else if(((bookingID.substring(bookingID.length() - 3, bookingID.length())).equals("WST")) == true)
				{
					rt.value = roomCancelForwarder(studentId, bookingID, central.udpPortWSTRM1);
					this.out = out;
				}
			}
			else if((serverName == "KKL") && (((bookingID.substring(bookingID.length() - 3, bookingID.length())).equals("KKL")) == false))
			{
				if(((bookingID.substring(bookingID.length() - 3, bookingID.length())).equals("DVL")) == true)
				{
					rt.value = roomCancelForwarder(studentId, bookingID, central.udpPortDVLRM1);
					this.out = out;
				}
				else if(((bookingID.substring(bookingID.length() - 3, bookingID.length())).equals("WST")) == true)
				{
					rt.value = roomCancelForwarder(studentId, bookingID, central.udpPortWSTRM1);
					this.out = out;
				}
			}
			else if((serverName == "WST") && (((bookingID.substring(bookingID.length() - 3, bookingID.length())).equals("WST")) == false))
			{
				if(((bookingID.substring(bookingID.length() - 3, bookingID.length())).equals("KKL")) == true)
				{
					rt.value = roomCancelForwarder(studentId, bookingID, central.udpPortKKLRM1);
					this.out = out;
				}
				else if(((bookingID.substring(bookingID.length() - 3, bookingID.length())).equals("DVL")) == true)
				{
					rt.value = roomCancelForwarder(studentId, bookingID, central.udpPortDVLRM1);
					this.out = out;
				}
			}
			else
			{
				rt.value = bookingCanceller(studentId, bookingID);
			}
			synchronized(out)
			{
				out.printf("\nServer Response: %d", rt.value);
				out.close();
				
				synchronized (StudentRecord)
				{
					if(rt.value == 0)
					{
						StudentRecord.put(studentId, StudentRecord.get(studentId) - 1);
						out.printf("Request Success");
					}
					else
					{
						out.printf("Request Failed");
					}
				}
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	
	private Integer roomCancelForwarder(String studentId, String bookingId, Integer inUdpPort)
	{
		Integer rt = null;
		
		DatagramSocket clientSocket = null;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		
		try
		{
			clientSocket = new DatagramSocket();
		    InetAddress IPAddress = InetAddress.getByName("localhost"); //InetAddress.getByName(inUrl);
		    byte[] sendData = new byte[1024];
		    byte[] receiveData = new byte[1024];
		    int i = 0;
		    String request = "ROOMCANCEL," + studentId + "," + bookingId;
		    
		    String sentence = request;
		    sendData = sentence.getBytes();
		    // System.out.printf("\nSending on address %s, port %d\n", IPAddress, inUdpPort + 1);
		    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, inUdpPort + 1);
		    clientSocket.send(sendPacket);
		    
		    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		    clientSocket.receive(receivePacket);
		    if(receiveData[0] == 0)
		    {
		    	return null;
		    }
		    for(i = 0; i < receiveData.length; i++)
		    {
		    	if(receiveData[i] == 0)
		    	{
		    		break;
		    	}
		    }
		    receiveData.toString();
		    String response = new String(Arrays.copyOf(receiveData, i), "UTF-8");
		    
		    //System.out.println("FROM SERVER:" + response);
		    rt = Integer.parseInt(response);
		}
		catch(SocketException e)
		{
			e.printStackTrace();
		}
		catch(UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(NegativeArraySizeException e)
		{
			// Handled
		}
		finally
		{
			if(clientSocket != null)
			{
				clientSocket.close();
			}
		}
		
		return rt;
	}
	
	private int bookingCanceller(String studentId, String bookingID)
	{
		IntHolder rt = new IntHolder(0);
		
		if(bookingID.length() > 14)
		{
			bookingID = bookingID.substring(0, 41);
		}
		else
		{
			rt.value = -1;
			System.out.println("Incorrect Booking ID Received for Cancellation");
			return rt.value;
		}
		synchronized (BookingInfo) 
		{
			if(BookingInfo.containsValue(bookingID))
			{
				if(studentId.equals(bookingID.substring(0, bookingID.indexOf("_"))))
				{
					BookingInfo.values().remove(bookingID);
					System.out.println("Booking Successfully Deleted");
					bookingAvailRecords = updateBookingAvailRecord();
				}
				else
				{
					rt.value = -1;
					System.out.println("A student can only delete his own bookings");
				}
			}
			else
			{
				rt.value = -1;
				System.out.println("No such booking record with provided bookingID was found");
			}
		}
		return rt.value;
	}
	
	private HashMap<String, String> updateBookingAvailRecord()
	{
		HashMap<String, String> tmp = new HashMap<String, String>();
		
		synchronized(DateRoomSlots)
		{
			for(String date : DateRoomSlots.keySet())
			{
				Integer count = new Integer(0);
				for(Integer room : (DateRoomSlots.get(date)).keySet())
				{
					ListIterator<String> li = ((DateRoomSlots.get(date)).get(room)).listIterator();
					try
					{
						while(li.hasNext())
						{
							String tmpTimeSlot = li.next();
							synchronized(BookingInfo)
							{
								if(BookingInfo.containsKey(genBookingInfoKey(date, room, tmpTimeSlot)))
								{
									continue;
								}
								else
								{
									count++;
								}
							}
						}
					}
					catch(ConcurrentModificationException e)
					{
						System.out.println("Concurrent Modification Exception Handled");
					}
					
				}
				tmp.put(date, (serverName + " " + Integer.toString(count)));
			}
		}
		synchronized (udpListenThread.bookingRecords) {
			udpListenThread.bookingRecords = tmp;
		}
		
		return tmp;
	}
	
	private Integer validateAdminId(String adminId)
	{
		String tmpStr;
		tmpStr = adminId.substring(0, 4);
		if(adminId.length() > 8)
		{
			return -1;
		}
		
		if(serverName == "DVL")
		{
			if((tmpStr.equals("DVLA")) == false)
			{
				return -1;
			}
		}
		else if(serverName == "KKL")
		{
			if((tmpStr.equals("KKLA")) == false)
			{
				return -1;
			}
		}
		else if(serverName == "WST")
		{
			if((tmpStr.equals("WSTA")) == false)
			{
				return -1;
			}
		}
		
		try
		{
			Integer tmp = Integer.parseInt(adminId.substring(4, 8));
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
			return -1;
		}
		
		return 0;
	}
	
	private Integer validateStudentId(String studentId)
	{
		if(studentId.length() > 8)
		{
			return -1;
		}

		if((serverName == "DVL") && (((studentId.substring(0, 4)).equals("DVLS")) == false))
		{
			return -1;
		}
		else if((serverName == "KKL") && (((studentId.substring(0, 4)).equals("KKLS")) == false))
		{
			return -1;
		}
		else if((serverName == "WST") && (((studentId.substring(0, 4)).equals("WSTS")) == false))
		{
			return -1;
		}
		
		try
		{
			Integer tmp = Integer.parseInt(studentId.substring(4, 8));
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
			return -1;
		}
		
		return 0;
	}

	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

	public void changeReservation(String studentId, String bookingID, String newCampusName, 
			int newRoomNumber, String newDate, String newTimeSlot, StringHolder outputRt) 
	{
		outputRt.value = "FAILED";
		Integer rt = 0;
		String fileName = "D:\\Test\\".concat(serverName);
		fileName = fileName.concat("\\");
		fileName = fileName.concat(studentId);
		fileName = fileName.concat(".txt");
		File logFile = new File(fileName);
		PrintWriter out = null;
		
		System.out.println("Change RES: Change request received: ");
		System.out.println("Change RES: Old booking: " + bookingID);
		System.out.println("Change RES: New booking: " + newCampusName + "_" + newRoomNumber + "_" + newDate + "_" + newTimeSlot);
		
		try
		{
			if(logFile.exists() && !logFile.isDirectory())
			{
				synchronized(this)
				{
					out = new PrintWriter(new FileOutputStream(new File(fileName), true));
				}
			}
			else
			{
				out = new PrintWriter(fileName);
			}
			
			synchronized(out)
			{
			    out.printf("\n\nDate: %s\nTime: %s\nRequestType: CancelBooking"
			    		+ "\nRequestParam: \n(studentId = %s)\n(bookingID = %s)", 
			    		LocalDate.now(), LocalTime.now(), studentId, bookingID);
				    
				if(validateStudentId(studentId) == -1)
				{
					System.out.println("student validation failed");
					out.printf("\nRequest Failed\nServer Response: null");
					outputRt.value = "FAILED";
					return;
				}
			}
			synchronized (this)
			{
				if(newCampusName.equals("DVL") == true)
				{
					System.out.println("CHANGE REQ FORWARDED");
					outputRt.value = roomBookForwarder(studentId, newRoomNumber, newDate, newTimeSlot, central.udpPortDVLRM1);
				}
				else if(newCampusName.equals("KKL") == true)
				{
					System.out.println("CHANGE REQ FORWARDED");
					outputRt.value = roomBookForwarder(studentId, newRoomNumber, newDate, newTimeSlot, central.udpPortKKLRM1);
				}
				else if(newCampusName.equals("WST") == true)
				{
					System.out.println("CHANGE REQ FORWARDED");
					outputRt.value = roomBookForwarder(studentId, newRoomNumber, newDate, newTimeSlot, central.udpPortWSTRM1);
				}
				
				if(outputRt.value.equals("FAILED") == true)
				{
					System.out.println("CHANGE REQ FORWARDED FAILED");
					outputRt.value = "FAILED";
					return;
				}
				
				if((serverName == "DVL") && (((bookingID.substring(bookingID.length() - 3, bookingID.length())).equals("DVL")) == false))
				{
					if(((bookingID.substring(bookingID.length() - 3, bookingID.length())).equals("KKL")) == true)
					{
						rt = roomCancelForwarder(studentId, bookingID, central.udpPortKKLRM1);
						this.out = out;
					}
					else if(((bookingID.substring(bookingID.length() - 3, bookingID.length())).equals("WST")) == true)
					{
						rt = roomCancelForwarder(studentId, bookingID, central.udpPortWSTRM1);
						this.out = out;
					}
				}
				else if((serverName == "KKL") && (((bookingID.substring(bookingID.length() - 3, bookingID.length())).equals("KKL")) == false))
				{
					if(((bookingID.substring(bookingID.length() - 3, bookingID.length())).equals("DVL")) == true)
					{
						rt = roomCancelForwarder(studentId, bookingID, central.udpPortDVLRM1);
						this.out = out;
					}
					else if(((bookingID.substring(bookingID.length() - 3, bookingID.length())).equals("WST")) == true)
					{
						rt = roomCancelForwarder(studentId, bookingID, central.udpPortWSTRM1);
						this.out = out;
					}
				}
				else if((serverName == "WST") && (((bookingID.substring(bookingID.length() - 3, bookingID.length())).equals("WST")) == false))
				{
					if(((bookingID.substring(bookingID.length() - 3, bookingID.length())).equals("KKL")) == true)
					{
						rt = roomCancelForwarder(studentId, bookingID, central.udpPortKKLRM1);
						this.out = out;
					}
					else if(((bookingID.substring(bookingID.length() - 3, bookingID.length())).equals("DVL")) == true)
					{
						rt = roomCancelForwarder(studentId, bookingID, central.udpPortDVLRM1);
						this.out = out;
					}
				}
				else
				{
					rt = bookingCanceller(studentId, bookingID);
					if(rt == 0)
					{
						out.printf("Request Success");
						synchronized (StudentRecord)
						{
							StudentRecord.put(studentId, StudentRecord.get(studentId) - 1);
						}
					}
					else
					{
						out.printf("Request Failed");
					}
				}
			}
			synchronized(out)
			{
				out.printf("\nServer Response: %s", outputRt.value);
				out.close();
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
}




