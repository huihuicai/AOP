package com.wynne.compiler;


import com.wynne.annotation.BindView;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by ybm on 2017/9/5.
 */

public class ViewField {
    private VariableElement mVariableElement;
    private int mResId;

    public ViewField(Element element) {
        if (element == null || element.getKind() != ElementKind.FIELD) {
            throw new IllegalArgumentException("This is not field annotation");
        }
        mVariableElement = (VariableElement) element;
        BindView ann = mVariableElement.getAnnotation(BindView.class);
        if (ann != null) {
            mResId = ann.value();
        } else {
            throw new IllegalArgumentException("This annotation lack value");
        }
    }

    //获取变量的名字
    public Name getVariableName() {
        return mVariableElement.getSimpleName();
    }

    //获取value的值
    public int getResId() {
        return mResId;
    }

    //获取得到的变量的类型
    public TypeMirror getVariableType() {
        return mVariableElement.asType();
    }

}
