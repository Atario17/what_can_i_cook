package com.kondrat.wcic.component;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyComponentTest {
    @Autowired
    MyComponent myComponent;
    @Test
    public void constructor() throws Exception {
        assertNotNull(myComponent);
    }
}
