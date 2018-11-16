package com.codecool.netflixandchill.security;

class SecurityConstants {

    static final String SECRET = "tarhaltmegoldas";
    static final long EXPIRATION_TIME = 600_000; // 600 seconds
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String JOIN_URL = "/users/join";
    static final String[] PUBLIC_URLS = {"/series/**", "/seasons/**", "/episodes/**"};

}
