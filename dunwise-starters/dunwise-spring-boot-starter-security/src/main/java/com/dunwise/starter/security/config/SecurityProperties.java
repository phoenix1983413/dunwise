package com.dunwise.starter.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

/**
 * Security 配置属性
 */
@ConfigurationProperties(prefix = "dunwise.security")
public class SecurityProperties {

    private final Jwt jwt = new Jwt();

    private final Login login = new Login();

    public Jwt getJwt() {
        return jwt;
    }

    public Login getLogin() {
        return login;
    }

    public static class Jwt {
        private String secret;
        private long expiration = 86400000L;
        private String header = "Authorization";

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public long getExpiration() {
            return expiration;
        }

        public void setExpiration(long expiration) {
            this.expiration = expiration;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }
    }

    public static class Login {
        private int maxAttempts = 5;
        private long lockDuration = 1800;

        public int getMaxAttempts() {
            return maxAttempts;
        }

        public void setMaxAttempts(int maxAttempts) {
            this.maxAttempts = maxAttempts;
        }

        public long getLockDuration() {
            return lockDuration;
        }

        public void setLockDuration(long lockDuration) {
            this.lockDuration = lockDuration;
        }
    }
}
