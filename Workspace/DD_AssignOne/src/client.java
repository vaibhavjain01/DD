import java.rmi.*;

public class client {
	public static void main(String args[]) throws Exception {
		try
		{
			rmiinterface rpcObj = (rmiinterface)Naming.lookup("//localhost/RmiServer");
			System.out.println(rpcObj.getMsg());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
