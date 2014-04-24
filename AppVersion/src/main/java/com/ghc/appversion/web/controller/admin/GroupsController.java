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

import com.ghc.appversion.domain.admin.Group;
import com.ghc.appversion.domain.admin.GroupMembers;
import com.ghc.appversion.domain.admin.GroupUserCheck;
import com.ghc.appversion.service.jpa.admin.GroupMembersService;
import com.ghc.appversion.service.jpa.admin.GroupService;
import com.ghc.appversion.service.jpa.admin.GroupUserCheckService;
import com.ghc.appversion.service.jpa.admin.UserGroupService;
import com.ghc.appversion.web.form.ErrorMessage;
import com.ghc.appversion.web.form.Message;
import com.ghc.appversion.web.form.ValidationResponse;
import com.ghc.appversion.web.form.admin.DataGrid;
import com.ghc.appversion.web.util.UrlUtil;

@RequestMapping("/admin/groups")
@Controller
public class GroupsController extends AbstractAdminController {

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserGroupService userGroupService;
	
	@Autowired
	private GroupUserCheckService groupUserCheckService;
	
	@Autowired
	private GroupMembersService groupMembersService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		Group group = new Group();
		model.addAttribute("group", group);
		return "admin/groups/list";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable(value = "id") Long id, Model model) {
		Group group = groupService.findById(id);
		model.addAttribute("group", group);

		return "admin/groups/show";
	}

	@RequestMapping(params = "ajax", method = RequestMethod.POST)
	@ResponseBody
	public ValidationResponse createAjax(Model model,
			@ModelAttribute(value = "group") @Valid Group group,
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
			groupService.save(group);
		}

		return res;
	}

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable(value = "id") Long id, Model model) {
		Group group = groupService.findById(id);
		model.addAttribute("group", group);

		return "admin/groups/update";
	}

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
	public String update(@Valid Group group, BindingResult bindingResult,
			Model model, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale) {
		if (bindingResult.hasErrors()) {
			model.addAttribute(
					"message",
					new Message("error", messageSource.getMessage(
							"admin_group_save_fail", new Object[] {}, locale)));
			model.addAttribute("group", group);
			return "admin/groups/create";
		}
		model.asMap().clear();
		redirectAttributes.addFlashAttribute(
				"message",
				new Message("success", messageSource.getMessage(
						"admin_group_save_success", new Object[] {}, locale)));
		groupService.save(group);

		return "redirect:/admin/groups/"
				+ UrlUtil.encodeUrlPathSegment(group.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataGrid<Group> listGrid(
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
		Page<Group> groupPage = groupService.findAllByPage(pageRequest);
		DataGrid<Group> groupGrid = new DataGrid<>();
		groupGrid.setCurrentPage(groupPage.getNumber() + 1);
		groupGrid.setTotalPages(groupPage.getTotalPages());
		groupGrid.setTotalRecords(groupPage.getTotalElements());
		groupGrid.setData(groupPage.getContent());

		return groupGrid;
	}
	
	@RequestMapping(value = "/listgrid", params = "user", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataGrid<GroupUserCheck> listGridUserCheck(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sortBy,
			@RequestParam(value = "sord", required = false) String order,
			@RequestParam(value = "userId", required = true) Long userId) {
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
		long total = groupService.count();
		Page<GroupUserCheck> groupPage = groupUserCheckService.findAllByPage(pageRequest, userId, total);
		DataGrid<GroupUserCheck> groupGrid = new DataGrid<>();
		groupGrid.setCurrentPage(groupPage.getNumber() + 1);
		groupGrid.setTotalPages(groupPage.getTotalPages());
		groupGrid.setTotalRecords(groupPage.getTotalElements());
		groupGrid.setData(groupPage.getContent());

		return groupGrid;
	}
	
	@RequestMapping(value = "/listgrid", params = "members", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataGrid<GroupMembers> listGridMembers(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sortBy,
			@RequestParam(value = "sord", required = false) String order,
			@RequestParam(value = "groupId", required = false) Long groupId) {
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
		long total = userGroupService.count(groupId);
		Page<GroupMembers> groupPage = groupMembersService.findAllByPage(pageRequest, groupId, total);
		DataGrid<GroupMembers> groupGrid = new DataGrid<>();
		groupGrid.setCurrentPage(groupPage.getNumber() + 1);
		groupGrid.setTotalPages(groupPage.getTotalPages());
		groupGrid.setTotalRecords(groupPage.getTotalElements());
		groupGrid.setData(groupPage.getContent());

		return groupGrid;
	}
	
	@RequestMapping(value="/{id}", params="delete", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void deleteGroup(@PathVariable("id") Long id) {
		groupService.delete(id);		
	}
}
