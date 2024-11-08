/*
 * Copyright 2024 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.bsd.replicator;

import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author hrupp
 */
@ApplicationScoped
public class Brewery {

    @WithSpan()
    void brewTea(@SpanAttribute(value = "kind-of-tea") String kind) throws InterruptedException {
        if (Math.random()*100 < 30) {
            throw new NotEnoughDilithiumException(kind);
        }

        if (Math.random()*100 < 70) {
            Thread.sleep((long) (Math.random()*2000L));
        }
    }

}
