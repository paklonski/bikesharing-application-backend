package cz.cvut.bikesharingproject.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDto {

    private String username;
    private String token;
}
