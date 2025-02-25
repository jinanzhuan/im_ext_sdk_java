package com.easemob.ext_sdk.common;

public class ExtSdkMethodType {
    /// EMClient methods
    public static final String init = "init";
    public static final String createAccount = "createAccount";
    public static final String login = "login";
    public static final String logout = "logout";
    public static final String changeAppKey = "changeAppKey";
    public static final String isLoggedInBefore = "isLoggedInBefore";
    public static final String updateCurrentUserNick = "updateCurrentUserNick";
    public static final String uploadLog = "uploadLog";
    public static final String compressLogs = "compressLogs";
    public static final String kickDevice = "kickDevice";
    public static final String kickAllDevices = "kickAllDevices";
    public static final String getLoggedInDevicesFromServer = "getLoggedInDevicesFromServer";
    public static final String getCurrentUser = "getCurrentUser";
    public static final String getToken = "getToken";
    public static final String loginWithAgoraToken = "loginWithAgoraToken";
    public static final String isConnected = "isConnected";
    public static final String renewToken = "renewToken";

    public static final String onConnected = "onConnected";
    public static final String onDisconnected = "onDisconnected";
    public static final String onMultiDeviceEvent = "onMultiDeviceEvent";
    public static final String onSendDataToFlutter = "onSendDataToFlutter";
    public static final String onTokenWillExpire = "onTokenWillExpire";
    public static final String onTokenDidExpire = "onTokenDidExpire";

    public static final String onUserDidLoginFromOtherDevice = "onUserDidLoginFromOtherDevice";
    public static final String onUserDidRemoveFromServer = "onUserDidRemoveFromServer";
    public static final String onUserDidForbidByServer = "onUserDidForbidByServer";
    public static final String onUserDidChangePassword = "onUserDidChangePassword";
    public static final String onUserDidLoginTooManyDevice = "onUserDidLoginTooManyDevice";
    public static final String onUserKickedByOtherDevice = "onUserKickedByOtherDevice";
    public static final String onUserAuthenticationFailed = "onUserAuthenticationFailed";

    /// EMContactManager methods
    public static final String addContact = "addContact";
    public static final String deleteContact = "deleteContact";
    public static final String getAllContactsFromServer = "getAllContactsFromServer";
    public static final String getAllContactsFromDB = "getAllContactsFromDB";
    public static final String addUserToBlockList = "addUserToBlockList";
    public static final String removeUserFromBlockList = "removeUserFromBlockList";
    public static final String getBlockListFromServer = "getBlockListFromServer";
    public static final String getBlockListFromDB = "getBlockListFromDB";
    public static final String acceptInvitation = "acceptInvitation";
    public static final String declineInvitation = "declineInvitation";
    public static final String getSelfIdsOnOtherPlatform = "getSelfIdsOnOtherPlatform";


    public static final String onContactChanged = "onContactChanged";

    /// EMChatManager methods
    public static final String sendMessage = "sendMessage";
    public static final String resendMessage = "resendMessage";
    public static final String ackMessageRead = "ackMessageRead";
    public static final String ackGroupMessageRead = "ackGroupMessageRead";
    public static final String ackConversationRead = "ackConversationRead";
    public static final String recallMessage = "recallMessage";
    public static final String getConversation = "getConversation";
    public static final String markAllChatMsgAsRead = "markAllChatMsgAsRead";
    public static final String getUnreadMessageCount = "getUnreadMessageCount";
    public static final String updateChatMessage = "updateChatMessage";
    public static final String downloadAttachment = "downloadAttachment";
    public static final String downloadThumbnail = "downloadThumbnail";
    public static final String importMessages = "importMessages";
    public static final String loadAllConversations = "loadAllConversations";
    public static final String getConversationsFromServer = "getConversationsFromServer";
    public static final String deleteConversation = "deleteConversation";
    public static final String fetchHistoryMessages = "fetchHistoryMessages";
    public static final String searchChatMsgFromDB = "searchChatMsgFromDB";
    public static final String getMessage = "getMessage";
    public static final String asyncFetchGroupAcks = "asyncFetchGroupAcks";
    public static final String deleteRemoteConversation = "deleteRemoteConversation";

    public static final String translateMessage = "translateMessage";
    public static final String fetchSupportedLanguages = "fetchSupportLanguages";

    /// EMChatManager listener
    public static final String onMessagesReceived = "onMessagesReceived";
    public static final String onCmdMessagesReceived = "onCmdMessagesReceived";
    public static final String onMessagesRead = "onMessagesRead";
    public static final String onGroupMessageRead = "onGroupMessageRead";
    public static final String onMessagesDelivered = "onMessagesDelivered";
    public static final String onMessagesRecalled = "onMessagesRecalled";

    public static final String onConversationUpdate = "onConversationUpdate";
    public static final String onConversationHasRead = "onConversationHasRead";

    /// EMMessage listener
    public static final String onMessageProgressUpdate = "onMessageProgressUpdate";
    public static final String onMessageError = "onMessageError";
    public static final String onMessageSuccess = "onMessageSuccess";
    public static final String onMessageReadAck = "onMessageReadAck";
    public static final String onMessageDeliveryAck = "onMessageDeliveryAck";
    public static final String onMessageStatusChanged = "onMessageStatusChanged"; // deprecated 2022.05.04

    /// EMConversation
    public static final String getUnreadMsgCount = "getUnreadMsgCount";
    public static final String markAllMessagesAsRead = "markAllMessagesAsRead";
    public static final String markMessageAsRead = "markMessageAsRead";
    public static final String syncConversationExt = "syncConversationExt";
    public static final String syncConversationName = "syncConversationName"; // deprecated 2022.05.04
    public static final String removeMessage = "removeMessage";
    public static final String getLatestMessage = "getLatestMessage";
    public static final String getLatestMessageFromOthers = "getLatestMessageFromOthers";
    public static final String clearAllMessages = "clearAllMessages";
    public static final String insertMessage = "insertMessage";
    public static final String appendMessage = "appendMessage";
    public static final String updateConversationMessage = "updateConversationMessage";

    public static final String loadMsgWithId = "loadMsgWithId";
    public static final String loadMsgWithStartId = "loadMsgWithStartId";
    public static final String loadMsgWithKeywords = "loadMsgWithKeywords";
    public static final String loadMsgWithMsgType = "loadMsgWithMsgType";
    public static final String loadMsgWithTime = "loadMsgWithTime";

    // EMChatRoomManager
    public static final String joinChatRoom = "joinChatRoom";
    public static final String leaveChatRoom = "leaveChatRoom";
    public static final String fetchPublicChatRoomsFromServer = "fetchPublicChatRoomsFromServer";
    public static final String fetchChatRoomInfoFromServer = "fetchChatRoomInfoFromServer";
    public static final String getChatRoom = "getChatRoom";
    public static final String getAllChatRooms = "getAllChatRooms";
    public static final String createChatRoom = "createChatRoom";
    public static final String destroyChatRoom = "destroyChatRoom";
    public static final String changeChatRoomSubject = "changeChatRoomSubject";
    public static final String changeChatRoomDescription = "changeChatRoomDescription";
    public static final String fetchChatRoomMembers = "fetchChatRoomMembers";
    public static final String muteChatRoomMembers = "muteChatRoomMembers";
    public static final String unMuteChatRoomMembers = "unMuteChatRoomMembers";
    public static final String changeChatRoomOwner = "changeChatRoomOwner";
    public static final String addChatRoomAdmin = "addChatRoomAdmin";
    public static final String removeChatRoomAdmin = "removeChatRoomAdmin";
    public static final String fetchChatRoomMuteList = "fetchChatRoomMuteList";
    public static final String removeChatRoomMembers = "removeChatRoomMembers";
    public static final String blockChatRoomMembers = "blockChatRoomMembers";
    public static final String unBlockChatRoomMembers = "unBlockChatRoomMembers";
    public static final String fetchChatRoomBlockList = "fetchChatRoomBlockList";
    public static final String updateChatRoomAnnouncement = "updateChatRoomAnnouncement";
    public static final String fetchChatRoomAnnouncement = "fetchChatRoomAnnouncement";

    public static final String addMembersToChatRoomWhiteList = "addMembersToChatRoomWhiteList";
    public static final String removeMembersFromChatRoomWhiteList = "removeMembersFromChatRoomWhiteList";
    public static final String fetchChatRoomWhiteListFromServer = "fetchChatRoomWhiteListFromServer";
    public static final String isMemberInChatRoomWhiteListFromServer = "isMemberInChatRoomWhiteListFromServer";

    public static final String muteAllChatRoomMembers = "muteAllChatRoomMembers";
    public static final String unMuteAllChatRoomMembers = "unMuteAllChatRoomMembers";


    // EMChatRoomManagerListener
    public static final String chatRoomChange = "onChatRoomChanged";

    /// EMGroupManager
    public static final String getGroupWithId = "getGroupWithId";
    public static final String getJoinedGroups = "getJoinedGroups";
    public static final String getGroupsWithoutPushNotification = "getGroupsWithoutPushNotification";
    public static final String getJoinedGroupsFromServer = "getJoinedGroupsFromServer";
    public static final String getPublicGroupsFromServer = "getPublicGroupsFromServer";
    public static final String createGroup = "createGroup";
    public static final String getGroupSpecificationFromServer = "getGroupSpecificationFromServer";
    public static final String getGroupMemberListFromServer = "getGroupMemberListFromServer";
    public static final String getGroupBlockListFromServer = "getGroupBlockListFromServer";
    public static final String getGroupMuteListFromServer = "getGroupMuteListFromServer";
    public static final String getGroupWhiteListFromServer = "getGroupWhiteListFromServer";
    public static final String isMemberInWhiteListFromServer = "isMemberInWhiteListFromServer";
    public static final String getGroupFileListFromServer = "getGroupFileListFromServer";
    public static final String getGroupAnnouncementFromServer = "getGroupAnnouncementFromServer";
    public static final String addMembers = "addMembers";
    public static final String inviterUser = "inviterUser";
    public static final String removeMembers = "removeMembers";
    public static final String blockMembers = "blockMembers";
    public static final String unblockMembers = "unblockMembers";
    public static final String updateGroupSubject = "updateGroupSubject";
    public static final String updateDescription = "updateDescription";
    public static final String leaveGroup = "leaveGroup";
    public static final String destroyGroup = "destroyGroup";
    public static final String blockGroup = "blockGroup";
    public static final String unblockGroup = "unblockGroup";
    public static final String updateGroupOwner = "updateGroupOwner";
    public static final String addAdmin = "addAdmin";
    public static final String removeAdmin = "removeAdmin";
    public static final String muteMembers = "muteMembers";
    public static final String unMuteMembers = "unMuteMembers";
    public static final String muteAllMembers = "muteAllMembers";
    public static final String unMuteAllMembers = "unMuteAllMembers";
    public static final String addWhiteList = "addWhiteList";
    public static final String removeWhiteList = "removeWhiteList";
    public static final String uploadGroupSharedFile = "uploadGroupSharedFile";
    public static final String downloadGroupSharedFile = "downloadGroupSharedFile";
    public static final String removeGroupSharedFile = "removeGroupSharedFile";
    public static final String updateGroupAnnouncement = "updateGroupAnnouncement";
    public static final String updateGroupExt = "updateGroupExt";
    public static final String joinPublicGroup = "joinPublicGroup";
    public static final String requestToJoinPublicGroup = "requestToJoinPublicGroup";
    public static final String acceptJoinApplication = "acceptJoinApplication";
    public static final String declineJoinApplication = "declineJoinApplication";
    public static final String acceptInvitationFromGroup = "acceptInvitationFromGroup";
    public static final String declineInvitationFromGroup = "declineInvitationFromGroup";
    public static final String ignoreGroupPush = "ignoreGroupPush";

    /// EMGroupManagerListener
    public static final String onGroupChanged = "onGroupChanged";

    /// EMPushManager
    public static final String getImPushConfig = "getImPushConfig";
    public static final String getImPushConfigFromServer = "getImPushConfigFromServer";
    public static final String updatePushNickname = "updatePushNickname";
    public static final String updateHMSPushToken = "updateHMSPushToken";
    public static final String updateFCMPushToken = "updateFCMPushToken";
    public static final String enableOfflinePush = "enableOfflinePush";
    public static final String disableOfflinePush = "disableOfflinePush";
    public static final String getNoPushGroups = "getNoPushGroups";
    public static final String setNoDisturbUsers = "setNoDisturbUsers"; // deprecated 2022.05.04
    public static final String getNoDisturbUsersFromServer = "getNoDisturbUsersFromServer"; // deprecated 2022.05.04

    /// ImPushConfig
    public static final String imPushNoDisturb = "imPushNoDisturb"; // deprecated 2022.05.04
    public static final String updateImPushStyle = "updateImPushStyle";
    public static final String updateGroupPushService = "updateGroupPushService";
    public static final String getNoDisturbGroups = "getNoDisturbGroups"; // deprecated 2022.05.04
    public static final String updateUserPushService = "updateUserPushService";
    public static final String getNoPushUsers = "getNoPushUsers";

    /// EMUserInfoManager
    public static final String updateOwnUserInfo = "updateOwnUserInfo";
    public static final String updateOwnUserInfoWithType = "updateOwnUserInfoWithType";
    public static final String fetchUserInfoById = "fetchUserInfoById";
    public static final String fetchUserInfoByIdWithType = "fetchUserInfoByIdWithType";

    /// EMPresenceManager methods
    public static final String presenceWithDescription = "publishPresenceWithDescription";
    public static final String presenceSubscribe = "presenceSubscribe";
    public static final String presenceUnsubscribe = "presenceUnsubscribe";
    public static final String fetchSubscribedMembersWithPageNum = "fetchSubscribedMembersWithPageNum";
    public static final String fetchPresenceStatus = "fetchPresenceStatus";

    /// EMPresenceManagerListener
    public static final String onPresenceStatusChanged = "onPresenceStatusChanged";
}
