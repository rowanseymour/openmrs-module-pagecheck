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

package org.openmrs.module.pagecheck.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pagecheck.Pages;

/**
 * Database import task
 */
public class URLCheckTask implements Runnable {

	protected static final Log log = LogFactory.getLog(URLCheckTask.class);
			
	private UserContext userContext;
	private Date startTime = null;
	private Date endTime = null;
	private int totalURLs = 0;
	private int checkedURLs = 0;
	private List<String> issues = new ArrayList<String>();
	
	@Override
	public void run() {
		startTime = new Date();
		
		try {
			log.info("Starting URL checking task");
			
			Context.openSession();
			Context.setUserContext(userContext);
			
			doCheck();
			
			log.info("Finished URL checking task");
		}
		catch (Exception ex) {
			log.error("Unable to complete URL checking task", ex);
		}
		finally {
			Context.closeSession();
			endTime = new Date();
		}
	}
	
	/**
	 * Does the actual checking work
	 */
	private void doCheck() {	
		Set<String> urls = Pages.getMappedURLs();
		totalURLs = urls.size();
		
		for (String url : urls) {
			
		}
	}
	
	/**
	 * Gets if task is complete
	 * @return true if task is complete
	 */
	public boolean isCompleted() {
		return (endTime != null);
	}
	
	/**
	 * Gets the import progress
	 * @return the progress (0...100)
	 */
	public float getProgress() {
		if (isCompleted())
			return 100.0f;
		else
			return (totalURLs > 0) ? (100.0f * checkedURLs / totalURLs) : 0.0f;
	}
	
	/**
	 * Gets the number of URLs checked
	 * @return the number
	 */
	public int getCheckedURLs() {
		return checkedURLs;
	}

	/**
	 * Gets the issues
	 * @return the issues
	 */
	public List<String> getIssues() {
		return issues;
	}
	
	/**
	 * Gets time taken by the task. If task is still running then it's the time taken so far.
	 * If task has completed then its the time that was taken to complete
	 * @return the time in seconds
	 */
	public int getTimeTaken() {
		Date end = (endTime != null) ? endTime : new Date(); 
		return (int)((end.getTime() - startTime.getTime()) / 1000);
	}
}
