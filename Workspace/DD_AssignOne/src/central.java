import drrs.drrsCorba;

/* Central Repository */
public class central {
	public static Integer udpPortRM1 = 9851;
	public static Integer udpPortDVLRM1 = 9852;
	public static Integer udpPortKKLRM1 = 9853;
	public static Integer udpPortWSTRM1 = 9854;
	
	public static Integer udpPortRM2 = 9751;
	public static Integer udpPortDVLRM2 = 9752;
	public static Integer udpPortKKLRM2 = 9753;
	public static Integer udpPortWSTRM2 = 9754;
	
	public static Integer udpPortRM3 = 9651;
	public static Integer udpPortDVLRM3 = 9652;
	public static Integer udpPortKKLRM3 = 9653;
	public static Integer udpPortWSTRM3 = 9654;
	
	public static Integer udpPortFE = 9889;
	
	public static drrsCorba frontEndURL = null;
	
	public static void setFrontEnd(drrsCorba inUrl)
	{
		frontEndURL = inUrl;
	}
	
	public static drrsCorba getFrontEnd()
	{
		return frontEndURL;
	}
}
