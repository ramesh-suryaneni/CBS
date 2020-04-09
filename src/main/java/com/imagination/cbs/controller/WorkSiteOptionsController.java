package com.imagination.cbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.dto.SiteOptionsDto;
import com.imagination.cbs.service.WorkSiteOptionsService;

/**
 * @author pravin.budage
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/work-site-options")
public class WorkSiteOptionsController {

	@Autowired
	private WorkSiteOptionsService workSiteOptionsService;

	@GetMapping
	public ResponseEntity<List<SiteOptionsDto>> fetchWorkSiteOptions() {
		List<SiteOptionsDto> siteOptions = workSiteOptionsService.fetchWorkSites();
		return new ResponseEntity<List<SiteOptionsDto>>(siteOptions, HttpStatus.OK);
	}
}
