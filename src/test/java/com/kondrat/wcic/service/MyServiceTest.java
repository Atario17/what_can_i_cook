package com.kondrat.wcic.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyServiceTest {
    @Autowired
    MyService1 myService1;
    @Autowired
    MyService1 myService2;

    @Test
    public void constructor() throws Exception {
        assertNotNull(myService1);
        assertNotNull(myService2);
        assertTrue(myService1.myComponent == myService2.myComponent);
    }
    int a = 2;
    public int calculate(int a){
        a = a * a;
        return a;
    }

    Integer b = 2;
    public Integer calc(Integer bl) {
//        b = b * b;
        b = new Integer(b.intValue() * b.intValue());
        return b;
    }

    AtomicInteger c = new AtomicInteger(2);

    public AtomicInteger calc1(AtomicInteger ca) {
        c.addAndGet(c.get());
        return c;
    }

    @Test
    public void calculate(){
        int bc = 3;
        assertEquals(9,calculate(bc));
        assertEquals(2,a);

        assertEquals(new Integer(4) ,calc(b));
        assertEquals(new Integer(4),b);

        assertEquals(4 ,calc1(c).get());
        assertEquals(4,c.get());

    }
}
