/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.item.tool;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.block.generator.tileentity.TileEntityWindGenerator;
import ic2.core.block.kineticgenerator.tileentity.TileEntityWindKineticGenerator;
import ic2.core.event.WorldData;
import ic2.core.init.Localization;
import ic2.core.item.PriorityUsableItem;
import ic2.core.item.tool.ItemElectricTool;
import ic2.core.profile.NotClassic;
import ic2.core.util.StackUtil;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

@NotClassic
public class ItemWindMeter
extends ItemElectricTool
implements PriorityUsableItem {
    public ItemWindMeter(Item.Properties properties) {
        super(properties, 50);
        this.maxCharge = 10000;
        this.transferLimit = 100;
        this.tier = 1;
    }

    @Override
    public void m_7373_(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add((Component)Component.m_237115_((String)"ic2.wind_meter.tooltipA"));
        list.add((Component)Component.m_237115_((String)"ic2.wind_meter.tooltipB"));
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack itemStack, UseOnContext useOnContext) {
        Player player = useOnContext.m_43723_();
        Level level = useOnContext.m_43725_();
        BlockPos blockPos = useOnContext.m_8083_();
        if (level.f_46443_ || player == null || player.m_6144_()) {
            return InteractionResult.PASS;
        }
        if (!ElectricItem.manager.canUse(itemStack, this.operationEnergyCost)) {
            return InteractionResult.PASS;
        }
        BlockEntity blockEntity = level.m_7702_(blockPos);
        if (blockEntity instanceof TileEntityWindKineticGenerator) {
            TileEntityWindKineticGenerator tileEntityWindKineticGenerator = (TileEntityWindKineticGenerator)blockEntity;
            if (!tileEntityWindKineticGenerator.getActive()) {
                if (tileEntityWindKineticGenerator.hasRotor()) {
                    IC2.sideProxy.messagePlayer(player, Localization.translate("ic2.wind_meter.info.rotor.blocked"), new Object[0]);
                } else {
                    IC2.sideProxy.messagePlayer(player, Localization.translate("ic2.wind_meter.info.rotor.none"), new Object[0]);
                }
                return InteractionResult.FAIL;
            }
            this.consumeEnergy(itemStack, this.operationEnergyCost, (LivingEntity)player);
            if (tileEntityWindKineticGenerator.getObstructions() >= 0) {
                float f = ItemWindMeter.roundWind(tileEntityWindKineticGenerator.calcWindStrength());
                if (f <= 0.0f) {
                    IC2.sideProxy.messagePlayer(player, Localization.translate("ic2.wind_meter.info.obstructed", tileEntityWindKineticGenerator.getObstructions()), new Object[0]);
                } else {
                    IC2.sideProxy.messagePlayer(player, Localization.translate("ic2.wind_meter.info.effective", Float.valueOf(f)), new Object[0]);
                }
            } else {
                IC2.sideProxy.messagePlayer(player, Localization.translate("ic2.wind_meter.info.blocked", tileEntityWindKineticGenerator.getRotorDiameter() * 3), new Object[0]);
            }
            return InteractionResult.SUCCESS;
        }
        if (blockEntity instanceof TileEntityWindGenerator) {
            this.consumeEnergy(itemStack, this.operationEnergyCost, (LivingEntity)player);
            TileEntityWindGenerator tileEntityWindGenerator = (TileEntityWindGenerator)blockEntity;
            double d = (double)tileEntityWindGenerator.getObstructions() / 567.0;
            double d2 = d >= 1.0 ? 0.0 : WorldData.get((Level)level).windSim.getWindAt(blockPos.m_123342_()) * (1.0 - d);
            float f = ItemWindMeter.roundWind(d2);
            if (f <= 0.0f) {
                IC2.sideProxy.messagePlayer(player, Localization.translate("ic2.wind_meter.info.obstructed", tileEntityWindGenerator.getObstructions()), new Object[0]);
            } else {
                IC2.sideProxy.messagePlayer(player, Localization.translate("ic2.wind_meter.info.effective", Float.valueOf(f)), new Object[0]);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = StackUtil.get(player, interactionHand);
        if (!IC2.sideProxy.isSimulating()) {
            return new InteractionResultHolder(InteractionResult.PASS, (Object)itemStack);
        }
        if (!this.consumeEnergy(itemStack, this.operationEnergyCost, (LivingEntity)player)) {
            return new InteractionResultHolder(InteractionResult.PASS, (Object)itemStack);
        }
        double d = WorldData.get((Level)level).windSim.getWindAt(player.m_20186_());
        if (d < 0.0) {
            d = 0.0;
        }
        IC2.sideProxy.messagePlayer(player, Localization.translate("ic2.wind_meter.info", Float.valueOf(ItemWindMeter.roundWind(d))), new Object[0]);
        return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)itemStack);
    }

    private static float roundWind(double d) {
        return (float)Math.round(d * 100.0) / 100.0f;
    }
}

