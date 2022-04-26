package cn.samnya.nativeandroid.io.csharp;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.util.Log;

import androidx.documentfile.provider.DocumentFile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class File {

    private static final String TAG = "AndroidIO.File";

    public static boolean exists(Context context, String uriString) {
        Uri uri = Uri.parse(uriString);
        DocumentFile  dir = DocumentFile.fromSingleUri(context, uri);

        boolean result = dir != null && dir.exists();
        Log.d(TAG, "exists result : " + result);
        return result;
    }

    public static String readAllText(Context context, String uriString) {
        Uri uri = Uri.parse(uriString);
        if (DocumentsContract.isDocumentUri(context, uri)) {
            DocumentFile file = DocumentFile.fromSingleUri(context, uri);
            if (file.exists()) {
                try (
                        ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                        FileInputStream fis = new FileInputStream(pfd.getFileDescriptor());
                        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                        BufferedReader br = new BufferedReader(isr);
                ) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    return sb.toString();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;

    }
}
