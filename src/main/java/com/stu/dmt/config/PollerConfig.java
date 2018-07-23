package com.stu.dmt.config;

import java.util.ArrayList;
import java.util.List;

public class PollerConfig {
	
	int corePoolSize;
    int maximumPoolSize;
    int pollCycle;
    int uploadCycle;
    int snmpTrapPort;
    int connTimedOut;
    int pollDown;
    List<String> snmpManagers;
    String pollerAgent;
    String community;
    boolean rectiEnabled;
    boolean microEnabled;
    boolean mateEnabled;
    String mateUrl;
    boolean serialEnabled;
    String serialPort;
    int serialBaud;
    String ccPath;
    int packCount;
    List<String> packPath = new ArrayList<String>();
    private String location = "/home/pi/STX-M4SS/RAM_data";
    
    public PollerConfig() {}
    
    public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public int getPollCycle() {
		return pollCycle;
	}

	public void setPollCycle(int pollCycle) {
		this.pollCycle = pollCycle;
	}

	public int getUploadCycle() {
		return uploadCycle;
	}

	public void setUploadCycle(int uploadCycle) {
		this.uploadCycle = uploadCycle;
	}

	public int getSnmpTrapPort() {
		return snmpTrapPort;
	}

	public void setSnmpTrapPort(int snmpTrapPort) {
		this.snmpTrapPort = snmpTrapPort;
	}

	public int getConnTimedOut() {
		return connTimedOut;
	}

	public void setConnTimedOut(int connTimedOut) {
		this.connTimedOut = connTimedOut;
	}

	public int getPollDown() {
		return pollDown;
	}

	public void setPollDown(int pollDown) {
		this.pollDown = pollDown;
	}

	public List<String> getSnmpManagers() {
		return snmpManagers;
	}

	public void setSnmpManagers(List<String> snmpManagers) {
		this.snmpManagers = snmpManagers;
	}

	public String getPollerAgent() {
		return pollerAgent;
	}

	public void setPollerAgent(String pollerAgent) {
		this.pollerAgent = pollerAgent;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}
    
	public boolean isRectiEnabled() {
		return rectiEnabled;
	}

	public void setRectiEnabled(boolean rectiEnabled) {
		this.rectiEnabled = rectiEnabled;
	}

	public boolean isMicroEnabled() {
		return microEnabled;
	}

	public void setMicroEnabled(boolean microEnabled) {
		this.microEnabled = microEnabled;
	}

	public boolean isMateEnabled() {
		return mateEnabled;
	}

	public void setMateEnabled(boolean mateEnabled) {
		this.mateEnabled = mateEnabled;
	}

	public String getMateUrl() {
		return mateUrl;
	}

	public void setMateUrl(String mateUrl) {
		this.mateUrl = mateUrl;
	}

	public boolean isSerialEnabled() {
		return serialEnabled;
	}

	public void setSerialEnabled(boolean serialEnabled) {
		this.serialEnabled = serialEnabled;
	}

	public String getSerialPort() {
		return serialPort;
	}

	public void setSerialPort(String serialPort) {
		this.serialPort = serialPort;
	}

	public int getSerialBaud() {
		return serialBaud;
	}

	public void setSerialBaud(int serialBaud) {
		this.serialBaud = serialBaud;
	}

	public int getPackCount() {
		return packCount;
	}

	public void setPackCount(int packCount) {
		this.packCount = packCount;
	}

	public List<String> getPackPath() {
		return packPath;
	}

	public void setPackPath(List<String> packPath) {
		this.packPath = packPath;
	}

	public String getCcPath() {
		return ccPath;
	}

	public void setCcPath(String ccPath) {
		this.ccPath = ccPath;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PollerConfig:\n");
		sb.append("\tcorePoolSize = ").append(getCorePoolSize()).append("\n");
		sb.append("\tmaxPoolSize = ").append(getMaximumPoolSize()).append("\n");
		sb.append("\tpollCycle = ").append(getPollCycle()).append(" sec\n");
		sb.append("\tPath = ").append(getLocation()).append("\n");
		return sb.toString();
	}
}
