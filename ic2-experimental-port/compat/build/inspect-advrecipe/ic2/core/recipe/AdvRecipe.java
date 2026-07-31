/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  ic2.api.item.ElectricItem
 *  ic2.api.recipe.IRecipeInput
 *  ic2.compat.Ic2CraftingRecipe
 *  ic2.core.init.MainConfig
 *  ic2.core.recipe.v2.RecipeIo
 *  ic2.core.ref.Ic2RecipeSerializers
 *  ic2.core.util.Config
 *  ic2.core.util.ConfigUtil
 *  ic2.core.util.StackUtil
 *  ic2.core.util.Util
 *  net.minecraft.core.NonNullList
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.Container
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.RecipeSerializer
 *  net.minecraft.world.level.Level
 *  ru.mot.ic2exfidelity.integration.AdvRecipeIngredientCompat
 */
package ic2.core.recipe;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import ic2.api.item.ElectricItem;
import ic2.api.recipe.IRecipeInput;
import ic2.compat.Ic2CraftingRecipe;
import ic2.core.init.MainConfig;
import ic2.core.recipe.v2.RecipeIo;
import ic2.core.ref.Ic2RecipeSerializers;
import ic2.core.util.Config;
import ic2.core.util.ConfigUtil;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import ru.mot.ic2exfidelity.integration.AdvRecipeIngredientCompat;

public class AdvRecipe
implements Ic2CraftingRecipe {
    private static final boolean debug = Util.hasAssertions();
    public final ItemStack output;
    public final IRecipeInput[] input;
    public final IRecipeInput[] inputMirrored;
    public final int[] masks;
    public final int[] masksMirrored;
    public final int inputWidth;
    public final int inputHeight;
    public final boolean hidden;
    public final boolean consuming;
    private final ResourceLocation id;

    private static AdvRecipe create(ResourceLocation resourceLocation, int n, int n2, IRecipeInput[] iRecipeInputArray, ItemStack itemStack, boolean bl, boolean bl2) {
        int n3;
        int n4 = 0;
        ArrayList<IRecipeInput> arrayList = new ArrayList<IRecipeInput>();
        for (int i = 0; i < 3; ++i) {
            for (n3 = 0; n3 < 3; ++n3) {
                n4 <<= 1;
                if (n3 >= n || i >= n2 || iRecipeInputArray[n3 + i * n] == null) continue;
                n4 |= 1;
                arrayList.add(iRecipeInputArray[n3 + i * n]);
            }
        }
        IRecipeInput[] iRecipeInputArray2 = arrayList.toArray(new IRecipeInput[0]);
        n3 = 0;
        if (n != 1) {
            for (int i = 0; i < n2; ++i) {
                if (iRecipeInputArray[i * n] == iRecipeInputArray[n - 1 + i * n]) continue;
                n3 = 1;
                break;
            }
        }
        return new AdvRecipe(resourceLocation, n, n2, n3 != 0, n4, iRecipeInputArray2, itemStack, bl, bl2);
    }

    private AdvRecipe(ResourceLocation resourceLocation, int n, int n2, boolean bl, int n3, IRecipeInput[] iRecipeInputArray, ItemStack itemStack, boolean bl2, boolean bl3) {
        int n4;
        int n5;
        this.id = resourceLocation;
        this.inputWidth = n;
        this.inputHeight = n2;
        this.output = itemStack;
        this.consuming = bl2;
        this.hidden = bl3;
        this.input = iRecipeInputArray;
        if (!bl) {
            this.inputMirrored = null;
        } else {
            IRecipeInput[] iRecipeInputArray2 = new IRecipeInput[9];
            n5 = 0;
            for (int i = 0; i < 9; ++i) {
                if ((n3 & 1 << 8 - i) == 0) continue;
                iRecipeInputArray2[i] = iRecipeInputArray[n5];
                ++n5;
            }
            IRecipeInput iRecipeInput = iRecipeInputArray2[0];
            iRecipeInputArray2[0] = iRecipeInputArray2[2];
            iRecipeInputArray2[2] = iRecipeInput;
            iRecipeInput = iRecipeInputArray2[3];
            iRecipeInputArray2[3] = iRecipeInputArray2[5];
            iRecipeInputArray2[5] = iRecipeInput;
            iRecipeInput = iRecipeInputArray2[6];
            iRecipeInputArray2[6] = iRecipeInputArray2[8];
            iRecipeInputArray2[8] = iRecipeInput;
            this.inputMirrored = new IRecipeInput[iRecipeInputArray.length];
            n4 = 0;
            for (n5 = 0; n5 < 9; ++n5) {
                if (iRecipeInputArray2[n5] == null) continue;
                this.inputMirrored[n4] = iRecipeInputArray2[n5];
                ++n4;
            }
        }
        int n6 = -n + 4;
        int n7 = -n2 + 4;
        this.masks = new int[n6 * n7];
        this.masksMirrored = (int[])(!bl ? null : new int[this.masks.length]);
        for (n5 = 0; n5 < n7; ++n5) {
            n4 = n3 >>> n5 * 3;
            for (int i = 0; i < n6; ++i) {
                int n8;
                this.masks[i + n5 * n6] = n8 = n4 >>> i;
                if (!bl) continue;
                this.masksMirrored[i + n5 * n6] = n8 << 2 & 0x124 | n8 & 0x92 | n8 >>> 2 & 0x49;
            }
        }
    }

    public boolean matches(CraftingContainer craftingContainer, Level level) {
        return this.craft(craftingContainer) != StackUtil.emptyStack;
    }

    public ItemStack craft(CraftingContainer craftingContainer) {
        ItemStack itemStack;
        ItemStack itemStack2;
        int n = craftingContainer.m_6643_();
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            n2 <<= 1;
            if (StackUtil.isEmpty((ItemStack)craftingContainer.m_8020_(i))) continue;
            n2 |= 1;
        }
        if (n == 4) {
            n2 = (n2 & 0xC) << 5 | (n2 & 3) << 4;
        }
        if (AdvRecipe.checkMask(n2, this.masks) && !StackUtil.isEmpty((ItemStack)(itemStack2 = this.checkItems((Container)craftingContainer, this.input)))) {
            return itemStack2;
        }
        if (this.masksMirrored != null && AdvRecipe.checkMask(n2, this.masksMirrored) && !StackUtil.isEmpty((ItemStack)(itemStack = this.checkItems((Container)craftingContainer, this.inputMirrored)))) {
            return itemStack;
        }
        return StackUtil.emptyStack;
    }

    public ItemStack m_8043_() {
        return this.output;
    }

    public static boolean canShow(Object[] objectArray, ItemStack itemStack, boolean bl) {
        return !bl || !ConfigUtil.getBool((Config)MainConfig.get(), (String)"misc/hideSecretRecipes");
    }

    public boolean canShow() {
        return AdvRecipe.canShow(this.input, this.output, this.hidden);
    }

    private static boolean checkMask(int n, int[] nArray) {
        for (int n2 : nArray) {
            if (n != n2) continue;
            return true;
        }
        return false;
    }

    private ItemStack checkItems(Container container, IRecipeInput[] iRecipeInputArray) {
        int n = container.m_6643_();
        double d = 0.0;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            ItemStack itemStack = container.m_8020_(i);
            if (StackUtil.isEmpty((ItemStack)itemStack)) continue;
            if (iRecipeInputArray[n2++].matches(itemStack)) {
                d += ElectricItem.manager.getCharge(StackUtil.copyWithSize((ItemStack)itemStack, (int)1));
                continue;
            }
            return StackUtil.emptyStack;
        }
        ItemStack itemStack = this.output.m_41777_();
        ElectricItem.manager.charge(itemStack, d, Integer.MAX_VALUE, true, false);
        return itemStack;
    }

    public NonNullList<ItemStack> getRemainder(CraftingContainer craftingContainer) {
        return this.consuming ? NonNullList.m_122780_((int)craftingContainer.m_6643_(), (Object)StackUtil.emptyStack) : super.m_7457_((Container)craftingContainer);
    }

    public boolean m_8004_(int n, int n2) {
        return this.inputWidth <= n && this.inputHeight <= n2;
    }

    public int getIc2RecipeWidth() {
        return this.inputWidth;
    }

    public int getIc2RecipeHeight() {
        return this.inputHeight;
    }

    public boolean m_5598_() {
        return this.hidden;
    }

    public ResourceLocation m_6423_() {
        return this.id;
    }

    public RecipeSerializer<?> m_7707_() {
        return Ic2RecipeSerializers.SHAPED;
    }

    public NonNullList<Ingredient> m_7527_() {
        return AdvRecipeIngredientCompat.getIngredients((AdvRecipe)this);
    }

    public static final class Serializer
    implements RecipeSerializer<AdvRecipe> {
        public AdvRecipe read(ResourceLocation resourceLocation, JsonObject jsonObject) {
            Map<String, IRecipeInput> map = Serializer.readSymbols(GsonHelper.m_13930_((JsonObject)jsonObject, (String)"key"));
            String[] stringArray = Serializer.getPattern(GsonHelper.m_13933_((JsonObject)jsonObject, (String)"pattern"));
            int n = stringArray[0].length();
            int n2 = stringArray.length;
            IRecipeInput[] iRecipeInputArray = Serializer.createPatternMatrix(stringArray, map, n, n2);
            ItemStack itemStack = RecipeIo.parseOutput((JsonObject)GsonHelper.m_13930_((JsonObject)jsonObject, (String)"result"));
            boolean bl = GsonHelper.m_13855_((JsonObject)jsonObject, (String)"consuming", (boolean)false);
            boolean bl2 = GsonHelper.m_13855_((JsonObject)jsonObject, (String)"hidden", (boolean)false);
            return AdvRecipe.create(resourceLocation, n, n2, iRecipeInputArray, itemStack, bl, bl2);
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
}
