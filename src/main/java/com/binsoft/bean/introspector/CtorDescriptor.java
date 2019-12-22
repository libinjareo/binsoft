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
