package com.devoflife.app.testdownload.ComCons;

import android.Manifest;

/**
 * Created by n on 4/11/2017.
 */

public class AppConstant {
    public static final String[] APP_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final int DEFAULT_ANIMATION_DURATION= 700;

    public class REQUEST_CODE{
        public static final int PERMISSION = 0;
    }

    public class FILE_PATH_FILTER {
        public static final int FILE= 0x01;
        public static final int FOLDER= 0x10;
        public static final int BOTH= FILE | FOLDER;
    }

    public class FILE_TYPE_FILTER{
        public static final int IMAGE= 0x01;
        public static final int VIDEO= 0x10;
        public static final int ALL= IMAGE | VIDEO;
    }
}
