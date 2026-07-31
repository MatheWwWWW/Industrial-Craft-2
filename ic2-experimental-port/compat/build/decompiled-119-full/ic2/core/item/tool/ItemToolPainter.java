/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Registry
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.animal.Sheep
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.BannerBlock
 *  net.minecraft.world.level.block.BedBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.ConcretePowderBlock
 *  net.minecraft.world.level.block.GlazedTerracottaBlock
 *  net.minecraft.world.level.block.ShulkerBoxBlock
 *  net.minecraft.world.level.block.StainedGlassBlock
 *  net.minecraft.world.level.block.StainedGlassPaneBlock
 *  net.minecraft.world.level.block.WallBannerBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.item.tool;

import ic2.api.item.IBoxable;
import ic2.core.IC2;
import ic2.core.item.tool.ItemToolCrafting;
import ic2.core.ref.Ic2Items;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.Ic2Color;
import ic2.core.util.StackUtil;
import ic2.core.util.VanillaColorBlockId;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.GlazedTerracottaBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.block.WallBannerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class ItemToolPainter
extends ItemToolCrafting
implements IBoxable {
    Ic2Color color = null;
    private static final int maxDamage = 32;

    public ItemToolPainter(Item.Properties properties, Ic2Color ic2Color) {
        super(properties);
        this.color = ic2Color;
    }

    public InteractionResult m_6225_(UseOnContext useOnContext) {
        if (this.color == null) {
            return InteractionResult.PASS;
        }
        ItemStack itemStack = useOnContext.m_43722_();
        Level level = useOnContext.m_43725_();
        BlockPos blockPos = useOnContext.m_8083_();
        Player player = useOnContext.m_43723_();
        InteractionHand interactionHand = useOnContext.m_43724_();
        if (!(itemStack.m_41720_() instanceof ItemToolPainter)) {
            return InteractionResult.PASS;
        }
        BlockState blockState = level.m_8055_(blockPos);
        Block block = blockState.m_60734_();
        if (this.colorBlock(level, blockPos, block, blockState, this.color)) {
            this.damagePainter(itemStack, player, interactionHand, this.color);
            if (level.f_46443_ && player != null) {
                player.m_5496_(Ic2SoundEvents.ITEM_PAINTER_USE, 1.0f, 1.0f);
            }
            return level.f_46443_ ? InteractionResult.PASS : InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private boolean colorBlock(Level level, BlockPos blockPos, Block block, BlockState blockState, Ic2Color ic2Color) {
        Property property2;
        DyeColor dyeColor = ic2Color.dyeColor;
        for (Property property2 : blockState.m_61148_().keySet()) {
            if (property2.m_61709_() != DyeColor.class) continue;
            Property property3 = property2;
            DyeColor dyeColor2 = (DyeColor)blockState.m_61143_(property3);
            if (dyeColor2 == dyeColor || !property3.m_6908_().contains(dyeColor)) {
                return false;
            }
            level.m_46597_(blockPos, (BlockState)blockState.m_61124_(property3, (Comparable)dyeColor));
            return true;
        }
        if (!ItemToolPainter.canColor(block, ic2Color.dyeColor)) {
            return false;
        }
        List list = block.m_49966_().m_204343_().toList();
        if (list.contains(BlockTags.f_13089_)) {
            level.m_46597_(blockPos, ItemToolPainter.getColorBlockState(ic2Color.dyeColor, VanillaColorBlockId.WOOL));
            return true;
        }
        if (block instanceof StainedGlassBlock || block.m_49966_().m_60713_(Blocks.f_50058_)) {
            level.m_46597_(blockPos, ItemToolPainter.getColorBlockState(ic2Color.dyeColor, VanillaColorBlockId.STAINED_GLASS));
            return true;
        }
        if (block instanceof StainedGlassPaneBlock || block.m_49966_().m_60713_(Blocks.f_50185_)) {
            level.m_46597_(blockPos, ItemToolPainter.getBlockStateWithProperties(ic2Color.dyeColor, VanillaColorBlockId.STAINED_GLASS_PANE, blockState));
            return true;
        }
        if (list.contains(BlockTags.f_13038_)) {
            property2 = (BedBlock)block;
            BlockPos blockPos2 = blockPos.m_121945_(BedBlock.m_49557_((BlockState)blockState));
            BlockState blockState2 = level.m_8055_(blockPos2);
            if (blockState2.m_60713_((Block)property2)) {
                level.m_7731_(blockPos, Blocks.f_50016_.m_49966_(), 48);
                level.m_7731_(blockPos2, Blocks.f_50016_.m_49966_(), 48);
                level.m_46597_(blockPos, ItemToolPainter.getBlockStateWithProperties(ic2Color.dyeColor, VanillaColorBlockId.BED, blockState));
                level.m_46597_(blockPos2, ItemToolPainter.getBlockStateWithProperties(ic2Color.dyeColor, VanillaColorBlockId.BED, blockState2));
            }
            return true;
        }
        if (list.contains(BlockTags.f_144265_)) {
            level.m_46597_(blockPos, ItemToolPainter.getBlockStateWithProperties(ic2Color.dyeColor, VanillaColorBlockId.CANDLE, blockState));
            return true;
        }
        if (block instanceof BannerBlock) {
            level.m_46597_(blockPos, ItemToolPainter.getBlockStateWithProperties(ic2Color.dyeColor, VanillaColorBlockId.BANNER, blockState));
            return true;
        }
        if (block instanceof WallBannerBlock) {
            level.m_46597_(blockPos, ItemToolPainter.getBlockStateWithProperties(ic2Color.dyeColor, VanillaColorBlockId.WALL_BANNER, blockState));
            return true;
        }
        if (list.contains(BlockTags.f_198156_)) {
            level.m_46597_(blockPos, ItemToolPainter.getColorBlockState(ic2Color.dyeColor, VanillaColorBlockId.TERRACOTTA));
            return true;
        }
        if (block instanceof GlazedTerracottaBlock) {
            level.m_46597_(blockPos, ItemToolPainter.getBlockStateWithProperties(ic2Color.dyeColor, VanillaColorBlockId.GLAZED_TERRACOTTA, blockState));
            return true;
        }
        if (block instanceof ConcretePowderBlock) {
            level.m_46597_(blockPos, ItemToolPainter.getColorBlockState(ic2Color.dyeColor, VanillaColorBlockId.CONCRETE_POWDER));
            return true;
        }
        if (list.contains(BlockTags.f_215838_)) {
            level.m_46597_(blockPos, ItemToolPainter.getColorBlockState(ic2Color.dyeColor, VanillaColorBlockId.CARPET));
            return true;
        }
        if (list.contains(BlockTags.f_13083_)) {
            property2 = level.m_7702_(blockPos);
            if (property2 == null) {
                return false;
            }
            CompoundTag compoundTag = property2.m_187481_();
            BlockState blockState3 = ShulkerBoxBlock.m_56190_((DyeColor)ic2Color.dyeColor).m_152465_(blockState);
            level.m_46597_(blockPos, blockState3);
            BlockEntity blockEntity = BlockEntity.m_155241_((BlockPos)blockPos, (BlockState)blockState3, (CompoundTag)compoundTag);
            level.m_151523_(blockEntity);
            return true;
        }
        property2 = Registry.f_122824_.m_7981_((Object)block);
        if (property2.m_135827_().equals("minecraft") && property2.m_135815_().contains("concrete")) {
            level.m_46597_(blockPos, ItemToolPainter.getColorBlockState(ic2Color.dyeColor, VanillaColorBlockId.CONCRETE));
            return true;
        }
        return false;
    }

    public static boolean canColor(Block block, DyeColor dyeColor) {
        ResourceLocation resourceLocation = Registry.f_122824_.m_7981_((Object)block);
        return !resourceLocation.m_135815_().contains(dyeColor.m_41065_());
    }

    public static BlockState getColorBlockState(DyeColor dyeColor, VanillaColorBlockId vanillaColorBlockId) {
        ResourceLocation resourceLocation = new ResourceLocation("minecraft", dyeColor.m_41065_() + "_" + vanillaColorBlockId.id);
        return ((Block)Registry.f_122824_.m_7745_(resourceLocation)).m_49966_();
    }

    public static BlockState getBlockStateWithProperties(DyeColor dyeColor, VanillaColorBlockId vanillaColorBlockId, BlockState blockState) {
        ResourceLocation resourceLocation = new ResourceLocation("minecraft", dyeColor.m_41065_() + "_" + vanillaColorBlockId.id);
        return ((Block)Registry.f_122824_.m_7745_(resourceLocation)).m_152465_(blockState);
    }

    public InteractionResult m_6880_(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        Sheep sheep;
        if (this.color == null) {
            return InteractionResult.PASS;
        }
        if (livingEntity instanceof Sheep && (sheep = (Sheep)livingEntity).m_29874_() != this.color.dyeColor) {
            sheep.m_29855_(this.color.dyeColor);
            this.damagePainter(itemStack, player, player.m_7655_(), this.color);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = StackUtil.get(player, interactionHand);
        if (!level.f_46443_ && IC2.keyboard.isModeSwitchKeyDown(player)) {
            CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
            boolean bl = !compoundTag.m_128471_("autoRefill");
            compoundTag.m_128379_("autoRefill", bl);
            if (bl) {
                IC2.sideProxy.messagePlayer(player, "Painter automatic refill mode enabled", new Object[0]);
            } else {
                IC2.sideProxy.messagePlayer(player, "Painter automatic refill mode disabled", new Object[0]);
            }
            return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)itemStack);
        }
        return new InteractionResultHolder(InteractionResult.PASS, (Object)itemStack);
    }

    public void damagePainter(ItemStack itemStack, Player player2, InteractionHand interactionHand, Ic2Color ic2Color) {
        assert (ic2Color != null);
        if (itemStack.m_41773_() >= itemStack.m_41776_()) {
            CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
            if (compoundTag.m_128471_("autoRefill") && StackUtil.consumeFromPlayerInventory(player2, StackUtil.sameItem(Ic2Items.PAINTER), 1, false)) {
                player2.m_21008_(interactionHand, new ItemStack((ItemLike)itemStack.m_41720_(), 1));
            } else {
                player2.m_21008_(interactionHand, new ItemStack((ItemLike)Ic2Items.PAINTER, 1));
            }
        } else {
            itemStack.m_41622_(1, (LivingEntity)player2, player -> player.m_21190_(interactionHand));
        }
    }

    @Override
    public boolean canBeStoredInToolbox(ItemStack itemStack) {
        return super.canBeStoredInToolbox(itemStack);
    }
}

