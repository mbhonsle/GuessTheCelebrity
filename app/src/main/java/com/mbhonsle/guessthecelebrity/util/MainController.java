package com.mbhonsle.guessthecelebrity.util;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by mak on 12/13/17.
 */

public class MainController {

    private StringUtils stringUtils = new StringUtils();
    private Map<String, ImageHelper> db = new HashMap<>();
    private SiteDownloader siteDownloader;
    private Random r = new Random();

    public MainController() {
        this.siteDownloader = new SiteDownloader("http://www.posh24.se/kandisar");
        try {
            String siteText = this.siteDownloader.fetch();
            this.buildDatabase(siteText);
        } catch (Exception e) {
            Log.e("SITE DOWNLOAD FAILURE", e.getMessage());
        }
    }

    private void buildDatabase(String siteText) {
        List<String> imgUrls = stringUtils.getImageDetailsFromHtmlString(siteText);
        for (String s : imgUrls) {
            String name = stringUtils.getNameFromImageUrl(s);
            if (!Objects.equals(s, "")) {
                this.db.put(name, new ImageHelper(s.split(" ")[0], name));
            }
        }
    }

    public String chooseARandomImage() {
        return (String) this.db.keySet().toArray()[r.nextInt(this.db.size())];
    }

    public Bitmap getImage(String name) {
        try {
            return this.db.get(name).fetch();
        } catch (Exception e) {
            Log.e("IMAGE DOWNLOAD FAILURE", e.getMessage());
        }
        return null;
    }
}
