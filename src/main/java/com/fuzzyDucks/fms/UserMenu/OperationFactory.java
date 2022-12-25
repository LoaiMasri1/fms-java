package com.fuzzyDucks.fms.UserMenu;

import com.fuzzyDucks.fms.UserMenu.Authentication.AddUser;
import com.fuzzyDucks.fms.UserMenu.Authentication.Login;
import com.fuzzyDucks.fms.UserMenu.Authentication.Logout;
import com.fuzzyDucks.fms.UserMenu.FileMenu.Export;
import com.fuzzyDucks.fms.UserMenu.FileMenu.Import;
import com.fuzzyDucks.fms.UserMenu.Operation;

public class OperationFactory {
        public Operation createOperation(String operation)
        {
            if (operation == null || operation.isEmpty())
                return null;
            switch (operation) {
                case "import":
                    return new Import();
                case "export":
                    return new Export();
                case "login":
                    return new Login();
                case "logout":
                    return new Logout();
                case"adduser":
                    return new AddUser();
                default:
                    throw new IllegalArgumentException("Unknown Operation "+operation);
            }
        }
    }

