package com.oracle.oal.seaas.util;

public class TokenProvider {

    private String token;

    private static TokenProviderBuilder builder = new TokenProviderBuilder();

    private TokenProvider(String token){
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }

    public static TokenProviderBuilder builder(){
        return builder;
    }

    public static class TokenProviderBuilder{

        private String authString;

        // todo: accept username and password here.
        public TokenProviderBuilder withDefaultAuthString(){
            authString = "ZnVzaW9uLW9hbC1zZXQtaW50Z3Jfd3dAb3JhY2xlLmNvbTpNT1VOdGFpbjEyMw==";
            return  this;
        }

        public TokenProvider build(){
           return new TokenProvider(this.authString);
        }

    }
}
