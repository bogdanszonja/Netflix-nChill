package com.codecool.netflixandchill.security;

public class SecurityConstants {

    public static final String SECRET = "tarhaltmegoldas";
    public static final long EXPIRATION_TIME = 120_000; // 120 seconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String JOIN_URL = "/users/join";

}
