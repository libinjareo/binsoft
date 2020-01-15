package com.binsoft.myioc;

public class Foo {

    private String name = "";

    public Foo(){

    }

    public Foo(String name){
        this.name = name;
    }

    public void hello(){
        System.out.println("Hello World!");
        System.out.println("My name is " + name);
    }
}
