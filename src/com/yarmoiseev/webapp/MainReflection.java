//package com.yarmoiseev.webapp;
//
//import com.yarmoiseev.webapp.model.Resume;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//
//public class MainReflection {
//
//    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//        Resume r = new Resume();
//        Field field = r.getClass().getDeclaredFields()[0];
//        field.setAccessible(true);
//        System.out.println(field.getName());
//        System.out.println(field.get(r));
//        field.set(r, "new_uuid");
//        System.out.println(r);
//
//        Class<Resume> resumeClassObj = Resume.class;
//        Method method = resumeClassObj.getMethod("toString");
//        System.out.println("via reflection: " + method.invoke(r) + " via toString: " + r.toString());
//
//    }
//}
