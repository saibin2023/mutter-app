package com.mutter.config;

public class OAuthConfig {

    // !!! IMPORTANT: Replace with your actual Client ID and Secret !!!
    // !!! DO NOT commit these directly into version control in a real project !!!
    public static final String GOOGLE_CLIENT_ID = "60416769185-ksgfl19ada57r0j99u6fkvcdruf758m7.apps.googleusercontent.com";
    public static final String GOOGLE_CLIENT_SECRET = "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";

    // This must match the authorized redirect URI you configured in Google Cloud Console
    public static final String GOOGLE_REDIRECT_URI = "http://localhost:8080/mutter-app/oauth2/callback/google"; 

    // Scopes define the level of access you are requesting
    public static final java.util.List<String> SCOPES = java.util.Arrays.asList(
            "https://www.googleapis.com/auth/userinfo.profile", // Get basic profile info (name, picture)
            "https://www.googleapis.com/auth/userinfo.email"    // Get user's email address
    );
    
    // Private constructor to prevent instantiation
    private OAuthConfig() {}
} 