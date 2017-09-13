package com.wynne.compiler;

import com.google.auto.service.AutoService;
import com.wynne.annotation.BindView;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by ybm on 2017/9/4.
 * 收集信息，生成代理类
 */

@AutoService(Processor.class)
public class FinderProcessor extends AbstractProcessor {

    private Filer mFiler;
    private Elements mElements;

    private HashMap<String, AnnotationClass> mProxyMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment pe) {
        super.init(pe);
        mFiler = pe.getFiler();
        mElements = pe.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment re) {
        mProxyMap.clear();
        //得到所有标注的有指定的注解的Element
        Set<? extends Element> elements = re.getElementsAnnotatedWith(BindView.class);
        for (Element element : elements) {
            //每个element有类型和对应变量名称
            //1.得到整个element块的parent，相当于root层，也就是整个类的最外层
            TypeElement classElement = (TypeElement) element.getEnclosingElement();
            //2.得到这个类的名称
            String className = classElement.getQualifiedName().toString();
            //3.从map中获取到类名对应的AnnotationClass是不是存在
            AnnotationClass annotationClass = mProxyMap.get(className);
            //4.为空的话就构建一个
            if (annotationClass == null) {
                annotationClass = new AnnotationClass(classElement, mElements);
                mProxyMap.put(className, annotationClass);
            }
            //5.把获取到的ViewField添加到AnnotationClass中去
            ViewField viewField = new ViewField(element);
            annotationClass.addViewField(viewField);
        }
        try {
            for (AnnotationClass annotation : mProxyMap.values()) {
                if (annotation == null) {
                    continue;
                }
                annotation.createFinder().writeTo(mFiler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
