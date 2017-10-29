import java.io.FileOutputStream;
import java.io.PrintStream;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class AppMain
{
	private central cenRepoObj = new central();
	
	public Integer initServers(Integer portDVL, Integer portKKL, Integer portWST) throws Exception
	{
		Integer rt = 0;
		
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
		
		//String urlDVL = "//localhost/RmiDVLServer";
		//String urlKKL = "//localhost/RmiKKLServer";
		//String urlWST = "//localhost/RmiWSTServer";
		
		//cenRepoObj.setDVLServer(urlDVL);
		//cenRepoObj.setKKLServer(urlKKL);
		//cenRepoObj.setWSTServer(urlWST);
		//cenRepoObj.setUdpPortDVL(portDVL);
		//cenRepoObj.setUdpPortKKL(portKKL);
		//cenRepoObj.setUdpPortWST(portWST);
		
		/* Dorval Campus */
		//rmirpc rmiDvlObj = new rmirpc("DVL", portDVL, cenRepoObj);
		//Naming.rebind(urlDVL, rmiDvlObj);
		
		/* Kirkland Campus */
		//rmirpc rmiKklObj = new rmirpc("KKL", portKKL, cenRepoObj);
		//Naming.rebind(urlKKL, rmiKklObj);
		
		/* Westmount */
		//rmirpc rmiWstObj = new rmirpc("WST", portWST, cenRepoObj);
		//Naming.rebind(urlWST, rmiWstObj);
		
		
		return rt;
	}
	
	public Integer initAdmin(Integer numAdminsPerServer, Integer operation)
	{
		Integer rt = 0;
		Integer roomNum = 200;
		
		System.out.println("Starting Client");
		/* Client */
		for(int i = 0; i < numAdminsPerServer; i++, roomNum++)
		{
			String clientId = null;
			String clientId2 = null;
			String clientId3 = null;
			
			if((i >= 0) && (i <= 9))
			{
				clientId = "DVLA123" + i;
				clientId2 = "KKLA123" + i;
				clientId3 = "WSTA123" + i;
			}
			else if((i >= 10) && (i <= 99))
			{
				clientId = "DVLA12" + i;
				clientId2 = "KKLA12" + i;
				clientId3 = "WSTA12" + i;
			}
			else if((i >= 100) && (i <= 999))
			{
				clientId = "DVLA1" + i;
				clientId2 = "KKLA1" + i;
				clientId3 = "WSTA1" + i;
			}
			else if((i >= 1000) && (i <= 9999))
			{
				clientId = "DVLA" + i;
				clientId2 = "KKLA" + i;
				clientId3 = "WSTA" + i;
			}
			
			try 
			{
				//Thread.sleep(200);
				//adminClient client = new adminClient(clientId, cenRepoObj, operation, roomNum, "27-10-2017");
				Thread.sleep(100);
				//adminClient client2 = new adminClient(clientId2, cenRepoObj, operation, roomNum, "27-10-2017");
				Thread.sleep(100);
				//adminClient client3 = new adminClient(clientId3, cenRepoObj, operation, roomNum, "27-10-2017");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		return rt;
	}
	
	public Integer initStudent(Integer numStudents, Integer opration)
	{
		Integer rt = 0;
		Integer roomNum = 200;
		
		System.out.println("Starting Client");
		/* Client */
		for(int i = 0; i < numStudents; i++, roomNum++)
		{
			String clientId = null;
			String clientId2 = null;
			String clientId3 = null;
			
			if((i >= 0) && (i <= 9))
			{
				clientId = "DVLS123" + i;
				clientId2 = "KKLS234" + i;
				clientId3 = "WSTS345" + i;
			}
			else if((i >= 10) && (i <= 99))
			{
				clientId = "DVLS12" + i;
				clientId2 = "KKLS23" + i;
				clientId3 = "WSTS34" + i;
			}
			else if((i >= 100) && (i <= 999))
			{
				clientId = "DVLS1" + i;
				clientId2 = "KKLS2" + i;
				clientId3 = "WSTS3" + i;
			}
			else if((i >= 1000) && (i <= 9999))
			{
				clientId = "DVLS" + i;
				clientId2 = "KKLS" + i;
				clientId3 = "WSTS" + i;
			}
			
			try 
			{
				//adminClient client = new adminClient(clientId, cenRepoObj, opration, roomNum, "27-10-2017");
				Thread.sleep(100);
				//adminClient client2 = new adminClient(clientId2, cenRepoObj, opration, roomNum, "27-10-2017");
				Thread.sleep(100);
				//adminClient client3 = new adminClient(clientId3, cenRepoObj, opration, roomNum, "27-10-2017");
				Thread.sleep(100);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return rt;
	}
	
	/* Test Cases */
	/* Checks for availability
	 * Request sent from DVL
	 * Checks for WST and KKL
	 */
	private void TC1(Integer numClients)
	{
		this.initStudent(10, 3);
	}
	
	private void TCBasic()
	{
		
		try 
		{
			this.initAdmin(1, 0);
			Thread.sleep(5000);
			this.initStudent(1, 2);
			Thread.sleep(2000);
			this.initStudent(1, 3);
			Thread.sleep(20000);
			this.initAdmin(1, 1);
			Thread.sleep(5000);
			this.initStudent(1, 3);
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Creates Rooms, Check Availability, Book All Rooms, CheckAvailability , Delete All Rooms, Check Availability */
	private void TC2()
	{
		try 
		{
			this.initAdmin(10, 0);
			Thread.sleep(5000);
			this.initStudent(10, 3);
			Thread.sleep(10);
			this.initStudent(10, 2);
			Thread.sleep(10);
			this.initStudent(10, 3);
			Thread.sleep(50000);
			this.initAdmin(10, 1);
			Thread.sleep(100);
			this.initStudent(10, 3);
			
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void TC3()
	{
		try 
		{
			this.initAdmin(1, 0);
			Thread.sleep(5000);
			this.initStudent(1, 3);
			Thread.sleep(1000);
			this.initStudent(1, 2);
			Thread.sleep(1000);
			this.initStudent(1, 2);
			Thread.sleep(1000);
			this.initStudent(1, 3);
			
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void TC4()
	{
		try 
		{
			this.initAdmin(2, 0);
			Thread.sleep(5000);
			this.initStudent(1, 3);
			Thread.sleep(1000);
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws Exception 
	{
		Integer rt = 0;
		AppMain objDriver = new AppMain();
		
		rt = objDriver.initServers(9587, 9687, 9787);
		//rt = objDriver.initAdmin(200, 0);
		//Thread.sleep(12000);
		
		objDriver.TCBasic();
		//objDriver.TC2();
		//objDriver.TC3();
	}
	

}
