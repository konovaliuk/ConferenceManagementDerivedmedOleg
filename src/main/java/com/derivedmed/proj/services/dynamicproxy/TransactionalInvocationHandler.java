package com.derivedmed.proj.services.dynamicproxy;

import com.derivedmed.proj.dao.TransactionManager;
import com.derivedmed.proj.util.annotations.Transactional;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TransactionalInvocationHandler implements InvocationHandler {

    private TransactionManager transactionManager = TransactionManager.getInstance();

    private Object object;

    public TransactionalInvocationHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (isTransactional(method)) {
            transactionManager.beginTransaction();
            Object result = method.invoke(object, args);
            transactionManager.commit();
            return result;
        }
        return method.invoke(object, args);
    }

    private boolean isTransactional(Method method) throws NoSuchMethodException {
        Method realMethod = object.getClass().getMethod(method.getName(), method.getParameterTypes());
        return realMethod.isAnnotationPresent(Transactional.class);
    }
}
