package cn.samnya.nativeandroid.io.csharp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.core.provider.DocumentsContractCompat;
import androidx.documentfile.provider.DocumentFile;

import java.util.LinkedList;
import java.util.List;

import cn.samnya.nativeandroid.io.util.FilenameUtils;
import cn.samnya.nativeandroid.io.util.IOCase;

public class Directory {

    private static final String TAG = "AndroidIO.Directory";

    public static String[] enumerateDirectories(Context context, String uriString) {
        Uri uri = Uri.parse(uriString);
        List<String> result = new LinkedList<>();

        DocumentFile currentDir = null;

        if (DocumentsContractCompat.isTreeUri(uri)) {
            currentDir = DocumentFile.fromTreeUri(context, uri);
        } else {
            DocumentFile currentFile = DocumentFile.fromSingleUri(context, uri);
            if (currentFile.isDirectory()) {
                currentDir = currentFile;
            }
        }

        if (currentDir != null) {
            for (DocumentFile file: currentDir.listFiles()) {
                if (file.isDirectory()) {
                    result.add(file.getUri().toString());
                }
            }
        }
        Log.d(TAG, "enumerateDirectories result size: " + result.size());
        return result.toArray(new String[0]);
    }

    public static String[] enumerateFiles(Context context, String uriString, String wildcard) {
        Uri uri = Uri.parse(uriString);
        List<String> result = new LinkedList<>();

        DocumentFile currentDir = null;

        if (DocumentsContractCompat.isTreeUri(uri)) {
            currentDir = DocumentFile.fromTreeUri(context, uri);
        } else {
            DocumentFile currentFile = DocumentFile.fromSingleUri(context, uri);
            if (currentFile.isDirectory()) {
                currentDir = currentFile;
            }
        }

        if (currentDir != null) {
            for (DocumentFile file: currentDir.listFiles()) {
                if (FilenameUtils.wildcardMatch(file.getName(), wildcard, IOCase.INSENSITIVE)) {
                    result.add(file.getUri().toString());
                }
            }
        }
        Log.d(TAG, "enumerateFiles result size: " + result.size());
        return result.toArray(new String[0]);
    }

    public static boolean exists(Context context, String uriString) {
        Uri uri = Uri.parse(uriString);
        DocumentFile  dir = DocumentFile.fromSingleUri(context, uri);

        boolean result = dir != null && dir.exists();
        Log.d(TAG, "exists result : " + result);
        return result;
    }

    public static String getName(Context context, String uriString) {
        Uri uri = Uri.parse(uriString);
        DocumentFile  dir = DocumentFile.fromSingleUri(context, uri);

        String result = dir.getName();
        Log.d(TAG, "getName result : " + result);
        return result;
    }
}
