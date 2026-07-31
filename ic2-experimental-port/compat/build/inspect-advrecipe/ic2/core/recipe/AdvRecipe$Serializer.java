/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  ic2.api.recipe.IRecipeInput
 *  ic2.core.recipe.AdvRecipe
 *  ic2.core.recipe.v2.RecipeIo
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.RecipeSerializer
 */
package ic2.core.recipe;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import ic2.api.recipe.IRecipeInput;
import ic2.core.recipe.AdvRecipe;
import ic2.core.recipe.v2.RecipeIo;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

/*
 * Exception performing whole class analysis ignored.
 */
public static final class AdvRecipe.Serializer
implements RecipeSerializer<AdvRecipe> {
    public AdvRecipe read(ResourceLocation resourceLocation, JsonObject jsonObject) {
        Map<String, IRecipeInput> map = AdvRecipe.Serializer.readSymbols(GsonHelper.m_13930_((JsonObject)jsonObject, (String)"key"));
        String[] stringArray = AdvRecipe.Serializer.getPattern(GsonHelper.m_13933_((JsonObject)jsonObject, (String)"pattern"));
        int n = stringArray[0].length();
        int n2 = stringArray.length;
        IRecipeInput[] iRecipeInputArray = AdvRecipe.Serializer.createPatternMatrix(stringArray, map, n, n2);
        ItemStack itemStack = RecipeIo.parseOutput((JsonObject)GsonHelper.m_13930_((JsonObject)jsonObject, (String)"result"));
        boolean bl = GsonHelper.m_13855_((JsonObject)jsonObject, (String)"consuming", (boolean)false);
        boolean bl2 = GsonHelper.m_13855_((JsonObject)jsonObject, (String)"hidden", (boolean)false);
        return AdvRecipe.create((ResourceLocation)resourceLocation, (int)n, (int)n2, (IRecipeInput[])iRecipeInputArray, (ItemStack)itemStack, (boolean)bl, (boolean)bl2);
    }

    public AdvRecipe read(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
        IRecipeInput[] iRecipeInputArray = new IRecipeInput[friendlyByteBuf.m_130242_()];
        for (int i = 0; i < iRecipeInputArray.length; ++i) {
            iRecipeInputArray[i] = RecipeIo.readInput((FriendlyByteBuf)friendlyByteBuf);
        }
        return new AdvRecipe(resourceLocation, friendlyByteBuf.m_130242_(), friendlyByteBuf.m_130242_(), friendlyByteBuf.readBoolean(), friendlyByteBuf.readInt(), iRecipeInputArray, friendlyByteBuf.m_130267_(), friendlyByteBuf.readBoolean(), friendlyByteBuf.readBoolean());
    }

    public void write(FriendlyByteBuf friendlyByteBuf, AdvRecipe advRecipe) {
        friendlyByteBuf.m_130130_(advRecipe.input.length);
        for (IRecipeInput iRecipeInput : advRecipe.input) {
            RecipeIo.writeInput((FriendlyByteBuf)friendlyByteBuf, (IRecipeInput)iRecipeInput);
        }
        friendlyByteBuf.m_130130_(advRecipe.inputWidth);
        friendlyByteBuf.m_130130_(advRecipe.inputHeight);
        friendlyByteBuf.writeBoolean(advRecipe.inputMirrored != null);
        friendlyByteBuf.writeInt(advRecipe.masks[0]);
        friendlyByteBuf.m_130055_(advRecipe.output);
        friendlyByteBuf.writeBoolean(advRecipe.consuming);
        friendlyByteBuf.writeBoolean(advRecipe.hidden);
    }

    private static Map<String, IRecipeInput> readSymbols(JsonObject jsonObject) {
        HashMap hashMap = Maps.newHashMap();
        for (Map.Entry entry : jsonObject.entrySet()) {
            if (((String)entry.getKey()).length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }
            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }
            hashMap.put((String)entry.getKey(), RecipeIo.parseInput((JsonElement)((JsonElement)entry.getValue())));
        }
        return hashMap;
    }

    private static String[] getPattern(JsonArray jsonArray) {
        String[] stringArray = new String[jsonArray.size()];
        if (stringArray.length > 3) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
        }
        if (stringArray.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        }
        for (int i = 0; i < stringArray.length; ++i) {
            String string = GsonHelper.m_13805_((JsonElement)jsonArray.get(i), (String)("pattern[" + i + "]"));
            if (string.length() > 3) {
                throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
            }
            if (i > 0 && stringArray[0].length() != string.length()) {
                throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
            }
            stringArray[i] = string;
        }
        return stringArray;
    }

    private static IRecipeInput[] createPatternMatrix(String[] stringArray, Map<String, IRecipeInput> map, int n, int n2) {
        IRecipeInput[] iRecipeInputArray = new IRecipeInput[n * n2];
        HashSet<String> hashSet = new HashSet<String>(map.keySet());
        for (int i = 0; i < stringArray.length; ++i) {
            for (int j = 0; j < stringArray[i].length(); ++j) {
                String string = stringArray[i].substring(j, j + 1);
                if (string.equals(" ")) continue;
                IRecipeInput iRecipeInput = map.get(string);
                if (iRecipeInput == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + string + "' but it's not defined in the key");
                }
                hashSet.remove(string);
                iRecipeInputArray[j + n * i] = iRecipeInput;
            }
        }
        if (!hashSet.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + hashSet);
        }
        return iRecipeInputArray;
    }
}
