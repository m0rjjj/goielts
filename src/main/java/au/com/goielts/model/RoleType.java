package au.com.goielts.model;

public enum RoleType {
    ADMIN("ADMIN"),
    STUDENT("STUDENT"),
    TEACHER("TEACHER");
     
    String userProfileType;
     
    private RoleType(String userProfileType){
        this.userProfileType = userProfileType;
    }
     
    public String getUserRole(){
        return userProfileType;
    }
     
}