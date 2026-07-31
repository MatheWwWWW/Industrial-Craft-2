/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.CompoundText
 *  mcjty.theoneprobe.api.ElementAlignment
 *  mcjty.theoneprobe.api.IElement
 *  mcjty.theoneprobe.api.ILayoutStyle
 *  mcjty.theoneprobe.api.IProbeConfig
 *  mcjty.theoneprobe.api.IProbeHitData
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.api.ProbeMode
 *  mcjty.theoneprobe.api.TextStyleClass
 *  mcjty.theoneprobe.apiimpl.elements.ElementItemStack
 *  mcjty.theoneprobe.apiimpl.elements.ElementProgress
 *  mcjty.theoneprobe.apiimpl.styles.ItemStyle
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.fml.ModContainer
 *  net.minecraftforge.fml.ModList
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.probeplugin.info;

import ic2.api.crops.ICrop;
import ic2.api.crops.ICropRegistry;
import ic2.api.crops.ICropTile;
import ic2.core.block.crops.CropRegistry;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.math.ColorUtils;
import ic2.probeplugin.base.IProbeModifier;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.override.styles.ILayoutStyleBuilder;
import ic2.probeplugin.styles.IC2Styles;
import mcjty.theoneprobe.api.CompoundText;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IElement;
import mcjty.theoneprobe.api.ILayoutStyle;
import mcjty.theoneprobe.api.IProbeConfig;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import mcjty.theoneprobe.apiimpl.elements.ElementItemStack;
import mcjty.theoneprobe.apiimpl.elements.ElementProgress;
import mcjty.theoneprobe.apiimpl.styles.ItemStyle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

public class CropComponent
implements ITileInfoComponent<ICropTile>,
IProbeModifier {
    public static final CropComponent INSTANCE = new CropComponent();
    public static final int COLOR_GROWTH = ColorUtils.rgb(0, 170, 0);
    public static final int COLOR_WATER = ColorUtils.rgb(93, 105, 255);
    public static final int COLOR_NUTRIENTS = ColorUtils.rgb(0, 255, 5);
    public static final int COLOR_LIGHT = ColorUtils.rgb(255, 255, 85);
    public static final int COLOR_ENV = ColorUtils.rgb(85, 255, 255);
    public static final int COLOR_WEEDEX = ColorUtils.rgb(255, 85, 255);
    public static final int COLOR_FERTILIZER = ColorUtils.rgb(86, 54, 36);
    public static final int COLOR_STAT_GROWTH = ColorUtils.rgb(0, 170, 170);
    public static final int COLOR_STAT_GAIN = ColorUtils.rgb(170, 0, 170);
    public static final int COLOR_STAT_RESIST = ColorUtils.rgb(255, 170, 0);

    @Override
    public boolean hasValidReader(PlayerHandler handler) {
        return handler.hasCropScanner();
    }

    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, ICropTile tile) {
        Panel crops = new Panel(ILayoutStyleBuilder.spacing(3), Panel.Type.VERTICAL);
        ILayoutStyle barContainerPaddableLayoutStyle = ILayoutStyleBuilder.spacing(1).alignment(ElementAlignment.ALIGN_CENTER);
        ICrop crop = tile.getCrop();
        if (crop != null) {
            int maxSize = crop.getGrowthSteps();
            int currentSize = tile.getGrowthStage();
            if (tile.getScanLevel() >= 4 || currentSize >= maxSize) {
                Panel growth = new Panel(barContainerPaddableLayoutStyle, Panel.Type.VERTICAL);
                Panel growth_bars = new Panel(IC2Styles.BARS_STYLE, Panel.Type.VERTICAL);
                growth.text("ic2.probe.crop.growth");
                growth.element(growth_bars);
                crops.element(growth);
                if (tile.getGrowthStage() < crop.getGrowthSteps()) {
                    growth_bars.element((IElement)CropComponent.createCropBar(currentSize, maxSize, "ic2.probe.crop.info.stage", COLOR_GROWTH));
                    growth_bars.element((IElement)CropComponent.createCropBar(tile.getGrowthPoints(), crop.getGrowthDuration(tile), "ic2.probe.crop.info.points", COLOR_GROWTH));
                    growth.padding(2, 1);
                    if (crop.canGrow(tile) && crop.getCropType().isCompatible(tile.isWaterLogged())) {
                        growth.text("ic2.probe.crop.grow.rate", tile.calculateGrowthSpeed());
                    } else {
                        growth.text((Component)this.translate("ic2.probe.crop.grow.not").m_130948_(ColorUtils.toColor(ColorUtils.RED)));
                    }
                } else {
                    growth_bars.element((IElement)CropComponent.createCropBarDisplay(currentSize, maxSize, "ic2.probe.crop.info.stage_done", COLOR_GROWTH));
                }
                if (tile.getScanLevel() >= 4) {
                    Panel stats = new Panel(barContainerPaddableLayoutStyle, Panel.Type.VERTICAL);
                    Panel stats_bars = new Panel(IC2Styles.BARS_STYLE, Panel.Type.VERTICAL);
                    stats.text("ic2.probe.crop.stats");
                    stats_bars.element((IElement)CropComponent.createCropBar(tile.getGrowthStat(), 31, "ic2.probe.crop.info.growth", COLOR_STAT_GROWTH));
                    stats_bars.element((IElement)CropComponent.createCropBar(tile.getGainStat(), 31, "ic2.probe.crop.info.gain", COLOR_STAT_GAIN));
                    stats_bars.element((IElement)CropComponent.createCropBar(tile.getResistanceStat(), 31, "ic2.probe.crop.info.resistance", COLOR_STAT_RESIST));
                    stats.element(stats_bars);
                    crops.element(stats);
                }
            } else {
                crops.element((IElement)CropComponent.createCropBar(tile.getScanLevel(), 4, "ic2.probe.crop.info.scan", COLOR_GROWTH));
            }
        }
        Panel storage = new Panel(barContainerPaddableLayoutStyle, Panel.Type.VERTICAL);
        Panel storage_bars = new Panel(IC2Styles.BARS_STYLE, Panel.Type.VERTICAL);
        storage.text("ic2.probe.crop.storage");
        storage_bars.element((IElement)CropComponent.createCropBar(tile.getFertilizerStorage(), 300, "ic2.probe.crop.info.fertilizer", COLOR_FERTILIZER));
        storage_bars.element((IElement)CropComponent.createCropBar(tile.getWaterStorage(), 200, "ic2.probe.crop.info.water", COLOR_WATER));
        storage_bars.element((IElement)CropComponent.createCropBar(tile.getWeedExStorage(), 150, "ic2.probe.crop.info.weedex", COLOR_WEEDEX));
        storage.element(storage_bars);
        crops.element(storage);
        Panel environment = new Panel(barContainerPaddableLayoutStyle, Panel.Type.VERTICAL);
        Panel environment_bars = new Panel(IC2Styles.BARS_STYLE, Panel.Type.VERTICAL);
        environment.text("ic2.probe.crop.env");
        environment_bars.element((IElement)CropComponent.createCropBar(tile.getNutrients(), 20, "ic2.probe.crop.info.nutrients", COLOR_NUTRIENTS));
        environment_bars.element((IElement)CropComponent.createCropBar(tile.getHumidity(), 20, "ic2.probe.crop.info.humidity", COLOR_WATER));
        environment_bars.element((IElement)CropComponent.createCropBar(tile.getEnvironmentQuality(), 10, "ic2.probe.crop.info.env", COLOR_ENV));
        environment_bars.element((IElement)CropComponent.createCropBar(tile.getLightLevel(), 15, "ic2.probe.crop.info.light", COLOR_LIGHT));
        environment.element(environment_bars);
        crops.element(environment);
        info.getElements().add(1, crops);
    }

    @Override
    public boolean hasOverrideStandardInfo() {
        return true;
    }

    @Override
    public void overrideStandardInfo(IProbeConfig config, ProbeMode mode, IProbeInfo probeInfo, BlockState blockState, Block block, Level Level2, BlockPos pos, Player player, IProbeHitData data) {
        Panel header = new Panel(IC2Styles.HEADER_STYLE, Panel.Type.HORIZONTAL);
        ICropTile cropTile = (ICropTile)Level2.m_7702_(data.getPos());
        ItemStack icon = new ItemStack((ItemLike)IC2Items.CROP_STICKS);
        ICrop crop = cropTile.getCrop();
        Component name = data.getPickBlock().m_41786_();
        Component discovered = null;
        if (crop != null) {
            int maxSize = crop.getGrowthSteps();
            int currentSize = cropTile.getGrowthStage();
            if (cropTile.getScanLevel() >= 1 || currentSize >= maxSize || crop == ICropRegistry.WEED || crop == CropRegistry.SEA_WEED) {
                name = crop.getName();
                icon = !crop.getDisplayItem().m_41619_() ? crop.getDisplayItem() : new ItemStack((ItemLike)IC2Items.CROP_SEED);
                discovered = crop.discoveredBy();
            } else {
                icon = new ItemStack((ItemLike)IC2Items.CROP_SEED);
                name = this.translate("info.crop.ic2.data.unknown");
            }
        }
        Panel right = new Panel(ILayoutStyleBuilder.create().spacing(1), Panel.Type.VERTICAL);
        header.element((IElement)new ElementItemStack(icon, new ItemStyle().height(20).width(20)));
        right.text(CompoundText.create().name(name));
        if (discovered != null) {
            right.text(CompoundText.create().text("\u00a7oby ").text(discovered).text("\u00a7r").style(TextStyleClass.INFO));
        }
        right.text(CompoundText.create().style(TextStyleClass.MODNAME).text(((ModContainer)ModList.get().getModContainerById(ForgeRegistries.BLOCKS.getKey((Object)block).m_135827_()).get()).getModInfo().getDisplayName()));
        header.element(right);
        probeInfo.element((IElement)header);
    }

    public static ElementProgress createCropBar(int current, int max, String type, int color) {
        return new ElementProgress((long)current, (long)max, IC2Styles.crops(type, color, current, max));
    }

    public static ElementProgress createCropBarDisplay(int current, int max, String display, int color) {
        return new ElementProgress((long)current, (long)max, IC2Styles.crops(display, color, new Object[0]));
    }
}

