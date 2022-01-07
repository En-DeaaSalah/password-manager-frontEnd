package api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cryptography.asymmericCipers.RSA;
import customRequests.*;
import customResponses.AddItemResponse;
import customResponses.RegistrationResponse;
import customResponses.SharedOrderResponce;
import model.Record;
import model.User;
import okhttp3.*;
import okhttp3.Request;
import storage.DataBaseOperation;

import java.lang.reflect.Type;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Apis {


    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");


    public static String register(String userName, String UserEmail, String userPassword) {


        PublicKey serverKey = RSA.generatePublicKeyString(Apis.getServerKey());


        RegistrationRequest registrationRequest = new RegistrationRequest();

        KeyPair keyPair = RSA.generateKeys();

        String publicKey = RSA.encoder(keyPair.getPublic().getEncoded());

        String privateKey = RSA.encoder(keyPair.getPrivate().getEncoded());


        registrationRequest.setEmail(UserEmail);

        registrationRequest.setPassword(userPassword);

        registrationRequest.setUserName(userName);


        registrationRequest.setPublicKey(publicKey);

        registrationRequest = registrationRequest.encrypt(serverKey);


//        registrationRequest.signature(RSA.generatePrivateKeyFormString(privateKey));


        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, new Gson().toJson(registrationRequest));
            Request request = new Request.Builder()
                    .url(Urls.URLRegistration)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            RegistrationResponse object = new Gson().fromJson(response.body().string(), RegistrationResponse.class);


//            object = object.decrypt(RSA.generatePrivateKeyFormString(privateKey));

            User.getUser().setId(object.user.getId());
            User.getUser().setPrivateKey(privateKey);
            User.getUser().setPublicKey(publicKey);
            User.getUser().setPassword(object.user.getPassword());
            User.getUser().setEmail(object.user.getEmail());
            User.getUser().setName(object.user.getName());

            DataBaseOperation.saveUser(User.getUser());

            return object.message;


        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;

    }


    public static void login() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Urls.URLLogin)
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();
            System.err.println(response.body().string());

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    public static void logout() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Urls.URLLogout)
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();
            System.err.println(response.body().string());

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    public static String getUserFromServer(Long id) {


        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, new Gson().toJson(""));
            Request request = new Request.Builder()
                    .url(Urls.URLRegistration)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            RegistrationResponse object = new Gson().fromJson(response.body().string(), RegistrationResponse.class);
            System.err.println(object);


            User.getUser().setId(object.user.getId());
            User.getUser().setPassword(object.user.getPassword());
            User.getUser().setEmail(object.user.getEmail());
            User.getUser().setName(object.user.getName());

            DataBaseOperation.saveUser(User.getUser());

            return object.message;


        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;

    }


    public static String addItem(Record record) {

        AddItemRequest addItemRequest = new AddItemRequest(User.getUser().getId(), record.getTitle(), record.getEmail(), record.getPassword()
                , record.getDescription());

        PublicKey serverKey = RSA.generatePublicKeyString(Apis.getServerKey());

        addItemRequest = addItemRequest.encrypt(serverKey);

//        addItemRequest = addItemRequest.encrypt(RSA.generatePublicKeyString(User.getUser().getPublicKey()));


//        addItemRequest.signature(RSA.generatePrivateKeyFormString(User.getUser().getPrivateKey()));


        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, new Gson().toJson(addItemRequest));
            Request request = new Request.Builder()
                    .url(Urls.URLAddItem)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            AddItemResponse object = new Gson().fromJson(response.body().string(), AddItemResponse.class);
//            object = object.decrypt(RSA.generatePrivateKeyFormString(User.getUser().getPrivateKey()));

            Record item = new Record(object.id, object.title, object.email,
                    object.password, object.description);

            User.getUser().addRecord(item);

            System.err.println(object);


            return "add item is done";

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "error in add item";
    }


    public static void removeItem(Long itemId) {

        PublicKey serverKey = RSA.generatePublicKeyString(Apis.getServerKey());


        try {
            OkHttpClient client = new OkHttpClient();
            DeleteItemRequest deleteItemRequest = new DeleteItemRequest(User.getUser().getId(), itemId);


//            deleteItemRequest = deleteItemRequest.encrypt(serverKey);

            // deleteItemRequest = deleteItemRequest.signature(RSA.generatePrivateKeyFormString(User.getUser().getPrivateKey()));

            System.err.println(deleteItemRequest);
            RequestBody body = RequestBody.create(JSON, new Gson().toJson(deleteItemRequest));
            Request request = new Request.Builder()
                    .url(Urls.URLRemoveItem)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            System.err.println(response.body().string());

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    public static void updateItem(Record record) {

        PublicKey serverKey = RSA.generatePublicKeyString(Apis.getServerKey());

        UpdateItemRequest updateItemRequest = new UpdateItemRequest(record.getId(), User.getUser().getId(), record.getTitle(), record.getEmail(), record.getPassword(), record.getDescription());

        updateItemRequest = updateItemRequest.encrypt(serverKey);
//
//        updateItemRequest.signature(RSA.generatePrivateKeyFormString(User.getUser().getPrivateKey()));


        updateItemRequest = updateItemRequest.encrypt(RSA.generatePublicKeyString(User.getUser().getPublicKey()));


        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, new Gson().toJson(updateItemRequest));
            Request request = new Request.Builder()
                    .url(Urls.URLUpdateItem)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            System.err.println(response.body().string());

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    public static void shareItem(Long itemId, String userName, String message, String userKey) {

        PublicKey serverKey = RSA.generatePublicKeyString(Apis.getServerKey());

        SharedItemRequest sharedItemRequest = new SharedItemRequest();
        sharedItemRequest.setUserSharedName(userName);
        sharedItemRequest.setItemID(itemId);
        sharedItemRequest.setMessage(message);
        sharedItemRequest.setUserId(User.getUser().getId());
        sharedItemRequest = sharedItemRequest.encrypt(serverKey);

//        sharedItemRequest.signature(RSA.generatePrivateKeyFormString(User.getUser().getPrivateKey()));

        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, new Gson().toJson(sharedItemRequest));
            Request request = new Request.Builder()
                    .url(Urls.URLShareItem)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            System.err.println(response.body().string());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static ArrayList<Record> getAllItem() {

        PublicKey serverKey = RSA.generatePublicKeyString(Apis.getServerKey());

        GetAllItemRequest getAllItemRequest = new GetAllItemRequest(User.getUser().getId());

//        getAllItemRequest=getAllItemRequest.encrypt(serverKey);


//        getAllItemRequest = getAllItemRequest.signature(RSA.generatePrivateKeyFormString(User.getUser().getPrivateKey()));


        try {
            RequestBody body = RequestBody.create(JSON, new Gson().toJson(getAllItemRequest));

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Urls.URLGetAllItems)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            Type listType = new TypeToken<ArrayList<Record>>() {
            }.getType();
            ArrayList<Record> list = new Gson().fromJson(response.body().string(), listType);


            return list;
//
//            for (int i = 0; i < list.size(); i++) {
//
//                list.get(i).decrypt(RSA.generatePrivateKeyFormString(User.getUser().getPrivateKey()));
//
//            }
//            return list;


        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }


    public static Record getSpecificItem(Long itemId) {

        PublicKey serverKey = RSA.generatePublicKeyString(Apis.getServerKey());


        try {
            OkHttpClient client = new OkHttpClient();
            GetItemRequest getItemRequest = new GetItemRequest(User.getUser().getId(), itemId);

            getItemRequest=getItemRequest.encrypt(serverKey);

            RequestBody body = RequestBody.create(JSON, new Gson().toJson(getItemRequest));
            Request request = new Request.Builder()
                    .url(Urls.URLGetItem)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            System.err.println(response.body().string());

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;

    }


    public static List<String> getUsersName() {

//        PublicKey serverKey = RSA.generatePublicKeyString(Apis.getServerKey());


        try {
            OkHttpClient client = new OkHttpClient();
            GetUsersName getUsersName = new GetUsersName(User.getUser().getId());

//            getItemRequest=getItemRequest.encrypt(serverKey);

            RequestBody body = RequestBody.create(JSON, new Gson().toJson(getUsersName));
            Request request = new Request.Builder()
                    .url(Urls.URLGetAllUserNames)
                    .post(body)
                    .build();


            Response response = client.newCall(request).execute();
            Type listType = new TypeToken<ArrayList<String>>() {
            }.getType();
            ArrayList<String> list = new Gson().fromJson(response.body().string(), listType);
            return list;


        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;

    }


    public static String getServerKey() {


        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Urls.URLGetServerKey)
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {

            e.printStackTrace();
        }


        return null;

    }


    public static String sendClientKey(String key) {

        return null;

    }


    public static String getUserKey(String userName) {

//        PublicKey serverKey = RSA.generatePublicKeyString(Apis.getServerKey());


        try {
            OkHttpClient client = new OkHttpClient();
            GetUserKeyRequest getUserKeyRequest = new GetUserKeyRequest(User.getUser().getId(), userName);


            System.err.println(getUserKeyRequest);
//            getUserKeyRequest = getUserKeyRequest.encrypt(serverKey);
            System.err.println(getUserKeyRequest);
            RequestBody body = RequestBody.create(JSON, new Gson().toJson(getUserKeyRequest));
            Request request = new Request.Builder()
                    .url(Urls.URLGetUserKey)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            System.err.println(response.body().string());

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;


    }


    public static List<SharedOrderResponce> getOrders(Long id) {

//        PublicKey serverKey = RSA.generatePublicKeyString(Apis.getServerKey());


        try {
            OkHttpClient client = new OkHttpClient();
            GetOrdersRequest getOrdersRequest = new GetOrdersRequest(id);


            RequestBody body = RequestBody.create(JSON, new Gson().toJson(getOrdersRequest));
            Request request = new Request.Builder()
                    .url(Urls.URLGetSharedOrder)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            Type listType = new TypeToken<ArrayList<SharedOrderResponce>>() {
            }.getType();
            ArrayList<SharedOrderResponce> list = new Gson().fromJson(response.body().string(), listType);
            return list;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public static void orderProcess(OrderAnswerRequest orderAnswerRequest) {

        PublicKey serverKey = RSA.generatePublicKeyString(Apis.getServerKey());


        try {
            OkHttpClient client = new OkHttpClient();

            orderAnswerRequest = orderAnswerRequest.encrypt(serverKey);

//            orderAnswerRequest.signature(RSA.generatePrivateKeyFormString(User.getUser().getPrivateKey()));


            RequestBody body = RequestBody.create(JSON, new Gson().toJson(orderAnswerRequest));
            Request request = new Request.Builder()
                    .url(Urls.URLOrderProcess)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            System.err.println(response.body().string());


        } catch (Exception e) {

            e.printStackTrace();
        }


    }
}
