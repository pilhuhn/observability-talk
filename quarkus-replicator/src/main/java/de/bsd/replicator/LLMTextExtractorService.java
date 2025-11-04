/*
 * Copyright 2025 Red Hat, Inc. and/or its affiliates
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

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

/**
 * @author hrupp
 */
@RegisterAiService(modelName = "model1")
public interface LLMTextExtractorService {

    @SystemMessage("You are a helpful assistant that extracts information from text. Please reply in plain text" +
            " without any additional context or explanations.")
    @UserMessage("Please extract the kind of tea from this text: {text}. " +
            "Only return the name of the tea, do not include anything else. " +
            "Also do not include the word tea in your response.")
    String extract(String text);
}
