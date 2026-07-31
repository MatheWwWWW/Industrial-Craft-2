/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 */
package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class ValueObject {
    public String toString() {
        StringBuilder $$0 = new StringBuilder("{");
        for (Field $$1 : this.getClass().getFields()) {
            if (ValueObject.m_87715_($$1)) continue;
            try {
                $$0.append(ValueObject.m_87713_($$1)).append("=").append($$1.get(this)).append(" ");
            }
            catch (IllegalAccessException illegalAccessException) {
                // empty catch block
            }
        }
        $$0.deleteCharAt($$0.length() - 1);
        $$0.append('}');
        return $$0.toString();
    }

    private static String m_87713_(Field p_87714_) {
        SerializedName $$1 = p_87714_.getAnnotation(SerializedName.class);
        return $$1 != null ? $$1.value() : p_87714_.getName();
    }

    private static boolean m_87715_(Field p_87716_) {
        return Modifier.isStatic(p_87716_.getModifiers());
    }
}

