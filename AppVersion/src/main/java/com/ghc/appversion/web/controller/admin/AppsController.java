/**
 * AppsController.java
 *
 *	
 */
package com.ghc.appversion.web.controller.admin;

import static com.ghc.appversion.service.jpa.admin.SQLConstants.PLATFORM_TYPE_ANDROID;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.PLATFORM_TYPE_IOS;
import static com.ghc.appversion.web.Constants.ANDROID_TYPE;
import static com.ghc.appversion.web.Constants.PHOTO_TYPE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
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
import com.ghc.appversion.domain.admin.AppVersions;
import com.ghc.appversion.domain.admin.Platform;
import com.ghc.appversion.service.jpa.admin.app.AppService;
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
	private AppVersionsService appVersionsService;

	@Autowired
	private PlatformService platformService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		App app = new App();
		model.addAttribute("app", app);

		AppVersions appVersions = new AppVersions();
		appVersions.setReleaseDate(new DateTime());
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

	@RequestMapping(params = "validateAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ValidationResponse validateAjax(
			@ModelAttribute(value = "app") @Valid App app,
			BindingResult result, MultipartHttpServletRequest request,
			Locale locale) {
		ValidationResponse res = new ValidationResponse();
		res.setStatus("FAIL");
		if (result.hasErrors()) {
			List<FieldError> allErrors = result.getFieldErrors();
			List<ErrorMessage> errorMesages = res.getResult();
			for (FieldError objectError : allErrors) {
				errorMesages.add(new ErrorMessage(objectError.getField(),
						objectError.getDefaultMessage()));
			}
		} else {
			// Check name
			App exitApp = appService.findByName(app.getName());
			if (exitApp != null) {
				List<ErrorMessage> errorMesages = res.getResult();
				errorMesages.add(new ErrorMessage("name", messageSource
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
					res.setStatus("SUCCESS");
				} else {
					List<ErrorMessage> errorMesages = res.getResult();
					errorMesages.add(new ErrorMessage("iconUrl", messageSource
							.getMessage("validation.icon.InvalidType.message",
									new Object[] { PHOTO_TYPE }, locale)));
				}
			} else {
				List<ErrorMessage> errorMesages = res.getResult();
				errorMesages.add(new ErrorMessage("iconUrl", messageSource
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
		res.setStatus("FAIL");
		if (result.hasErrors()) {
			List<FieldError> allErrors = result.getFieldErrors();
			List<ErrorMessage> errorMesages = res.getResult();
			for (FieldError objectError : allErrors) {
				errorMesages.add(new ErrorMessage(objectError.getField(),
						objectError.getDefaultMessage()));
			}
		} else {
			// Check name
			App exitApp = appService.findByName(app.getName());
			if (exitApp != null) {
				List<ErrorMessage> errorMesages = res.getResult();
				errorMesages.add(new ErrorMessage("name", messageSource
						.getMessage("validation.appName.Exist.message",
								new Object[] {}, locale)));
				return res;
			}
			// get the file from the request object
			Iterator<String> itr = request.getFileNames();
			if (!itr.hasNext()) {
				List<ErrorMessage> errorMesages = res.getResult();
				errorMesages.add(new ErrorMessage("iconUrl", messageSource
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
					res.setStatus("SUCCESS");
				} else {
					List<ErrorMessage> errorMesages = res.getResult();
					errorMesages.add(new ErrorMessage("iconUrl", messageSource
							.getMessage("validation.icon.InvalidType.message",
									new Object[] { PHOTO_TYPE }, locale)));
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
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
		res.setStatus("FAIL");
		if (result.hasErrors()) {
			List<FieldError> allErrors = result.getFieldErrors();
			List<ErrorMessage> errorMesages = res.getResult();
			for (FieldError objectError : allErrors) {
				errorMesages.add(new ErrorMessage(objectError.getField(),
						objectError.getDefaultMessage()));
			}
		} else {
			// validate version
			AppVersions latestVersion = appVersionsService
					.latestVersion(appVersions.getAppId());
			if (latestVersion != null
					&& compareVersions(latestVersion.getVersion(),
							appVersions.getVersion()) >= 0) {
				List<ErrorMessage> errorMesages = res.getResult();
				errorMesages.add(new ErrorMessage("version", messageSource
						.getMessage("validation.version.Smaller.message",
								new Object[] { latestVersion.getVersion() },
								locale)));
				return res;
			}
			// get the file from the request object
			Iterator<String> itr = request.getFileNames();
			if (!itr.hasNext()) {
				List<ErrorMessage> errorMesages = res.getResult();
				errorMesages.add(new ErrorMessage("appDownloadUrl",
						messageSource.getMessage(
								"validation.file.NotEmpty.message",
								new Object[] {}, locale)));
				return res;
			}
			MultipartFile mpf = request.getFile(itr.next());
			String originalName = mpf.getOriginalFilename();
			if (UploadUtil.isValidAndroid(originalName)) {
				res.setStatus("SUCCESS");
			} else {
				List<ErrorMessage> errorMesages = res.getResult();
				errorMesages.add(new ErrorMessage("appDownloadUrl",
						messageSource.getMessage(
								"validation.file.InvalidType.message",
								new Object[] { ANDROID_TYPE }, locale)));
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
		res.setStatus("FAIL");
		if (result.hasErrors()) {
			List<FieldError> allErrors = result.getFieldErrors();
			List<ErrorMessage> errorMesages = res.getResult();
			for (FieldError objectError : allErrors) {
				errorMesages.add(new ErrorMessage(objectError.getField(),
						objectError.getDefaultMessage()));
			}
		} else {
			// get the file from the request object
			Iterator<String> itr = request.getFileNames();
			if (!itr.hasNext()) {
				List<ErrorMessage> errorMesages = res.getResult();
				errorMesages.add(new ErrorMessage("appDownloadUrl",
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
					res.setStatus("SUCCESS");
				} else {
					List<ErrorMessage> errorMesages = res.getResult();
					errorMesages.add(new ErrorMessage("appDownloadUrl",
							messageSource.getMessage(
									"validation.file.InvalidType.message",
									new Object[] { ANDROID_TYPE }, locale)));
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

	@RequestMapping(value="/platform/{id}", params="deleteAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void deletePlatformAjax(@PathVariable("id") Long id) {
		platformService.delete(id);		
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
		String[] path1 = oldVersion.split(".");
		String[] path2 = newVersion.split(".");
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
