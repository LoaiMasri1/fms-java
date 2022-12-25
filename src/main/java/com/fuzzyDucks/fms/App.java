package com.fuzzyDucks.fms;

import com.fuzzyDucks.fms.UserMenu.Authentication.Logout;
import com.fuzzyDucks.fms.UserMenu.Operation;
import com.fuzzyDucks.fms.UserMenu.OperationFactory;

import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner s1 = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        Scanner s3 = new Scanner(System.in);
        Scanner s4 = new Scanner(System.in);
        Scanner s5 = new Scanner(System.in);
        Scanner s6 = new Scanner(System.in);


        int option = 0;
        String info = "";
        do {
            System.out.println("Menu:");
            System.out.println("1. Login");
            System.out.println("2. Logout");
            System.out.println("3. AddUser");
            System.out.println("4. Import file");
            System.out.println("5. Export file");
            System.out.println("6. Quit");
            System.out.print("Enter an option: ");

            option = s1.nextInt();

            if (option < 1 || option > 6) {
                System.out.println("Invalid option. Please try again.");
            }


            switch (option) {
                case 1:
                    System.out.println("Enter username and password");
                    info = s2.nextLine();
                    OperationFactory login = new OperationFactory();
                    Operation operation = login.createOperation("login");
                    operation.execute(info);
                    break;
                case 2:
                    System.out.println("Enter any char to logout");
                    info = s3.nextLine();
                    OperationFactory logout = new OperationFactory();
                    operation = logout.createOperation("logout");
                    operation.execute(info);
                    break;
                case 3:
                    System.out.println("Enter username password email and name");
                    info = s4.nextLine();
                    OperationFactory adduser = new OperationFactory();
                    operation = adduser.createOperation("adduser");
                    operation.execute(info);
                    break;
                case 4:
                    System.out.println("Enter file path");
                    info = s5.nextLine();
                    OperationFactory importFile = new OperationFactory();
                    operation = importFile.createOperation("import");
                    operation.execute(info);
                    break;
                case 5:
                    System.out.println("Enter file name and type");
                    info = s6.nextLine();
                    OperationFactory exportFile = new OperationFactory();
                    operation = exportFile.createOperation("export");
                    operation.execute(info);
                    break;
                case 6:
                    System.out.println("GoodBye");
                    break;
            }


        }while (option != 6) ;
    }}
