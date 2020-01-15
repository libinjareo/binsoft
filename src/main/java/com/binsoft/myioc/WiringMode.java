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

/**
 * 装载模式
 */
public enum WiringMode {

    /**
     * 容器设置的默认的装载模式
     */
    DEFAULT(-1),

    /**
     * 任何情况都不会装载
     */
    NONE(0),

    /**
     * 显示的严格装载。装载显示定义的注入点。如果无法满足装载，则抛出异常。
     */
    STRICT(1),

    /**
     * 显示并松散装载，仅装载显示定义的注入点。如果无法满足状态，则不会抛出异常。
     *
     */
    OPTIONAL(2),


    /**
     * 自动装载，Beans将会被注入到定义的注入点。所有满足命名约定的地方都会被注入。
     * 如果装载不满足条件，则不会抛出异常。
     */
    AUTOWIRE(3);

    private final int value;

    WiringMode(final int value){
        this.value = value;
    }

    public int value(){
        return value;
    }

    @Override
    public String toString() {
        return name();
    }
}
