/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.item.tfbp;

import ic2.api.item.ITerraformingBP;
import ic2.core.IC2;
import ic2.core.item.tfbp.TerraformerBase;
import ic2.core.ref.Ic2Items;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Tfbp
extends Item
implements ITerraformingBP {
    private static final List<Tfbp> instances = new ArrayList<Tfbp>();
    public final double consume;
    public final int range;
    final TerraformerBase logic;

    public static void init() {
        for (Tfbp tfbp : instances) {
            if (tfbp.logic == null) continue;
            tfbp.logic.init();
        }
    }

    public Tfbp(Item.Properties properties, double d, int n, TerraformerBase terraformerBase) {
        super(properties);
        this.consume = d;
        this.range = n;
        this.logic = terraformerBase;
        instances.add(this);
    }

    @Override
    public double getConsume(ItemStack itemStack) {
        return this.consume;
    }

    @Override
    public int getRange(ItemStack itemStack) {
        return this.range;
    }

    @Override
    public boolean canInsert(ItemStack itemStack, Player player, Level level, BlockPos blockPos) {
        if (this == Ic2Items.CULTIVATION_TFBP && level.m_46472_() == Level.f_46430_) {
            IC2.achievements.issueAchievement(player, "terraformEndCultivation");
        }
        return true;
    }

    @Override
    public boolean terraform(ItemStack itemStack, Level level, BlockPos blockPos) {
        return this.logic != null && this.logic.terraform(level, blockPos);
    }
}

