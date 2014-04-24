package com.ghc.appversion.web.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ghc.appversion.domain.admin.User;
import com.ghc.appversion.domain.admin.UserGroup;
import com.ghc.appversion.domain.admin.UserSummary;
import com.ghc.appversion.service.jpa.admin.UserGroupService;
import com.ghc.appversion.service.jpa.admin.UserService;
import com.ghc.appversion.service.jpa.admin.UserSummaryService;
import com.ghc.appversion.web.form.ErrorMessage;
import com.ghc.appversion.web.form.Message;
import com.ghc.appversion.web.form.ValidationResponse;
import com.ghc.appversion.web.form.admin.DataGrid;
import com.ghc.appversion.web.util.UrlUtil;

@RequestMapping("/admin/users")
@Controller
public class UsersController extends AbstractAdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserSummaryService userSummaryService;

	@Autowired
	private UserGroupService userGroupService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "admin/users/list";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable(value = "id") Long id, Model model) {
		User user = userService.findById(id);
		model.addAttribute("user", user);

		return "admin/users/show";
	}

	@RequestMapping(params = "ajax", method = RequestMethod.POST)
	@ResponseBody
	public ValidationResponse createAjax(Model model,
			@ModelAttribute(value = "user") @Valid User user,
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
			userService.save(user);
		}

		return res;
	}

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable(value = "id") Long id, Model model) {
		User user = userService.findById(id);
		model.addAttribute("user", user);

		return "admin/users/update";
	}

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
	public String update(@Valid User user, BindingResult bindingResult,
			Model model, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale) {
		if (bindingResult.hasErrors()) {
			model.addAttribute(
					"message",
					new Message("error", messageSource.getMessage(
							"admin_user_save_fail", new Object[] {}, locale)));
			model.addAttribute("user", user);
			return "admin/users/update";
		}
		model.asMap().clear();
		redirectAttributes.addFlashAttribute(
				"message",
				new Message("success", messageSource.getMessage(
						"admin_user_save_success", new Object[] {}, locale)));
		userService.save(user);

		return "redirect:/admin/users/"
				+ UrlUtil.encodeUrlPathSegment(user.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataGrid<User> listGrid(
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
		Page<User> userPage = userService.findAllByPage(pageRequest);
		DataGrid<User> userGrid = new DataGrid<>();
		userGrid.setCurrentPage(userPage.getNumber() + 1);
		userGrid.setTotalPages(userPage.getTotalPages());
		userGrid.setTotalRecords(userPage.getTotalElements());
		userGrid.setData(userPage.getContent());

		return userGrid;
	}

	@RequestMapping(value = "/listsummary", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataGrid<UserSummary> listSummary(
			@RequestParam(value = "filterGroup", required = false) String filterGroup,
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
		long total = userService.count();
		Page<UserSummary> userPage = userSummaryService.findAllByPage(
				pageRequest, total, filterGroup);
		DataGrid<UserSummary> userGrid = new DataGrid<>();
		userGrid.setCurrentPage(userPage.getNumber() + 1);
		userGrid.setTotalPages(userPage.getTotalPages());
		userGrid.setTotalRecords(userPage.getTotalElements());
		userGrid.setData(userPage.getContent());

		return userGrid;
	}

	@RequestMapping(value = "/usergroup", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Long updateUserGroup(
			@RequestParam(value = "userId", required = false) Long userId,
			@RequestParam(value = "groupId", required = false) Long groupId,
			@RequestParam(value = "userGroupId", required = false) Long userGroupId) {
		if (userId != null && groupId != null) {
			UserGroup userGroup = new UserGroup();
			userGroup.setUserId(userId);
			userGroup.setGroupId(groupId);
			UserGroup data = userGroupService.save(userGroup);
			return data.getId();
		} else if (userGroupId != null && userGroupId > 0) {
			userGroupService.delete(userGroupId);
		}
		return 0l;
	}
}
