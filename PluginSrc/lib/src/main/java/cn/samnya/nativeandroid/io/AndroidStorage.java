package cn.samnya.nativeandroid.io;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.core.provider.DocumentsContractCompat;

import java.io.File;



public class AndroidStorage {

    private static final String TAG = "AndroidIO.StorageUtil";

    public static String getRealPathFromContentUri(Context context, String uriString) {
        Uri uri = Uri.parse(uriString);

        if (DocumentsContractCompat.isTreeUri(uri)) {
            // Tree uri looks like content://com.android.externalstorage.documents/tree/primary%3AMusic

            // treeDocId is the "primary%3AMusic" part.
            final String treeDocId = DocumentsContract.getTreeDocumentId(uri);
            final String[] split = treeDocId.split(":");
            final String type = split[0];

            String filePath;
            if (DocumentsContract.isDocumentUri(context, uri)) {
                filePath = DocumentsContract.getDocumentId(uri).split(":")[1];
            } else {
                filePath = split[split.length -1];
            }

            Log.d(TAG, "getRealPathFromContentUri file path: " + filePath);

            // If selected from primary storage (/sdcard), the type is primary
            if ("primary".equalsIgnoreCase(type)) {
                return Environment.getExternalStorageDirectory() + "/" + filePath;
            }

            // If selected from external sd card, the type is looks like "D83B-111A"
            // getExternalFilesDirs will get all data dir for application (/Android/data/<package name>/files)
            File[] externalStorageVolumes = ContextCompat.getExternalFilesDirs(context, null);
            for (File f : externalStorageVolumes) {
                // the absolute path should look like /storage/D83B-111A/Android/data/<package name>/files
                String appDataPath = f.getAbsolutePath();
                Log.d("File Reader", appDataPath);

                // Get the prefix from path.
                if (appDataPath.contains(type)) {
                    String storagePath = appDataPath.substring(0, appDataPath.indexOf(type) + type.length());
                    return storagePath + "/" + filePath;
                }

            }
        }
        return null;
    }

    public static void openDocumentTree(Context context, String callbackGameObject, String resultCallbackMethod, String errorCallbackMethod) {
        // Choose a directory using the system's file picker.
        ResultHandler.setCallback(Constant.REQUEST_CODE_ACTION_OPEN_DOCUMENT_TREE, callbackGameObject, resultCallbackMethod);
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        ((Activity) context).startActivityForResult(intent, Constant.REQUEST_CODE_ACTION_OPEN_DOCUMENT_TREE);
    }

    public static String openDocumentTreeResult(Context context, Uri uri) {
        final int takeFlags = (Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        context.getContentResolver().takePersistableUriPermission(uri, takeFlags);
        return uri.toString();
    }
}
