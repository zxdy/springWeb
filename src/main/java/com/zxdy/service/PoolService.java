package com.zxdy.service;


import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxdy.dao.PoolDao;
@Service("PoolService")
@Path(value = "/pool")
public class PoolService {
	@Autowired
	private PoolDao poolDao;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/1.0/list")
	public String getPoolList(
			@DefaultValue("") @FormParam("serverType") String serverType) {
		JSONArray result=poolDao.getPoolList(serverType);
		return result.toString();
	}
}
