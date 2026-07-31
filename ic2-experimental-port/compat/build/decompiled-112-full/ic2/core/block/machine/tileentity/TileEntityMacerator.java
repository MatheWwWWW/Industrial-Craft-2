/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumParticleTypes
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.recipe.BasicMachineRecipeManager;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMacerator
extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack> {
    public static List<Map.Entry<ItemStack, ItemStack>> recipes = new Vector<Map.Entry<ItemStack, ItemStack>>();

    public TileEntityMacerator() {
        super(2, 300, 1);
        this.inputSlot = new InvSlotProcessableGeneric((IInventorySlotHolder<?>)this, "input", 1, Recipes.macerator);
    }

    public static void init() {
        Recipes.macerator = new BasicMachineRecipeManager();
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    protected void updateEntityClient() {
        super.updateEntityClient();
        World world = this.func_145831_w();
        if (this.getActive() && world.field_73012_v.nextInt(8) == 0) {
            for (int i = 0; i < 4; ++i) {
                double x = (double)this.field_174879_c.func_177958_n() + 0.5 + (double)world.field_73012_v.nextFloat() * 0.6 - 0.3;
                double y = (double)(this.field_174879_c.func_177956_o() + 1) + (double)world.field_73012_v.nextFloat() * 0.2 - 0.1;
                double z = (double)this.field_174879_c.func_177952_p() + 0.5 + (double)world.field_73012_v.nextFloat() * 0.6 - 0.3;
                world.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0, 0.0, 0.0, new int[0]);
            }
        }
    }

    @Override
    public String getStartSoundFile() {
        return "Machines/MaceratorOp.ogg";
    }

    @Override
    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
    }
}

