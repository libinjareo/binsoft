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
        ClassDescriptor cd = ClassIntrospector.get().lookup(type);
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
