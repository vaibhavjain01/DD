import java.io.FileOutputStream;
import java.io.PrintStream;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class AppMain 
{
	public static void main(String args[]) throws Exception 
	{
		central cenRepoObj = new central();
		String a = "DVL";
		System.out.println(a.length());
		
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
		
		String urlDVL = "//localhost/RmiDVLServer";
		String urlKKL = "//localhost/RmiKKLServer";
		String urlWST = "//localhost/RmiWSTServer";
		
		cenRepoObj.setDVLServer(urlDVL);
		cenRepoObj.setKKLServer(urlKKL);
		cenRepoObj.setWSTServer(urlWST);
		cenRepoObj.setUdpPortDVL(9587);
		cenRepoObj.setUdpPortKKL(9687);
		cenRepoObj.setUdpPortWST(9787);
		
		/* Dorval Campus */
		rmirpc rmiDvlObj = new rmirpc("DVL", 9587, cenRepoObj);
		Naming.rebind(urlDVL, rmiDvlObj);
		
		/* Kirkland Campus */
		rmirpc rmiKklObj = new rmirpc("KKL", 9687, cenRepoObj);
		Naming.rebind(urlKKL, rmiKklObj);
		
		/* Westmount */
		rmirpc rmiWstObj = new rmirpc("WST", 9787, cenRepoObj);
		Naming.rebind(urlWST, rmiWstObj);
		
		System.out.println("Starting Client");
		/* Client */
		for(int i=0; i<1; i++)
		{
			String clientId = "";
			String clientId2 = "";
			String clientId3 = "";
			
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
			
			Thread.sleep(2000);
			adminClient client = new adminClient(clientId, cenRepoObj, 0);
			Thread.sleep(2000);
			adminClient client2 = new adminClient(clientId2, cenRepoObj, 0);
			Thread.sleep(2000);
			adminClient client3 = new adminClient(clientId3, cenRepoObj, 0);
			
			if((i >= 0) && (i <= 9))
			{
				clientId = "DVLS123" + i;
			}
			else if((i >= 10) && (i <= 99))
			{
				clientId = "DVLS12" + i;
			}
			else if((i >= 100) && (i <= 999))
			{
				clientId = "DVLS1" + i;
			}
			Thread.sleep(9000);
			adminClient client4 = new adminClient(clientId, cenRepoObj, 3);
		}
		
	}
}
