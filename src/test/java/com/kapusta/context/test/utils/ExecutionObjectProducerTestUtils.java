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

    public abstract static class ClassE {
        private boolean isVisited = false;

        public void incrementCounter(CounterHolder counter){
            if(!isVisited) {
                counter.inc();
                isVisited = true;
            }
        }

        protected class CounterHolder {
            private int counter = 0;

            protected void inc(){
                counter++;
            }

            protected int get(){
                return counter;
            }
        }
    }

    public static class ClassF extends ClassE {
        private final ClassH ch;
        private final ClassG cg;

        public ClassF(ClassH ch, ClassG cg) {
            this.ch = ch;
            this.cg = cg;
        }

        public int test(){
            CounterHolder counterHolder = new CounterHolder();
            this.incrementCounter(counterHolder);
            this.ch.test(counterHolder);
            this.cg.test(counterHolder);
            return counterHolder.get();
        }
    }

    public static class ClassH extends ClassE {
        private final ClassG cg;

        public ClassH(ClassG cg) {
            this.cg = cg;
        }

        public void test(CounterHolder counterHolder){
            this.incrementCounter(counterHolder);
            this.cg.test(counterHolder);
        }
    }

    public static class ClassG extends ClassE {

        public void test(CounterHolder counterHolder){
            this.incrementCounter(counterHolder);
        }
    }
}
