package cn.samnya.nativeandroid.io.csharp;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;

import androidx.core.provider.DocumentsContractCompat;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;

public class Path {

    public static String combine(Context context, String path1, String path2) throws UnsupportedEncodingException {
        if (path1.startsWith("content://")) {
            Uri uri = Uri.parse(path1);
            if (DocumentsContractCompat.isTreeUri(uri)) {

                Uri resultUri;
                if (DocumentsContract.isDocumentUri(context, uri)) {
                    String docId = DocumentsContract.getDocumentId(uri);
                    String resultDocId = docId + "/" + path2;
                    resultUri = DocumentsContract.buildDocumentUriUsingTree(uri, resultDocId);
                } else {
                    String treeDocId = DocumentsContract.getTreeDocumentId(uri);
                    String docId = treeDocId + "/" + path2;
                    resultUri = DocumentsContract.buildDocumentUriUsingTree(uri, docId);
                }
                return resultUri.toString();
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Paths.get(path1, path2).toString();
        } else {
            return path1 + File.separator + path2;
        }
    }

    public static String getDirectoryName(Context context, String path) {
        if (path.startsWith("content://")) {
            Uri uri = Uri.parse(path);
            if (DocumentsContractCompat.isTreeUri(uri)) {

                Uri resultUri;
                if (DocumentsContract.isDocumentUri(context, uri)) {
                    String docId = DocumentsContract.getDocumentId(uri);
                    String resultDocId = docId.substring(0,
                            docId.contains("/") ? docId.lastIndexOf("/") : docId.length() - 1
                    );
                    resultUri = DocumentsContract.buildDocumentUriUsingTree(uri, resultDocId);
                } else {
                    String treeDocId = DocumentsContract.getTreeDocumentId(uri);
                    String docId = treeDocId.substring(0,
                            treeDocId.contains("/") ? treeDocId.lastIndexOf("/") : treeDocId.length() - 1
                    );
                    resultUri = DocumentsContract.buildDocumentUriUsingTree(uri, docId);
                }
                return resultUri.toString();
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Paths.get(path).getParent().toString();
        } else {
            return path.contains(File.separator) ? path.substring(0, path.lastIndexOf(File.separator)) : path;
        }

    }
}
