package com.easemob.ext_sdk.dispatch;

import com.easemob.ext_sdk.common.ExtSdkCallback;
import com.easemob.ext_sdk.common.ExtSdkMethodType;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConversationListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroupReadAck;
import com.hyphenate.chat.EMLanguage;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExtSdkChatManagerWrapper extends ExtSdkWrapper {

    public static class SingleHolder { static ExtSdkChatManagerWrapper instance = new ExtSdkChatManagerWrapper(); }

    public static ExtSdkChatManagerWrapper getInstance() { return ExtSdkChatManagerWrapper.SingleHolder.instance; }

    ExtSdkChatManagerWrapper() { registerEaseListener(); }

    public void sendMessage(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        final EMMessage msg = ExtSdkMessageHelper.fromJson(param);
        msg.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                Map<String, Object> map = new HashMap<>();
                map.put("message", ExtSdkMessageHelper.toJson(msg));
                map.put("localTime", msg.localTime());
                map.put("callbackType", ExtSdkMethodType.onMessageSuccess);
                ExtSdkWrapper.onReceive(channelName, map);
            }

            @Override
            public void onProgress(int progress, String status) {

                Map<String, Object> map = new HashMap<>();
                map.put("progress", progress);
                map.put("localTime", msg.localTime());
                map.put("callbackType", ExtSdkMethodType.onMessageProgressUpdate);
                ExtSdkWrapper.onReceive(channelName, map);
            }

            @Override
            public void onError(int code, String desc) {
                Map<String, Object> data = new HashMap<>();
                data.put("code", code);
                data.put("description", desc);

                Map<String, Object> map = new HashMap<>();
                map.put("message", ExtSdkMessageHelper.toJson(msg));
                map.put("localTime", msg.localTime());
                map.put("error", data);
                map.put("callbackType", ExtSdkMethodType.onMessageError);
                ExtSdkWrapper.onReceive(channelName, map);
            }
        });

        EMClient.getInstance().chatManager().sendMessage(msg);
        onSuccess(result, channelName, ExtSdkMessageHelper.toJson(msg));
    }

    public void resendMessage(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        EMMessage tempMsg = ExtSdkMessageHelper.fromJson(param);
        EMMessage msg = EMClient.getInstance().chatManager().getMessage(tempMsg.getMsgId());
        if (msg == null) {
            msg = tempMsg;
        }
        msg.setStatus(EMMessage.Status.CREATE);
        EMMessage finalMsg = msg;
        finalMsg.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {

                Map<String, Object> map = new HashMap<>();
                map.put("message", ExtSdkMessageHelper.toJson(finalMsg));
                map.put("localTime", finalMsg.localTime());
                map.put("callbackType", ExtSdkMethodType.onMessageSuccess);
                ExtSdkWrapper.onReceive(channelName, map);
            }

            @Override
            public void onProgress(int progress, String status) {

                Map<String, Object> map = new HashMap<>();
                map.put("progress", progress);
                map.put("localTime", finalMsg.localTime());
                map.put("callbackType", ExtSdkMethodType.onMessageProgressUpdate);
                ExtSdkWrapper.onReceive(channelName, map);
            }

            @Override
            public void onError(int code, String desc) {
                Map<String, Object> data = new HashMap<>();
                data.put("code", code);
                data.put("description", desc);

                Map<String, Object> map = new HashMap<>();
                map.put("message", ExtSdkMessageHelper.toJson(finalMsg));
                map.put("localTime", finalMsg.localTime());
                map.put("error", data);
                map.put("callbackType", ExtSdkMethodType.onMessageError);
                ExtSdkWrapper.onReceive(channelName, map);
            }
        });
        EMClient.getInstance().chatManager().sendMessage(msg);

        onSuccess(result, channelName, ExtSdkMessageHelper.toJson(finalMsg));
    }

    public void ackMessageRead(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        String msgId = param.getString("msg_id");
        String to = param.getString("to");

        try {
            EMClient.getInstance().chatManager().ackMessageRead(to, msgId);
            onSuccess(result, channelName, true);
        } catch (HyphenateException e) {
            onError(result, e, null);
        }
    }

    public void ackGroupMessageRead(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        String msgId = param.getString("msg_id");
        String to = param.getString("group_id");
        String content = null;
        if (param.has("content")) {
            content = param.getString("content");
        }
        String finalContent = content;

        try {
            EMClient.getInstance().chatManager().ackGroupMessageRead(to, msgId, finalContent);
        } catch (HyphenateException e) {
            onError(result, e, null);
        }
    }

    public void ackConversationRead(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        String conversationId = param.getString("con_id");

        try {
            EMClient.getInstance().chatManager().ackConversationRead(conversationId);
            onSuccess(result, channelName, true);
        } catch (HyphenateException e) {
            onError(result, e, null);
        }
    }

    public void recallMessage(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        String msgId = param.getString("msg_id");

        try {
            EMMessage msg = EMClient.getInstance().chatManager().getMessage(msgId);
            if (msg != null) {
                EMClient.getInstance().chatManager().recallMessage(msg);
            }
            onSuccess(result, channelName, true);
        } catch (HyphenateException e) {
            onError(result, e, null);
        }
    }

    public void getMessage(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        String msgId = param.getString("msg_id");

        EMMessage msg = EMClient.getInstance().chatManager().getMessage(msgId);
        onSuccess(result, channelName, ExtSdkMessageHelper.toJson(msg));
    }

    public void getConversation(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        String conId = param.getString("con_id");
        EMConversation.EMConversationType type = ExtSdkConversationHelper.typeFromInt(param.getInt("type"));

        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(conId, type, true);
        onSuccess(result, channelName, ExtSdkConversationHelper.toJson(conversation));
    }

    public void markAllChatMsgAsRead(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        EMClient.getInstance().chatManager().markAllConversationsAsRead();

        onSuccess(result, channelName, true);
    }

    public void getUnreadMessageCount(JSONObject param, String channelName, ExtSdkCallback result)
        throws JSONException {
        int count = EMClient.getInstance().chatManager().getUnreadMessageCount();

        onSuccess(result, channelName, count);
    }

    public void updateChatMessage(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        EMMessage msg = ExtSdkMessageHelper.fromJson(param.getJSONObject("message"));

        EMClient.getInstance().chatManager().updateMessage(msg);
        onSuccess(result, channelName, ExtSdkMessageHelper.toJson(msg));
    }

    public void importMessages(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        JSONArray ary = param.getJSONArray("messages");
        List<EMMessage> messages = new ArrayList<>();
        for (int i = 0; i < ary.length(); i++) {
            JSONObject obj = ary.getJSONObject(i);
            messages.add(ExtSdkMessageHelper.fromJson(obj));
        }

        EMClient.getInstance().chatManager().importMessages(messages);
        onSuccess(result, channelName, true);
    }

    public void downloadAttachment(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        EMMessage tempMsg = ExtSdkMessageHelper.fromJson(param.getJSONObject("message"));
        final EMMessage msg = EMClient.getInstance().chatManager().getMessage(tempMsg.getMsgId());
        msg.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {

                Map<String, Object> map = new HashMap<>();
                map.put("message", ExtSdkMessageHelper.toJson(msg));
                map.put("localTime", msg.localTime());
                map.put("callbackType", ExtSdkMethodType.onMessageSuccess);
                ExtSdkWrapper.onReceive(channelName, map);
            }

            @Override
            public void onProgress(int progress, String status) {

                Map<String, Object> map = new HashMap<>();
                map.put("progress", progress);
                map.put("localTime", msg.localTime());
                map.put("callbackType", ExtSdkMethodType.onMessageProgressUpdate);
                ExtSdkWrapper.onReceive(channelName, map);
            }

            @Override
            public void onError(int code, String desc) {
                Map<String, Object> data = new HashMap<>();
                data.put("code", code);
                data.put("description", desc);

                Map<String, Object> map = new HashMap<>();
                map.put("message", ExtSdkMessageHelper.toJson(msg));
                map.put("localTime", msg.localTime());
                map.put("error", data);
                map.put("callbackType", ExtSdkMethodType.onMessageError);
                ExtSdkWrapper.onReceive(channelName, map);
            }
        });

        EMClient.getInstance().chatManager().downloadAttachment(msg);
        onSuccess(result, channelName, ExtSdkMessageHelper.toJson(msg));
    }

    public void downloadThumbnail(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        EMMessage tempMsg = ExtSdkMessageHelper.fromJson(param.getJSONObject("message"));
        final EMMessage msg = EMClient.getInstance().chatManager().getMessage(tempMsg.getMsgId());
        msg.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {

                Map<String, Object> map = new HashMap<>();
                map.put("message", ExtSdkMessageHelper.toJson(msg));
                map.put("localTime", msg.localTime());
                map.put("callbackType", ExtSdkMethodType.onMessageSuccess);
                ExtSdkWrapper.onReceive(channelName, map);
            }

            @Override
            public void onProgress(int progress, String status) {

                Map<String, Object> map = new HashMap<>();
                map.put("progress", progress);
                map.put("localTime", msg.localTime());
                map.put("callbackType", ExtSdkMethodType.onMessageProgressUpdate);
                ExtSdkWrapper.onReceive(channelName, map);
            }

            @Override
            public void onError(int code, String desc) {
                Map<String, Object> data = new HashMap<>();
                data.put("code", code);
                data.put("description", desc);

                Map<String, Object> map = new HashMap<>();
                map.put("message", ExtSdkMessageHelper.toJson(msg));
                map.put("localTime", msg.localTime());
                map.put("error", data);
                map.put("callbackType", ExtSdkMethodType.onMessageError);
                ExtSdkWrapper.onReceive(channelName, map);
            }
        });

        EMClient.getInstance().chatManager().downloadThumbnail(msg);
        onSuccess(result, channelName, ExtSdkMessageHelper.toJson(msg));
    }

    public void loadAllConversations(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {

        List<EMConversation> list =
            new ArrayList<>(EMClient.getInstance().chatManager().getAllConversations().values());
        Collections.sort(list, new Comparator<EMConversation>() {
            @Override
            public int compare(EMConversation o1, EMConversation o2) {
                if (o1.getLastMessage() == null) {
                    return 1;
                }

                if (o2.getLastMessage() == null) {
                    return -1;
                }
                return o2.getLastMessage().getMsgTime() - o1.getLastMessage().getMsgTime() > 0 ? 1 : -1;
            }
        });
        List<Map> conversations = new ArrayList<>();
        for (EMConversation conversation : list) {
            conversations.add(ExtSdkConversationHelper.toJson(conversation));
        }
        onSuccess(result, channelName, conversations);
    }

    public void getConversationsFromServer(JSONObject param, String channelName, ExtSdkCallback result)
        throws JSONException {

        try {
            List<EMConversation> list =
                new ArrayList<>(EMClient.getInstance().chatManager().fetchConversationsFromServer().values());
            Collections.sort(
                list, (o1, o2) -> (o2.getLastMessage().getMsgTime() - o1.getLastMessage().getMsgTime() > 0 ? 1 : -1));
            List<Map> conversations = new ArrayList<>();
            for (EMConversation conversation : list) {
                conversations.add(ExtSdkConversationHelper.toJson(conversation));
            }
            onSuccess(result, channelName, conversations);
        } catch (HyphenateException e) {
            onError(result, e, null);
        }
    }

    public void deleteConversation(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        String conId = param.getString("con_id");
        boolean isDelete = param.getBoolean("deleteMessages");

        boolean ret = EMClient.getInstance().chatManager().deleteConversation(conId, isDelete);
        onSuccess(result, channelName, ret);
    }

    public void fetchHistoryMessages(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        String conId = param.getString("con_id");
        EMConversation.EMConversationType type = ExtSdkConversationHelper.typeFromInt(param.getInt("type"));
        int pageSize = param.getInt("pageSize");
        String startMsgId = param.getString("startMsgId");

        try {
            EMCursorResult<EMMessage> cursorResult =
                EMClient.getInstance().chatManager().fetchHistoryMessages(conId, type, pageSize, startMsgId);
            onSuccess(result, channelName, ExtSdkCursorResultHelper.toJson(cursorResult));
        } catch (HyphenateException e) {
            onError(result, e, null);
        }
    }

    public void searchChatMsgFromDB(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        String keywords = param.getString("keywords");
        long timeStamp = param.getLong("timeStamp");
        int count = param.getInt("maxCount");
        String from = param.getString("from");
        EMConversation.EMSearchDirection direction = searchDirectionFromString(param.getString("direction"));

        List<EMMessage> msgList =
            EMClient.getInstance().chatManager().searchMsgFromDB(keywords, timeStamp, count, from, direction);
        List<Map> messages = new ArrayList<>();
        for (EMMessage msg : msgList) {
            messages.add(ExtSdkMessageHelper.toJson(msg));
        }
        onSuccess(result, channelName, messages);
    }

    public void asyncFetchGroupAcks(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        String msgId = param.getString("msg_id");
        String ackId = param.getString("ack_id");
        int pageSize = param.getInt("pageSize");
        EMClient.getInstance().chatManager().asyncFetchGroupReadAcks(
            msgId, pageSize, ackId, new EMValueCallBack<EMCursorResult<EMGroupReadAck>>() {
                @Override
                public void onSuccess(EMCursorResult<EMGroupReadAck> value) {
                    ExtSdkWrapper.onSuccess(result, channelName, ExtSdkCursorResultHelper.toJson(value));
                }

                @Override
                public void onError(int error, String errorMsg) {
                    ExtSdkWrapper.onError(result, error, errorMsg);
                }
            });
    }

    public void deleteRemoteConversation(JSONObject param, String channelName, ExtSdkCallback result)
        throws JSONException {
        String conversationId = param.getString("conversationId");
        EMConversation.EMConversationType type = typeFromInt(param.getInt("conversationType"));
        boolean isDeleteRemoteMessage = param.getBoolean("isDeleteRemoteMessage");
        EMClient.getInstance().chatManager().deleteConversationFromServer(
            conversationId, type, isDeleteRemoteMessage, new EMCallBack() {
                @Override
                public void onSuccess() {
                    ExtSdkWrapper.onSuccess(result, channelName, null);
                }

                @Override
                public void onError(int code, String error) {
                    ExtSdkWrapper.onError(result, error, "");
                }

                @Override
                public void onProgress(int progress, String status) {}
            });
    }

    private EMConversation.EMConversationType typeFromInt(int conversationType) {
        EMConversation.EMConversationType ret = EMConversation.EMConversationType.Chat;
        switch (conversationType) {
        case 0:
            ret = EMConversation.EMConversationType.Chat;
            break;
        case 1:
            ret = EMConversation.EMConversationType.GroupChat;
            break;
        case 2:
            ret = EMConversation.EMConversationType.ChatRoom;
            break;
        }
        return ret;
    }

    public void translateMessage(JSONObject param, String channelName, ExtSdkCallback result) throws JSONException {
        EMMessage msg = ExtSdkMessageHelper.fromJson(param.getJSONObject("message"));
        List<String> list = new ArrayList<String>();
        if (param.has("languages")) {
            JSONArray array = param.getJSONArray("languages");
            for (int i = 0; i < array.length(); i++) {
                list.add(array.getString(i));
            }
        }
        EMClient.getInstance().chatManager().translateMessage(msg, list, new EMValueCallBack<EMMessage>() {
            @Override
            public void onSuccess(EMMessage value) {
                Map<String, Object> data = new HashMap<>();
                data.put("message", ExtSdkMessageHelper.toJson(value));
                ExtSdkWrapper.onSuccess(result, channelName, data);
            }

            @Override
            public void onError(int error, String errorMsg) {
                ExtSdkWrapper.onError(result, error, errorMsg);
            }
        });
    }

    public void fetchSupportedLanguages(JSONObject param, String channelName, ExtSdkCallback result)
        throws JSONException {
        EMClient.getInstance().chatManager().fetchSupportLanguages(new EMValueCallBack<List<EMLanguage>>() {
            @Override
            public void onSuccess(List<EMLanguage> value) {
                List<Map> list = new ArrayList<>();
                for (EMLanguage language : value) {
                    list.add(ExtSdkLanguageHelper.toJson(language));
                }
                ExtSdkWrapper.onSuccess(result, channelName, list);
            }

            @Override
            public void onError(int error, String errorMsg) {}
        });
    }

    private void registerEaseListener() {
        if (this.messageListener != null) {
            EMClient.getInstance().chatManager().removeMessageListener(this.messageListener);
        }
        this.messageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                ArrayList<Map<String, Object>> msgList = new ArrayList<>();
                for (EMMessage message : messages) {
                    msgList.add(ExtSdkMessageHelper.toJson(message));
                }
                ExtSdkWrapper.onReceive(ExtSdkMethodType.onMessagesReceived, msgList);
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {

                ArrayList<Map<String, Object>> msgList = new ArrayList<>();
                for (EMMessage message : messages) {
                    msgList.add(ExtSdkMessageHelper.toJson(message));
                }
                ExtSdkWrapper.onReceive(ExtSdkMethodType.onCmdMessagesReceived, msgList);
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                ArrayList<Map<String, Object>> msgList = new ArrayList<>();
                for (EMMessage message : messages) {
                    msgList.add(ExtSdkMessageHelper.toJson(message));
                    ExtSdkWrapper.onReceive(ExtSdkMethodType.onMessageReadAck, ExtSdkMessageHelper.toJson(message));
                }

                ExtSdkWrapper.onReceive(ExtSdkMethodType.onMessagesRead, msgList);
            }

            @Override
            public void onMessageDelivered(List<EMMessage> messages) {
                ArrayList<Map<String, Object>> msgList = new ArrayList<>();
                for (EMMessage message : messages) {
                    msgList.add(ExtSdkMessageHelper.toJson(message));
                    ExtSdkWrapper.onReceive(ExtSdkMethodType.onMessageDeliveryAck, ExtSdkMessageHelper.toJson(message));
                }
                ExtSdkWrapper.onReceive(ExtSdkMethodType.onMessagesDelivered, msgList);
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {
                ArrayList<Map<String, Object>> msgList = new ArrayList<>();
                for (EMMessage message : messages) {
                    msgList.add(ExtSdkMessageHelper.toJson(message));
                }
                ExtSdkWrapper.onReceive(ExtSdkMethodType.onMessagesRecalled, msgList);
            }

            @Override
            public void onGroupMessageRead(List<EMGroupReadAck> var1) {
                ArrayList<Map<String, Object>> msgList = new ArrayList<>();
                for (EMGroupReadAck ack : var1) {
                    msgList.add(ExtSdkGroupAckHelper.toJson(ack));
                }
                ExtSdkWrapper.onReceive(ExtSdkMethodType.onGroupMessageRead, msgList);
            }

            @Override
            public void onReadAckForGroupMessageUpdated() {}
        };
        EMClient.getInstance().chatManager().addMessageListener(this.messageListener);

        if (this.conversationListener != null) {
            EMClient.getInstance().chatManager().removeConversationListener(this.conversationListener);
        }
        this.conversationListener = new EMConversationListener() {
            @Override
            public void onCoversationUpdate() {
                Map<String, Object> data = new HashMap<>();
                ExtSdkWrapper.onReceive(ExtSdkMethodType.onConversationUpdate, data);
            }

            @Override
            public void onConversationRead(String from, String to) {
                Map<String, Object> data = new HashMap<>();
                data.put("from", from);
                data.put("to", to);
                ExtSdkWrapper.onReceive(ExtSdkMethodType.onConversationHasRead, data);
            }
        };
        EMClient.getInstance().chatManager().addConversationListener(this.conversationListener);
    }

    private EMConversation.EMSearchDirection searchDirectionFromString(String direction) {
        return direction.equals("up") ? EMConversation.EMSearchDirection.UP : EMConversation.EMSearchDirection.DOWN;
    }

    private EMMessageListener messageListener = null;
    private EMConversationListener conversationListener = null;
}
