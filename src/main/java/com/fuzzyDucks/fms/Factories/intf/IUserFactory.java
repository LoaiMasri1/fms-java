package com.fuzzyDucks.fms.Factories.intf;

import java.io.IOException;

public interface IUserFactory extends IFactory {
    void doAction(String action)throws IOException, ClassNotFoundException;
    void doAction(String action,String userName,String password)throws IOException, ClassNotFoundException;
}

