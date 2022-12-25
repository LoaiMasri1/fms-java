package com.fuzzyDucks.fms.UserMenu.FileMenu;

import com.fuzzyDucks.fms.File.fileSchema.models.FileSchema;
import com.fuzzyDucks.fms.UserMenu.Operation;

import java.io.File;
import java.io.IOException;

public class Import implements Operation {
    @Override
    public void execute(String info) {
        try {
            new FileSchema(new File(info));

        } catch (NullPointerException | IOException | ClassNotFoundException e) {
            e.printStackTrace();

        }
    }
}
