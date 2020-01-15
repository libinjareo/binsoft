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

import com.binsoft.myioc.def.BeanReferences;
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
     * 返回与BeanReferences中名称相同的bean实例
     * @param beanReferences
     * @return
     */

    protected Object getBean(final BeanReferences beanReferences){
        final int total = beanReferences.size();

        for(int i = 0; i < total; i++){
            String name = beanReferences.name(i);

            if(name != null){
                Object bean = getBean(name);
                if(bean != null){
                    return bean;
                }

            }
        }
        return null;
    }



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
