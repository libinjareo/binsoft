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

import com.binsoft.myioc.MyIOCException;
import com.binsoft.myioc.def.BeanReferences;


import java.lang.reflect.Executable;

/**
 * 构造函数或方法引用解析
 */
public class ReferencesResolver {

    /**
     *  默认构造函数
     */
    public ReferencesResolver(){}

    /**
     * 获取给定的参数引用，并且返回给定方法或构造函数的引用集合
     * @param methodOrCtor
     * @param parameterReference
     * @return
     */
    public BeanReferences[] resolveReferenceFromValue(final Executable methodOrCtor,final String... parameterReference){
        //首先通过字符串数组参数构建BeanReference
        BeanReferences[] references = convertRefToReferences(parameterReference);

        //如果字符串参数为空,则通过构造函数来构建BeanReference
        if(references == null || references.length == 0){
            buildDefaultReferences(methodOrCtor);
        }

        //判断构造函数的参数类型的长度是否与引用的长度一致
        if(methodOrCtor.getParameterTypes().length != references.length){
            throw new MyIOCException("Different number of method parameter and references for: " + methodOrCtor.getDeclaringClass().getName() + '#' + methodOrCtor.getName());
        }

        //移除所有的重复的名称
        removeAllDuplicateNames(references);

        return references;
    }

    /**
     * 为Bean引用移除重复的名称
     * @param allBeanReferences
     */
    private void removeAllDuplicateNames(final BeanReferences[] allBeanReferences){
        for(int i = 0; i < allBeanReferences.length; i++){
            BeanReferences references = allBeanReferences[i];
            allBeanReferences[i] = references.removeDuplicateNames();
        }
    }
    /**
     * 创建默认的方法引用
     * @param methodOrCtor
     * @return
     */
    private BeanReferences[] buildDefaultReferences(final Executable methodOrCtor){
        //获取可能存在的总Config配置信息
        Class<?>[] parameterTypes = methodOrCtor.getParameterTypes();
        final BeanReferences[] references = new BeanReferences[parameterTypes.length];

        return references;
    }

    /**
     * 把单个字符串数组转换为bean的引用数组
     * @param references
     * @return
     */
    private BeanReferences[] convertRefToReferences(final String[] references){
        if(references == null){
            return null;
        }

        BeanReferences[] ref = new BeanReferences[references.length];

        for(int i = 0; i < references.length; i++){
            ref[i] = BeanReferences.of(references[i]);
        }
        return ref;
    }
}
