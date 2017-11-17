package com.winhong.cicdweb;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rometools.rome.io.FeedException;
import com.winhong.plugins.cicd.tool.Tools;
import com.winhong.plugins.cicd.view.Dashboard;

@Path("dashboard")
@Consumes("application/json;charset=UTF-8")
public class DashboardRest {

	private static final Logger log = LoggerFactory.getLogger(DashboardRest.class);

	public DashboardRest() {
		// TODO Auto-generated constructor stub
	}
	// /dashboard/totalstatus
	
	@GET
	@Path("/totalstatus")
	@Produces("application/json;charset=utf-8")
	public String getTotalStatus(){
		try {
			return Dashboard.getAllProjectStatus();
		} catch (Exception e) {
 			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
 			return WebTools.Error(e);
		}
  	}

	@GET
	@Path("totalbuildstatus")
	@Produces("application/json;charset=utf-8")
	public String getTimeRangeStatfromShell(@QueryParam("startTime")long beginTime,@QueryParam("endTime") long endTime){
		try {
 			if (beginTime==0){
				final Calendar cal = Calendar.getInstance();
			    cal.add(Calendar.DATE, -1);
			     beginTime=cal.getTimeInMillis();
			    }
			if (endTime==0)
				endTime=System.currentTimeMillis();
			
			
			return Dashboard.getTimeRangeStatfromShell(beginTime, endTime);
		} catch (Exception e) {
 			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
 			return WebTools.Error(e);
		}
 	}
	
	
	
	@GET
	@Path("totalbuildsdetail")
	@Produces("application/json;charset=utf-8")
	public String getTimeRangeDetailfromShell(@QueryParam("startTime")long beginTime,@QueryParam("endTime") long endTime){
		try {
			if (beginTime==0){
				final Calendar cal = Calendar.getInstance();
			    cal.add(Calendar.DATE, -1);
			     beginTime=cal.getTimeInMillis();
			    }
			if (endTime==0)
				endTime=System.currentTimeMillis();
			
			
			return Dashboard.getTimeRangeDetailfromShell(beginTime, endTime);
		} catch (Exception e) {
 			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
 			return WebTools.Error(e);
		}
	}
	
	
	@GET
	@Path("recentruns")
	@Produces("application/json;charset=utf-8")
	public String getbuidls(@QueryParam("status")String status,@QueryParam("maxNumber")int maxNumber){
		try {
			log.debug("status:"+status);
			if (maxNumber<=0)
				maxNumber=5;
			if (status!=null && status.equals("failed"))
					return  Tools.ToUTF8(Dashboard.getFailedBuids(maxNumber));
			else
				return Tools.ToUTF8(Dashboard.getLatestExecutedBuid(maxNumber));
			
		} catch (Exception e) {
 			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
 			return WebTools.Error(e);
		}
	}

}
