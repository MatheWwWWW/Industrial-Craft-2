/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package ic2.api.recipes.ingridients.inputs;

import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;

public interface INullableInput
extends IInput {
    @Override
    default public JsonObject serialize() {
        return new JsonObject();
    }
}

