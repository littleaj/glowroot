/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.glowroot.engine.bytecode.api;

import org.checkerframework.checker.nullness.qual.Nullable;

import org.glowroot.instrumentation.api.MessageSupplier;
import org.glowroot.instrumentation.api.MethodInfo;
import org.glowroot.instrumentation.api.TraceEntry;

public interface BytecodeService {

    void enteringMain(String mainClass, @Nullable String /*@Nullable*/ [] mainArgs);

    void enteringApacheCommonsDaemonLoad(String mainClass,
            @Nullable String /*@Nullable*/ [] mainArgs);

    void exitingGetPlatformMBeanServer();

    ThreadContextThreadLocal.Holder getCurrentThreadContextHolder();

    ThreadContextPlus createOptionalThreadContext(
            ThreadContextThreadLocal.Holder threadContextHolder, int currentNestingGroupId,
            int currentSuppressionKeyId);

    Object getClassMeta(int index) throws Exception;

    Object getMethodMeta(int index) throws Exception;

    MessageTemplate createMessageTemplate(String template, MethodInfo methodInfo);

    MessageSupplier createMessageSupplier(MessageTemplate template, Object receiver,
            String methodName, @Nullable Object... args);

    String getMessageText(MessageTemplate template, Object receiver, String methodName,
            @Nullable Object... args);

    void updateWithReturnValue(TraceEntry traceEntry, @Nullable Object returnValue);

    void logThrowable(Throwable throwable);

    void preloadSomeSuperTypes(ClassLoader loader, @Nullable String className);
}
