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

/**
 * 通用描述符
 */
public abstract class Descriptor {
    protected final ClassDescriptor classDescriptor;
    protected final boolean isPublic;

    protected Descriptor(final ClassDescriptor classDescriptor, final boolean isPublic) {
        this.classDescriptor = classDescriptor;
        this.isPublic = isPublic;
    }

    /**
     * 返回所属的类描述符
     *
     * @return
     */
    public ClassDescriptor getClassDescriptor() {
        return classDescriptor;
    }

    /**
     * 如果描述符内容是public的，则返回<code>true</code>
     *
     * @return
     */
    public boolean isPublic() {
        return isPublic;
    }

    /**
     * 如果描述符内容匹配所需的声明标志,则返回<code>true</code>
     *
     * @param declared
     * @return
     */
    public boolean matchDeclared(final boolean declared) {
        if (!declared) {
            return isPublic;
        }
        return true;
    }

    /**
     * 返回目标描述符的名称，由子类实现
     *
     * @return
     */
    public abstract String getName();
}
