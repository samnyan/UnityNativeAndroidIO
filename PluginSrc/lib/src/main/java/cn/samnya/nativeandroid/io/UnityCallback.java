package cn.samnya.nativeandroid.io;

import com.unity3d.player.UnityPlayer;

public class UnityCallback {

    private String callbackGameObject;
    private String callbackMethod;

    public UnityCallback(String callbackGameObject, String callbackMethod) {
        this.callbackGameObject = callbackGameObject;
        this.callbackMethod = callbackMethod;
    }

    public String getCallbackGameObject() {
        return callbackGameObject;
    }

    public void setCallbackGameObject(String callbackGameObject) {
        this.callbackGameObject = callbackGameObject;
    }

    public String getCallbackMethod() {
        return callbackMethod;
    }

    public void setCallbackMethod(String callbackMethod) {
        this.callbackMethod = callbackMethod;
    }

    public void invoke(String returnValue) {
        UnityPlayer.UnitySendMessage(callbackGameObject, callbackMethod, returnValue);
    }

    public static void Invoke(String callbackGameObject, String callbackMethod, String returnValue) {
        UnityPlayer.UnitySendMessage(callbackGameObject, callbackMethod, returnValue);
    }
}
