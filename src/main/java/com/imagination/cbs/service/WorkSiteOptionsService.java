package com.imagination.cbs.service;

import java.util.List;

import com.imagination.cbs.dto.SiteOptionsDto;

/**
 * @author pravin.budage
 *
 */
public interface WorkSiteOptionsService {
	List<SiteOptionsDto> fetchWorkSites();
}
