package com.mbhonsle.guessthecelebrity.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mak on 12/13/17.
 */

class StringUtils {

    private Pattern htmlImgRegexPattern = Pattern.compile("<img src=(.*?)/>");
    private Pattern imgNameRegexPattern = Pattern.compile("alt=(.*)$");

    List<String> getImageDetailsFromHtmlString(String htmlString) {
        List<String> result = new ArrayList<>();
        Matcher m = htmlImgRegexPattern.matcher(htmlString);
        while(m.find()) {
            result.add(m.group(1).replaceAll("\"", ""));
        }
        return result;
    }

    String getNameFromImageUrl(String imageUrlString) {
        Matcher m = imgNameRegexPattern.matcher(imageUrlString);
        return m.find() ? m.group(1) : "";
    }

}
