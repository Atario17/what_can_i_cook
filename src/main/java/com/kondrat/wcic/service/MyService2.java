package com.kondrat.wcic.service;

import com.kondrat.wcic.component.MyComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService2 {
    @Autowired
    MyComponent myComponent;
}
