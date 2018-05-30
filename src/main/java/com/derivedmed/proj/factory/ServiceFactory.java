package com.derivedmed.proj.factory;


import com.derivedmed.proj.services.*;
import com.derivedmed.proj.services.dynamicproxy.ProxyService;
import com.derivedmed.proj.util.annotations.Transactional;

import java.util.Arrays;

public class ServiceFactory {

    private static ServiceFactory instance = new ServiceFactory();
    public static ServiceFactory getInstance() {
        return instance;
    }

    private ServiceFactory() {
    }

    private static <T extends Service> T getService(T type) {
        Class<? extends Service> serviceClass = type.getClass();
        if (isTransactional(serviceClass)) {
            ProxyService<T> proxyService = new ProxyService<>(type, serviceClass);
            return proxyService.getProxy();
        }
        return type;
    }

    public static NotificationService getNotificationService() {
        return getService(NotificationServiceImpl.getInstance());
    }

    public static UserService getUserService() {
        return getService(UserServiceImpl.getInstance());
    }

    public static ReportService getReportService() {
        return getService(ReportServiceImpl.getInstance());
    }

    public static ConfService getConfService() {
        return getService(ConfServiceImpl.getInstance());
    }

    private static boolean isTransactional(Class clazz) {
        return Arrays.stream(clazz.getMethods())
                .anyMatch(m -> m.isAnnotationPresent(Transactional.class));
    }
}
