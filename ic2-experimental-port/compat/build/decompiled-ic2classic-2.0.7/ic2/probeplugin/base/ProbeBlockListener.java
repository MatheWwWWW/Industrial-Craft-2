/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.Tools
 *  mcjty.theoneprobe.api.ElementAlignment
 *  mcjty.theoneprobe.api.IBlockDisplayOverride
 *  mcjty.theoneprobe.api.IElement
 *  mcjty.theoneprobe.api.IProbeConfig
 *  mcjty.theoneprobe.api.IProbeConfig$ConfigMode
 *  mcjty.theoneprobe.api.IProbeHitData
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.api.ProbeMode
 *  mcjty.theoneprobe.api.TextStyleClass
 *  mcjty.theoneprobe.apiimpl.ProbeHitData
 *  mcjty.theoneprobe.apiimpl.elements.ElementIcon
 *  mcjty.theoneprobe.apiimpl.providers.DefaultProbeInfoProvider
 *  mcjty.theoneprobe.apiimpl.styles.IconStyle
 *  mcjty.theoneprobe.config.Config
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.InfestedBlock
 *  net.minecraft.world.level.block.LiquidBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fml.ModContainer
 *  net.minecraftforge.fml.ModList
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.probeplugin.base;

import ic2.core.block.base.features.multiblock.IStructureListener;
import ic2.core.block.misc.TreeTapAndBucketBlock;
import ic2.core.platform.events.StructureManager;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.platform.registries.IC2Blocks;
import ic2.probeplugin.base.IProbeModifier;
import ic2.probeplugin.base.ProbePlugin;
import ic2.probeplugin.base.ProbeTileListener;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.styles.IC2Styles;
import java.util.List;
import mcjty.theoneprobe.Tools;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IBlockDisplayOverride;
import mcjty.theoneprobe.api.IElement;
import mcjty.theoneprobe.api.IProbeConfig;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import mcjty.theoneprobe.apiimpl.ProbeHitData;
import mcjty.theoneprobe.apiimpl.elements.ElementIcon;
import mcjty.theoneprobe.apiimpl.providers.DefaultProbeInfoProvider;
import mcjty.theoneprobe.apiimpl.styles.IconStyle;
import mcjty.theoneprobe.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

public class ProbeBlockListener
implements IBlockDisplayOverride {
    public boolean overrideStandardInfo(ProbeMode probe, IProbeInfo info, Player player, Level Level2, BlockState state, IProbeHitData hitData) {
        IStructureListener click;
        boolean override = false;
        IProbeModifier mod = ProbePlugin.getMod(state.m_60734_());
        if (mod != null) {
            if (mod.hasOverrideStandardInfo()) {
                mod.overrideStandardInfo(Config.getRealConfig(), probe, info, state, state.m_60734_(), Level2, hitData.getPos(), player, hitData);
                override = true;
            }
            mod.modifyData(probe, info, player, Level2, state, hitData);
        }
        if (Level2.m_7702_(hitData.getPos()) == null && (click = StructureManager.INSTANCE.getListener(Level2, hitData.getPos())) instanceof BlockEntity) {
            BlockEntity tile = (BlockEntity)click;
            BlockState newState = tile.m_58900_();
            DefaultProbeInfoProvider.showStandardBlockInfo((IProbeConfig)Config.getRealConfig(), (ProbeMode)probe, (IProbeInfo)info, (BlockState)newState, (Block)newState.m_60734_(), (Level)Level2, (BlockPos)hitData.getPos(), (Player)player, (IProbeHitData)new ProbeHitData(hitData.getPos(), hitData.getHitVec(), hitData.getSideHit(), new ItemStack((ItemLike)newState.m_60734_())));
            List<ITileInfoComponent<?>> list = ProbeTileListener.getData(tile.getClass());
            if (!list.isEmpty()) {
                PlayerHandler handler = PlayerHandler.getHandler(player);
                int m = list.size();
                for (int i = 0; i < m; ++i) {
                    ITileInfoComponent<?> comp = list.get(i);
                    if (!comp.isValid(probe, handler)) continue;
                    comp.addTileInfo(info, player, hitData.getSideHit(), tile);
                }
            }
            return true;
        }
        if (state.m_60734_() == IC2Blocks.TREETAP_AND_BUCKET) {
            int current = (Integer)state.m_61143_((Property)TreeTapAndBucketBlock.FILL_STAGE);
            info.element((IElement)new Panel(Panel.Type.VERTICAL).progress(current, 5, IC2Styles.SCRAP_BAR.copy().prefix("Progress: " + current + " / 5")));
        }
        return override;
    }

    public static void showStandardBlockInfo(IProbeConfig config, ProbeMode mode, IProbeInfo probeInfo, BlockState blockState, Block block, Level Level2, BlockPos pos, Player player, IProbeHitData data) {
        FluidState state;
        String modid = ((ModContainer)ModList.get().getModContainerById(ForgeRegistries.BLOCKS.getKey((Object)block).m_135827_()).get()).getModInfo().getDisplayName();
        ItemStack pickBlock = data.getPickBlock();
        if (block instanceof InfestedBlock && mode != ProbeMode.DEBUG && !Tools.show((ProbeMode)mode, (IProbeConfig.ConfigMode)config.getShowSilverfish())) {
            block = ((InfestedBlock)block).m_54192_();
            pickBlock = new ItemStack((ItemLike)block, 1);
        }
        if ((state = blockState.m_60819_()).m_76152_() != Fluids.f_76191_ && block instanceof LiquidBlock) {
            Fluid fluid = state.m_76152_();
            FluidStack fluidStack = new FluidStack(fluid, 1000);
            IProbeInfo horizontal = probeInfo.horizontal();
            horizontal.element((IElement)new ElementIcon(IClientFluidTypeExtensions.of((Fluid)fluid).getStillTexture(), -1, -1, 16, 16, new IconStyle().width(20).color(IClientFluidTypeExtensions.of((Fluid)fluid).getTintColor(fluidStack))));
            horizontal.vertical().text((Component)Component.m_237113_((String)(TextStyleClass.NAME + "{*" + fluidStack.getTranslationKey() + "*}"))).text((Component)Component.m_237113_((String)(TextStyleClass.MODNAME + modid)));
            return;
        }
        if (!pickBlock.m_41619_()) {
            if (Tools.show((ProbeMode)mode, (IProbeConfig.ConfigMode)config.getShowModName())) {
                probeInfo.horizontal().item(pickBlock).vertical().itemLabel(pickBlock).text((Component)Component.m_237113_((String)(TextStyleClass.MODNAME + modid)));
            } else {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).item(pickBlock).itemLabel(pickBlock);
            }
        } else if (Tools.show((ProbeMode)mode, (IProbeConfig.ConfigMode)config.getShowModName())) {
            probeInfo.vertical().text((Component)Component.m_237113_((String)(TextStyleClass.NAME + "{*" + block.m_7705_() + ".name*}"))).text((Component)Component.m_237113_((String)(TextStyleClass.MODNAME + modid)));
        } else {
            probeInfo.vertical().text((Component)Component.m_237113_((String)(TextStyleClass.NAME + "{*" + block.m_7705_() + ".name*}")));
        }
    }
}

