/* Central Repository */
public class central {
	private String DVLServer = null;
	private String KKLServer = null;
	private String WSTServer = null;
	private Integer udpPortDVL = null;
	private Integer udpPortKKL = null;
	private Integer udpPortWST = null;
	
	public void setDVLServer(String inUrl)
	{
		DVLServer = inUrl;
	}
	
	public void setKKLServer(String inUrl)
	{
		KKLServer = inUrl;
	}
	
	public void setWSTServer(String inUrl)
	{
		WSTServer = inUrl;
	}
	
	public String getDVLServer()
	{
		return DVLServer;
	}
	
	public String getKKLServer()
	{
		return KKLServer;
	}
	
	public String getWSTServer()
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
