package com.binsoft.myioc;

import com.binsoft.myioc.def.CtorInjectionPoint;

public class BeanDefinition<T> {
    //finals
    protected final String name; //bean 名称
    protected final Class<T> type; // bean类型

    //构造函数注入点
    protected CtorInjectionPoint ctorInjectionPoint;

    /**
     * 构造函数
     *
     * @param name bean 名称
     * @param type bean 类型
     */
    public BeanDefinition(final String name, final Class<T> type) {
        this.name = name;
        this.type = type;
    }

    // ----------------------------------------------------------------  getters方法

    /**
     * 返回 bean 名称
     *
     * @return
     */
    public String name() {
        return this.name;
    }

    /**
     * 返回 bean 类型
     *
     * @return
     */
    public Class<T> type() {
        return this.type;
    }

    /**
     * 返回构造函数注入点
     *
     * @return
     */
    public CtorInjectionPoint ctorInjectionPoint() {
        return ctorInjectionPoint;
    }
    // ----------------------------------------------------------------  toString方法

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
