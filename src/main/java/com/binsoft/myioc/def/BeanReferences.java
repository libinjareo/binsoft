package com.binsoft.myioc.def;

public class BeanReferences {
    private final String name;

    BeanReferences(final String name) {
        this.name = name;
    }

    /**
     * 取得名称
     * @return
     */
    public String name(){
        return this.name;
    }

    /**
     * 创建新的 Bean 引用
     *
     * @param name
     * @return
     */
    public static BeanReferences of(final String name) {
        return new BeanReferences(name);
    }


    @Override
    public String toString() {
        return "BeanReferences{" +
                "name='" + name + '\'' +
                '}';
    }
}
