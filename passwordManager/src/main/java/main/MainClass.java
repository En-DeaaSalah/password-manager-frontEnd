package main;


import api.Apis;
import customRequests.OrderAnswerRequest;
import customResponses.SharedOrderResponce;
import model.Record;
import model.User;
import storage.DataBaseOperation;

import java.util.List;
import java.util.Scanner;


public class MainClass {


    static void addItem() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter title");

        String title = scanner.nextLine();

        System.out.println("Enter email");

        String itemEmail = scanner.nextLine();

        System.out.println("Enter password");

        String itemPassword = scanner.nextLine();

        System.out.println("Enter description");

        String description = scanner.nextLine();


        Record record = new Record(null, title, itemEmail, itemPassword, description);


        System.out.println(Apis.addItem(record));


    }

    public static void registration() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Registration");

        System.out.println("Enter UserName");

        String userName = scanner.nextLine();

        System.out.println("Enter Email");

        String email = scanner.nextLine();

        System.out.println("Enter password");

        String password = scanner.nextLine();


        System.out.println(Apis.register(userName, email, password));

    }


    public static void getSpecificItem() {
        System.out.println("Enter item Id");
        Scanner scanner = new Scanner(System.in);

        Long itemId = scanner.nextLong();


        Apis.getSpecificItem(itemId);

    }

    public static void updateItem() {
        System.out.println("Enter item Id");

        Scanner scanner = new Scanner(System.in);
        Long itemId = scanner.nextLong();


        Apis.getSpecificItem(itemId);

        scanner = new Scanner(System.in);


        System.out.println("Enter title (new)");

        String title = scanner.nextLine();

        System.out.println("Enter email (new)");

        String itemEmail = scanner.nextLine();

        System.out.println("Enter password (new)");

        String itemPassword = scanner.nextLine();

        System.out.println("Enter description (new)");

        String description = scanner.nextLine();

        Record record = new Record(itemId, title, itemEmail, itemPassword, description);
        Apis.updateItem(record);

    }

    public static void removeItem() {
        System.err.println(Apis.getAllItem());
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter item Id");

        Long itemId = scanner.nextLong();

        Apis.removeItem(itemId);

    }


    public static void showItems() {


        System.err.println(Apis.getAllItem());

    }


    public static void userShowers() {


        String flag;
        do {
            System.out.println("Enter 1 to exit");
            System.out.println("Enter 2 to show Your Items");
            System.out.println("Enter 3 to add Item");
            System.out.println("Enter 4 to remove Item");
            System.out.println("Enter 5 to update Item");
            System.out.println("Enter 6 to logout");
            System.out.println("Enter 7 to show Specific Item");
            System.out.println("Enter 8 to share account with another user");
            System.out.println("Enter 9 to show Share items Order");
            Scanner s = new Scanner(System.in);

            flag = s.next();

            switch (flag) {

                case "1": {
                    break;
                }

                case "2": {
                    showItems();
                    break;

                }

                case "3": {
                    addItem();
                    break;
                }

                case "4": {
                    removeItem();
                    break;
                }

                case "5": {
                    updateItem();
                    break;
                }
                case "7": {

                    getSpecificItem();

                    break;
                }
                case "8": {
                    shareAccount();
                    break;
                }

                case "9": {
                    showOrders();
                    break;

                }

            }


        } while (!flag.equals("1"));


    }

    private static void showOrders() {

        Scanner scanner = new Scanner(System.in);

        List<SharedOrderResponce> list = Apis.getOrders(User.getUser().getId());


        if (list == null || list.size() == 0) {

            System.err.println("No Order to display");
            return;


        }

        System.out.println("Your Order : ");

        for (SharedOrderResponce sharedOrderResponce : list) {

            System.out.println(sharedOrderResponce);


        }

        System.err.println("Enter number of Order ((( enter 0 to exit )))");


        Long orderNumber = Long.valueOf(scanner.nextLine());


        if (orderNumber == 0) {

            return;
        }


        System.err.println("Enter (1) to accept or (2) to remove");

        String answer = scanner.nextLine();

        OrderAnswerRequest orderAnswerRequest = new OrderAnswerRequest(orderNumber, answer);

        Apis.orderProcess(orderAnswerRequest);


    }

    private static void shareAccount() {


        List<String> Names = Apis.getUsersName();


        if (Names == null || Names.size() == 0) {

            System.out.println("No User to shared ");
            return;

        }


        Scanner scanner = new Scanner(System.in);


        List<Record> records = Apis.getAllItem();


        if (records == null || records.size() == 0) {


            System.err.println("No items to shared ");
            return;

        }

        System.err.println(records);

        System.out.println("Enter (id)  of account");

        Long itemId = Long.valueOf(scanner.nextLine());

        for (int i = 0; i < Names.size(); i++) {

            System.out.println((i + 1) + " ) " + Names.get(i));

        }

        System.out.println("Enter name of user to shared");


        String userName = scanner.nextLine();

        String userKey = Apis.getUserKey(userName);


        System.out.println("Enter message (optionally)");


        String message = scanner.nextLine();


        Apis.shareItem(itemId, userName, message, userKey);


    }

    public static void main(String[] args) {


//        System.out.println(DataBaseOperation.dropUserTable());

        System.out.println(DataBaseOperation.createUserTable());


        Scanner scanner = new Scanner(System.in);
        System.out.println("You Have Account ? Y/N");
        String userAnswer = scanner.nextLine();

        if (userAnswer.equalsIgnoreCase("y")) {

            System.out.println("Enter Your Password");

            String password = scanner.nextLine();

            User user = DataBaseOperation.getUser(password);

            while (user == null || !user.getPassword().equals(password)) {

                System.err.println(user);
                System.err.println("wrong password try again");

                System.out.println("Enter Your Password");

                Scanner s = new Scanner(System.in);

                password = s.nextLine();

                user = DataBaseOperation.getUser(password);

            }

            userShowers();

        } else {


            registration();
            userShowers();

        }


    }


}
