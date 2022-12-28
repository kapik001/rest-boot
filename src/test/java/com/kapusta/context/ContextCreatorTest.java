package com.kapusta.context;

import com.kapusta.context.test.utils.*;
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
        dependencies.add(ContextCreatorTestUtils.ClassB.class);
        dependencies.add(ContextCreatorTestUtils.ClassC.class);
        dependencies.add(ContextCreatorTestUtils.ClassD.class);
        HashSet<Class<?>> arguments = new LinkedHashSet<>();
        arguments.add(ContextCreatorTestUtils.ClassA.class);
        Set<ContextMap> contextMaps = new ContextMapCreator(dependencies).createContextMap(arguments);
        assertEquals(ContextCreatorTestUtils.ClassA.class, contextMaps.iterator().next().getClazz());
        Iterator<ContextMap> classAIterator = contextMaps.iterator().next().getDependencies().iterator();
        assertTrue(ContextCreatorTestUtils.ClassD.class.equals(contextMaps.iterator().next().getDependencies().iterator().next().getDependencies().iterator().next().getClazz())
            || ContextCreatorTestUtils.ClassC.class.equals(contextMaps.iterator().next().getDependencies().iterator().next().getDependencies().iterator().next().getClazz()));
        assertEquals(0, contextMaps.iterator().next().getDependencies().iterator().next().getDependencies().iterator().next().getDependencies().size());
        ContextMap classBContext = classAIterator.next();
        assertTrue(ContextCreatorTestUtils.ClassD.class.equals(classBContext.getDependencies().iterator().next().getClazz())
        || ContextCreatorTestUtils.ClassC.class.equals(classBContext.getDependencies().iterator().next().getClazz()));
        assertEquals(0, classBContext.getDependencies().iterator().next().getDependencies().size());
    }

    @Test
    public void testTooManyConstructorsException(){
        ContextCreationException cce = Assertions.assertThrows(ContextCreationException.class, () -> {
            new ContextMapCreator(Set.of(ContextCreatorTestUtils.ClassD.class)).createContextMap(Set.of(ContextCreatorTestUtils.ClassG.class));
        });
        assertEquals("Constructor of class com.kapusta.context.test.utils.ContextCreatorTestUtils$ClassG have not correct number of public constructors declared! Should have exactly one, but 2 was detected", cce.getMessage());
    }

    @Test
    public void testNotKnownParameterException(){
        Assertions.assertThrows(ContextCreationException.class, () -> {
            HashSet<Class<?>> dependencies = new LinkedHashSet<>();
            dependencies.add(ContextCreatorTestUtils.ClassB.class);
            dependencies.add(ContextCreatorTestUtils.ClassC.class);
            HashSet<Class<?>> arguments = new LinkedHashSet<>();
            arguments.add(ContextCreatorTestUtils.ClassA.class);
            new ContextMapCreator(dependencies).createContextMap(arguments);
        });
    }

    @Test
    public void testCyclicDependenciesException(){
        Assertions.assertThrows(ContextCreationException.class, () -> {
            HashSet<Class<?>> dependencies = new LinkedHashSet<>();
            dependencies.add(ContextCreatorTestUtils.ClassI.class);
            dependencies.add(ContextCreatorTestUtils.ClassJ.class);
            HashSet<Class<?>> arguments = new LinkedHashSet<>();
            arguments.add(ContextCreatorTestUtils.ClassH.class);
            new ContextMapCreator(dependencies).createContextMap(arguments);
        });
    }
}