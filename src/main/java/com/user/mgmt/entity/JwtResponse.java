package com.user.mgmt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class JwtResponse {

    private User user;
    private String jwtToken;

    
}
