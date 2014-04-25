/**
 * AppsController.java
 *
 *	
 */
package com.ghc.appversion.web.controller.admin;

import static com.ghc.appversion.service.jpa.admin.SQLConstants.PLATFORM_TYPE_ANDROID;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.PLATFORM_TYPE_IOS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ghc.appversion.domain.admin.App;
import com.ghc.appversion.domain.admin.Platform;
import com.ghc.appversion.service.jpa.admin.app.AppService;
import com.ghc.appversion.service.jpa.admin.app.PlatformService;
import com.ghc.appversion.web.form.ErrorMessage;
import com.ghc.appversion.web.form.ValidationResponse;
import com.ghc.appversion.web.form.admin.DataGrid;

/**
 * 
 */
@RequestMapping("/admin/apps")
@Controller
public class AppsController {
	@Autowired
	private AppService appService;
	
	@Autowired
	private PlatformService platformService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		App app = new App();
		model.addAttribute("app", app);
		
		Platform platform = new Platform();
		model.addAttribute("platform", platform);
		
		List<Platform> listPlatforms = platformService.findAll();
		Map<Long, String> platforms = new HashMap<>();
		for (Platform p : listPlatforms) {
			platforms.put(p.getId(), p.getName());
		}
		model.addAttribute("listPlatforms", platforms);
		
		Map<String, String> platformTypes = new HashMap<>();
		platformTypes.put(PLATFORM_TYPE_ANDROID, PLATFORM_TYPE_ANDROID);
		platformTypes.put(PLATFORM_TYPE_IOS, PLATFORM_TYPE_IOS);
		model.addAttribute("listPlatformTypes", platformTypes);
		
		return "admin/apps/list";
	}

	@RequestMapping(value = "/platform", params = "createAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ValidationResponse createPlatformAjax(Model model,
			@ModelAttribute(value = "platform") @Valid Platform platform,
			BindingResult result) {
		ValidationResponse res = new ValidationResponse();
		if (result.hasErrors()) {
			res.setStatus("FAIL");
			List<FieldError> allErrors = result.getFieldErrors();
			List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
			for (FieldError objectError : allErrors) {
				errorMesages.add(new ErrorMessage(objectError.getField(),
						objectError.getDefaultMessage()));
			}
			res.setResult(errorMesages);
		} else {
			res.setStatus("SUCCESS");
			platformService.save(platform);
		}

		return res;
	}

	@RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataGrid<App> listGrid(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sortBy,
			@RequestParam(value = "sord", required = false) String order) {
		Sort sort = null;
		String orderBy = sortBy;
		if (orderBy != null && order != null) {
			if (order.equals("desc")) {
				sort = new Sort(Sort.Direction.DESC, orderBy);
			} else {
				sort = new Sort(Sort.Direction.ASC, orderBy);
			}
		}
		PageRequest pageRequest = null;
		if (sort != null) {
			pageRequest = new PageRequest(page - 1, rows, sort);
		} else {
			pageRequest = new PageRequest(page - 1, rows);
		}
		Page<App> userPage = appService.findAllByPage(pageRequest);
		DataGrid<App> appGrid = new DataGrid<>();
		appGrid.setCurrentPage(userPage.getNumber() + 1);
		appGrid.setTotalPages(userPage.getTotalPages());
		appGrid.setTotalRecords(userPage.getTotalElements());
		appGrid.setData(userPage.getContent());

		return appGrid;
	}
	
	@RequestMapping(value = "/platform", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataGrid<Platform> listPlatform(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sortBy,
			@RequestParam(value = "sord", required = false) String order) {
		Sort sort = null;
		String orderBy = sortBy;
		if (orderBy != null && order != null) {
			if (order.equals("desc")) {
				sort = new Sort(Sort.Direction.DESC, orderBy);
			} else {
				sort = new Sort(Sort.Direction.ASC, orderBy);
			}
		}
		PageRequest pageRequest = null;
		if (sort != null) {
			pageRequest = new PageRequest(page - 1, rows, sort);
		} else {
			pageRequest = new PageRequest(page - 1, rows);
		}
		Page<Platform> userPage = platformService.findAllByPage(pageRequest);
		DataGrid<Platform> platformGrid = new DataGrid<>();
		platformGrid.setCurrentPage(userPage.getNumber() + 1);
		platformGrid.setTotalPages(userPage.getTotalPages());
		platformGrid.setTotalRecords(userPage.getTotalElements());
		platformGrid.setData(userPage.getContent());

		return platformGrid;
	}
}