/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentCategory
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool.electric;

import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IDamagelessElectricItem;
import ic2.api.items.electric.IElectricEnchantable;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.block.resource.RubberwoodLogBlock;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.tool.TreeTapTool;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import java.util.ArrayList;
import java.util.function.Consumer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ElectricTreeTap
extends TreeTapTool
implements IDamagelessElectricItem,
IElectricEnchantable {
    protected int energyCost = 50;

    public ElectricTreeTap() {
        super("electric_tree_tap", new PropertiesBuilder().group(IC2.IC2_MAIN_GROUP).setNoRepair().maxStackSize(1));
    }

    protected ElectricTreeTap(String itemName) {
        super(itemName, new PropertiesBuilder().group(IC2.IC2_MAIN_GROUP).setNoRepair().maxStackSize(1));
    }

    @Override
    public InteractionResult m_6225_(UseOnContext context) {
        Level world = context.m_43725_();
        BlockPos pos = context.m_8083_();
        Player player = context.m_43723_();
        ItemStack stack = player.m_21120_(context.m_43724_());
        if (world.m_8055_(pos).m_60734_() instanceof RubberwoodLogBlock) {
            if (!ElectricItem.MANAGER.canUse(stack, this.energyCost)) {
                return InteractionResult.PASS;
            }
            boolean server = IC2.PLATFORM.isSimulating();
            ArrayList<ItemStack> list = new ArrayList<ItemStack>();
            boolean extractedRubber = TreeTapTool.extractRubber(context, this.canInventoryImport(stack) ? list : null);
            if (extractedRubber && server) {
                IC2.AUDIO.playSound(context.m_43723_(), IC2Sounds.TREE_TAP, AudioManager.SoundType.ITEM);
                ElectricItem.MANAGER.use(stack, this.energyCost, (LivingEntity)player);
                for (ItemStack item : list) {
                    StackUtil.addOrDrop(player, item);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return 10000;
    }

    @Override
    public int getTier(ItemStack stack) {
        return 1;
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return 100;
    }

    @Override
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        if (this.canInventoryImport(stack)) {
            helper.addSimpleToolTip("tooltip.item.ic2.electric_tree_tap.inv_import", new Object[0]);
        }
    }

    public int m_142158_(ItemStack stack) {
        return IC2ElectricItem.getElectricWidth(stack);
    }

    public int m_142159_(ItemStack stack) {
        return IC2ElectricItem.getRGBDurability(stack);
    }

    public boolean m_142522_(ItemStack stack) {
        return true;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools/treetap").get("base");
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (!this.m_220152_(group)) {
            return;
        }
        IC2ElectricItem.addEmptyAndFullToGroup((ItemLike)this, items);
        ItemStack inventoryImport = new ItemStack((ItemLike)this);
        inventoryImport.m_41784_().m_128379_("inv_import", true);
        IC2ElectricItem.addEmptyAndFullToGroup(inventoryImport, items);
    }

    public boolean canInventoryImport(ItemStack stack) {
        CompoundTag nbt = StackUtil.getNbtData(stack);
        return nbt.m_128471_("inv_import");
    }

    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        ElectricItem.MANAGER.discharge(stack, ElectricItem.applyEnchantmentEffect(stack, this.energyCost * amount), Integer.MAX_VALUE, true, false, false);
        return 0;
    }

    public boolean m_8120_(ItemStack stack) {
        return false;
    }

    @Override
    public InteractionResult getEnchantmentCompatibility(ItemStack stack, Enchantment ench) {
        return ench == Enchantments.f_44962_ ? InteractionResult.FAIL : (ench == Enchantments.f_44986_ ? InteractionResult.SUCCESS : InteractionResult.PASS);
    }

    @Override
    public EnchantmentCategory getEnchantmentType(ItemStack stack) {
        return EnchantmentCategory.BREAKABLE;
    }
}

