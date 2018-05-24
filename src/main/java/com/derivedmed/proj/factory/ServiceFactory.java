package com.derivedmed.proj.factory;


import com.derivedmed.proj.services.*;
import com.derivedmed.proj.services.dynamicproxy.ProxyService;
import com.derivedmed.proj.util.annotations.Transactional;

import java.util.Arrays;

public class ServiceFactory {

    private static ServiceFactory ourInstance = new ServiceFactory();
    private static UserService userService = getService(UserServiceImpl.getInstance());
    private static ReportService reportService = getService(ReportServiceImpl.getInstance());
    private static ConfService confService = getService(ConfServiceImpl.getInstance());
    private static NotificationService notificationService = getService(NotificationServiceImpl.getInstance());

    public static ServiceFactory getInstance() {
        return ourInstance;
    }

    private ServiceFactory() {
    }

    private static <T extends Service> T getService(T t) {
        Class<? extends Service> serviceClass = t.getClass();
        if (isTransactional(serviceClass)) {
            ProxyService<T> proxyService = new ProxyService<>(t, serviceClass);
            return proxyService.getProxy();
        }
        return t;
    }

    public static ServiceFactory getOurInstance() {
        return ourInstance;
    }

    public static NotificationService getNotificationService() {
        return notificationService;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static ReportService getReportService() {
        return reportService;
    }

    public static ConfService getConfService() {
        return confService;
    }

    private static boolean isTransactional(Class clazz) {
        return Arrays.stream(clazz.getMethods())
                .anyMatch(m -> m.isAnnotationPresent(Transactional.class));
    }
}
