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

package org.openmrs.module.pagecheck;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.openmrs.module.pagecheck.util.ContextProvider;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

/**
 * Utility functions related to pages
 */
public class Pages {

	/**
	 * Gets all URLs which have been mapped to controllers in Spring
	 * @return the URLs
	 */
	@SuppressWarnings("unchecked")
	public static Set<String> getMappedURLs() {
		Set<String> urls = new TreeSet<String>();
		
		Map<String, SimpleUrlHandlerMapping> simpleHandlers = ContextProvider.getApplicationContext().getBeansOfType(SimpleUrlHandlerMapping.class);
		Map<String, DefaultAnnotationHandlerMapping> annotationBeans = ContextProvider.getApplicationContext().getBeansOfType(DefaultAnnotationHandlerMapping.class);
		
		for (SimpleUrlHandlerMapping handler : simpleHandlers.values())
			urls.addAll(handler.getHandlerMap().keySet());
		
		for (DefaultAnnotationHandlerMapping handler : annotationBeans.values())
			urls.addAll(handler.getHandlerMap().keySet());
		
		return urls;
	}
}
