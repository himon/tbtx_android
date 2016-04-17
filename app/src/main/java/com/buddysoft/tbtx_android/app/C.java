package com.buddysoft.tbtx_android.app;

/**
 * Created by lc on 16/4/10.
 */
public class C {

    public static class LimitType {
        public static final String LATEST = "latest";
        public static final String ALL = "all";
    }

    public static class IntentKey {
        public static final String MESSAGE_EXTRA_KEY = "message_extra_key";
        public static final String MESSAGE_EXTRA_KEY2 = "message_extra_key2";
        public static final String MESSAGE_EXTRA_KEY3 = "message_extra_key3";
    }

    public static class AlbumType{
        public static final String CLASS = "class";
        public static final String KINDERGARTEN = "kindergarten";
    }

    public static class Role{
        public static final int RoleFirstParents = 0;   // 家长主账号
        public static final int RoleOtherParents = 1;   // 家长子账号
        public static final int RoleFirstTeacher = 2;   // 班主任老师
        public static final int RoleSecondTeacher = 3;  // 副班主任老师
        public static final int RoleNurse = 4;          // 班级保育员
        public static final int RoleAdministrationTeacher = 5;  // 行政老师
        public static final int RoleKindergartenLeader = 6; // 园长
        public static final int RoleOtherTeacher = 7;    // 其他老师
    }
}
