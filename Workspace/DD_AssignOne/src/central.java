import drrs.drrsCorba;

/* Central Repository */
public class central {
	private drrsCorba DVLServer = null;
	private drrsCorba KKLServer = null;
	private drrsCorba WSTServer = null;
	private Integer udpPortDVL = null;
	private Integer udpPortKKL = null;
	private Integer udpPortWST = null;
	
	public void setDVLServer(drrsCorba inUrl)
	{
		DVLServer = inUrl;
	}
	
	public void setKKLServer(drrsCorba inUrl)
	{
		KKLServer = inUrl;
	}
	
	public void setWSTServer(drrsCorba inUrl)
	{
		WSTServer = inUrl;
	}
	
	public drrsCorba getDVLServer()
	{
		return DVLServer;
	}
	
	public drrsCorba getKKLServer()
	{
		return KKLServer;
	}
	
	public drrsCorba getWSTServer()
	{
		return WSTServer;
	}
	
	public void setUdpPortDVL(Integer udpPort)
	{
		udpPortDVL = udpPort;
	}
	
	public void setUdpPortKKL(Integer udpPort)
	{
		udpPortKKL = udpPort;
	}
	
	public void setUdpPortWST(Integer udpPort)
	{
		udpPortWST = udpPort;
	}
	
	public Integer getUdpPortDVL()
	{
		return udpPortDVL;
	}
	
	public Integer getUdpPortKKL()
	{
		return udpPortKKL;
	}
	
	public Integer getUdpPortWST()
	{
		return udpPortWST;
	}
}
