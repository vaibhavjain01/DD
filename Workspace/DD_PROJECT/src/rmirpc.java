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
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;

/* REPLICA MANAGER */
public class rmirpc
{
	Integer DVLport = central.udpPortDVLRM1;
	Integer KKLport = central.udpPortKKLRM1;
	Integer WSTport = central.udpPortWSTRM1;
	Integer RMport = central.udpPortRM1;
	central objCentral = new central();
	
	/* VJ IMPL */
	RMVJ vjImplDVL = new RMVJ("DVL", objCentral.getUdpPortDVL(), objCentral);
	RMVJ vjImplKKL = new RMVJ("KKL", objCentral.getUdpPortKKL(), objCentral);
	RMVJ vjImplWST = new RMVJ("WST", objCentral.getUdpPortWST(), objCentral);
	RMVJ vjImpl;
	
	/* SOMAYEH IMPL */
	RMSOMAYEH smImpl = new RMSOMAYEH();
	
	/* MAHSA PORTS */
	Integer DVLport2 = central.udpPortDVLRM2;
	Integer KKLport2 = central.udpPortKKLRM2;
	Integer WSTport2 = central.udpPortWSTRM2;
	Integer RMport2 = central.udpPortRM2;
	
	/* SOMAYEH PORTS */
	Integer DVLport3 = central.udpPortDVLRM3;
	Integer KKLport3 = central.udpPortKKLRM3;
	Integer WSTport3 = central.udpPortWSTRM3;
	Integer RMport3 = central.udpPortRM3;
	
	void createServers()
	{
		try
		{
			dvlServer.start();
			kklServer.start();
			wstServer.start();
			replicaManager.start();
		    System.out.println("Servers are ready and waiting ...");
		}
		catch(Exception e)
		{
			System.err.println("ERROR: " + e);
			e.printStackTrace();
		}
		
		System.out.println("Servers Exiting ...");
	}
	
	Thread replicaManager = new Thread()
	{
		int failureCounter = 0;
		public void run()
		{
			DatagramSocket serverSocket = null;
			String response = null;
			try
			{
				byte[] receiveData = new byte[1024];
			    byte[] sendData = new byte[1024];
			    
			    while(true)
		        {
			    	serverSocket = new DatagramSocket(RMport);
		            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		            serverSocket.receive(receivePacket);
		            String request = new String(receivePacket.getData());
		            System.out.println("VJRM : received :" + request);
		            if(request.contains("SUCCESS") == true) {
		            	failureCounter = 0;
		            }
		            else if(request.contains("FAILED") == true) {
		            	failureCounter++;
		            	System.out.println("VJRM Failure Counter: " + failureCounter);
		            	if(failureCounter == 3) {
		            		System.out.println("CONSECUTIVE FAILURES MAXIMUM LIMIT");
		            		RMVJ.FAILUREFLAG = false;
		            		failureCounter = 0;
		            	}
		            }
		            
		            //System.out.println(RMport + " received " + request);
		            /* CHECK REQUEST and get response*/    
		            InetAddress IPAddress = receivePacket.getAddress();
		            int port = receivePacket.getPort();     
	                if(response != null) {
	                	sendData = response.getBytes();
	                }
	                else {
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
	
	Thread dvlServer = new Thread()
	{
		public void run()
		{
			DatagramSocket serverSocket = null;
			String response = null;
			try
			{
				byte[] receiveData = new byte[1024];
			    byte[] sendData = new byte[1024];
			    
			    while(true)
		        {
			    	serverSocket = new DatagramSocket(DVLport);
		            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		            serverSocket.receive(receivePacket);
		            String request = new String(receivePacket.getData());
		            //System.out.println(DVLport + " received " + request);
		            /* CHECK REQUEST and get response*/    
		            response = handleUdpRequest(request);
		            InetAddress IPAddress = receivePacket.getAddress();
		            int port = central.udpRecvRespRM;     
	                if(response != null) {
	                	sendData = response.getBytes();
	                }
	                else {
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
	
	Thread kklServer = new Thread()
	{
		public void run()
		{
			DatagramSocket serverSocket = null;
			String response = null;
			try
			{
				byte[] receiveData = new byte[1024];
			    byte[] sendData = new byte[1024];
			    
			    while(true)
		        {
			    	serverSocket = new DatagramSocket(KKLport);
		            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		            serverSocket.receive(receivePacket);
		            String request = new String(receivePacket.getData());
		            //System.out.println(KKLport + " received " + request);
		            /* CHECK REQUEST and get response*/
		            response = handleUdpRequest(request);
		            InetAddress IPAddress = receivePacket.getAddress();
		            int port = central.udpRecvRespRM;     
	                if(response != null) {
	                	sendData = response.getBytes();
	                }
	                else {
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
	
	Thread wstServer = new Thread()
	{
		public void run()
		{
			DatagramSocket serverSocket = null;
			String response = null;
			try
			{
				byte[] receiveData = new byte[1024];
			    byte[] sendData = new byte[1024];
			    
			    while(true)
		        {
			    	serverSocket = new DatagramSocket(WSTport);
		            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		            serverSocket.receive(receivePacket);
		            String request = new String(receivePacket.getData());
		            //System.out.println(WSTport + " received " + request);
		            /* CHECK REQUEST and get response*/
		            response = handleUdpRequest(request);
		            InetAddress IPAddress = receivePacket.getAddress();
		            int port = central.udpRecvRespRM;     
	                if(response != null) {
	                	sendData = response.getBytes();
	                }
	                else {
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
	
	public String handleUdpRequest(String req)
	{
		String reqTokens[] = req.split(";");
		String serverName = reqTokens[1];
		ArrayList<String> timeSlotsList;
		int ctr = 0;
		Integer roomNumber;
		String strTimeSlots[];
		central objCentral = new central();
		Integer intResp = null;
		String strResp = null;
		
		/* Check Server Type */
		serverName = serverName.substring(0, 3);
		if(serverName.equals("DVL") == true) {
			vjImpl = vjImplDVL;
		}
		else if(serverName.equals("KKL") == true) {
			vjImpl = vjImplKKL;
		}
		else if(serverName.equals("WST") == true) {
			vjImpl = vjImplWST;
		}
		
		/* Check Request Type */
		switch(reqTokens[0])
		{
		case "create":
			ctr = 4;
			timeSlotsList = new ArrayList<String>();
			while(true) {
				if(reqTokens[ctr].substring(0, 2).equals("RM") == false) {
					timeSlotsList.add(reqTokens[ctr]);
				}
				else {
					break;
				}
				ctr++;
			}
			roomNumber = Integer.parseInt(reqTokens[2]);
			strTimeSlots = timeSlotsList.toArray(new String[timeSlotsList.size()]);
			intResp = vjImpl.createRoom(reqTokens[1], roomNumber, reqTokens[3], strTimeSlots);
			strResp = intResp.toString() + ";" + reqTokens[ctr] + ";" + reqTokens[ctr + 1] + ";";
			break;
			
		case "delete":
			timeSlotsList = new ArrayList<String>();
			ctr = 4;
			while(true) {
				if(reqTokens[ctr].substring(0, 2).equals("RM") == false) {
					timeSlotsList.add(reqTokens[ctr]);
				}
				else {
					break;
				}
				ctr++;
			}
			roomNumber = Integer.parseInt(reqTokens[2]);
			strTimeSlots = timeSlotsList.toArray(new String[timeSlotsList.size()]);
			intResp = vjImpl.deleteRoom(reqTokens[1], roomNumber, reqTokens[3], strTimeSlots);
			strResp = intResp.toString() + ";" + reqTokens[ctr] + ";" + reqTokens[ctr + 1] + ";";
			break;
			
		case "book":
			roomNumber = Integer.parseInt(reqTokens[2]);
			strResp = vjImpl.bookRoom(reqTokens[1], roomNumber, reqTokens[3], reqTokens[4], reqTokens[5]);
			strResp = strResp + ";" + reqTokens[6] + ";" + reqTokens[7] + ";";
			break;
			
		case "cancel":
			intResp = vjImpl.cancelBooking(reqTokens[1], reqTokens[2]);
			strResp = intResp.toString();
			strResp = strResp + ";" + reqTokens[3] + ";" + reqTokens[4] + ";";
			break;
			
		case "available":
			strResp = vjImpl.getAvailableTimeSlot(reqTokens[1], reqTokens[2]);
			String tokens[] = strResp.split(", ");
			String DVL = null;
			String KKL = null;
			String WST = null;
			System.out.println("vj remove:" + strResp);
			for(int ctr2 = 0; ctr2 < 3; ctr2++) {
				if(tokens.length <= ctr2) {
					break;
				}
				if(tokens[ctr2].length() >= 3) {
					if(tokens[ctr2].substring(0, 3).equals("DVL")) {
						DVL = tokens[ctr2];
					}
					else if(tokens[ctr2].substring(0, 3).equals("KKL")) {
						KKL = tokens[ctr2];
					}
					else if(tokens[ctr2].substring(0, 3).equals("WST")) {
						WST = tokens[ctr2];
					}
				}
			}
			if(DVL == null) {
				DVL = "DVL 0";
			}
			if(KKL == null) {
				KKL = "KKL 0";
			}
			if(WST == null) {
				WST = "WST 0";
			}
			strResp = DVL + ", " + KKL + ", " + WST + ";RM1;" + reqTokens[4] + ";";
			break;
			
		case "change":
			roomNumber = Integer.parseInt(reqTokens[4]);
			reqTokens[5] = reqTokens[5].replaceAll("\\s","");
			strResp = vjImpl.changeReservation(reqTokens[1], reqTokens[2], reqTokens[3], roomNumber, 
					reqTokens[5], reqTokens[6]);
			strResp = strResp + ";" + reqTokens[7] + ";" + reqTokens[8] + ";";
			break;
		}
		
		//System.out.println("ReplicaVJ Resp: " + strResp);
		return strResp;
	};
	
	
	/* MAHSA */
	Thread replicaManagerMahsa = new Thread()
	{
		int failureCounter = 0;
		public void run()
		{
			DatagramSocket serverSocket = null;
			String response = null;
			try
			{
				byte[] receiveData = new byte[1024];
			    byte[] sendData = new byte[1024];
			    
			    while(true)
		        {
			    	serverSocket = new DatagramSocket(RMport2);
		            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		            serverSocket.receive(receivePacket);
		            String request = new String(receivePacket.getData());
		            System.out.println("MAHSARM : received :" + request);
		            if(request.contains("SUCCESS") == true) {
		            	failureCounter = 0;
		            }
		            else if(request.contains("FAILED") == true) {
		            	failureCounter++;
		            	if(failureCounter == 3) {
		            		System.out.println("CONSECUTIVE FAILURES MAXIMUM LIMIT");
		            	}
		            }
		            //System.out.println(RMport + " received " + request);
		            /* CHECK REQUEST and get response*/    
		            InetAddress IPAddress = receivePacket.getAddress();
		            int port = receivePacket.getPort();     
	                if(response != null) {
	                	sendData = response.getBytes();
	                }
	                else {
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
	
	
	Thread replicaManagerSomayeh = new Thread()
	{
		int failureCounter = 0;
		public void run()
		{
			DatagramSocket serverSocket = null;
			String response = null;
			try
			{
				byte[] receiveData = new byte[1024];
			    byte[] sendData = new byte[1024];
			    
			    while(true)
		        {
			    	serverSocket = new DatagramSocket(RMport3);
		            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		            serverSocket.receive(receivePacket);
		            String request = new String(receivePacket.getData());
		            System.out.println("SOMAYEHRM : received :" + request);
		            if(request.contains("SUCCESS") == true) {
		            	failureCounter = 0;
		            }
		            else if(request.contains("FAILED") == true) {
		            	failureCounter++;
		            	if(failureCounter == 3) {
		            		System.out.println("CONSECUTIVE FAILURES MAXIMUM LIMIT");
		            	}
		            }
		            //System.out.println(RMport + " received " + request);
		            /* CHECK REQUEST and get response*/    
		            InetAddress IPAddress = receivePacket.getAddress();
		            int port = receivePacket.getPort();     
	                if(response != null) {
	                	sendData = response.getBytes();
	                }
	                else {
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
	
	Thread smServer = new Thread()
	{
		public void run()
		{
			DatagramSocket serverSocket = null;
			String response = null;
			try
			{
				byte[] receiveData = new byte[1024];
			    byte[] sendData = new byte[1024];
			    
			    while(true)
		        {
			    	serverSocket = new DatagramSocket(central.udpCommonReplicaPort);
		            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		            serverSocket.receive(receivePacket);
		            String request = new String(receivePacket.getData());
		            //System.out.println(central.udpCommonReplicaPort + " SOMAYEH received " + request);
		            /* CHECK REQUEST and get response*/
		            response = handleUdpRequestSm(request);
		            InetAddress IPAddress = receivePacket.getAddress();
		            int port = central.udpRecvRespRM;     
	                if(response != null) {
	                	sendData = response.getBytes();
	                }
	                else {
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
	
	public String handleUdpRequestSm(String req)
	{
		String reqTokens[] = req.split(";");
		String serverName = reqTokens[1];
		ArrayList<String> timeSlotsList;
		int ctr = 0;
		Integer roomNumber;
		String strTimeSlots[];
		central objCentral = new central();
		Integer intResp = null;
		String strResp = null;
		String timeSlotsStr;
		byte[] smBuffer = new byte[1050];
		
		/* Check Server Type */
		serverName = serverName.substring(0, 3);
		
		/* Check Request Type */
		switch(reqTokens[0])
		{
		case "create":
			ctr = 4;
			timeSlotsList = new ArrayList<String>();
			timeSlotsStr = "";
			while(true) {
				if(reqTokens[ctr].substring(0, 2).equals("RM") == false) {
					timeSlotsList.add(reqTokens[ctr]);
				}
				else {
					break;
				}
				ctr++;
			}
			for(int ctrTwo = 0; ctrTwo < timeSlotsList.size(); ctrTwo++) {
				timeSlotsStr = timeSlotsStr + timeSlotsList.get(ctrTwo).replaceAll("\\s","") + ",";
				strResp = smImpl.UDPOperations(reqTokens[1],"create",serverName,serverName,
						reqTokens[2],reqTokens[3], timeSlotsList.get(ctrTwo).replaceAll("\\s","") + ",","");
				strResp = strResp + ";" + reqTokens[ctr] + ";" + reqTokens[ctr + 1] + ";";
			}
			if(strResp.substring(0,4).equals("true")) {
				strResp = "0;" + reqTokens[ctr] + ";" + reqTokens[ctr + 1] + ";";
			}
			else {
				strResp = "-1;" + reqTokens[ctr] + ";" + reqTokens[ctr + 1] + ";";
			}
			System.out.println(timeSlotsStr);
			
			break;
			
		case "delete":
			timeSlotsList = new ArrayList<String>();
			ctr = 4;
			timeSlotsStr = "";
			while(true) {
				if(reqTokens[ctr].substring(0, 2).equals("RM") == false) {
					timeSlotsList.add(reqTokens[ctr]);
				}
				else {
					break;
				}
				ctr++;
			}
			for(int ctrTwo = 0; ctrTwo < timeSlotsList.size(); ctrTwo++) {
				strResp = smImpl.UDPOperations(reqTokens[1],"delete",serverName,serverName,
						reqTokens[2],reqTokens[3], timeSlotsList.get(ctrTwo).replaceAll("\\s","") + ",","");
				strResp = strResp + ";" + reqTokens[ctr] + ";" + reqTokens[ctr + 1] + ";";
			}
			if(strResp.substring(0,4).equals("true")) {
				strResp = "0;" + reqTokens[ctr] + ";" + reqTokens[ctr + 1] + ";";
			}
			else {
				strResp = "-1;" + reqTokens[ctr] + ";" + reqTokens[ctr + 1] + ";";
			}
			break;
			
		case "book":
			roomNumber = Integer.parseInt(reqTokens[2]);
			
			strResp = smImpl.UDPOperations(reqTokens[1],"book",serverName,reqTokens[5],
					roomNumber.toString(),reqTokens[3], reqTokens[4].replaceAll("\\s",""),"");
			strResp = strResp.substring(0, 39) + ";" + reqTokens[6] + ";" + reqTokens[7] + ";";
			break;
			
		case "cancel":
			intResp = vjImpl.cancelBooking(reqTokens[1], reqTokens[2]);
			String tokens[] = reqTokens[2].split("_");			
			reqTokens[2] = tokens[4].substring(0, 3) + tokens[1] + tokens[2] + tokens[3] + tokens[0];
			strResp = smImpl.UDPOperations(reqTokens[1],"cancel",serverName,"",
					"","", "",reqTokens[2]);
			strResp = strResp.toString() + ";" + reqTokens[3] + ";" + reqTokens[4] + ";";
			if(strResp.substring(0,4).equals("true")) {
				strResp = "0;" + reqTokens[3] + ";" + reqTokens[3 + 1] + ";";
			}
			else {
				strResp = "-1;" + reqTokens[3] + ";" + reqTokens[3 + 1] + ";";
			}
			break;
			
		case "available":
			strResp = smImpl.UDPOperations(reqTokens[1],"count",serverName,"",
					"",reqTokens[2], "","");
			strResp = strResp.replaceAll("\\s","");
			System.out.println("vj:" + strResp);
			strResp = strResp.replace("\\t", "");
			strResp = strResp.substring(0, 3) + " " + strResp.substring(4, 5) + ", " + strResp.substring(5, 8) + " " +
					strResp.substring(9, 10) + ", " + strResp.substring(10, 13) + " " + strResp.substring(14, 15) + 
					";RM3;" + reqTokens[4] + ";";
			break;
			
		case "change":
			roomNumber = Integer.parseInt(reqTokens[4]);
			//reqTokens[5] = reqTokens[5].replaceAll("\\s","");
			strResp = smImpl.UDPOperations(reqTokens[1],"changeRsv",serverName,reqTokens[3],
					roomNumber.toString(),reqTokens[5], reqTokens[6].replaceAll("\\s",""),reqTokens[2]);
			strResp = strResp.toString() + reqTokens[8] + ";";
			break;
		}
		
		//System.out.println("ReplicaSM Resp: " + strResp);
		return strResp;
	};
	
	public void createServersMahsa() {
		
	}
	
	public void createServersSomayeh() {
		try
		{
			replicaManagerSomayeh.start();
			smServer.start();
		    System.out.println("Servers are ready and waiting ...");
		}
		catch(Exception e)
		{
			System.err.println("ERROR: " + e);
			e.printStackTrace();
		}
		
		System.out.println("Servers Exiting ...");
	}
	
	public static void main(String args[])
	{
		rmirpc objServerStarter = new rmirpc();
		objServerStarter.createServers();
		objServerStarter.createServersMahsa();
		objServerStarter.createServersSomayeh();
	}
}
