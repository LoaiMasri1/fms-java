package com.fuzzyDucks.fms.UserMenu.FileMenu;

import com.fuzzyDucks.fms.File.FileService;
import com.fuzzyDucks.fms.UserMenu.Operation;

import java.io.IOException;

public class Export implements Operation {
    @Override
    public void execute(String info) throws IOException, ClassNotFoundException {
        String name = info.split(" ")[0];
        String type = info.split(" ")[1];
        FileService.exportFile(name,type);

    }
}
