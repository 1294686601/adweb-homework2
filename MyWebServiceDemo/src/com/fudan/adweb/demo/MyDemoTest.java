package com.fudan.adweb.demo;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Produces("text/html")
@Consumes("text/html")
@Path("demotest")
public class MyDemoTest {

	@GET
	@Path("example")
	@Produces("text/html")
	@Consumes("text/html")
	public String example(@QueryParam("pstr") String jstr) {
		//throw new UnsupportedOperationException("Not yet implemented.");
		return jstr;
	}
}