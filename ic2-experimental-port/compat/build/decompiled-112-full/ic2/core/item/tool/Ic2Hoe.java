/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemHoe
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item.tool;

import ic2.core.IC2;
import ic2.core.init.BlocksItems;
import ic2.core.init.Localization;
import ic2.core.item.ItemIC2;
import ic2.core.ref.IItemModelProvider;
import ic2.core.ref.ItemName;
import ic2.core.util.Util;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Ic2Hoe
extends ItemHoe
implements IItemModelProvider {
    private final Object repairMaterial = "ingotBronze";

    public Ic2Hoe(Item.ToolMaterial material) {
        super(material);
        this.func_77655_b(ItemName.bronze_hoe.name());
        this.func_77637_a(IC2.tabIC2);
        BlocksItems.registerItem(this, IC2.getIdentifier(ItemName.bronze_hoe.name()));
        ItemName.bronze_hoe.setInstance(this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerModels(ItemName name) {
        ItemIC2.registerModel((Item)this, 0, name, null);
    }

    public String func_77658_a() {
        return "ic2." + super.func_77658_a().substring(5);
    }

    public String func_77667_c(ItemStack stack) {
        return this.func_77658_a();
    }

    public String func_77657_g(ItemStack stack) {
        return this.func_77667_c(stack);
    }

    public String func_77653_i(ItemStack stack) {
        return Localization.translate(this.func_77667_c(stack));
    }

    public boolean func_82789_a(ItemStack stack1, ItemStack stack2) {
        return stack2 != null && Util.matchesOD(stack2, this.repairMaterial);
    }
}

