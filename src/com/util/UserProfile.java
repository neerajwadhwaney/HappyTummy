package com.util;

public class UserProfile {

    private static String userName;
    private static String userId;
    private static String emailId;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        UserProfile.userName = userName;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        UserProfile.userId = userId;
    }

    public static String getEmailId() {
        return emailId;
    }

    public static void setEmailId(String emailId) {
        UserProfile.emailId = emailId;
    }

    @Override
    public String toString() {
        return "UserProfile{" + userName + "," + userId + "," + emailId + "," + "}";
    }

}
