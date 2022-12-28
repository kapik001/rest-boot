package com.kapusta.context.test.utils;

import java.util.ArrayList;
import java.util.List;

public class ExecutionObjectProducerTestUtils {

    public static class ClassA {
        private final ClassB cb;
        private final ClassC cc;

        public ClassA(ClassB cb, ClassC cc){
            this.cb = cb;
            this.cc = cc;
        }

        public List<String> test(){
            List<String> testList = new ArrayList<>();
            testList.add("A");
            this.cb.test(testList);
            this.cc.test(testList);
            return testList;
        }
    }

    public static class ClassB {
        private final ClassD cd;

        public ClassB(ClassD cd) {
            this.cd = cd;
        }

        public void test(List<String> testList){
            testList.add("B");
            this.cd.test(testList);

        }
    }

    public static class ClassC {
        public void test(List<String> testList){
            testList.add("C");
        }
    }

    public static class ClassD{
        public void test(List<String> testList){
            testList.add("D");
        }
    }
}
