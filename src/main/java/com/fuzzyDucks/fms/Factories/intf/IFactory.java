package com.fuzzyDucks.fms.Factories.intf;

import java.io.IOException;

public interface IFactory {
    void doAction(String action, Object object) throws IOException, ClassNotFoundException;
}
