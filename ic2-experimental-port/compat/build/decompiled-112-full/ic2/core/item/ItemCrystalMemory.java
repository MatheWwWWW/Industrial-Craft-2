/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.util.ITooltipFlag
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item;

import ic2.core.init.Localization;
import ic2.core.item.ItemIC2;
import ic2.core.profile.NotClassic;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import ic2.core.uu.UuIndex;
import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class ItemCrystalMemory
extends ItemIC2 {
    public ItemCrystalMemory() {
        super(ItemName.crystal_memory);
        this.func_77625_d(1);
    }

    public boolean isRepairable() {
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
        ItemStack recorded = this.readItemStack(stack);
        if (!StackUtil.isEmpty(recorded)) {
            tooltip.add(Localization.translate("ic2.item.CrystalMemory.tooltip.Item") + " " + recorded.func_82833_r());
            tooltip.add(Localization.translate("ic2.item.CrystalMemory.tooltip.UU-Matter") + " " + Util.toSiString(UuIndex.instance.getInBuckets(recorded), 4) + "B");
        } else {
            tooltip.add(Localization.translate("ic2.item.CrystalMemory.tooltip.Empty"));
        }
    }

    public ItemStack readItemStack(ItemStack stack) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        NBTTagCompound contentTag = nbt.func_74775_l("Pattern");
        ItemStack Item2 = new ItemStack(contentTag);
        return Item2;
    }

    public void writecontentsTag(ItemStack stack, ItemStack recorded) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        NBTTagCompound contentTag = new NBTTagCompound();
        recorded.func_77955_b(contentTag);
        nbt.func_74782_a("Pattern", (NBTBase)contentTag);
    }
}

