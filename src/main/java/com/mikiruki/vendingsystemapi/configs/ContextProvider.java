package com.mikiruki.vendingsystemapi.configs;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContextProvider {

    private static ContextProvider instance;
    private ApplicationContext context;

    private ContextProvider(){
        context = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    public ApplicationContext getContext() {
        return context;
    }

    public static ContextProvider getInstance(){
        if(instance == null){
            synchronized (ContextProvider.class) {
                if(instance == null){
                    instance = new ContextProvider();
                }
            }
        }
        return instance;
    }
}
