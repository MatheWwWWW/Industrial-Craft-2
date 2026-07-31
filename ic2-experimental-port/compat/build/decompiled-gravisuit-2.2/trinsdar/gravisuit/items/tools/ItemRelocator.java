/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.api.items.electric.ElectricItem
 *  ic2.api.tiles.teleporter.TeleporterTarget
 *  ic2.core.audio.AudioManager$SoundType
 *  ic2.core.inventory.base.IHasGui
 *  ic2.core.inventory.base.IHasHeldGui
 *  ic2.core.inventory.base.IPortableInventory
 *  ic2.core.item.base.IC2ElectricItem
 *  ic2.core.platform.rendering.IC2Textures
 *  ic2.core.platform.rendering.features.item.ISimpleItemModel
 *  ic2.core.utils.helpers.TeleportUtil
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package trinsdar.gravisuit.items.tools;

import ic2.api.items.electric.ElectricItem;
import ic2.api.tiles.teleporter.TeleporterTarget;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.inventory.base.IHasGui;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.helpers.TeleportUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import trinsdar.gravisuit.entity.PlasmaBall;
import trinsdar.gravisuit.items.container.ItemInventoryRelocator;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.GravisuitLang;
import trinsdar.gravisuit.util.Registry;

public class ItemRelocator
extends IC2ElectricItem
implements ISimpleItemModel,
IHasHeldGui {
    public ItemRelocator() {
        super("relocator");
        Registry.REGISTRY.put(new ResourceLocation("gravisuit", "relocator"), (Item)this);
    }

    protected int getEnergyCost(ItemStack itemStack) {
        return 1000000;
    }

    public boolean canProvideEnergy(ItemStack itemStack) {
        return false;
    }

    public int getCapacity(ItemStack stack) {
        return GravisuitConfig.POWER_VALUES.RELOCATOR_STORAGE;
    }

    public int getTier(ItemStack itemStack) {
        return 5;
    }

    public int getTransferLimit(ItemStack stack) {
        return GravisuitConfig.POWER_VALUES.RELOCATOR_TRANSFER;
    }

    public IPortableInventory getInventory(Player player, InteractionHand interactionHand, ItemStack itemStack) {
        return new ItemInventoryRelocator(player, this, itemStack, interactionHand);
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        CompoundTag nbt = stack.m_41784_();
        if (IC2.PLATFORM.isSimulating() && IC2.KEYBOARD.isModeSwitchKeyDown(player)) {
            byte mode = nbt.m_128445_("mode");
            if (mode == 2) {
                nbt.m_128344_("mode", (byte)0);
                player.m_5661_((Component)this.translate(GravisuitLang.messageRelocatorPersonal, new ChatFormatting[]{ChatFormatting.GREEN}), false);
            } else if (mode == 0) {
                nbt.m_128344_("mode", (byte)1);
                player.m_5661_((Component)this.translate(GravisuitLang.messageRelocatorTranslocator, new ChatFormatting[]{ChatFormatting.GOLD}), false);
            } else {
                nbt.m_128344_("mode", (byte)2);
                player.m_5661_((Component)this.translate(GravisuitLang.messageRelocatorPortal, new ChatFormatting[]{ChatFormatting.AQUA}), false);
            }
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        if (IC2.PLATFORM.isSimulating()) {
            String name;
            CompoundTag map;
            if (player.m_6047_() || nbt.m_128445_("mode") == 0) {
                IC2.PLATFORM.launchGui(player, hand, null, (IHasGui)this.getInventory(player, hand, stack));
                return InteractionResultHolder.m_19090_((Object)stack);
            }
            if (nbt.m_128445_("mode") >= 1 && nbt.m_128441_("DefaultLocation") && nbt.m_128441_("Locations") && (map = nbt.m_128469_("Locations")).m_128441_(name = nbt.m_128461_("DefaultLocation"))) {
                int use;
                boolean portal = nbt.m_128445_("mode") == 2;
                int n = use = portal ? 10000000 : 500000;
                if (ElectricItem.MANAGER.canUse(stack, use)) {
                    PlasmaBall entity = new PlasmaBall(player.f_19853_, player, TeleportData.fromNBT(map.m_128469_(name), name), hand);
                    level.m_7967_((Entity)entity);
                    if (portal) {
                        ElectricItem.MANAGER.use(stack, use, (LivingEntity)player);
                    }
                } else {
                    player.m_213846_((Component)this.translate(GravisuitLang.messageRelocatorNotEnoughPower, new ChatFormatting[]{ChatFormatting.RED}));
                }
                return InteractionResultHolder.m_19090_((Object)stack);
            }
        }
        return super.m_7203_(level, player, hand);
    }

    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return (TextureAtlasSprite)IC2Textures.getMappedEntriesItem((String)"gravisuit", (String)"tools").get("relocator");
    }

    public static void teleportEntity(Player player, CompoundTag teleportData, ItemStack stack) {
        Registry.RELOCATOR.teleportEntity((LivingEntity)player, TeleporterTarget.read((CompoundTag)teleportData), player.m_6374_(), stack);
    }

    public void teleportEntity(LivingEntity player, TeleporterTarget target, Direction dir, ItemStack stack) {
        int weight = TeleportUtil.getWeightOfEntity((Entity)player, (boolean)true);
        if (weight != 0) {
            ServerLevel server = target.getWorld();
            BlockPos pos = target.getTargetPosition();
            if (ElectricItem.MANAGER.use(stack, (int)((double)weight * TeleportUtil.getDistanceCost((Level)player.m_9236_(), (BlockPos)player.m_20183_(), (Level)server, (BlockPos)pos) * 5.0), player)) {
                TeleportUtil.teleportEntity((Entity)player, (ServerLevel)server, (BlockPos)pos, (Direction)dir);
                IC2.AUDIO.playSound(player, new ResourceLocation("ic2", "sounds/machines/teleport.ogg"), AudioManager.SoundType.ITEM);
            } else {
                player.m_213846_((Component)this.translate(GravisuitLang.messageRelocatorNotEnoughPower, new ChatFormatting[]{ChatFormatting.RED}));
            }
        }
    }

    public static class TeleportData {
        long pos;
        String dimId;
        String name;

        public TeleportData(long pos, String dimId, String name) {
            this.pos = pos;
            this.dimId = dimId;
            this.name = name;
        }

        public TeleportData(String name) {
            this.pos = 0L;
            this.dimId = "minecraft:overworld";
            this.name = name;
        }

        public long getPos() {
            return this.pos;
        }

        public String getDimId() {
            return this.dimId;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDimId(String dimId) {
            this.dimId = dimId;
        }

        public CompoundTag writeToNBT() {
            CompoundTag compound = new CompoundTag();
            compound.m_128356_("pos", this.pos);
            compound.m_128359_("id", this.dimId);
            return compound;
        }

        public static TeleportData fromNBT(CompoundTag tag, String name) {
            return new TeleportData(tag.m_128454_("pos"), tag.m_128461_("id"), name);
        }

        public TeleporterTarget toTeleportTarget() {
            CompoundTag compoundTag = this.writeToNBT();
            return TeleporterTarget.read((CompoundTag)compoundTag);
        }
    }

    public static enum TeleportMode {
        PERSONAL,
        PORTAL,
        TRANSLOCATOR;


        public TeleportMode getNext() {
            return switch (this) {
                default -> throw new IncompatibleClassChangeError();
                case PERSONAL -> PORTAL;
                case PORTAL -> TRANSLOCATOR;
                case TRANSLOCATOR -> PERSONAL;
            };
        }
    }
}

