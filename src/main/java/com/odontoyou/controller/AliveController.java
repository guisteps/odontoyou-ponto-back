package com.odontoyou.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* Controller exists only to trigger this backend Heroku, while the user is interacting with the front */

@RestController
@RequestMapping(value = "/alive")
public class AliveController {

	@GetMapping
	public String alive() {
		return LocalDateTime.now().toString();
	}
	
}
