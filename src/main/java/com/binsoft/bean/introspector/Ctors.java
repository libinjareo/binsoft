package com.binsoft.bean.introspector;

import java.lang.reflect.Constructor;

/**
 * 构造函数集合
 */
public class Ctors {
    protected final ClassDescriptor classDescriptor;
    protected final CtorDescriptor[] allCtors;
    protected CtorDescriptor defaultCtor;

    public Ctors(final ClassDescriptor classDescriptor) {
        this.classDescriptor = classDescriptor;
        this.allCtors = inspectConstructors();
    }

    /**
     * 检查目标类型中的所有声明的构造函数
     *
     * @return
     */
    protected CtorDescriptor[] inspectConstructors() {
        //从类描述器中取得类型
        Class type = classDescriptor.getType();
        //根据类型取得所有声明的构造函数
        Constructor[] declaredConstructors = type.getDeclaredConstructors();
        //创建构造函数描述器数组
        CtorDescriptor[] allCtors = new CtorDescriptor[declaredConstructors.length];
        //遍历该类型所声明的所有的构造函数
        for (int i = 0; i < declaredConstructors.length; i++) {
            Constructor ctor = declaredConstructors[i];

            //为每一个构造函数创建构造函数描述器
            CtorDescriptor ctorDescriptor = createCtorDescriptor(ctor);
            allCtors[i] = ctorDescriptor;

            //判断是否是默认的构造函数（参数长度为0）
            if (ctorDescriptor.isDefault()) {
                defaultCtor = ctorDescriptor;
            }
        }

        return allCtors;
    }

    /**
     * 创建新的{@link CtorDescriptor}
     *
     * @param ctor
     * @return
     */
    protected CtorDescriptor createCtorDescriptor(final Constructor ctor) {
        return new CtorDescriptor(classDescriptor, ctor);
    }
    // ---------------------------------------------------------------- get

    /**
     * 返回默认的（无参）构造函数描述器
     *
     * @return
     */
    public CtorDescriptor getDefaultCtor() {
        return defaultCtor;
    }

    /**
     * 查找与得定类型匹配的构造函数描述器
     * @param args
     * @return
     */
    public CtorDescriptor getCtorDescriptor(final Class... args) {

        //遍历所有构造函数描述器
        ctors:
        for (CtorDescriptor ctorDescriptor : allCtors) {
            //取得每个构造函数的参数列表
            Class[] arg = ctorDescriptor.getParameters();

            //如果构造函数的参数个数不一致，则继续循环下一个构造函数
            if (arg.length != args.length) {
                continue;
            }

            //如果参数个数相等，则继续判断每个位置上的参数类型是否匹配
            for (int j = 0; j < arg.length; j++) {
                if (arg[j] != args[j]) {
                    //如果参数类型不一致，则goto到最外层，继续下一个构造函数的轮训
                    continue ctors;
                }
            }
            return ctorDescriptor;
        }
        return null;
    }


    /**
     * 返回所有的构造函数描述器
     * @return
     */
    CtorDescriptor[] getAllCtorDescriptors(){
        return allCtors;
    }

}
