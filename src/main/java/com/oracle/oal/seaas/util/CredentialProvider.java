package com.oracle.oal.seaas.util;

public class CredentialProvider {

    private String token;

    private static TokenProviderBuilder builder = new TokenProviderBuilder();

    private CredentialProvider(String token){
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

        public CredentialProvider build(){
           return new CredentialProvider(this.authString);
        }

    }
}
