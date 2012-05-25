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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.pagecheck.Utils;
import org.openmrs.module.pagecheck.task.TaskEngine;
import org.openmrs.module.pagecheck.task.URLCheckTask;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Task status AJAX controller
 */
@Controller("pageCheckStatusController")
@RequestMapping("/module/pagecheck/status")
public class StatusController {

	protected static final Log log = LogFactory.getLog(StatusController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public void getProgress(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Utils.checkSuperUser();
		
		URLCheckTask task = TaskEngine.getCurrentTask();
		StringBuilder json = new StringBuilder();
		
		if (task != null) {
			String completed = task.isCompleted() ? "true" : "false";
					
			json.append("{\n");
			json.append("  task: {\n");
			json.append("    completed: " + completed + ",\n");
			json.append("    progress: " + task.getProgress() + ",\n");
			json.append("    checkedURLs: " + task.getCheckedURLs() + ",\n");
			json.append("    issues: [\n");
			
			for (String issue : task.getIssues()) {
				json.append("      \"" + issue + "\",\n");
			}
			
			json.append("    ]\n");
			json.append("  }\n");
			json.append("}");
		}
		else
			json.append("{ task: null }");
		
		response.setContentType("application/json");			
		response.getWriter().write(json.toString());
	}
}
