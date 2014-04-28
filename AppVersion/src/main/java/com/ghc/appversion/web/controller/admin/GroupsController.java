package com.ghc.appversion.web.controller.admin;

import java.util.List;

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

import com.ghc.appversion.domain.admin.Group;
import com.ghc.appversion.domain.admin.GroupAppCheck;
import com.ghc.appversion.domain.admin.GroupApps;
import com.ghc.appversion.domain.admin.GroupMembers;
import com.ghc.appversion.domain.admin.GroupSummary;
import com.ghc.appversion.domain.admin.GroupUserCheck;
import com.ghc.appversion.service.jpa.admin.app.AppGroupService;
import com.ghc.appversion.service.jpa.admin.group.GroupAppCheckService;
import com.ghc.appversion.service.jpa.admin.group.GroupAppsService;
import com.ghc.appversion.service.jpa.admin.group.GroupMembersService;
import com.ghc.appversion.service.jpa.admin.group.GroupService;
import com.ghc.appversion.service.jpa.admin.group.GroupSummaryService;
import com.ghc.appversion.service.jpa.admin.group.GroupUserCheckService;
import com.ghc.appversion.service.jpa.user.UserGroupService;
import com.ghc.appversion.web.form.DataGrid;
import com.ghc.appversion.web.form.ErrorMessage;
import com.ghc.appversion.web.form.ValidationResponse;

@RequestMapping("/admin/groups")
@Controller
public class GroupsController extends AbstractAdminController {

	@Autowired
	private GroupService groupService;

	@Autowired
	private GroupSummaryService groupSummaryService;

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private AppGroupService appGroupService;

	@Autowired
	private GroupUserCheckService groupUserCheckService;

	@Autowired
	private GroupAppCheckService groupAppCheckService;

	@Autowired
	private GroupMembersService groupMembersService;

	@Autowired
	private GroupAppsService groupAppsService;

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

	@RequestMapping(value = "/{id}", params = "showAjax", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Group showAjax(@PathVariable(value = "id") Long id) {
		Group group = groupService.findById(id);

		return group;
	}

	@RequestMapping(params = "createAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ValidationResponse createAjax(Model model,
			@ModelAttribute(value = "group") @Valid Group group,
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
			groupService.save(group);
		}

		return res;
	}

	@RequestMapping(value = "/{id}", params = "updateAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ValidationResponse updateAjax(Model model,
			@ModelAttribute(value = "group") @Valid Group group,
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
			groupService.save(group);
		}

		return res;
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

	@RequestMapping(value = "/listSummary", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataGrid<GroupSummary> listSummary(
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
		long total = groupService.count();
		Page<GroupSummary> groupPage = groupSummaryService.findAllByPage(
				pageRequest, total);
		DataGrid<GroupSummary> groupGrid = new DataGrid<>();
		groupGrid.setCurrentPage(groupPage.getNumber() + 1);
		groupGrid.setTotalPages(groupPage.getTotalPages());
		groupGrid.setTotalRecords(groupPage.getTotalElements());
		groupGrid.setData(groupPage.getContent());

		return groupGrid;
	}

	/**
	 * Select all groups and show groups that a user joined
	 */
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
		Page<GroupUserCheck> groupPage = groupUserCheckService.findAllByPage(
				pageRequest, userId, total);
		DataGrid<GroupUserCheck> groupGrid = new DataGrid<>();
		groupGrid.setCurrentPage(groupPage.getNumber() + 1);
		groupGrid.setTotalPages(groupPage.getTotalPages());
		groupGrid.setTotalRecords(groupPage.getTotalElements());
		groupGrid.setData(groupPage.getContent());

		return groupGrid;
	}

	/**
	 * Select all groups and show groups that a app belong
	 */
	@RequestMapping(value = "/listgrid", params = "app", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataGrid<GroupAppCheck> listGridAppCheck(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sortBy,
			@RequestParam(value = "sord", required = false) String order,
			@RequestParam(value = "appId", required = true) Long appId) {
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
		Page<GroupAppCheck> groupPage = groupAppCheckService.findAllByPage(
				pageRequest, appId, total);
		DataGrid<GroupAppCheck> groupGrid = new DataGrid<>();
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
		Page<GroupMembers> groupPage = groupMembersService.findAllByPage(
				pageRequest, groupId, total);
		DataGrid<GroupMembers> groupGrid = new DataGrid<>();
		groupGrid.setCurrentPage(groupPage.getNumber() + 1);
		groupGrid.setTotalPages(groupPage.getTotalPages());
		groupGrid.setTotalRecords(groupPage.getTotalElements());
		groupGrid.setData(groupPage.getContent());

		return groupGrid;
	}

	@RequestMapping(value = "/listgrid", params = "apps", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataGrid<GroupApps> listGridApps(
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
		long total = appGroupService.count(groupId);
		Page<GroupApps> groupPage = groupAppsService.findAllByPage(pageRequest,
				groupId, total);
		DataGrid<GroupApps> groupGrid = new DataGrid<>();
		groupGrid.setCurrentPage(groupPage.getNumber() + 1);
		groupGrid.setTotalPages(groupPage.getTotalPages());
		groupGrid.setTotalRecords(groupPage.getTotalElements());
		groupGrid.setData(groupPage.getContent());

		return groupGrid;
	}

	@RequestMapping(value = "/{id}", params = "deleteAjax", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void deleteGroup(@PathVariable("id") Long id) {
		groupService.delete(id);
	}
}
