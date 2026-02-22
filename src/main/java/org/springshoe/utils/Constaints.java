package org.springshoe.utils;

public class Constaints {

    public static final String ROOT_PACKAGE = getRootPackage();

    private static String getRootPackage() {
        try {
            String mainClassName =
                    System.getProperty("sun.java.command").split(" ")[0];

            return Class.forName(mainClassName).getPackageName();

        } catch (Exception e) {
            throw new RuntimeException("Cannot detect root package", e);
        }
    }
}