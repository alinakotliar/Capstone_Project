package org.example.utils;

import java.util.Base64;

public class EncodeLoginToken {

        public static String getAuthToken(String clientId, String clientSecret) {
            String idToken = clientId + ":" + clientSecret;
            String convertToken = Base64.getEncoder().encodeToString(idToken.getBytes());
            return new String(convertToken);
        }
}
