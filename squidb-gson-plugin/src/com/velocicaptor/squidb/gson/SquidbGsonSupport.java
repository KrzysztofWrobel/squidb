/*
 * Copyright 2015, Yahoo Inc.
 * Copyrights licensed under the Apache 2.0 License.
 * See the accompanying LICENSE file for terms.
 */
package com.velocicaptor.squidb.gson;

import com.google.gson.Gson;
import com.yahoo.squidb.data.AbstractModel;
import com.yahoo.squidb.sql.Property;

public class SquidbGsonSupport {

    public static final Gson GSON = new Gson();

    @SuppressWarnings("unchecked")
    public static <T> T fromJson(AbstractModel model, Property.StringProperty property, Class<T> type) {
        if (!model.hasTransitory(property.getName())) {
            T data = null;
            if (model.containsNonNullValue(property)) {
                data = (T) GSON.fromJson(model.get(property), type);
            }
            model.putTransitory(property.getName(), data);
            return data;
        }

        return (T) model.getTransitory(property.getName());
    }

    /**
     * Sets the given GSON-serialized property to the given value
     *
     * @return true if the value object was successfully serialized, false otherwise
     * @see {@link #toJson}
     */
    public static boolean toJson(AbstractModel model, Property.StringProperty property, Object data) {
        String json = null;
        if (data != null) {
            json = GSON.toJson(data);
            if (model.containsNonNullValue(property)
                    && json.equals(model.get(property))) {
                return false;
            }
        }
        model.set(property, json);
        model.putTransitory(property.getName(), data);
        return true;

    }

}
