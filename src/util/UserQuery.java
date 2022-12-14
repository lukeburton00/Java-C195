package util;

public abstract class UserQuery {

    public static int insert(String userName, String password)
    {
        String sql = "INSERT INTO users (User_Name. Password) VALUES(?,?)";
        return 0;
    }
}
