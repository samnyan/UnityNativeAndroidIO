package cn.samnya.nativeandroid.io;

import static cn.samnya.nativeandroid.io.Constant.REQUEST_CODE_ACTION_OPEN_DOCUMENT_TREE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

public class ResultHandler {

    private static final Map<Integer, UnityCallback> callbackMap = new HashMap<>();

    public static void setCallback(int code, String callbackGameObject, String callbackMethod) {
        callbackMap.remove(code);
        callbackMap.put(code, new UnityCallback(callbackGameObject, callbackMethod));
    }

    public static void invoke(int code, String result) {
        if (callbackMap.containsKey(code)) {
            UnityCallback cb = callbackMap.get(code);
            cb.invoke(result);
            callbackMap.remove(code);
        }
    }

    public static void remove(int code) {
        callbackMap.remove(code);
    }

    public static void dispatchResult(int requestCode, int resultCode, Intent data, Context context) {
        if (resultCode == Activity.RESULT_OK) {
            String result = null;
            switch (requestCode) {
                case REQUEST_CODE_ACTION_OPEN_DOCUMENT_TREE: {
                    result = AndroidStorage.openDocumentTreeResult(context, data.getData());
                }
                default: {

                }
            }

            if (result != null) {
                invoke(requestCode, result);
            }
        }
    }
}
