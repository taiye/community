package com.example.song.DTO;

import lombok.Data;

@Data
public class AccessTokenDto {
    private String client_id;
    private String client_secret;
    private  String code;
    private  String redirect_uri;
    private  String state;
}
