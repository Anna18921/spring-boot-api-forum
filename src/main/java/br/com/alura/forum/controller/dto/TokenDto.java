package br.com.alura.forum.controller.dto;

public class TokenDto {


    private final String token;
    private final String typeAuth;



    public TokenDto(String token, String typeAuth) {
        this.token = token;
        this.typeAuth = typeAuth;
    }

    public String getToken() {
        return token;
    }

    public String getTypeAuth() {
        return typeAuth;
    }
    
    
    
}
