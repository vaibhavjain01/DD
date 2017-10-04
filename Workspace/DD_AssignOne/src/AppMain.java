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
		adminClient client = new adminClient("DVLA1234", cenRepoObj);
		
	}
}