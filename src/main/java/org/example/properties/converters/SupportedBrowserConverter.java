package org.example.properties.converters;

import org.example.enumeration.SupportedBrowsers;

public class SupportedBrowserConverter {
    public static SupportedBrowsers valueOfWebBrowser(String webBrowserName) {
        return switch (webBrowserName) {
            case "chrome" -> SupportedBrowsers.CHROME;
            default -> throw new IllegalArgumentException(
                    "This type of the browser is not supported");
        };
    }
}
