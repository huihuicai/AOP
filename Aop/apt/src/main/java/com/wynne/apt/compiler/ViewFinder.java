package com.wynne.apt.compiler;

import com.google.auto.service.AutoService;
import com.sun.java.browser.net.ProxyInfo;
import com.wynne.apt.annotation.BindView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by ybm on 2017/9/4.
 * 收集信息，生成代理类
 */

@AutoService(Processor.class)
public class ViewFinder extends AbstractProcessor {

    private Filer mFiler;
    private Elements mElements;
    private Messager mMessager;
    private AnnotationClass mAnnClass;

    private HashMap<String, ProxyInfo> mProxyMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment pe) {
        super.init(pe);
        mFiler = pe.getFiler();
        mElements = pe.getElementUtils();
        mMessager = pe.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment re) {
        mProxyMap.clear();
        Set<? extends Element> elements = re.getElementsAnnotatedWith(BindView.class);
        for (Element element : elements) {
            //field
            VariableElement variableElement = (VariableElement) element;
            //class
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            String qualifiedName = typeElement.getQualifiedName().toString();

            ProxyInfo proxy = mProxyMap.get(qualifiedName);
            if (proxy == null) {
            }
            //对应的annotation的id
            BindView ann = variableElement.getAnnotation(BindView.class);
            int id = ann.value();

        }
        return false;
    }
}
