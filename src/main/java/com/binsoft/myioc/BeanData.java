package com.binsoft.myioc;

import com.binsoft.myioc.def.CtorInjectionPoint;

/**
 * {@link BeanDefinition}和 bean实例的封装，包含数据以及与BeanDefiniton和值的相关操作。
 */
public class BeanData<T> {
    private final MyContainer pc;
    private final BeanDefinition<T> beanDefinition;
    private final T bean;

    /**
     * 构造函数
     * @param myContainer
     * @param beanDefinition
     * @param bean
     */
    public BeanData(final MyContainer myContainer,final BeanDefinition<T> beanDefinition,final T bean){
        this.pc = myContainer;
        this.beanDefinition = beanDefinition;
        this.bean = bean;
    }

    /**
     * 构造函数
     * @param myContainer
     * @param beanDefinition
     */
    public BeanData(final MyContainer myContainer,final BeanDefinition<T> beanDefinition){
        this.pc = myContainer;
        this.beanDefinition = beanDefinition;
        this.bean = (T)newBeanInstance();
    }

    /**
     * 返回{@link BeanDefinition}
     * @return
     */
    public BeanDefinition<T> definition(){return beanDefinition;}

    /**
     * 返回bean实例
     * @return
     */
    public T bean(){
        return bean;
    }

    /**
     * 创建实例
     * @return
     */
    public Object newBeanInstance(){
        if(beanDefinition.ctorInjectionPoint == CtorInjectionPoint.EMPTY){
            throw new MyIOCException("无构造函数注入点===>" + beanDefinition.type.getName());
        }

        //暂且不处理构造函数中的相关依赖注入
        //beanDefinition.ctor.references.length;
        final Object bean;

        try{
             bean = beanDefinition.ctorInjectionPoint.constructor.newInstance(null);
        }catch (Exception ex){
            throw new MyIOCException("创建新实例失败===>'" + beanDefinition.type.getName() + "' 构造函数:" + beanDefinition.ctorInjectionPoint.constructor,ex);
        }
        return bean;

    }

}
