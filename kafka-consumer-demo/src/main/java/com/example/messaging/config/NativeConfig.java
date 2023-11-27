package com.example.messaging.config;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@ImportRuntimeHints(NativeConfig.RuntimeHints.class)
public class NativeConfig {

    static class RuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(org.springframework.aot.hint.RuntimeHints hints, ClassLoader classLoader) {
            hints.resources().registerPattern("logback-spring.xml");
            hints.reflection().registerType(ObservationRegistry.class, MemberCategory.values());
            hints.reflection().registerType(ObservedAspect.class, MemberCategory.values());
        }
    }

}