// Copyright (c) 2003-present, Jodd Team (http://jodd.org)
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package com.binsoft.myioc;

import com.binsoft.bean.introspector.ClassDescriptor;
import com.binsoft.bean.introspector.ClassIntrospector;
import com.binsoft.bean.introspector.CtorDescriptor;
import com.binsoft.myioc.def.BeanReferences;
import com.binsoft.myioc.def.CtorInjectionPoint;
import com.binsoft.myioc.resolver.ReferencesResolver;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public abstract class MyBeans {

    /**
     * 缓存所有的bean 定义
     */
    protected final Map<String, BeanDefinition> beans = new HashMap<>();

    /**
     * {@link ReferencesResolver bean 引用解析器}
     */
    protected final ReferencesResolver referencesResolver;


    protected MyBeans() {
        this.referencesResolver = new ReferencesResolver();
    }

    /**
     * 查找 {@link BeanDefinition bean definition}.
     * 如果名称不存在，则返回<code>null</code>
     *
     * @param beanReferences
     * @return
     */
    public BeanDefinition lookupBeanDefinitions(final BeanReferences beanReferences) {
        final int total = beanReferences.size();
        for (int i = 0; i < total; i++) {
            final String name = beanReferences.name(i);

            BeanDefinition beanDefinition = lookupBeanDefinition(name);

            if (beanDefinition != null) {
                return beanDefinition;
            }

        }

        return null;
    }

    /**
     * 查找 {@link BeanDefinition bean definition}.
     * 如果名称不存在，则返回<code>null</code>
     *
     * @param name
     * @return
     */
    public BeanDefinition lookupBeanDefinition(final String name) {
        BeanDefinition beanDefinition = beans.get(name);
        return beanDefinition;
    }

    /**
     * 返回存在的{@link BeanDefinition }.
     * 如果bean不存在，则抛出异常
     *
     * @param name
     * @return
     */
    protected BeanDefinition lookupExistingBeanDefinition(final String name) {
        BeanDefinition beanDefinition = lookupBeanDefinition(name);
        if (beanDefinition == null) {
            throw new MyIOCException("Bean not found: " + name);
        }
        return beanDefinition;
    }

    /**
     * 注册或定义一个 bean
     *
     * @param type bean的类型，必须指定
     * @param name bean的名称，如果为<code>null</code>,将会从class中解析
     *
     * @param <T>
     * @return
     */
    public <T> BeanDefinition<T> registerMyBean(final Class<T> type, String name,WiringMode wiringMode) {
        //如果name为空的情况下，则解析type来取得name
        if (null == name) {
            name = resolveBeanName(type);
        }

        //如果wiringModel为空，则设置默认的装载模式
        if(wiringMode == null){
            wiringMode = WiringMode.DEFAULT;
        }
        //移除缓存中存在的Bean
        BeanDefinition existing = removeBean(name);
        //TODO:如果存在重复注册的Bean，如何处理？抛出异常？还是覆盖不用处理？


        //判断类型是否有效
        if(type.isInterface()){
            throw new MyIOCException("MyBean can not be an interface: " + type.getName());
        }

        //打印注册详细信息


        BeanDefinition beanDefinition = createBeanDefinitionForRegistration(name, type);

        //注册Bean到Map
        registerBean(name, beanDefinition);

        return beanDefinition;
    }

    /**
     * 注册Bean到缓存Map中
     *
     * @param name
     * @param beanDefinition
     */
    protected void registerBean(final String name, final BeanDefinition beanDefinition) {
        beans.put(name, beanDefinition);
    }

    /**
     * 返回一个{@link BeanDefinition}的新实例，通过
     * {@link #registerMyBean(Class, String)}注册Bean
     * 这是一个修改Bean数据的钩子方法。类似于代理类等
     * 默认情况，返回新的{@link BeanDefinition}实例
     *
     * @param name
     * @param type
     * @param <T>
     * @return
     */
    protected <T> BeanDefinition createBeanDefinitionForRegistration(final String name, final Class<T> type,final WiringMode wiringMode) {
        return new BeanDefinition(name, type,wiringMode);
    }

    /**
     * 移除bean并且返回移除的bean的定义。
     *
     * @param name
     */
    public BeanDefinition removeBean(final String name) {
        BeanDefinition bd = beans.remove(name);
        if (bd == null) {
            return null;
        }

        //TODO
        return bd;
    }

    /**
     * 从类型信息取得Bean的名字
     *
     * @param type
     * @param <T>
     * @return
     */
    private <T> String resolveBeanName(Class<T> type) {
        String name = null;
        name = type.getName();
        return name;
    }


    //-----------------------------------统计相关类


    //-----------------------------------注入点

    /**
     * 注册构造函数注入点
     *
     * @param beanName   bean名称
     * @param paramTypes 构造函数参数类型，可能为<code>null</code>
     * @param references 参数引用
     */
    public void registerMyCtorInjectionPoint(final String beanName, final Class[] paramTypes, final String[] references) {
        //查找存在的BeanDefinition
        BeanDefinition beanDefinition = lookupBeanDefinition(beanName);

        //查找类描述符
        ClassDescriptor cd = ClassIntrospector.get().lookup(beanDefinition.type);
        Constructor constructor = null;

        //如果参数类型为null
        if (paramTypes == null) {
            CtorDescriptor[] ctors = cd.getAllCtorDescriptors();
            if (ctors != null && ctors.length > 0) {
                if (ctors.length > 1) {
                    throw new MyIOCException(ctors.length + "suitable constructor found as injection point for: " + beanDefinition.type.getName());
                }
                constructor = ctors[0].getConstructor();
            }

        } else {
            //根据参数类型获取构造函数描述符
            CtorDescriptor ctorDescriptor = cd.getCtorDescriptor(paramTypes, true);

            if (ctorDescriptor != null) {
                constructor = ctorDescriptor.getConstructor();
            }

        }

        //如果构造函数为空，则抛出异常
        if (constructor == null) {
            throw new MyIOCException("Constructor not found: " + beanDefinition.type.getName());
        }

        //根据构造函数解析出BeanReferences
        BeanReferences[] ref = referencesResolver.resolveReferenceFromValue(constructor, references);

        beanDefinition.ctorInjectionPoint = new CtorInjectionPoint(constructor, ref);

    }

    /**
     * 返回注册bean的总数量
     *
     * @return
     */
    public int beansCount() {
        return beans.size();
    }

}
