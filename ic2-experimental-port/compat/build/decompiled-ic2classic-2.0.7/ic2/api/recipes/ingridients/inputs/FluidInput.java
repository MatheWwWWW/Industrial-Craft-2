/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidUtil
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.api.recipes.ingridients.inputs;

import com.google.gson.JsonObject;
import ic2.api.core.IC2Classic;
import ic2.api.recipes.ingridients.inputs.IInput;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidInput
implements IInput {
    Fluid fluid;
    int fluidAmount;
    int size;

    public FluidInput(JsonObject obj) {
        this.fluid = (Fluid)ForgeRegistries.FLUIDS.getValue(ResourceLocation.m_135820_((String)obj.get("fluid").getAsString()));
        this.fluidAmount = obj.get("amount").getAsInt();
        this.size = obj.get("size").getAsInt();
    }

    public FluidInput(FriendlyByteBuf buffer) {
        this.fluid = (Fluid)buffer.readRegistryIdUnsafe(ForgeRegistries.FLUIDS);
        this.fluidAmount = buffer.readInt();
        this.size = buffer.readByte();
    }

    public FluidInput(FluidStack stack) {
        this(stack.getFluid(), stack.getAmount(), 1);
    }

    public FluidInput(Fluid fluid) {
        this(fluid, 1);
    }

    public FluidInput(Fluid fluid, int size) {
        this(fluid, 1000, size);
    }

    public FluidInput(Fluid fluid, int fluidAmount, int size) {
        this.fluid = fluid;
        this.fluidAmount = fluidAmount;
        this.size = size;
    }

    @Override
    public List<ItemStack> getComponents() {
        ObjectArrayList stacks = new ObjectArrayList();
        for (ItemStack stack : IC2Classic.getHelper().getFluidContainers(this.fluid)) {
            stacks.add((Object)stack.m_41777_());
        }
        return ObjectLists.unmodifiable((ObjectList)stacks);
    }

    @Override
    public int getInputSize() {
        return this.size;
    }

    @Override
    public boolean matches(ItemStack stack) {
        if (stack.m_41619_()) {
            return false;
        }
        FluidStack fluidStack = FluidUtil.getFluidContained((ItemStack)stack).orElse(FluidStack.EMPTY);
        return !fluidStack.isEmpty() && fluidStack.getFluid() == this.fluid && fluidStack.getAmount() >= this.fluidAmount;
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.writeRegistryIdUnsafe(ForgeRegistries.FLUIDS, (Object)this.fluid);
        buffer.writeInt(this.fluidAmount);
        buffer.writeByte(this.size);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("fluid", ForgeRegistries.FLUIDS.getKey((Object)this.fluid).toString());
        obj.addProperty("amount", (Number)this.fluidAmount);
        obj.addProperty("size", (Number)this.size);
        return obj;
    }
}

