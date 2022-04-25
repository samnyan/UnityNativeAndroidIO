package cn.samnya.nativeandroid.io.csharp;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class Path {

    public static String combine(Context context, String path1, String path2) throws UnsupportedEncodingException {
        if (path1.startsWith("content://")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri uri = Uri.parse(path1);
                if (DocumentsContract.isTreeUri(uri)) {

                    Uri resultUri;
                    if (DocumentsContract.isDocumentUri(context, uri)) {
                        String docId = DocumentsContract.getDocumentId(uri);
                        String resultDocId = docId + "/" + path2;
                        resultUri = DocumentsContract.buildDocumentUriUsingTree(uri, resultDocId);
                    } else {
                        String docId = uri + "/" + path2;
                        resultUri = DocumentsContract.buildDocumentUriUsingTree(uri, docId);
                    }
                    return resultUri.toString();
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Paths.get(path1, path2).toString();
        } else {
            return path1 + File.separator + path2;
        }
    }
}
