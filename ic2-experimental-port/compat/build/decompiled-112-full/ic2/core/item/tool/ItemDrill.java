/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.FMLCommonHandler
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item.tool;

import ic2.api.item.IMiningDrill;
import ic2.core.IC2;
import ic2.core.IHitSoundOverride;
import ic2.core.item.tool.HarvestLevel;
import ic2.core.item.tool.ItemElectricTool;
import ic2.core.item.tool.ToolClass;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import java.util.EnumSet;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDrill
extends ItemElectricTool
implements IMiningDrill,
IHitSoundOverride {
    public ItemDrill(ItemName name, int operationEnergyCost, HarvestLevel harvestLevel, int maxCharge, int transferLimit, int tier, float efficiency) {
        super(name, operationEnergyCost, harvestLevel, EnumSet.of(ToolClass.Pickaxe, ToolClass.Shovel));
        this.maxCharge = maxCharge;
        this.transferLimit = transferLimit;
        this.tier = tier;
        this.field_77864_a = efficiency;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public String getHitSoundForBlock(EntityPlayerSP player, World world, BlockPos pos, ItemStack stack) {
        return null;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public String getBreakSoundForBlock(EntityPlayerSP player, World world, BlockPos pos, ItemStack stack) {
        if (player.field_71075_bZ.field_75098_d) {
            return null;
        }
        IBlockState state = world.func_180495_p(pos);
        float hardness = state.func_185887_b(world, pos);
        return hardness > 1.0f || hardness < 0.0f ? "Tools/Drill/DrillHard.ogg" : "Tools/Drill/DrillSoft.ogg";
    }

    @Override
    public float func_150893_a(ItemStack stack, IBlockState state) {
        float speed = super.func_150893_a(stack, state);
        EntityPlayer player = ItemDrill.getPlayerHoldingItem(stack);
        if (player != null) {
            if (player.func_70055_a(Material.field_151586_h) && !EnchantmentHelper.func_185287_i((EntityLivingBase)player)) {
                speed *= 5.0f;
            }
            if (!player.field_70122_E) {
                speed *= 5.0f;
            }
        }
        return speed;
    }

    private static EntityPlayer getPlayerHoldingItem(ItemStack stack) {
        if (IC2.platform.isRendering()) {
            EntityPlayer player = IC2.platform.getPlayerInstance();
            if (player != null && player.field_71071_by.func_70448_g() == stack) {
                return player;
            }
        } else {
            for (EntityPlayer player : FMLCommonHandler.instance().getMinecraftServerInstance().func_184103_al().func_181057_v()) {
                if (player.field_71071_by.func_70448_g() != stack) continue;
                return player;
            }
        }
        return null;
    }

    @Override
    public int energyUse(ItemStack stack, World world, BlockPos pos, IBlockState state) {
        if (stack.func_77973_b() == ItemName.drill.getInstance()) {
            return 6;
        }
        if (stack.func_77973_b() == ItemName.diamond_drill.getInstance()) {
            return 20;
        }
        if (stack.func_77973_b() == ItemName.iridium_drill.getInstance()) {
            return 200;
        }
        throw new IllegalArgumentException("Invalid drill: " + StackUtil.toStringSafe(stack));
    }

    @Override
    public int breakTime(ItemStack stack, World world, BlockPos pos, IBlockState state) {
        if (stack.func_77973_b() == ItemName.drill.getInstance()) {
            return 200;
        }
        if (stack.func_77973_b() == ItemName.diamond_drill.getInstance()) {
            return 50;
        }
        if (stack.func_77973_b() == ItemName.iridium_drill.getInstance()) {
            return 20;
        }
        throw new IllegalArgumentException("Invalid drill: " + StackUtil.toStringSafe(stack));
    }

    @Override
    public boolean breakBlock(ItemStack stack, World world, BlockPos pos, IBlockState state) {
        if (stack.func_77973_b() == ItemName.drill.getInstance()) {
            return this.tryUsePower(stack, 50.0);
        }
        if (stack.func_77973_b() == ItemName.diamond_drill.getInstance()) {
            return this.tryUsePower(stack, 80.0);
        }
        if (stack.func_77973_b() == ItemName.iridium_drill.getInstance()) {
            return this.tryUsePower(stack, 800.0);
        }
        throw new IllegalArgumentException("Invalid drill: " + StackUtil.toStringSafe(stack));
    }
}

