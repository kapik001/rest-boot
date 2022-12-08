package com.kapusta.context;

import com.kapusta.context.testutils.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ContextCreatorTest {

    @Test
    public void testBasicCreation() throws ContextCreationException {
        HashSet<Class<?>> dependencies = new LinkedHashSet<>();
        dependencies.add(ClassB.class);
        dependencies.add(ClassC.class);
        dependencies.add(ClassD.class);
        HashSet<Class<?>> arguments = new LinkedHashSet<>();
        arguments.add(ClassA.class);
        Set<ContextMap> contextMaps = new ContextMapCreator(dependencies).createContextMap(arguments);
        assertEquals(ClassA.class, contextMaps.iterator().next().getClazz());
        Iterator<ContextMap> classAIterator = contextMaps.iterator().next().getDependencies().iterator();
        assertTrue(ClassD.class.equals(contextMaps.iterator().next().getDependencies().iterator().next().getDependencies().iterator().next().getClazz())
            || ClassC.class.equals(contextMaps.iterator().next().getDependencies().iterator().next().getDependencies().iterator().next().getClazz()));
        assertEquals(0, contextMaps.iterator().next().getDependencies().iterator().next().getDependencies().iterator().next().getDependencies().size());
        ContextMap classBContext = classAIterator.next();
        assertTrue(ClassD.class.equals(classBContext.getDependencies().iterator().next().getClazz())
        || ClassC.class.equals(classBContext.getDependencies().iterator().next().getClazz()));
        assertEquals(0, classBContext.getDependencies().iterator().next().getDependencies().size());
    }

    @Test
    public void testTooManyConstructorsException(){
        ContextCreationException cce = Assertions.assertThrows(ContextCreationException.class, () -> {
            new ContextMapCreator(Set.of(ClassD.class)).createContextMap(Set.of(ClassG.class));
        });
        assertEquals("Constructor of class com.kapusta.context.testutils.ClassG have not correct number of public constructors declared! Should have exactly one, but 2 was detected", cce.getMessage());
    }

    @Test
    public void testNotKnownParameterException(){
        Assertions.assertThrows(ContextCreationException.class, () -> {
            HashSet<Class<?>> dependencies = new LinkedHashSet<>();
            dependencies.add(ClassB.class);
            dependencies.add(ClassC.class);
            HashSet<Class<?>> arguments = new LinkedHashSet<>();
            arguments.add(ClassA.class);
            new ContextMapCreator(dependencies).createContextMap(arguments);
        });
    }

    @Test
    public void testCyclicDependenciesException(){
        Assertions.assertThrows(ContextCreationException.class, () -> {
            HashSet<Class<?>> dependencies = new LinkedHashSet<>();
            dependencies.add(ClassI.class);
            dependencies.add(ClassJ.class);
            HashSet<Class<?>> arguments = new LinkedHashSet<>();
            arguments.add(ClassH.class);
            new ContextMapCreator(dependencies).createContextMap(arguments);
        });
    }
}