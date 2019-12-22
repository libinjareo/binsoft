package com.binsoft.myioc;

import com.binsoft.myioc.resolver.CtorResolver;

/**
 * My IOC container
 */
public class MyContainer extends MyBeans {

    /**
     * My container reference name.
     * 用于 MyContainer自身被注册为它自己容器中的Bean
     */
    public static final String MY_CONTAINER_REF_NAME = "myContainer";

    /**
     * 返回 bean实例
     *
     * @param name
     * @param <T>
     * @return
     */
    public <T> T getBean(final String name) {

        //查找注册的Bean定义
        BeanDefinition def = lookupBeanDefinition(name);

        //先不判断def为null的情况
        //......


        //初始化BeanDefinition(包含构造函数描述器等其他信息)
        initBeanDefinition(def);
        //构建BeanData
        final BeanData beanData = new BeanData(this, def);
        //取得Bean
        Object bean = beanData.bean();
        //如何才能通过BeanDefinition 等信息来返回Bean的实例呢？？？
        return (T) bean;
    }

    /**
     * 解析和初始化BeanDefinition
     * @param def
     */
    protected void initBeanDefinition(final BeanDefinition def){
        //如果Bean定义中还没有构造函数的定义，则重新解析
        if(def.ctorInjectionPoint == null){
            CtorResolver ctorResolver = new CtorResolver();
            def.ctorInjectionPoint =ctorResolver.resolve(def.type);
        }
    }
}
