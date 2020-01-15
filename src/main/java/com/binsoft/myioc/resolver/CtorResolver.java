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

package com.binsoft.myioc.resolver;

import com.binsoft.bean.introspector.ClassDescriptor;
import com.binsoft.bean.introspector.ClassIntrospector;
import com.binsoft.bean.introspector.CtorDescriptor;
import com.binsoft.myioc.def.BeanReferences;
import com.binsoft.myioc.def.CtorInjectionPoint;

import java.lang.reflect.Constructor;

/**
 * 构造函数注入点解析器
 */
public class CtorResolver {

    public CtorResolver(){

    }
    /**
     * 通过类型解析构造函数注入点.检查构造函数的总数目，如果只有一个构造函数，那么此构造函数将被用于注入点。
     * 如果有多个构造函数，那么默认构造函数将作为注入点。否则，抛出异常。
     *
     * @param type
     * @return
     */
    public CtorInjectionPoint resolve(final Class type) {
        //查找方法
        //通过类自省器得到类描述符信息
        ClassDescriptor cd = ClassIntrospector.get().lookup(type);
        //通过类描述符得到所有的构造函数描述符
        CtorDescriptor[] allCtors = cd.getAllCtorDescriptors();
        Constructor foundedCtor = null;
        Constructor defaultCtor = null;

        //其他Bean引用
        BeanReferences[] references = null;


        //遍历所有的构造函数
        for (CtorDescriptor ctorDescriptor : allCtors) {
            Constructor ctor = ctorDescriptor.getConstructor();

            //取得构造函数参数
            Class[] parameterTypes = ctor.getParameterTypes();
            //如果参数长度为0，说明是默认的构造函数
            if (parameterTypes.length == 0) {
                defaultCtor = ctor;
                foundedCtor = ctor;
            }
        }

        references = new BeanReferences[0];

        return new CtorInjectionPoint(foundedCtor,references);
    }


}
