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

import com.binsoft.myioc.def.CtorInjectionPoint;

/**
 * {@link BeanDefinition}和 bean实例的封装，包含数据以及与BeanDefiniton和值的相关操作。
 */
public class BeanData<T> {
    private final MyContainer pc;
    private final BeanDefinition<T> beanDefinition;
    private final T bean;

    /**
     * 构造函数
     *
     * @param myContainer
     * @param beanDefinition
     * @param bean
     */
    public BeanData(final MyContainer myContainer, final BeanDefinition<T> beanDefinition, final T bean) {
        this.pc = myContainer;
        this.beanDefinition = beanDefinition;
        this.bean = bean;
    }

    /**
     * 构造函数
     *
     * @param myContainer
     * @param beanDefinition
     */
    public BeanData(final MyContainer myContainer, final BeanDefinition<T> beanDefinition) {
        this.pc = myContainer;
        this.beanDefinition = beanDefinition;
        this.bean = (T) newBeanInstance();
    }

    /**
     * 返回{@link BeanDefinition}
     *
     * @return
     */
    public BeanDefinition<T> definition() {
        return beanDefinition;
    }

    /**
     * 返回bean实例
     *
     * @return
     */
    public T bean() {
        return bean;
    }

    /**
     * 创建实例
     *
     * @return
     */
    public Object newBeanInstance() {
        if (beanDefinition.ctorInjectionPoint == CtorInjectionPoint.EMPTY) {
            throw new MyIOCException("No constructor (annotated,single or default) founded as injection point for : " + beanDefinition.type.getName());
        }

        //取得构造函数相关依赖长度
        int paramNo = beanDefinition.ctorInjectionPoint.references.length;
        Object[] args = new Object[paramNo];

        //如果配置了装载模式
        if(beanDefinition.wiringMode != WiringMode.NONE){
            //轮训每一个依赖对象
            for(int i = 0; i < paramNo; i++){
                //从容器中取得依赖对象
               args[i] =  pc.getBean(beanDefinition.ctorInjectionPoint.references[i]);
               //如果没有找到依赖对象
                if(args[i] == null){
                    //如果是严格的装载模式
                    if((beanDefinition.wiringMode == WiringMode.STRICT)){
                        //抛出异常
                        throw new MyIOCException("Wiring constructor failed. References '" + beanDefinition.ctorInjectionPoint.references[i] +
                        "' not found for constructor: " + beanDefinition.ctorInjectionPoint.constructor);
                    }
                }
            }
        }


        final Object bean;

        try {
            bean = beanDefinition.ctorInjectionPoint.constructor.newInstance(args);
        } catch (Exception ex) {
            throw new MyIOCException("Failed to create new bean instance '" + beanDefinition.type.getName() + "' using constructor:" + beanDefinition.ctorInjectionPoint.constructor, ex);
        }
        return bean;

    }

}
