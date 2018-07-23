package com.stu.dmt;

public class PollResult {
	
	private String device;
	private String resource;
	private PollStatus status;
	
	public PollResult() {}
	
	public PollResult(String device, String resource, PollStatus status) {
		this.device = device;
		this.resource = resource;
		this.status = status;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public PollStatus getStatus() {
		return status;
	}

	public void setStatus(PollStatus status) {
		this.status = status;
	}
	
}
