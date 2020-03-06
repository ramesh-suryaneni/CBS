package com.imagination.cbs.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.domain.Team;
import com.imagination.cbs.service.TeamService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/team")
public class TeamController {

	@Autowired
	private TeamService teamService;

	@PostMapping(value = "/store", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Team> saveTeamDetails(@Valid @RequestBody Team team) {
		Team storedTeam = teamService.storeTeamDetail(team);
		return new ResponseEntity<Team>(storedTeam, HttpStatus.CREATED);
	}

}
