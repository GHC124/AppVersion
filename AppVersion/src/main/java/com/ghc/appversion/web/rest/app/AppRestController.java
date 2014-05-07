/**
 * AppRestController.java
 *
 *	
 */
package com.ghc.appversion.web.rest.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ghc.appversion.domain.admin.App;
import com.ghc.appversion.service.jpa.admin.app.AppService;
import com.ghc.appversion.web.rest.RestResponse;

/**
 * 
 */
@Controller
@RequestMapping("/rest/apps")
@PreAuthorize("isAuthenticated()")
public class AppRestController {
	@Autowired
	private AppService appService;

	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public RestResponse getAllApps() {
		RestResponse response = new RestResponse();
		response.setStatus(RestResponse.SUCCESS);
		
		List<App> apps = appService.findAll();
		response.setData(apps);
		
		return response;
	}
}
