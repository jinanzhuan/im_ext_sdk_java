package com.easemob.ext_sdk.dispatch;

import com.easemob.ext_sdk.common.ExtSdkCallback;
import com.easemob.ext_sdk.common.ExtSdkContext;
import com.easemob.ext_sdk.common.ExtSdkMethodType;
import com.easemob.ext_sdk.common.ExtSdkThreadUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMMultiDeviceListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMDeviceInfo;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.exceptions.HyphenateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class ExtSdkClientWrapper extends ExtSdkWrapper {

    public static class SingleHolder { static ExtSdkClientWrapper instance = new ExtSdkClientWrapper(); }

    public static ExtSdkClientWrapper getInstance() { return ExtSdkClientWrapper.SingleHolder.instance; }

    public void getToken(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        onSuccess(result, channelName, EMClient.getInstance().getAccessToken());
    }

    public void createAccount(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        String username = param.getString("username");
        String password = param.getString("password");
        try {
            EMClient.getInstance().createAccount(username, password);
            onSuccess(result, channelName, username);
        } catch (HyphenateException e) {
            onError(result, e, null);
        }
    }

    public void login(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        boolean isPwd = param.getBoolean("isPassword");
        String username = param.getString("username");
        String pwdOrToken = param.getString("pwdOrToken");

        if (isPwd) {
            EMClient.getInstance().login(username, pwdOrToken, new EMCallBack() {
                @Override
                public void onSuccess() {
                    Map<String, String> param = new HashMap<>();
                    param.put("username", EMClient.getInstance().getCurrentUser());
                    param.put("token", EMClient.getInstance().getAccessToken());
                    ExtSdkWrapper.onSuccess(result, channelName, param);
                }

                @Override
                public void onError(int code, String error) {
                    ExtSdkWrapper.onError(result, code, error);
                }

                @Override
                public void onProgress(int progress, String status) {
                    // todo: 原来就没有写
                }
            });
        } else {
            EMClient.getInstance().loginWithToken(username, pwdOrToken, new EMCallBack() {
                @Override
                public void onSuccess() {
                    Map<String, String> param = new HashMap<>();
                    param.put("username", EMClient.getInstance().getCurrentUser());
                    param.put("token", EMClient.getInstance().getAccessToken());
                    ExtSdkWrapper.onSuccess(result, channelName, param);
                }

                @Override
                public void onError(int code, String error) {
                    ExtSdkWrapper.onError(result, code, error);
                }

                @Override
                public void onProgress(int progress, String status) {
                    // todo: 原来就没有写
                }
            });
        }
    }

    public void logout(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        boolean unbindToken = param.getBoolean("unbindToken");
        EMClient.getInstance().logout(unbindToken, new EMCallBack() {
            @Override
            public void onSuccess() {
                ExtSdkWrapper.onSuccess(result, channelName, true);
            }

            @Override
            public void onError(int code, String error) {
                ExtSdkWrapper.onError(result, code, error);
            }

            @Override
            public void onProgress(int progress, String status) {}
        });
    }

    public void changeAppKey(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        String appKey = param.getString("appKey");
        try {
            EMClient.getInstance().changeAppkey(appKey);
            onSuccess(result, channelName, true);
        } catch (HyphenateException e) {
            onError(result, e, null);
        }
    }

    public void getCurrentUser(JSONObject param, String channelName, ExtSdkCallback result) {
        onSuccess(result, channelName, EMClient.getInstance().getCurrentUser());
    }

    public void uploadLog(JSONObject param, String channelName, ExtSdkCallback result) {
        EMClient.getInstance().uploadLog(new EMCallBack() {
            @Override
            public void onSuccess() {}

            @Override
            public void onError(int code, String error) {}

            @Override
            public void onProgress(int progress, String status) {}
        });
    }

    public void compressLogs(JSONObject param, String channelName, ExtSdkCallback result) {
        try {
            String path = EMClient.getInstance().compressLogs();
            onSuccess(result, channelName, path);
        } catch (HyphenateException e) {
            onError(result, e, null);
        }
    }

    public void kickDevice(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {

        String username = param.getString("username");
        String password = param.getString("password");
        String resource = param.getString("resource");

        try {
            EMClient.getInstance().kickDevice(username, password, resource);
            onSuccess(result, channelName, true);
        } catch (HyphenateException e) {
            onError(result, e, null);
        }
    }

    public void kickAllDevices(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        String username = param.getString("username");
        String password = param.getString("password");

        try {
            EMClient.getInstance().kickAllDevices(username, password);
            onSuccess(result, channelName, true);
        } catch (HyphenateException e) {
            onError(result, e, null);
        }
    }

    public void isLoggedInBefore(JSONObject param, String channelName, ExtSdkCallback result) {
        onSuccess(result, channelName, EMClient.getInstance().isLoggedInBefore());
    }

    public void getLoggedInDevicesFromServer(JSONObject param, String channelName, ExtSdkCallback result)
        throws JSONException {
        String username = param.getString("username");
        String password = param.getString("password");
        try {
            List<EMDeviceInfo> devices = EMClient.getInstance().getLoggedInDevicesFromServer(username, password);
            List<Map> jsonList = new ArrayList<>();
            for (EMDeviceInfo info : devices) {
                jsonList.add(ExtSdkDeviceInfoHelper.toJson(info));
            }
            onSuccess(result, channelName, jsonList);
        } catch (HyphenateException e) {
            onError(result, e, null);
        }
    }

    public void init(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        EMOptions options = ExtSdkOptionsHelper.fromJson(param, ExtSdkContext.context);
        boolean debugModel = param.getBoolean("debugModel");

        EMOptions finalOptions = options;
        boolean finalDebugModel = debugModel;

        ExtSdkThreadUtil.mainThreadExecute(() -> {
            EMClient.getInstance().init(ExtSdkContext.context, finalOptions);
            EMClient.getInstance().setDebugMode(finalDebugModel);

            addEMListener();

            ExtSdkChatManagerWrapper.getInstance();
            ExtSdkChatRoomManagerWrapper.getInstance();
            ExtSdkContactManagerWrapper.getInstance();
            ExtSdkConversationWrapper.getInstance();
            ExtSdkGroupManagerWrapper.getInstance();
            ExtSdkPresenceManagerWrapper.getInstance();
            ExtSdkPushManagerWrapper.getInstance();
            ExtSdkUserInfoManagerWrapper.getInstance();

            ExtSdkThreadUtil.asyncExecute(() -> { onSuccess(result, channelName, null); });
        });
    }

    public void loginWithAgoraToken(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        String username = param.getString("username");
        String agoratoken = param.getString("agoratoken");

        EMClient.getInstance().loginWithAgoraToken(username, agoratoken, new EMCallBack() {
            @Override
            public void onSuccess() {
                Map<String, String> param = new HashMap<>();
                param.put("username", EMClient.getInstance().getCurrentUser());
                param.put("token", EMClient.getInstance().getAccessToken());
                ExtSdkWrapper.onSuccess(result, channelName, param);
            }

            @Override
            public void onError(int code, String error) {
                ExtSdkWrapper.onError(result, code, error);
            }

            @Override
            public void onProgress(int progress, String status) {}
        });
    }

    public void isConnected(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        onSuccess(result, channelName, EMClient.getInstance().isConnected());
    }

    public void renewToken(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        String agoraToken = param.getString("agora_token");
        EMClient.getInstance().renewToken(agoraToken);
        onSuccess(result, channelName, null);
    }

    public void addEMListener() {
        if (this.multiDeviceListener != null) {
            EMClient.getInstance().removeMultiDeviceListener(this.multiDeviceListener);
        }
        this.multiDeviceListener = new EMMultiDeviceListener() {
            @Override
            public void onContactEvent(int event, String target, String ext) {
                Map<String, Object> data = new HashMap<>();
                data.put("event", Integer.valueOf(event));
                data.put("target", target);
                data.put("ext", ext);
                onReceive(ExtSdkMethodType.onMultiDeviceEvent, data);
            }

            @Override
            public void onGroupEvent(int event, String target, List<String> userNames) {
                Map<String, Object> data = new HashMap<>();
                data.put("event", Integer.valueOf(event));
                data.put("target", target);
                data.put("userNames", userNames);
                onReceive(ExtSdkMethodType.onMultiDeviceEvent, data);
            }
        };
        EMClient.getInstance().addMultiDeviceListener(this.multiDeviceListener);

        if (this.connectionListener != null) {
            EMClient.getInstance().removeConnectionListener(this.connectionListener);
        }

        this.connectionListener = new EMConnectionListener() {
            @Override
            public void onConnected() {
                Map<String, Object> data = new HashMap<>();
                data.put("connected", Boolean.TRUE);
                onReceive(ExtSdkMethodType.onConnected, data);
            }

            @Override
            public void onDisconnected(int errorCode) {
                if (errorCode == 206) {
                    onReceive(ExtSdkMethodType.onUserDidLoginFromOtherDevice, null);
                } else if (errorCode == 207) {
                    onReceive(ExtSdkMethodType.onUserDidRemoveFromServer, null);
                } else if (errorCode == 305) {
                    onReceive(ExtSdkMethodType.onUserDidForbidByServer, null);
                } else if (errorCode == 216) {
                    onReceive(ExtSdkMethodType.onUserDidChangePassword, null);
                } else if (errorCode == 214) {
                    onReceive(ExtSdkMethodType.onUserDidLoginTooManyDevice, null);
                } else if (errorCode == 217) {
                    onReceive(ExtSdkMethodType.onUserKickedByOtherDevice, null);
                } else if (errorCode == 202) {
                    onReceive(ExtSdkMethodType.onUserAuthenticationFailed, null);
                } else {
                    onReceive(ExtSdkMethodType.onDisconnected, null);
                }
            }

            @Override
            public void onTokenExpired() {
                onReceive(ExtSdkMethodType.onTokenDidExpire, null);
            }

            @Override
            public void onTokenWillExpire() {
                onReceive(ExtSdkMethodType.onTokenWillExpire, null);
            }
        };

        // setup connection listener
        EMClient.getInstance().addConnectionListener(this.connectionListener);
    }

    private EMConnectionListener connectionListener;
    private EMMultiDeviceListener multiDeviceListener;
}
