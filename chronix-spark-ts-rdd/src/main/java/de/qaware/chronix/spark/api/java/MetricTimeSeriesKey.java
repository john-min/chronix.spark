/*
 * Copyright (C) 2016 QAware GmbH
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package de.qaware.chronix.spark.api.java;

import de.qaware.chronix.timeseries.MetricTimeSeries;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MetricTimeSeriesKey implements Serializable {

    private String metric;
    private Map<String, Object> attributes = new HashMap<>();

    public MetricTimeSeriesKey(MetricTimeSeries mts) {
        this.metric = mts.getMetric();
        this.attributes = mts.attributes();
    }

    public String getMetric() {
        return metric;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetricTimeSeriesKey that = (MetricTimeSeriesKey) o;

        //TODO: make identity function on key more flexible (& use EqualsBuilder)
        if (!metric.equals(that.metric)) return false;
        if (!equalsAttributes(that, "host")) return false;
        if (!equalsAttributes(that, "series")) return false;
        if (!equalsAttributes(that, "process")) return false;
        if (!equalsAttributes(that, "group")) return false;
        if (!equalsAttributes(that, "ag")) return false;
        return true;
    }

    private boolean equalsAttributes(MetricTimeSeriesKey that,
                                     String attribute) {
        Object oKey = this.attributes.get(attribute);
        Object thatKey = that.attributes.get(attribute);
        if (oKey == null) return false;
        else return oKey.equals(thatKey);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(attributes.get("host"))
                .append(attributes.get("series"))
                .append(attributes.get("process"))
                .append(attributes.get("group"))
                .append(attributes.get("ag")).toHashCode();
    }
}
