package com.user.mgmt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.user.mgmt.entity.JwtRequest;
import com.user.mgmt.entity.JwtResponse;
import com.user.mgmt.service.JwtService;

@RestController
@CrossOrigin
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
       
    	JwtResponse createJwtToken = jwtService.createJwtToken(jwtRequest);
    	return createJwtToken;
    }
}
