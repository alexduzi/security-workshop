package com.alexduzi.security.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/demo-controller")
@RequiredArgsConstructor
public class DemoController {

	@GetMapping
	public ResponseEntity<String> hello() {
		return ResponseEntity.ok("Hello from secured endpoint");
	}
}
