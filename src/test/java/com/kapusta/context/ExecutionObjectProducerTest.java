package com.kapusta.context;

import com.kapusta.context.test.utils.ExecutionObjectProducerTestUtils;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExecutionObjectProducerTest {

    @Test
    public void test() throws ContextCreationException {
       Set<ContextMap> cm = new ContextMapCreator( Set.of(
               ExecutionObjectProducerTestUtils.ClassB.class,
               ExecutionObjectProducerTestUtils.ClassC.class,
               ExecutionObjectProducerTestUtils.ClassD.class)).
               createContextMap(
                       Set.of(
                           ExecutionObjectProducerTestUtils.ClassA.class)
               );
        ExecutionObjectProducerTestUtils.ClassA executionObject = (ExecutionObjectProducerTestUtils.ClassA) new ExecutionObjectProducer(cm.iterator().next()).produceExecutionObject();
        assertTrue(List.of("A", "B", "C", "D").containsAll(executionObject.test()));
    }

    @Test
    public void testNestedObjects() throws ContextCreationException {
        Set<ContextMap> cm = new ContextMapCreator( Set.of(
                ExecutionObjectProducerTestUtils.ClassG.class,
                ExecutionObjectProducerTestUtils.ClassH.class)).
                createContextMap(
                        Set.of(
                                ExecutionObjectProducerTestUtils.ClassF.class)
                );
        ExecutionObjectProducerTestUtils.ClassF executionObject = (ExecutionObjectProducerTestUtils.ClassF) new ExecutionObjectProducer(cm.iterator().next()).produceExecutionObject();
        assertEquals(3, executionObject.test());
    }
}