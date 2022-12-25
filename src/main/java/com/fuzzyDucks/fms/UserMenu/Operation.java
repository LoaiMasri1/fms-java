package com.fuzzyDucks.fms.UserMenu;

import java.io.IOException;

public interface Operation {
    void execute(String info) throws IOException, ClassNotFoundException;
}
