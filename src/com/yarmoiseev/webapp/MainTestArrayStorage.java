//package com.yarmoiseev.webapp;
//
//import com.yarmoiseev.webapp.model.Resume;
//import com.yarmoiseev.webapp.storage.ArrayStorage;
//
///**
// * Test for your com.yarmoiseev.webapp.storage.ArrayStorage implementation
// */
//public class MainTestArrayStorage {
//    //static final SortedArrayStorage ARRAY_STORAGE = new SortedArrayStorage();
//    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();
//
//    public static void main(String[] args) {
//        Resume r1 = new Resume("uuid1", "full name");
//        Resume r2 = new Resume("uuid2", "full name");
//        Resume r3 = new Resume("uuid3", "full name");
//
//        ARRAY_STORAGE.save(r3);
//        ARRAY_STORAGE.save(r1);
//        ARRAY_STORAGE.save(r2);
//
//        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
//        System.out.println("Size: " + ARRAY_STORAGE.size());
//
// //       System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
////
////        ARRAY_STORAGE.delete("dummy");
////        printAll();
////        ARRAY_STORAGE.delete(r1.getUuid());
////        printAll();
//        ARRAY_STORAGE.update(r2);
//        printAll();
//        ARRAY_STORAGE.clear();
//        printAll();
//
//        System.out.println("Size: " + ARRAY_STORAGE.size());
//    }
//
////    static void printAll() {
////        System.out.println("\nGet All");
////        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
////            System.out.println(r);
////        }
////    }
////}
