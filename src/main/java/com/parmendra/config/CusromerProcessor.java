package com.parmendra.config;


import com.parmendra.entity.Customers;
import org.springframework.batch.item.ItemProcessor;

public class CusromerProcessor implements ItemProcessor<Customers , Customers> {


    @Override
    public Customers process(Customers customers) throws Exception {
        return customers;
    }
}
