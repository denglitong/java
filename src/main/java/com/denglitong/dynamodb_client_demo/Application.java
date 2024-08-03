package com.denglitong.dynamodb_client_demo;


import com.denglitong.dynamodb_client_demo.processor.MoviesCreateTable;
import com.denglitong.dynamodb_client_demo.processor.MoviesDataLoad;
import com.denglitong.dynamodb_client_demo.processor.MoviesItemCRUD;

public class Application {

    public static void main(String[] args) throws Exception {
        // one-time proceed
        MoviesCreateTable.main(args);

        MoviesDataLoad.main(args);

        MoviesItemCRUD.main(args);
    }
}
