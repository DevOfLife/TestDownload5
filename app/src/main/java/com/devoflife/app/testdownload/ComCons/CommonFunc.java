package com.devoflife.app.testdownload.ComCons;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.Surface;

import com.devoflife.app.testdownload.Entity.AlphanumericSorting;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Admin on 1/28/2017.
 */

public class CommonFunc {
    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isImageFile(String path) {
        //String mimeType = URLConnection.guessContentTypeFromName(path);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        //return mimeType != null && mimeType.indexOf("image") == 0 && options.outWidth != -1 && options.outHeight != -1;
        return options.outWidth != -1 && options.outHeight != -1;
    }

    public static boolean isGifFile(String path){
        return path.endsWith("gif");
    }

    public static List<String> getListFileFromPath(String directoryPath, int filter){
        return getListFileFromPath(directoryPath, filter, AppConstant.FILE_TYPE_FILTER.ALL);
    }

    public static List<String> getListFileFromPath(String directoryPath, int filter, int fileTypeFilter){
        return getListFileFromPath(directoryPath, filter, fileTypeFilter, false);
    }

    public static List<String> getListFileFromPath(String directoryPath, int filter, int fileTypeFilter, boolean recursive){
        List<String> ret = new ArrayList<>();
        List<String> inFolders = new ArrayList<>();
        try {
            File dirFile = new File(directoryPath);
            if (dirFile.listFiles() != null) {
                for (File aFile : dirFile.listFiles()) {
                    if (aFile.isFile() && (filter & AppConstant.FILE_PATH_FILTER.FILE) != 0) {
                        if (CommonFunc.isImageFile(aFile.getPath()) && (fileTypeFilter & AppConstant.FILE_TYPE_FILTER.IMAGE) != 0) {
                            ret.add(aFile.getPath());
                        }
                    }
                    if (aFile.isDirectory()) {
                        inFolders.add(aFile.getPath());
                    }
                }
                Collections.sort(ret, new AlphanumericSorting());

                for(String inFolder : inFolders){
                    if((filter & AppConstant.FILE_PATH_FILTER.FOLDER) != 0) {
                        ret.add(inFolder);
                        if(recursive){
                            List<String> childFolders= getListFileFromPath(inFolder, filter, fileTypeFilter, true);
                            Collections.sort(childFolders, new AlphanumericSorting());
                            ret.addAll(childFolders);
                        }
                    }
                    if(recursive && (filter & AppConstant.FILE_PATH_FILTER.FILE) != 0) {
                        List<String> childFiles= getListFileFromPath(inFolder, filter, fileTypeFilter, true);
                        Collections.sort(childFiles, new AlphanumericSorting());
                        ret.addAll(childFiles);
                    }
                }
            }
        }catch (Exception ex){ex.printStackTrace();}

        return ret;
    }

    public static String getFormatString(String formatString, Object... toFormatString){
        return String.format(Locale.US, formatString, toFormatString);
    }

    @SuppressLint("SimpleDateFormat")
    public static String newTimeStamp(){
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    public static boolean isPortrait(Activity activity){
        int rotation =  activity.getWindowManager().getDefaultDisplay().getRotation();
        return rotation== Surface.ROTATION_0 || rotation== Surface.ROTATION_180;
    }

    public static SpannableString getHighLightString(String text){
        return getHighLightString(text, 0x77000000);
    }

    public static SpannableString getHighLightString(String text, int highLight){
        SpannableString ret = new SpannableString(text);
        ret.setSpan(new BackgroundColorSpan(highLight), 0, text.length(), 0);

        return ret;
    }

    public static int getValidValueInRange(int value, int max){
        int ret= value;
        while (ret< 0 || ret>= max){
            if(ret< 0){ret+= max;}
            if(ret>= max){ret-= max;}
        }

        return ret;
    }
}
