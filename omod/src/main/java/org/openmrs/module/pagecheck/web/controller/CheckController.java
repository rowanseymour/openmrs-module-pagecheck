/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.pagecheck.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.pagecheck.Utils;
import org.openmrs.module.pagecheck.task.TaskEngine;
import org.openmrs.module.pagecheck.task.URLCheckTask;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The main controller.
 */
@Controller("pageCheckCheckController")
@RequestMapping("/module/pagecheck/check")
public class CheckController {
	
	protected static final Log log = LogFactory.getLog(CheckController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(HttpServletRequest request, ModelMap model) {
		Utils.checkSuperUser();
		
		model.addAttribute("task", TaskEngine.getCurrentTask());
		
		// Developer hack - end users shouldn't be stopping imports once they've started
		if (request.getParameter("stop") != null)
			TaskEngine.stopCurrentTask();
		
		return "/module/pagecheck/check";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String handleSubmit(HttpServletRequest request, ModelMap model) {
		Utils.checkSuperUser();
		
		// Start import if there's no running import task
		URLCheckTask task = TaskEngine.getCurrentTask();
		if (task == null || task.isCompleted())
			TaskEngine.startCheck();
	
		return showForm(request, model);
	}
}
