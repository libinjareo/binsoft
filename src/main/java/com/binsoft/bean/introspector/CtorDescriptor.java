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

package com.binsoft.bean.introspector;

import com.binsoft.core.util.ClassUtil;

import java.lang.reflect.Constructor;

/**
 * 构造函数描述器
 */
public class CtorDescriptor extends Descriptor {

    //构造函数
    protected final Constructor constructor;
    //参数类型数组
    protected final Class[] parameters;

    /**
     * 通过类描述器和构造函数创建构造函数描述器
     * @param classDescriptor 类描述器
     * @param constructor 构造函数
     */
    public CtorDescriptor(final ClassDescriptor classDescriptor, final Constructor constructor) {
        super(classDescriptor, ClassUtil.isPublic(constructor));
        this.constructor = constructor;
        this.parameters = constructor.getParameterTypes();

        ClassUtil.forceAccess(constructor);
    }

    /**
     * 返回构造函数名称
     *
     * @return
     */
    @Override
    public String getName() {
        return constructor.getName();
    }

    /**
     * 返回构造函数
     *
     * @return
     */
    public Constructor getConstructor() {
        return constructor;
    }

    /**
     * 返回构造函数参数。返回的数组不能被克隆。
     *
     * @return
     */
    public Class[] getParameters() {
        return parameters;
    }

    /**
     * 如果是一个默认的构造函数，则返回<code>true</code>
     *
     * @return
     */
    public boolean isDefault() {
        return parameters.length == 0;
    }
}
