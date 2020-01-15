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
 * Bean的定义和缓存。由定义和缓存Bean 的数据组成。
 * 如果为<code>null</code>的情况下，缓存可能没有被初始化。
 * 一般从容器中取得Bean时，缓存就会被初始化了。
 * @param <T>
 */
public class BeanDefinition<T> {
    //finals
    protected final String name; //bean 名称
    protected final Class<T> type; // bean类型
    protected final WiringMode wiringMode; //装载模式

    //构造函数注入点
    protected CtorInjectionPoint ctorInjectionPoint;

    /**
     * 构造函数
     *
     * @param name bean 名称
     * @param type bean 类型
     */
    public BeanDefinition(final String name, final Class<T> type ,final WiringMode wiringMode) {
        this.name = name;
        this.type = type;
        this.wiringMode = wiringMode;
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
                ", wiringMode=" + wiringMode +
                ", ctorInjectionPoint=" + ctorInjectionPoint +
                '}';
    }
}
