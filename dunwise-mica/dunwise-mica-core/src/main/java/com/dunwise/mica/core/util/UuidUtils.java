package com.dunwise.mica.core.util;

import java.util.UUID;

/**
 * UUID 工具
 */
public class UuidUtils {

    private UuidUtils() {
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String randomUUID32() {
        return randomUUID();
    }

    public static String simple() {
        return UUID.randomUUID().toString();
    }
}
