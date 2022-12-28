package com.kapusta.context.test.utils;


public class ContextCreatorTestUtils {


    public static class ClassA {
        public ClassA(ClassB cb, ClassC cc){}
    }

    public static class ClassB {
        public ClassB(ClassD cd){

        }
    }

    public static class ClassC {
        public ClassC(ClassD cd){}
    }

    public static class ClassD {
    }

    public static class ClassG {
        public ClassG(ClassD ch){}
        public ClassG(){}
    }

    public static class ClassH {
        public ClassH(ClassI ci){}
    }

    public static class ClassI {
        public ClassI(ClassJ cj){}
    }

    public static class ClassJ {
        public ClassJ(ClassI ci){}
    }
}