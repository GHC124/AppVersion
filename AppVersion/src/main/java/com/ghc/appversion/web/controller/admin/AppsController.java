/**
 * AppsController.java
 *
 *	
 */
package com.ghc.appversion.web.controller.admin;

import static com.ghc.appversion.service.jpa.admin.SQLConstants.PLATFORM_TYPE_ANDROID;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.PLATFORM_TYPE_IOS;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ghc.appversion.domain.admin.App;
import com.ghc.appversion.domain.admin.AppGroup;
import com.ghc.appversion.domain.admin.AppGroupCheck;
import com.ghc.appversion.domain.admin.AppSummary;
import com.ghc.appversion.domain.admin.AppVersions;
import com.ghc.appversion.domain.admin.Platform;
import com.ghc.appversion.service.jpa.admin.app.AppGroupCheckService;
import com.ghc.appversion.service.jpa.admin.app.AppGroupService;
import com.ghc.appversion.service.jpa.admin.app.AppService;
import com.ghc.appversion.service.jpa.admin.app.AppSummaryService;
import com.ghc.appversion.service.jpa.admin.app.AppVersionsService;
import com.ghc.appversion.service.jpa.admin.app.PlatformService;
import com.ghc.appversion.util.LogUtil;
import com.ghc.appversion.web.form.DataGrid;
import com.ghc.appversion.web.form.ErrorMessage;
import com.ghc.appversion.web.form.ValidationResponse;
import com.ghc.appversion.web.util.UploadUtil;

/**
 * 
 */
@RequestMapping("/admin/apps")
@Controller
public class AppsController extends AbstractAdminController {
	@Autowired
	private AppService appService;

	@Autowired
	private AppSummaryService appSummaryService;

	@Autowired
	private AppVersionsService appVersionsService;

	@Autowired
	private PlatformService platformService;

	@Autowired
	private AppGroupService appGroupService;

	@Autowired
	private AppGroupCheckService appGroupCheckService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		App app = new App();
		model.addAttribute("app", app);

		AppVersions appVersions = new AppVersions();
		appVersions.setReleaseDate(new LocalDateTime());
		model.addAttribute("appVersions", appVersions);

		Platform platform = new Platform();
		model.addAttribute("platform", platform);

		List<Platform> listPlatforms = platformService.findAll();
		Map<Long, String> platforms = new HashMap<>();
		for (Platform p : listPlatforms) {
			platforms.put(p.getId(), p.getName());
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String jsonPlatforms = objectMapper.writeValueAsString(platforms);
			model.addAttribute("jsonPlatforms", jsonPlatforms);
		} catch (IOException e) {
			LogUtil.error("error parsing platforms to JSON");
		}

		Map<String, String> platformTypes = new HashMap<>();
		platformTypes.put(PLATFORM_TYPE_ANDROID, PLATFORM_TYPE_ANDROID);
		platformTypes.put(PLATFORM_TYPE_IOS, PLATFORM_TYPE_IOS);
		model.addAttribute("listPlatformTypes", platformTypes);

		return "admin/apps/list";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable(value = "id") Long id, Model model) {
		App app = appService.findById(id);
		model.addAttribute("app", app);

		List<Platform> listPlatforms = platformService.findAll();
		Map<Long, String> platforms = new HashMap<>();
		for (Platform p : listPlatforms) {
			platforms.put(p.getId(), p.getName());
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String jsonPlatforms = objectMapper.writeValueAsString(platforms);
			model.addAttribute("jsonPlatforms", jsonPlatforms);
		} catch (IOException e) {
			LogUtil.error("error parsing platforms to JSON");
		}

		AppVersions appVersions = new AppVersions();
		appVersions.setReleaseDate(new LocalDateTime());
		model.addAttribute("appVersions", appVersions);

		return "admin/apps/show";
	}

	@RequestMapping(value = "/{id}", params = "showAjax", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public App showAjax(@PathVariable(value = "id") Long id) {
		App app = appService.findById(id);

		return app;
	}

	@RequestMapping(value = "/platform/{id}", params = "showAjax", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Platform showPlatformAjax(@PathVariable(value = "id") Long id) {
		Platform platform = platformService.findById(id);

		return platform;
	}

	@RequestMapping(value = "/platform", params = "createAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ValidationResponse createPlatformAjax(Model model,
			@ModelAttribute(value = "platform") @Valid Platform platform,
			BindingResult result) {
		ValidationResponse res = new ValidationResponse();
		if (result.hasErrors()) {
			res.setStatus(ValidationResponse.FAIL);
			List<FieldError> allErrors = result.getFieldErrors();
			for (FieldError objectError : allErrors) {
				res.addErrorMessage(new ErrorMessage(objectError.getField(),
						objectError.getDefaultMessage()));
			}
		} else {
			Platform savePlatform = platformService.save(platform);
			res.setExtraData(savePlatform.getId().toString());
			res.setStatus(ValidationResponse.SUCCESS);
		}

		return res;
	}

	@RequestMapping(params = "validateAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ValidationResponse validateAjax(
			@ModelAttribute(value = "app") @Valid App app,
			BindingResult result, MultipartHttpServletRequest request,
			Locale locale) {
		ValidationResponse res = new ValidationResponse();
		res.setStatus(ValidationResponse.FAIL);
		if (result.hasErrors()) {
			List<FieldError> allErrors = result.getFieldErrors();
			for (FieldError objectError : allErrors) {
				res.addErrorMessage(new ErrorMessage(objectError.getField(),
						objectError.getDefaultMessage()));
			}
		} else {
			// Check name
			App exitApp = appService.findByName(app.getName());
			if (exitApp != null) {
				res.addErrorMessage(new ErrorMessage("name", messageSource
						.getMessage("validation.appName.Exist.message",
								new Object[] {}, locale)));
				return res;
			}
			// get the file from the request object
			Iterator<String> itr = request.getFileNames();
			if (itr.hasNext()) {
				MultipartFile mpf = request.getFile(itr.next());
				String type = mpf.getContentType();
				if (UploadUtil.isValidPhoto(type)) {
					res.setStatus(ValidationResponse.SUCCESS);
				} else {
					res.addErrorMessage(new ErrorMessage("iconUrl",
							messageSource.getMessage(
									"validation.icon.InvalidType.message",
									new Object[] { mPhotoType }, locale)));
				}
			} else {
				res.addErrorMessage(new ErrorMessage("iconUrl", messageSource
						.getMessage("validation.icon.NotEmpty.message",
								new Object[] {}, locale)));
			}
		}

		return res;
	}

	@RequestMapping(params = "createAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ValidationResponse createAjax(
			@ModelAttribute(value = "app") @Valid App app,
			BindingResult result, MultipartHttpServletRequest request,
			Locale locale) {
		ValidationResponse res = new ValidationResponse();
		res.setStatus(ValidationResponse.FAIL);
		if (result.hasErrors()) {
			List<FieldError> allErrors = result.getFieldErrors();
			for (FieldError objectError : allErrors) {
				res.addErrorMessage(new ErrorMessage(objectError.getField(),
						objectError.getDefaultMessage()));
			}
		} else {
			// Check name
			App exitApp = appService.findByName(app.getName());
			if (exitApp != null) {
				res.addErrorMessage(new ErrorMessage("name", messageSource
						.getMessage("validation.appName.Exist.message",
								new Object[] {}, locale)));
				return res;
			}
			// get the file from the request object
			Iterator<String> itr = request.getFileNames();
			if (!itr.hasNext()) {
				res.addErrorMessage(new ErrorMessage("iconUrl", messageSource
						.getMessage("validation.icon.NotEmpty.message",
								new Object[] {}, locale)));
				return res;
			}
			MultipartFile mpf = request.getFile(itr.next());
			try {
				String type = mpf.getContentType();
				if (UploadUtil.isValidPhoto(type)) {
					String rootDirectory = mUploadRootDirectory;
					UploadUtil.createUploadFolder(rootDirectory);
					String iconUrl = UploadUtil.saveIconFile(rootDirectory,
							mpf.getInputStream());
					app.setIconUrl(iconUrl);
					App saveApp = appService.save(app);
					// Return new AppId
					res.setExtraData(saveApp.getId().toString());
					res.setStatus(ValidationResponse.SUCCESS);
				} else {
					res.addErrorMessage(new ErrorMessage("iconUrl",
							messageSource.getMessage(
									"validation.icon.InvalidType.message",
									new Object[] { mPhotoType }, locale)));
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return res;
	}

	@RequestMapping(value = "/{id}", params = "updateAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ValidationResponse updateAjax(Model model,
			@ModelAttribute(value = "app") @Valid App app,
			BindingResult result, Locale locale) {
		ValidationResponse res = new ValidationResponse();
		res.setStatus(ValidationResponse.FAIL);
		if (result.hasErrors()) {
			List<FieldError> allErrors = result.getFieldErrors();
			for (FieldError objectError : allErrors) {
				res.addErrorMessage(new ErrorMessage(objectError.getField(),
						objectError.getDefaultMessage()));
			}
		} else {
			// Check name
			App exitApp = appService.findByName(app.getName());
			if (exitApp != null && exitApp.getId() != app.getId()) {
				res.addErrorMessage(new ErrorMessage("name", messageSource
						.getMessage("validation.appName.Exist.message",
								new Object[] {}, locale)));
				return res;
			}
			res.setStatus(ValidationResponse.SUCCESS);
			appService.save(app);
		}

		return res;
	}

	@RequestMapping(value = "/version", params = "validateAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ValidationResponse validateVersionAjax(
			@ModelAttribute(value = "appVersions") @Valid AppVersions appVersions,
			BindingResult result, MultipartHttpServletRequest request,
			Locale locale) {
		ValidationResponse res = new ValidationResponse();
		res.setStatus(ValidationResponse.FAIL);
		if (result.hasErrors()) {
			List<FieldError> allErrors = result.getFieldErrors();
			for (FieldError objectError : allErrors) {
				res.addErrorMessage(new ErrorMessage(objectError.getField(),
						objectError.getDefaultMessage()));
			}
		} else {
			// validate version
			List<AppVersions> latestVersions = appVersionsService
					.latestVersion(appVersions.getAppId()); 
			AppVersions latestVersion = latestVersion(latestVersions);
			if (latestVersion != null
					&& compareVersions(latestVersion.getVersion(),
							appVersions.getVersion()) >= 0) {
				res.addErrorMessage(new ErrorMessage("version", messageSource
						.getMessage("validation.version.Smaller.message",
								new Object[] { latestVersion.getVersion() },
								locale)));
				return res;
			}
			// get the file from the request object
			Iterator<String> itr = request.getFileNames();
			if (!itr.hasNext()) {
				res.addErrorMessage(new ErrorMessage("appDownloadUrl",
						messageSource.getMessage(
								"validation.file.NotEmpty.message",
								new Object[] {}, locale)));
				return res;
			}
			MultipartFile mpf = request.getFile(itr.next());
			String originalName = mpf.getOriginalFilename();
			if (UploadUtil.isValidAndroid(originalName)) {
				res.setStatus(ValidationResponse.SUCCESS);
			} else {
				res.addErrorMessage(new ErrorMessage("appDownloadUrl",
						messageSource.getMessage(
								"validation.file.InvalidType.message",
								new Object[] { mAndroidType }, locale)));
			}
		}

		return res;
	}

	@RequestMapping(value = "/version", params = "createAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ValidationResponse createVersionAjax(
			@ModelAttribute(value = "appVersions") @Valid AppVersions appVersions,
			BindingResult result, MultipartHttpServletRequest request,
			Locale locale) {
		ValidationResponse res = new ValidationResponse();
		res.setStatus(ValidationResponse.FAIL);
		if (result.hasErrors()) {
			List<FieldError> allErrors = result.getFieldErrors();
			for (FieldError objectError : allErrors) {
				res.addErrorMessage(new ErrorMessage(objectError.getField(),
						objectError.getDefaultMessage()));
			}
		} else {
			// validate version
			List<AppVersions> latestVersions = appVersionsService
					.latestVersion(appVersions.getAppId()); 
			AppVersions latestVersion = latestVersion(latestVersions);
			if (latestVersion != null
					&& compareVersions(latestVersion.getVersion(),
							appVersions.getVersion()) >= 0) {
				res.addErrorMessage(new ErrorMessage("version", messageSource
						.getMessage("validation.version.Smaller.message",
								new Object[] { latestVersion.getVersion() },
								locale)));
				return res;
			}
			// get the file from the request object
			Iterator<String> itr = request.getFileNames();
			if (!itr.hasNext()) {
				res.addErrorMessage(new ErrorMessage("appDownloadUrl",
						messageSource.getMessage(
								"validation.file.NotEmpty.message",
								new Object[] {}, locale)));
				return res;
			}
			MultipartFile mpf = request.getFile(itr.next());
			try {
				String originalName = mpf.getOriginalFilename();
				long size = mpf.getSize();
				if (UploadUtil.isValidAndroid(originalName)) {
					String rootDirectory = mUploadRootDirectory;
					UploadUtil.createUploadFolder(rootDirectory);
					String downloadUrl = UploadUtil.saveAndroidFile(
							rootDirectory, mpf.getInputStream());
					appVersions.setAppDownloadUrl(downloadUrl);
					appVersions.setAppSize(size);
					appVersionsService.save(appVersions);
					res.setStatus(ValidationResponse.SUCCESS);
				} else {
					res.addErrorMessage(new ErrorMessage("appDownloadUrl",
							messageSource.getMessage(
									"validation.file.InvalidType.message",
									new Object[] { mAndroidType }, locale)));
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return res;
	}

	@RequestMapping(value = "/platform/{id}", params = "updateAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ValidationResponse updatePlatformAjax(Model model,
			@ModelAttribute(value = "platform") @Valid Platform platform,
			BindingResult result) {
		ValidationResponse res = new ValidationResponse();
		if (result.hasErrors()) {
			res.setStatus(ValidationResponse.FAIL);
			List<FieldError> allErrors = result.getFieldErrors();
			for (FieldError objectError : allErrors) {
				res.addErrorMessage(new ErrorMessage(objectError.getField(),
						objectError.getDefaultMessage()));
			}
		} else {
			res.setStatus(ValidationResponse.SUCCESS);
			platformService.save(platform);
		}

		return res;
	}

	@RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataGrid<AppSummary> listGrid(
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
		long total = appService.count();
		Page<AppSummary> appPage = appSummaryService.findAllByPage(pageRequest,
				total);
		DataGrid<AppSummary> appGrid = new DataGrid<>();
		appGrid.setCurrentPage(appPage.getNumber() + 1);
		appGrid.setTotalPages(appPage.getTotalPages());
		appGrid.setTotalRecords(appPage.getTotalElements());
		appGrid.setData(appPage.getContent());

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

	/**
	 * Select all apps and show that app joined a specific group or not
	 */
	@RequestMapping(value = "/listGroupCheck", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataGrid<AppGroupCheck> listGridGroupCheck(
			@RequestParam(value = "groupId", required = false) Long groupId,
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
		long total = appService.count();
		Page<AppGroupCheck> appPage = appGroupCheckService.findAllByPage(
				pageRequest, groupId, total);
		DataGrid<AppGroupCheck> appGrid = new DataGrid<>();
		appGrid.setCurrentPage(appPage.getNumber() + 1);
		appGrid.setTotalPages(appPage.getTotalPages());
		appGrid.setTotalRecords(appPage.getTotalElements());
		appGrid.setData(appPage.getContent());

		return appGrid;
	}

	/**
	 * Select all apps and show that app joined a specific group or not
	 */
	@RequestMapping(value = "/versions", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataGrid<AppVersions> listVersion(
			@RequestParam(value = "appId", required = false) Long appId,
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
		long total = appService.count();
		Page<AppVersions> appPage = appVersionsService.findAllByAppId(
				pageRequest, appId, total);
		DataGrid<AppVersions> appGrid = new DataGrid<>();
		appGrid.setCurrentPage(appPage.getNumber() + 1);
		appGrid.setTotalPages(appPage.getTotalPages());
		appGrid.setTotalRecords(appPage.getTotalElements());
		appGrid.setData(appPage.getContent());

		return appGrid;
	}

	@RequestMapping(value = "/{id}", params = "deleteAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ValidationResponse deleteAjax(@PathVariable("id") Long id,
			Locale locale) {
		ValidationResponse res = new ValidationResponse();
		res.setStatus(ValidationResponse.SUCCESS);

		App app = appService.findById(id);
		String iconUrl = app.getIconUrl();
		// remove icon
		String rootDirectory = mUploadRootDirectory;
		try {
			UploadUtil.deleteIconFile(rootDirectory, iconUrl);
		} catch (IOException e) {
			e.printStackTrace();
			res.setStatus(ValidationResponse.FAIL);
			res.addErrorMessage(new ErrorMessage("deleteAppError",
					messageSource.getMessage("admin_icon_delete_fail",
							new Object[] {}, locale)));
		}

		List<AppVersions> appVersions = appVersionsService.findAllByAppId(id);
		for (AppVersions version : appVersions) {
			String downloadUrl = version.getAppDownloadUrl();
			// remove version
			try {
				UploadUtil.deleteAndroidFile(rootDirectory, downloadUrl);
			} catch (IOException e) {
				e.printStackTrace();
				res.setStatus(ValidationResponse.FAIL);
				res.addErrorMessage(new ErrorMessage("deleteAppError",
						messageSource.getMessage("admin_icon_delete_file",
								new Object[] {}, locale)));
			}
		}

		appService.delete(id);

		return res;
	}

	@RequestMapping(value = "/platform/{id}", params = "deleteAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void deletePlatformAjax(@PathVariable("id") Long id) {
		platformService.delete(id);
	}

	@RequestMapping(value = "/version/{id}", params = "deleteAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ValidationResponse deleteVersionAjax(@PathVariable("id") Long id,
			@RequestParam(value = "appId", required = false) Long appId, Locale locale) {
		ValidationResponse res = new ValidationResponse();
		res.setStatus(ValidationResponse.SUCCESS);
		
		// remove version file
		AppVersions appVersion = appVersionsService.findById(id);
		String downloadUrl = appVersion.getAppDownloadUrl();
		try {
			String rootDirectory = mUploadRootDirectory;
			UploadUtil.deleteAndroidFile(rootDirectory, downloadUrl);
		} catch (IOException e) {
			e.printStackTrace();
			res.setStatus(ValidationResponse.FAIL);
			res.addErrorMessage(new ErrorMessage("deleteAppError",
					messageSource.getMessage("admin_icon_delete_file",
							new Object[] {}, locale)));
		}
		
		appVersionsService.delete(id);
		
		//
		List<AppVersions> latestVersions = appVersionsService
				.latestVersion(appId); 
		AppVersions latestVersion = latestVersion(latestVersions);
		if (latestVersion != null) {
			// Update app latest version
			String version = latestVersion.getVersion();
			appService.updateLatestVersion(version, appId);
			
			res.setExtraData(version);
		}	
		return res;
	}
	
	@RequestMapping(value = "/icon/{id}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] downloadIcon(@PathVariable("id") Long id) {
		App app = appService.findById(id);
		if (app.getIconUrl() != null) {
			String rootDirectory = mUploadRootDirectory;
			try {
				byte[] data = UploadUtil.getIconFile(rootDirectory,
						app.getIconUrl());
				return data;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@RequestMapping(value = "/icon", method = RequestMethod.GET)
	@ResponseBody
	public byte[] downloadIcon(
			@RequestParam(value = "url", required = true) String iconUrl) {
		if (iconUrl != null) {
			String rootDirectory = mUploadRootDirectory;
			try {
				byte[] data = UploadUtil.getIconFile(rootDirectory, iconUrl);
				return data;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@RequestMapping(value = "/appgroup", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Long updateAppGroup(
			@RequestParam(value = "appId", required = false) Long appId,
			@RequestParam(value = "groupId", required = false) Long groupId,
			@RequestParam(value = "appGroupId", required = false) Long appGroupId) {
		if (appId != null && groupId != null) {
			AppGroup appGroup = new AppGroup();
			appGroup.setAppId(appId);
			appGroup.setGroupId(groupId);
			AppGroup data = appGroupService.save(appGroup);
			return data.getId();
		} else if (appGroupId != null && appGroupId > 0) {
			appGroupService.delete(appGroupId);
		}
		return 0l;
	}

	@RequestMapping(value = "/updateIcon", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ValidationResponse updateIconAjax(
			@RequestParam(value = "appId", required = false) Long appId,
			@RequestParam(value = "oldUrl", required = false) String oldUrl,
			MultipartHttpServletRequest request, Locale locale) {
		ValidationResponse res = new ValidationResponse();
		res.setStatus(ValidationResponse.FAIL);

		// get the file from the request object
		Iterator<String> itr = request.getFileNames();
		if (!itr.hasNext()) {
			res.addErrorMessage(new ErrorMessage("iconUrl", messageSource
					.getMessage("validation.icon.NotEmpty.message",
							new Object[] {}, locale)));
			return res;
		}
		MultipartFile mpf = request.getFile(itr.next());
		try {
			String type = mpf.getContentType();
			if (UploadUtil.isValidPhoto(type)) {
				String rootDirectory = mUploadRootDirectory;
				UploadUtil.createUploadFolder(rootDirectory);
				String iconUrl = UploadUtil.saveIconFile(rootDirectory,
						mpf.getInputStream());
				appService.updateIcon(iconUrl, appId);
				// Remove old icon file
				UploadUtil.deleteIconFile(rootDirectory, oldUrl);
				res.setExtraData(iconUrl);
				res.setStatus(ValidationResponse.SUCCESS);
			} else {
				res.addErrorMessage(new ErrorMessage("iconUrl", messageSource
						.getMessage("validation.icon.InvalidType.message",
								new Object[] { mPhotoType }, locale)));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return res;
	}

	@RequestMapping(value = "/updateLatestVersion", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void updateLatestVersionAjax(
			@RequestParam(value = "appId", required = false) Long appId,
			@RequestParam(value = "version", required = false) String version) {
		appService.updateLatestVersion(version, appId);
	}

	/**
	 * Get latest version
	 */
	public AppVersions latestVersion(List<AppVersions> versions) {
		if (versions == null || versions.size() == 0) {
			return null;
		}
		AppVersions latest = versions.get(0);
		if (versions.size() > 1) {
			for (int i = 1; i < versions.size(); i++) {
				AppVersions version = versions.get(i);
				if (compareVersions(latest.getVersion(), version.getVersion()) < 0) {
					latest = version;
				}
			}
		}
		return latest;
	}

	/**
	 * Compare version. Pattern: x.x.x.x
	 * 
	 * @return 0: equal <br/>
	 *         1: lager <br/>
	 *         -1: smaller
	 */
	public int compareVersions(String oldVersion, String newVersion) {
		int compare = 0;
		String[] path1 = oldVersion.split("\\.");
		String[] path2 = newVersion.split("\\.");

		int length = path1.length > path2.length ? path2.length : path1.length;
		for (int i = 0; i < length; i++) {
			int num1 = Integer.parseInt(path1[i]);
			int num2 = Integer.parseInt(path2[i]);
			if (num1 > num2) {
				compare = 1;
				break;
			} else if (num1 < num2) {
				compare = -1;
				break;
			}
		}
		// case: 1.2.1 < 1.2.1.100
		if (compare == 0) {
			if (path1.length < path2.length) {
				compare = -1;
			} else if (path1.length > path2.length) {
				compare = 1;
			}
		}

		return compare;
	}
}
