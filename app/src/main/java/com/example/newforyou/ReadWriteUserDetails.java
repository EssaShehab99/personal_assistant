package com.example.newforyou;

public class ReadWriteUserDetails {
    public String Uid, userNameD, ageD, bloodTypeD, emailD, passWordD, userNameL, emailL, passWordL , userType ;
    public  int  userTypeInt ;

    //constructor For "Sign_Up_Deaf_Mute"
    public ReadWriteUserDetails(String uid, int userTypeInt, String nameDeafMute, String ageDeafMute, String bloodType, String emailDeafMute, String passWordDeafMute, String userType) {

        this.Uid = uid;
        this.userNameD = nameDeafMute;
        this.ageD = ageDeafMute;
        this.bloodTypeD = bloodType;
        this.emailD = emailDeafMute;
        this.passWordD = passWordDeafMute;
        this.userType = userType;
        this.userTypeInt = userTypeInt;


    }

    //constructor For "Sign_Up_Learner"
    public ReadWriteUserDetails(String uid, int userTypeInt, String nameLearner, String emailLearner, String passWordLearner, String userType) {
        this.Uid = uid;
        this.userTypeInt = userTypeInt;
        this.userNameL = nameLearner;
        this.emailL = emailLearner;
        this.passWordL = passWordLearner;
        this.userType = userType;

    }

    public ReadWriteUserDetails() {
    }


    // Constructor For updateProfile "DeafMute_Edit_Profile"
    public ReadWriteUserDetails(int i, String nameDM, String ageDM, String bloodtypeDM, String emailDM, String user_Type) {
        userTypeInt = i;
        this.userNameD = nameDM;
        this.ageD = ageDM;
        this.bloodTypeD = bloodtypeDM;
        this.emailD= emailDM;
        this.userType = user_Type;

    }

    // Constructor For updateProfile "Learner_Edit_Profile"
    public ReadWriteUserDetails(int i, String nameLe, String emailLe , String user_Type) {
        userTypeInt = i;
        this.userNameL = nameLe;
        this.emailL = emailLe;
        this.userType = user_Type;
    }

//    public ReadWriteUserDetails(int itemIdFav, String titleFav) {
//        this.itemIdFavCons = itemIdFav;
//        this.titleFavCons = titleFav ;
//    }
}