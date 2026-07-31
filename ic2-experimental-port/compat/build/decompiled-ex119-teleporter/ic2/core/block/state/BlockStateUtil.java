/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.block.state;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockStateUtil {
    public static String getVariantString(BlockState blockState) {
        ImmutableMap immutableMap = blockState.m_61148_();
        if (immutableMap.isEmpty()) {
            return "normal";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry entry : immutableMap.entrySet()) {
            Property property = (Property)entry.getKey();
            if (stringBuilder.length() > 0) {
                stringBuilder.append(',');
            }
            stringBuilder.append(property.m_61708_());
            stringBuilder.append('=');
            stringBuilder.append(property.m_6940_((Comparable)entry.getValue()));
        }
        return stringBuilder.toString();
    }

    public static BlockState getState(Block block, String string) {
        BlockState blockState = block.m_49966_();
        if (string.isEmpty() || string.equals("normal")) {
            return blockState;
        }
        int n = 0;
        while (n < string.length()) {
            int n2;
            int n3 = string.indexOf(44, n);
            if (n3 == -1) {
                n3 = string.length();
            }
            if ((n2 = string.indexOf(61, n)) == -1 || n2 >= n3) {
                return null;
            }
            String string2 = string.substring(n, n2);
            String string3 = string.substring(n2 + 1, n3);
            blockState = BlockStateUtil.applyProperty(blockState, string2, string3);
            n = n3 + 1;
        }
        return blockState;
    }

    private static <T extends Comparable<T>> BlockState applyProperty(BlockState blockState, String string, String string2) {
        Property property = null;
        for (Object object : blockState.m_61147_()) {
            if (!object.m_61708_().equals(string)) continue;
            property = object;
            break;
        }
        if (property == null) {
            return blockState;
        }
        for (Object object : property.m_6908_()) {
            if (!string2.equals(property.m_6940_((Comparable)object))) continue;
            return (BlockState)blockState.m_61124_(property, (Comparable)object);
        }
        return blockState;
    }
}

