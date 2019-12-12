package com.binsoft.myioc.def;

import java.lang.reflect.Constructor;
import java.util.Objects;

/**
 * 构造函数注入点持有类
 */
public class CtorInjectionPoint {
    public static final CtorInjectionPoint EMPTY = new CtorInjectionPoint();

    public final Constructor constructor;
    public final BeanReferences[] references;

    private CtorInjectionPoint() {
        this.constructor = null;
        this.references = null;
    }

    public CtorInjectionPoint(final Constructor constructor, final BeanReferences[] references) {
        Objects.requireNonNull(constructor);
        Objects.requireNonNull(references);

        this.constructor = constructor;
        this.references = references;
    }

}
