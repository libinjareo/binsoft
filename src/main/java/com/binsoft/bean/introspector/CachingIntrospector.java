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

import com.binsoft.core.cache.TypeCache;

/**
 * 默认的{@link ClassIntrospector }类自省，并且缓存所有的类描述符.
 * 它可以检测<b>accessible</b>或者是<b>supported</b>的字段/方法/构造函数
 * <p>
 * 仅仅缓存所有的类描述符
 * </p>
 */
public class CachingIntrospector implements ClassIntrospector {

    protected final TypeCache<ClassDescriptor> cache;//<Class<?>, T>
    protected final boolean scanAccessible;
    protected final boolean enhancedProperties;
    protected final boolean includeFieldAsProperties;
    protected final String[] propertyFieldPrefix;

    /**
     * 默认构造函数
     */
    public CachingIntrospector() {
        this(true, true, true, null);
    }

    /**
     * 创建新的缓存{@link ClassIntrospector}
     *
     * @param scanAccessible
     * @param enhancedProperties
     * @param includeFieldAsProperties
     * @param propertyFieldPrefix
     */
    public CachingIntrospector(final boolean scanAccessible, final boolean enhancedProperties, final boolean includeFieldAsProperties, final String[] propertyFieldPrefix) {
        this.cache = TypeCache.createDefault();
        this.scanAccessible = scanAccessible;
        this.enhancedProperties = enhancedProperties;
        this.includeFieldAsProperties = includeFieldAsProperties;
        this.propertyFieldPrefix = propertyFieldPrefix;
    }

    @Override
    public ClassDescriptor lookup(final Class type) {
        return cache.get(type, () ->
                new ClassDescriptor(type, scanAccessible, enhancedProperties, includeFieldAsProperties, propertyFieldPrefix));
    }

    @Override
    public void reset() {
        cache.clear();
    }
}
