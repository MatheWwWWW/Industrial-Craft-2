/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.IEnergyNet;
import ic2.api.energy.TransferStats;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.energy.tile.IMultiEnergySource;
import ic2.api.items.armor.IArmorModule;
import ic2.api.items.readers.IEUReader;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.item.INetworkItemBufferEvent;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.base.IC2Item;
import ic2.core.item.inv.container.EUReaderContainer;
import ic2.core.item.inv.inventory.EUReaderInventory;
import ic2.core.item.wearable.base.IBaseArmorModule;
import ic2.core.networking.buffers.data.NBTBuffer;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import java.text.DecimalFormat;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EUReaderTool
extends IC2Item
implements ISimpleItemModel,
IHasHeldGui,
INetworkItemBufferEvent<NBTBuffer>,
IEUReader,
IBaseArmorModule {
    public static final DecimalFormat EU_READER_FORMAT = new DecimalFormat("###,###.##");

    public EUReaderTool() {
        super("eu_reader");
    }

    @Override
    public boolean isEUReader(ItemStack stack) {
        return true;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addSimpleToolTip(this.translate("tooltip.item.ic2.eu_reader.active_mode", new Object[]{ReaderMode.byIndex(StackUtil.getNbtData(stack).m_128451_("mode")).getName()}).m_130940_(ChatFormatting.GRAY));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, "tooltip.item.ic2.eu_reader.switch_mode", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.BLOCK_CLICK, "tooltip.item.ic2.eu_reader.scan", new Object[0]));
        this.handleToolTip(stack, helper::addSimpleToolTip);
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.m_21120_(handIn);
        if (IC2.PLATFORM.isSimulating() && IC2.KEYBOARD.isModeSwitchKeyDown(playerIn)) {
            CompoundTag nbt = stack.m_41784_();
            ReaderMode mode = ReaderMode.byIndex(nbt.m_128451_("mode") + 1);
            nbt.m_128431_().clear();
            nbt.m_128405_("mode", mode.getIndex());
            playerIn.m_5661_((Component)this.translate("tooltip.item.ic2.eu_reader.active_mode", new Object[]{mode.getName()}), false);
        }
        return InteractionResultHolder.m_19090_((Object)stack);
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (IC2.PLATFORM.isSimulating()) {
            if (IC2.KEYBOARD.isModeSwitchKeyDown(context.m_43723_())) {
                return InteractionResult.FAIL;
            }
            ReaderMode mode = ReaderMode.byIndex(StackUtil.getNbtData(stack).m_128451_("mode"));
            if (mode == ReaderMode.POWER_FLOW) {
                IEnergyNet.GridTile tile = EnergyNet.INSTANCE.getTiles(context.m_43725_(), context.m_8083_());
                if (tile == null) {
                    return InteractionResult.FAIL;
                }
                TransferStats stat = EnergyNet.INSTANCE.getStats(tile.getMainTile());
                CompoundTag nbt = stack.m_41784_();
                BlockPos pos = tile.getPos();
                long time = context.m_43725_().m_46467_();
                if (nbt.m_128454_("pos") != pos.m_121878_() || !nbt.m_128461_("dim").equals(context.m_43725_().m_46472_().m_135782_().toString())) {
                    nbt.m_128356_("pos", context.m_8083_().m_121878_());
                    nbt.m_128359_("dim", context.m_43725_().m_46472_().m_135782_().toString());
                    context.m_43723_().m_5661_((Component)this.translate("tooltip.item.ic2.eu_reader.new_measurement"), true);
                } else {
                    long delta = Math.max(1L, time - nbt.m_128454_("last_time"));
                    double deltaSend = (double)(stat.getEnergyOut() - nbt.m_128454_("last_out")) / (double)delta;
                    double deltaReceived = (double)(stat.getEnergyIn() - nbt.m_128454_("last_in")) / (double)delta;
                    context.m_43723_().m_5661_((Component)this.translate("tooltip.item.ic2.eu_reader.measurement", new Object[]{EU_READER_FORMAT.format(deltaReceived), EU_READER_FORMAT.format(deltaSend), EU_READER_FORMAT.format(deltaReceived - deltaSend), stat.getEnergyLossOut(), stat.getEnergyLossIn(), delta}), false);
                }
                nbt.m_128356_("last_in", stat.getEnergyIn());
                nbt.m_128356_("last_out", stat.getEnergyOut());
                nbt.m_128356_("last_time", time);
                return InteractionResult.SUCCESS;
            }
            if (mode == ReaderMode.POWER_STATS) {
                IEnergySource source;
                IEnergyTile tile = EnergyNet.INSTANCE.getTile(context.m_43725_(), context.m_8083_());
                if (tile == null) {
                    return InteractionResult.FAIL;
                }
                if (tile instanceof IEnergySource) {
                    source = (IEnergySource)tile;
                    context.m_43723_().m_5661_((Component)this.translate("tooltip.item.ic2.eu_reader.source_buffer", new Object[]{source.getProvidedEnergy()}), false);
                }
                if (tile instanceof IEnergySink) {
                    IEnergySink sink = (IEnergySink)tile;
                    context.m_43723_().m_5661_((Component)this.translate("tooltip.item.ic2.eu_reader.sink_input", new Object[]{EnergyNet.INSTANCE.getPowerFromTier(sink.getSinkTier())}), false);
                }
                if (tile instanceof IEnergySource) {
                    source = (IEnergySource)tile;
                    context.m_43723_().m_5661_((Component)this.translate("tooltip.item.ic2.eu_reader.source_output", new Object[]{source.getMaxEnergyOutput()}), false);
                    if (tile instanceof IMultiEnergySource && ((IMultiEnergySource)tile).hasMultiplePackets()) {
                        context.m_43723_().m_5661_((Component)this.translate("tooltip.item.ic2.eu_reader.source_packets", new Object[]{((IMultiEnergySource)tile).getPacketCount()}), false);
                    }
                }
                if (tile instanceof IEnergyConductor) {
                    IEnergyConductor conductor = (IEnergyConductor)tile;
                    double loss = conductor.getConductionLoss();
                    context.m_43723_().m_5661_((Component)this.translate("tooltip.item.ic2.eu_reader.conductor_loss", new Object[]{loss, (int)Math.ceil(1.0 / loss)}), false);
                    context.m_43723_().m_5661_((Component)this.translate("tooltip.item.ic2.eu_reader.conductor_break", new Object[]{conductor.getConductorBreakdownEnergy() - 1}), false);
                }
                return InteractionResult.SUCCESS;
            }
            if (mode == ReaderMode.REAL_TIME_STAT) {
                IEnergyTile tile = EnergyNet.INSTANCE.getTile(context.m_43725_(), context.m_8083_());
                if (tile == null) {
                    return InteractionResult.FAIL;
                }
                CompoundTag nbt = stack.m_41784_();
                nbt.m_128356_("track_pos", context.m_8083_().m_121878_());
                IC2.PLATFORM.launchGui(context.m_43723_(), context.m_43724_(), context.m_43719_(), this.getInventory(context.m_43723_(), context.m_43724_(), stack));
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public void m_6883_(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (IC2.PLATFORM.isSimulating() && entityIn instanceof Player) {
            Player player = (Player)entityIn;
            CompoundTag nbt = StackUtil.getNbtData(stack);
            if (player.f_36096_ instanceof EUReaderContainer && nbt.m_128441_("track_pos") && ((EUReaderInventory)((EUReaderContainer)player.f_36096_).getHolder()).getID() == this.getGuiId(stack)) {
                EUReaderInventory.processEnergyNet(stack, player, BlockPos.m_122022_((long)nbt.m_128454_("track_pos")));
            }
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools").get("eu_reader");
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
        return new EUReaderInventory(player, this, stack, null).load(stack);
    }

    @Override
    public void onDataBufferReceived(ItemStack stack, Player player, String id, NBTBuffer buffer, Dist targetSide) {
        if (player.f_36096_ instanceof EUReaderContainer && id.equals("EU_INFO")) {
            ((EUReaderInventory)((EUReaderContainer)player.f_36096_).getHolder()).accept(buffer);
        }
    }

    @Override
    public IArmorModule.ModuleType getType(ItemStack stack) {
        return IArmorModule.ModuleType.HUD;
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.HEAD;
    }

    @Override
    public void onInstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        holder.addAddModifier(armor, IArmorModule.ArmorMod.EU_READER, 1);
    }

    @Override
    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        holder.removeAddModifier(armor, IArmorModule.ArmorMod.EU_READER, 1);
    }

    @Override
    public boolean handlePacket(Player player, ItemStack module, ItemStack armor, String id, INetworkDataBuffer buffer, Dist targetSide) {
        return false;
    }

    public static enum ReaderMode implements CollectionUtils.IIndexEnum
    {
        POWER_FLOW(0, "power_flow"),
        POWER_STATS(1, "power_stats"),
        REAL_TIME_STAT(2, "real_time_stats");

        public static final ReaderMode[] MODES;
        int index;
        String name;

        private ReaderMode(int index, String name) {
            this.index = index;
            this.name = name;
        }

        @Override
        public int getIndex() {
            return this.index;
        }

        public Component getName() {
            return Component.m_237115_((String)("tooltip.item.ic2.eu_reader." + this.name));
        }

        public static ReaderMode byIndex(int index) {
            return MODES[index % MODES.length];
        }

        static {
            MODES = (ReaderMode[])CollectionUtils.createSortedArray((CollectionUtils.IIndexEnum[])ReaderMode.values());
        }
    }
}

