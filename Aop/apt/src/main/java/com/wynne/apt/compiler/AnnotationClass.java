package com.wynne.apt.compiler;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by ybm on 2017/9/5.
 */

public class AnnotationClass {

    private TypeElement mTypeElement;
    private Elements mElements;
    private List<ViewField> mViewField;

    public AnnotationClass(TypeElement typeElement, Elements elements) {
        mTypeElement = typeElement;
        mElements = elements;
        mViewField = new ArrayList<>();
    }

    public String getClassName() {
        return mTypeElement.getQualifiedName().toString();
    }

    public void addViewField(ViewField viewField) {
        if (viewField != null) {
            mViewField.add(viewField);
        }
    }

}
