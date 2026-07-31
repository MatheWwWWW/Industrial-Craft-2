/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Function
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.block.model.ModelResourceLocation
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemArmor$ArmorMaterial
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.EnumFacing
 *  net.minecraftforge.client.model.ModelLoader
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item.armor;

import com.google.common.base.Function;
import ic2.api.item.IMetalArmor;
import ic2.core.IC2;
import ic2.core.init.BlocksItems;
import ic2.core.init.Localization;
import ic2.core.item.ItemIC2;
import ic2.core.profile.Version;
import ic2.core.ref.IItemModelProvider;
import ic2.core.ref.ItemName;
import ic2.core.util.Util;
import java.util.IdentityHashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorIC2
extends ItemArmor
implements IItemModelProvider,
IMetalArmor {
    private final String armorName;
    private final Object repairMaterial;
    private Map<Capability<?>, Function<ItemStack, ?>> caps;

    public ItemArmorIC2(ItemName name, ItemArmor.ArmorMaterial armorMaterial, String armorName, EntityEquipmentSlot armorType, Object repairMaterial) {
        super(armorMaterial, -1, armorType);
        this.repairMaterial = repairMaterial;
        this.armorName = armorName;
        this.func_77656_e(armorMaterial.func_78046_a(armorType));
        if (name != null) {
            this.func_77655_b(name.name());
        }
        this.func_77637_a(IC2.tabIC2);
        if (name != null) {
            BlocksItems.registerItem(this, IC2.getIdentifier(name.name()));
            name.setInstance(this);
        }
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerModels(ItemName name) {
        ModelLoader.setCustomModelResourceLocation((Item)this, (int)0, (ModelResourceLocation)ItemIC2.getModelLocation(name, null));
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        char suffix1 = this.field_77881_a == EntityEquipmentSlot.LEGS ? (char)'2' : '1';
        String suffix2 = type != null && this.hasOverlayTexture() ? "_overlay" : "";
        return "ic2:textures/armor/" + this.armorName + '_' + suffix1 + suffix2 + ".png";
    }

    protected boolean hasOverlayTexture() {
        return false;
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

    protected boolean func_194125_a(CreativeTabs tab) {
        return this.isEnabled() && super.func_194125_a(tab);
    }

    protected boolean isEnabled() {
        return Version.shouldEnable(this.getClass());
    }

    @Override
    public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player) {
        return true;
    }

    public boolean func_82789_a(ItemStack toRepair, ItemStack repair) {
        return repair != null && Util.matchesOD(repair, this.repairMaterial);
    }

    public <T> void addCapability(Capability<T> cap, Function<ItemStack, T> lookup) {
        if (this.caps == null) {
            this.caps = new IdentityHashMap();
        }
        assert (!this.caps.containsKey(cap));
        this.caps.put(cap, lookup);
    }

    public ICapabilityProvider initCapabilities(final ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ICapabilityProvider(){

            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                return ItemArmorIC2.this.caps != null && ItemArmorIC2.this.caps.containsKey(capability);
            }

            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                return (T)(ItemArmorIC2.this.caps == null || !ItemArmorIC2.this.caps.containsKey(capability) ? null : ((Function)ItemArmorIC2.this.caps.get(capability)).apply((Object)stack));
            }
        };
    }
}

