/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package net.minecraft.data.info;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Path;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockListReport
implements DataProvider {
    private final DataGenerator f_124034_;

    public BlockListReport(DataGenerator p_124037_) {
        this.f_124034_ = p_124037_;
    }

    @Override
    public void m_213708_(CachedOutput p_236197_) throws IOException {
        JsonObject $$1 = new JsonObject();
        for (Block $$2 : Registry.f_122824_) {
            ResourceLocation $$3 = Registry.f_122824_.m_7981_($$2);
            JsonObject $$4 = new JsonObject();
            StateDefinition<Block, BlockState> $$5 = $$2.m_49965_();
            if (!$$5.m_61092_().isEmpty()) {
                JsonObject $$6 = new JsonObject();
                for (Property property : $$5.m_61092_()) {
                    JsonArray $$8 = new JsonArray();
                    for (Comparable $$9 : property.m_6908_()) {
                        $$8.add(Util.m_137453_(property, $$9));
                    }
                    $$6.add(property.m_61708_(), (JsonElement)$$8);
                }
                $$4.add("properties", (JsonElement)$$6);
            }
            JsonArray $$10 = new JsonArray();
            for (BlockState blockState : $$5.m_61056_()) {
                JsonObject $$12 = new JsonObject();
                JsonObject $$13 = new JsonObject();
                for (Property<?> $$14 : $$5.m_61092_()) {
                    $$13.addProperty($$14.m_61708_(), Util.m_137453_($$14, blockState.m_61143_($$14)));
                }
                if ($$13.size() > 0) {
                    $$12.add("properties", (JsonElement)$$13);
                }
                $$12.addProperty("id", (Number)Block.m_49956_(blockState));
                if (blockState == $$2.m_49966_()) {
                    $$12.addProperty("default", Boolean.valueOf(true));
                }
                $$10.add((JsonElement)$$12);
            }
            $$4.add("states", (JsonElement)$$10);
            $$1.add($$3.toString(), (JsonElement)$$4);
        }
        Path $$15 = this.f_124034_.m_236034_(DataGenerator.Target.REPORTS).resolve("blocks.json");
        DataProvider.m_236072_(p_236197_, (JsonElement)$$1, $$15);
    }

    @Override
    public String m_6055_() {
        return "Block List";
    }
}

