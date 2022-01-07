package storage;

import api.Apis;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBaseOperation {


    public static boolean dropUserTable() {


        try {
            Connection connection = JdbcConnection.getConnection();
            Statement sql = connection.createStatement();

            boolean result = sql.execute(Queries.DROP_USER_TABLE);

            sql.close();


            return result;


        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }


    }


    public static boolean createUserTable() {

        try {


            Connection connection = JdbcConnection.getConnection();
            Statement sql = connection.createStatement();
            boolean result = sql.execute(Queries.CREATE_TABLE);
            sql.execute(Queries.CREATE_SEQUENCE);
            sql.close();


            return result;


        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }


    }


    public static boolean saveUser(User user) {

        try {

            String serverKey = Apis.getServerKey();

            Connection connection = JdbcConnection.getConnection();
            Statement sql = connection.createStatement();
//            sql.execute(Queries.InsertUserQuery(user.getId(), user.getPrivateKey(), user.getPublicKey(), serverKey));

            PreparedStatement st = connection.prepareStatement("INSERT INTO public.user(id, userid, publickey, privatekey, serverkey,password) VALUES (nextval('userSeq'), ?, ?, ?, ?, ?)");
            st.setLong(1, user.getId());
            st.setString(2, user.getPublicKey());
            st.setString(3, user.getPrivateKey());
            st.setString(4, serverKey);
            st.setString(5, user.getPassword());
            st.executeUpdate();
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }

        return true;


    }


    public static User getUser(String password) {

        try {


            Connection connection = JdbcConnection.getConnection();
            Statement sql = connection.createStatement();
            ResultSet rs = sql.executeQuery("select * from public.user where public.user.password=" + '\'' + password + '\'' + ";");


            if (rs.next()) {
                User.getUser().setId(Long.valueOf(rs.getString("userid")));
                User.getUser().setPublicKey(rs.getString("publickey"));
                User.getUser().setPrivateKey(rs.getString("privatekey"));
                User.getUser().setPassword(rs.getString("password"));
                return User.getUser();

            }


        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;


    }

    public static User getUser() {

        try {


            Connection connection = JdbcConnection.getConnection();
            Statement sql = connection.createStatement();
            ResultSet rs = sql.executeQuery(Queries.SELECT_USER);


            if (rs.next()) {
                User.getUser().setId(Long.valueOf(rs.getString("userid")));
                User.getUser().setPublicKey(rs.getString("publickey"));
                User.getUser().setPrivateKey(rs.getString("privatekey"));
            }


            return User.getUser();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;


    }

}
