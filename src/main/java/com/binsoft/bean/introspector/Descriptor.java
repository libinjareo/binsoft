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
