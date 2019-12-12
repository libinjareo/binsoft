package com.binsoft.myioc;

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


        //构建BeanData
        final BeanData beanData = new BeanData(this, def);
        //取得Bean
        Object bean = beanData.bean();
        //如何才能通过BeanDefinition 等信息来返回Bean的实例呢？？？
        return (T) bean;
    }
}
