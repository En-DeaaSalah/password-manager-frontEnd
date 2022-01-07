package storage;

public class Queries {






    public static final String CREATE_SEQUENCE ="CREATE SEQUENCE IF NOT EXISTS public.userSeq INCREMENT 1 START 1 MINVALUE 0 MAXVALUE 100000 CACHE 1 OWNED BY public.user.id;";

    public static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS public.user;";


    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS  public.user\n" +
            "(\n" +
            "    id bigint NOT NULL,\n" +
            "     userId bigint,\n" +
            "    publicKey text,\n" +
            "    privateKey text,\n" +
            "    serverKey text,\n" +
            "    password text,\n" +
            "    PRIMARY KEY (id)\n" +

            ");";

    public static final String SELECT_USER ="select * from public.user;";


    public static String InsertUserQuery(Long id ,String privateKey,String publicKey,String serverKey,String password){


        return "insert into public.user(id,userid, publickey, privatekey, serverkey,password)values("+1L+","+id+","+publicKey+","+privateKey+","+serverKey+","+password+");";

    }



}
