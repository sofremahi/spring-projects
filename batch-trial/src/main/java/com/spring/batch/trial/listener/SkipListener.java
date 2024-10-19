package com.spring.batch.trial.listener;

import com.spring.batch.trial.domain.OsProduct;
import com.spring.batch.trial.domain.Product;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

@Component
public class SkipListener {

    @OnSkipInRead
    public void onSkipInRead(Throwable t) throws IOException {
        if (t instanceof FlatFileParseException) {
            System.out.println(((FlatFileParseException) t).getInput());
            createFile("D:\\projects\\batch-trial\\src\\main\\resources\\data\\skipOnRead.txt", ((FlatFileParseException) t).getInput());
        }
    }

    @OnSkipInProcess
    public void onSkipInProcess(Product product, Throwable t) throws IOException {
        if (t instanceof FlatFileParseException) {
            System.out.println(product.toString());
            createFile("D:\\projects\\batch-trial\\src\\main\\resources\\data\\skipOnProcess.txt", product.toString());
        }
    }

    @OnSkipInWrite
    public void onSkipInWrite(OsProduct osProduct, Throwable t) throws IOException {
        if (t instanceof FlatFileParseException) {
            System.out.println(osProduct.toString());
            createFile("D:\\projects\\batch-trial\\src\\main\\resources\\data\\skipOnWrite.txt", osProduct.toString());

        }
    }

    public void createFile(String filePath, String data) throws IOException {

        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.write(data + "," + new Date() + "\n");

        }
    }
}
