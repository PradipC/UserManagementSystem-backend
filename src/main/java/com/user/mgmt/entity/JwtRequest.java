package com.user.mgmt.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JwtRequest {

    private String userName;
    private String userPassword;

 }
