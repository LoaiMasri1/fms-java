package com.fuzzyDucks.fms.Factories.intf;

import java.io.IOException;

public interface IFileFactory extends IFactory{
    public void doAction(String action, String name, String type) throws IOException, ClassNotFoundException ;

}
