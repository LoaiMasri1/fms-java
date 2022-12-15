package com.fuzzyDucks.fms;

import com.fuzzyDucks.fms.classification.FileService;
import com.fuzzyDucks.fms.classification.enums.SortType;
import org.bson.Document;

public class App {

    public static void main(String[] args) throws Exception {

//        for (Document s :FileService.getFileSortedBy("name",true)) {
//            System.out.println(s);
//        }
//        for (Document s :FileService.getFilesEqaulValue("name","moon")) {
//            System.out.println(s);
//        }
//        for (Document s :FileService.getFilesBySizeGte(7)) {
//            System.out.println(s);
//        }
//        for (Document s :FileService.getFilesBySizeLte(7)) {
//            System.out.println(s);
//        }
        for (Document s :FileService.getSortedBy("name", SortType.DESCENDING)) {
           System.out.println(s);
       }


    }

}
