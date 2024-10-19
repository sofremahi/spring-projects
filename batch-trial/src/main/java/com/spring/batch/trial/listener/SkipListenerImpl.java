package com.spring.batch.trial.listener;

import com.spring.batch.trial.domain.OsProduct;
import com.spring.batch.trial.domain.Product;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

@Component
public class SkipListenerImpl implements SkipListener<Product, OsProduct> {
    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println(((FlatFileParseException) t).getInput());
    }

    @Override
    public void onSkipInProcess(Product item, Throwable t) {
        System.out.println(item.toString());
    }

    @Override
    public void onSkipInWrite(OsProduct item, Throwable t) {
        System.out.println(item.toString());
    }
}
