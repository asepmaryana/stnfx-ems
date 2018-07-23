package com.stu.dmt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.stu.dmt.config.PollerConfig;
import com.stu.dmt.constant.ParameterConstant;
import com.stu.dmt.model.Mate;
import com.stu.dmt.model.Monitoring;
import com.stu.dmt.repository.MonitoringJdbcRepository;
import com.stu.dmt.service.MateService;

public class MatePoller implements Poller {
	
	private ApplicationContext ctx;
	private PollerConfig cfg;
	private List<Long> listOfPorts = new ArrayList<Long>();
	
	public MatePoller(ApplicationContext ctx){
		this.ctx = ctx;
		this.cfg  = ctx.getBean(PollerConfig.class);
	}
	
	public ApplicationContext getCtx() {
		return ctx;
	}

	public PollerConfig getCfg() {
		return cfg;
	}

	public ClientHttpRequestFactory getRequestFactory(Mate mate) {
		SimpleClientHttpRequestFactory srf = new SimpleClientHttpRequestFactory();
		srf.setConnectTimeout(mate.getConnectTimeout() * 1000);
		srf.setReadTimeout(mate.getReadTimeout() * 1000);
		return srf;
	}
	
	@Override
	public PollResult call() {
		MateService mateService = ctx.getBean(MateService.class);
		MonitoringJdbcRepository service = ctx.getBean(MonitoringJdbcRepository.class);
		Mate mate = mateService.getEnabled();
		if(mate == null) LoggerFactory.getLogger(MatePoller.class).info("Mate does not exist.");
		else if(mate.getId() == 1) LoggerFactory.getLogger(MatePoller.class).info("Mate 2 is active, so ignored.");
		else {
			
			try {
				RestTemplate restTemplate = new RestTemplate(getRequestFactory(mate));
				String json = restTemplate.getForObject("http://"+mate.getAddress()+"/Dev_status.cgi?&Port=0", String.class);			
				LoggerFactory.getLogger(MatePoller.class).info(mate.getAddress()+" --> "+json);
				
				JSONParser parser = new JSONParser();
				Object o = parser.parse(json);
				JSONObject root = (JSONObject) o;				
				JSONObject devstatus = (JSONObject) root.get("devstatus");
				//long sysTime = (Long) devstatus.get("Sys_Time");
				//double sysBatt= (Double) devstatus.get("Sys_Batt_V");
				// cc param
				
				Date dtime = new Date();
				List<Monitoring> params = new ArrayList<Monitoring>();
				JSONArray ports = (JSONArray) devstatus.get("ports");
				Iterator<?> iterator = ports.iterator();
				while (iterator.hasNext()) {
					JSONObject row = (JSONObject) iterator.next();
					if(row.get("Dev").equals("CC")) {
						long port = (Long) row.get("Port");
						listOfPorts.add(new Long(port));
						double battVoltage	= (Double) row.get("Batt_V");
						double pvVoltage	= (Double) row.get("In_V");
						double pvCurrent	= new Double((Long) row.get("In_I"));
						double outCurrent	= (Double) row.get("Out_I");
						double outKWH		= (Double) row.get("Out_kWh");
						double outAH		= new Double((Long) row.get("Out_AH"));
						String strMode		= (String) row.get("CC_mode");
						strMode				= strMode.trim();
						
						double mode = 0;
						if(strMode.equals("Silent")) mode = 0;
						else if(strMode.equals("Float")) mode = 1;
						else if(strMode.equals("Bulk")) mode = 2;
						else if(strMode.equals("Absorb")) mode = 3;
						else mode = 4;
						
						if(port == 1) {
							params.add(new Monitoring(ParameterConstant.PV_VOLTAGE, pvVoltage, dtime));
							params.add(new Monitoring(ParameterConstant.PV_CURRENT, pvCurrent, dtime));
							params.add(new Monitoring(ParameterConstant.BATT_VOLTAGE, battVoltage, dtime));
							params.add(new Monitoring(ParameterConstant.BATT_CURRENT, outCurrent, dtime));
							params.add(new Monitoring(ParameterConstant.KWH_CHARGE, outKWH, dtime));
							params.add(new Monitoring(ParameterConstant.AH_CHARGE, outAH, dtime));
							params.add(new Monitoring(ParameterConstant.CHARGE_STATE, mode, dtime));
							params.add(new Monitoring(ParameterConstant.INPUT_POWER, pvVoltage * pvCurrent, dtime));
							params.add(new Monitoring(ParameterConstant.OUT_POWER, battVoltage * outCurrent, dtime));
						}
						else if(port == 2) {
							params.add(new Monitoring(ParameterConstant.PV_VOLTAGE_2, pvVoltage, dtime));
							params.add(new Monitoring(ParameterConstant.PV_CURRENT_2, pvCurrent, dtime));
							params.add(new Monitoring(ParameterConstant.BATT_VOLTAGE_2, battVoltage, dtime));
							params.add(new Monitoring(ParameterConstant.BATT_CURRENT_2, outCurrent, dtime));
							params.add(new Monitoring(ParameterConstant.KWH_CHARGE_2, outKWH, dtime));
							params.add(new Monitoring(ParameterConstant.AH_CHARGE_2, outAH, dtime));
							params.add(new Monitoring(ParameterConstant.CHARGE_STATE_2, mode, dtime));
							params.add(new Monitoring(ParameterConstant.INPUT_POWER_2, pvVoltage * pvCurrent, dtime));
							params.add(new Monitoring(ParameterConstant.OUT_POWER_2, battVoltage * outCurrent, dtime));
						}
					}
				}
				service.update(params);
				writeToFile(params);
				
				return new PollResult("MATE", null, PollStatus.SUCCESS);
			}
			catch(Exception e){
				service.update();
				String path = getCfg().getLocation();
				
				write(path+File.separator+"Vpv", "0");
				write(path+File.separator+"Ipv", "0");
				write(path+File.separator+"Vbat", "0");
				write(path+File.separator+"Ibat", "0");
				write(path+File.separator+"KWHtot", "0");
				write(path+File.separator+"AHtot", "0");
				write(path+File.separator+"Chstate", "0");
				write(path+File.separator+"Pinput", "0");
				write(path+File.separator+"Poutput", "0");
				
				write(path+File.separator+"Vpv1", "0");
				write(path+File.separator+"Ipv1", "0");
				write(path+File.separator+"Vbat1", "0");
				write(path+File.separator+"Ibat1", "0");
				write(path+File.separator+"KWHtot1", "0");
				write(path+File.separator+"AHtot1", "0");
				write(path+File.separator+"Chstate1", "0");
				write(path+File.separator+"Pinput1", "0");
				write(path+File.separator+"Poutput1", "0");
				
				LoggerFactory.getLogger(MatePoller.class).error("Process mate error: ", e);				
			}
			return new PollResult("MATE", null, PollStatus.FAILED);
		}
		return new PollResult("MATE", null, PollStatus.SKIPPED);
	}
	
	private void write(String fileName, String value) {
		try {
			if(value.contains(".")) value = value.substring(0, value.indexOf("."));
			File file = new File(fileName);
			if(!file.exists()) file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(value);
			writer.close();
			LoggerFactory.getLogger(MatePoller.class).debug("Writing: "+value+" to: "+fileName+" --> succeed.");
		}
		catch (IOException e) {
			LoggerFactory.getLogger(MatePoller.class).error("error create file "+fileName, e);
		}
	}
	private void writeToFile(List<Monitoring> params) {
		String path = getCfg().getLocation();
		for(Monitoring mon : params) {
			if(mon.getId() == ParameterConstant.PV_VOLTAGE) write(path+File.separator+"Vpv", String.valueOf(mon.getValue() * 100));
			else if(mon.getId() == ParameterConstant.PV_CURRENT) write(path+File.separator+"Ipv", String.valueOf(mon.getValue() * 100));
			else if(mon.getId() == ParameterConstant.BATT_VOLTAGE) write(path+File.separator+"Vbat", String.valueOf(mon.getValue() * 100));
			else if(mon.getId() == ParameterConstant.BATT_CURRENT) write(path+File.separator+"Ibat", String.valueOf(mon.getValue() * 100));
			else if(mon.getId() == ParameterConstant.KWH_CHARGE) write(path+File.separator+"KWHtot", String.valueOf(mon.getValue() * 100));
			else if(mon.getId() == ParameterConstant.AH_CHARGE) write(path+File.separator+"AHtot", String.valueOf(mon.getValue()));
			else if(mon.getId() == ParameterConstant.CHARGE_STATE) write(path+File.separator+"Chstate", String.valueOf(mon.getValue()));
			else if(mon.getId() == ParameterConstant.INPUT_POWER) write(path+File.separator+"Pinput", String.valueOf(Math.round(mon.getValue() * 100)));
			else if(mon.getId() == ParameterConstant.OUT_POWER) write(path+File.separator+"Poutput", String.valueOf(Math.round(mon.getValue() * 100)));
			
			else if(mon.getId() == ParameterConstant.PV_VOLTAGE_2) write(path+File.separator+"Vpv1", String.valueOf(mon.getValue() * 100));
			else if(mon.getId() == ParameterConstant.PV_CURRENT_2) write(path+File.separator+"Ipv1", String.valueOf(mon.getValue() * 100));
			else if(mon.getId() == ParameterConstant.BATT_VOLTAGE_2) write(path+File.separator+"Vbat1", String.valueOf(mon.getValue() * 100));
			else if(mon.getId() == ParameterConstant.BATT_CURRENT_2) write(path+File.separator+"Ibat1", String.valueOf(mon.getValue() * 100));
			else if(mon.getId() == ParameterConstant.KWH_CHARGE_2) write(path+File.separator+"KWHtot1", String.valueOf(mon.getValue() * 100));
			else if(mon.getId() == ParameterConstant.AH_CHARGE_2) write(path+File.separator+"AHtot1", String.valueOf(mon.getValue()));
			else if(mon.getId() == ParameterConstant.CHARGE_STATE_2) write(path+File.separator+"Chstate1", String.valueOf(mon.getValue()));
			else if(mon.getId() == ParameterConstant.INPUT_POWER_2) write(path+File.separator+"Pinput1", String.valueOf(Math.round(mon.getValue() * 100)));
			else if(mon.getId() == ParameterConstant.OUT_POWER_2) write(path+File.separator+"Poutput1", String.valueOf(Math.round(mon.getValue() * 100)));
		}
	}
}
