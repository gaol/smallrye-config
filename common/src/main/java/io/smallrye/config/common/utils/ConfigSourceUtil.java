/*
 * Copyright 2019 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.smallrye.config.common.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.eclipse.microprofile.config.spi.ConfigSource;

/**
 * utilities and constants for {@link ConfigSource} implementations
 *
 * @author helloween
 */
public class ConfigSourceUtil {
    public static final String CONFIG_ORDINAL_KEY = "config_ordinal";
    public static final String CONFIG_ORDINAL_100 = "100";

    private ConfigSourceUtil() {
    }

    /**
     * convert {@link Properties} to {@link Map}
     *
     * @param properties {@link Properties} object
     * @return {@link Map} object
     */
    public static Map<String, String> propertiesToMap(Properties properties) {
        return properties.entrySet().stream().collect(Collectors.toMap(e -> String.valueOf(e.getKey()),
                e -> String.valueOf(e.getValue())));
    }

    public static Map<String, String> urlToMap(URL locationOfProperties) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(locationOfProperties.openStream(), StandardCharsets.UTF_8)) {
            Properties p = new Properties();
            p.load(reader);
            return ConfigSourceUtil.propertiesToMap(p);
        }
    }

    /**
     * Get the ordinal value configured within the given map.
     *
     * @param map the map to query
     * @param defaultOrdinal the ordinal to return if the ordinal key is not specified
     * @return the ordinal value to use
     */
    public static int getOrdinalFromMap(Map<String, String> map, int defaultOrdinal) {
        String ordStr = map.get(CONFIG_ORDINAL_KEY);
        return ordStr == null ? defaultOrdinal : Integer.parseInt(ordStr);
    }
}
