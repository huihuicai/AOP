package com.wynne.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import static javax.lang.model.element.Modifier.*;

/**
 * Created by ybm on 2017/9/5.
 */

public class AnnotationClass {

    private final ClassName PROVIDER =
            ClassName.get("com.huihuicai.finder.provider", "Provider");

    private final ClassName FINDER =
            ClassName.get("com.huihuicai.finder", "Finder");
    private TypeElement mTypeElement;
    private Elements mElements;
    private List<ViewField> mViewField;

    public AnnotationClass(TypeElement typeElement, Elements elements) {
        mTypeElement = typeElement;
        mElements = elements;
        mViewField = new ArrayList<>();
    }

    //获取element的class name
    public String getClassName() {
        return mTypeElement.getQualifiedName().toString();
    }

    public void addViewField(ViewField viewField) {
        if (viewField != null) {
            mViewField.add(viewField);
        }
    }

    /**
     * 构造整个类
     */
    public JavaFile createFinder() {
        //构造方法（就是要实现Finder的finder(host,source,provider)）
        MethodSpec.Builder injectBuilder = MethodSpec.methodBuilder("finder");
        injectBuilder.addModifiers(PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(mTypeElement.asType()), "host", FINAL)
                .addParameter(TypeName.OBJECT, "source")
                .addParameter(PROVIDER, "provider");
        //遍历每一个注解，生成findView的方法:view = activity.findViewById(id)
        for (ViewField field : mViewField) {
            injectBuilder.addStatement("host.$N = ($T)(provider.findView(source, $L))",
                    field.getVariableName(), ClassName.get(field.getVariableType()), field.getResId());
        }

        //获取当前的mTypeElement所在的package
        String packageName = mElements.getPackageOf(mTypeElement).getQualifiedName().toString();
        //构造整个类
        TypeSpec typeSpec = TypeSpec.classBuilder(mTypeElement.getSimpleName() + "$$Finder")
                .addModifiers(PUBLIC)//修饰符
                .addSuperinterface(ParameterizedTypeName.get(FINDER, TypeName.get(mTypeElement.asType())))
                .addMethod(injectBuilder.build())//里面的方法
                .build();
        return JavaFile.builder(packageName, typeSpec).build();
    }

}
