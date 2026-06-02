package com.akash.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeRequest {

    private String oldPassword;

    private String newPassword;
}