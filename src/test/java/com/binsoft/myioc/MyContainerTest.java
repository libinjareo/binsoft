package com.binsoft.myioc;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 

/** 
* MyContainer Tester. 
* 
* @author <Authors name> 
* @since <pre>7, 2019</pre>
* @version 1.0 
*/ 
public class MyContainerTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getBean(final String name) 
* 
*/ 
@Test
public void testGetBean() throws Exception { 

    MyContainer container = new MyContainer();
    container.registerMyBean(Foo.class,"foo");
    Foo foo = container.getBean("foo");
    Foo foo1 = container.getBean("foo");
    System.out.println("foo instance:"+foo);
    System.out.println("foo1 instance:"+foo1);
    foo.hello();
    foo1.hello();
}

} 
