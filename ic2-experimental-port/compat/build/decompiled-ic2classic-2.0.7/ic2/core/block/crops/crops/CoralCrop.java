/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.crops.crops;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICrop;
import ic2.api.crops.ICropTile;
import ic2.core.block.crops.crops.BaseCrop;
import ic2.core.platform.rendering.IC2Textures;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CoralCrop
extends BaseCrop {
    private String name;
    private Item drop_coral;
    private Item drop_fan;
    private Item drop_block;
    private static Map<Item, Item> DEAD_DROPS = CoralCrop.fillDeadMap();

    public CoralCrop(String name, Item drop_coral, Item drop_fan, Item drop_block, CropProperties properties, String ... attributes) {
        super(name + "_coral", properties, attributes);
        this.name = name;
        this.drop_coral = drop_coral;
        this.drop_fan = drop_fan;
        this.drop_block = drop_block;
    }

    public CoralCrop(String name, Item drop_coral, Item drop_fan, Item drop_block, String color) {
        this(name, drop_coral, drop_fan, drop_block, new CropProperties(2, 3, 0, 0, 5, 1), "Coral", "Decoration", color);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(int stage) {
        Object stageTexture = "";
        switch (stage) {
            case 0: 
            case 1: {
                stageTexture = "growing_" + stage;
                break;
            }
            case 2: 
            case 3: 
            case 4: {
                stageTexture = "growing_" + this.name + "_" + stage;
                break;
            }
            case 5: 
            case 6: 
            case 7: {
                stageTexture = "dead_" + this.name + "_" + (stage - 3);
            }
        }
        return IC2Textures.getMappedEntriesBlockIC2("crops/corals").get(stageTexture);
    }

    @Override
    public ICrop.CropType getCropType() {
        return ICrop.CropType.WATER;
    }

    protected boolean isDead(ICropTile tile) {
        return tile.getCustomData().m_128471_("isDead");
    }

    protected void setDead(ICropTile tile, boolean value) {
        tile.getCustomData().m_128379_("isDead", value);
    }

    @Override
    public void onTick(ICropTile cropTile) {
        if (!cropTile.isWaterLogged()) {
            this.setDead(cropTile, true);
            if (cropTile.getGrowthStage() > 1 && cropTile.getGrowthStage() < 5) {
                cropTile.setGrowthStage(cropTile.getGrowthStage() + 3);
            }
        }
    }

    @Override
    public int getGrowthSteps() {
        return 4;
    }

    @Override
    public int getAfterHarvestStage(ICropTile cropTile) {
        return 2;
    }

    @Override
    public int getGrowthDuration(ICropTile cropTile) {
        return super.getGrowthDuration(cropTile) * 2;
    }

    @Override
    public boolean canGrow(ICropTile cropTile) {
        return super.canGrow(cropTile) && !this.isDead(cropTile) && this.secondaryGrowthCondition(cropTile);
    }

    private boolean secondaryGrowthCondition(ICropTile cropTile) {
        return cropTile.getEnvironmentQuality() >= 3;
    }

    @Override
    public int getOptimalHarvestStep(ICropTile cropTile) {
        return this.isDead(cropTile) ? 7 : 4;
    }

    @Override
    public boolean canBeHarvested(ICropTile cropTile) {
        return this.isDead(cropTile) ? cropTile.getGrowthStage() > 5 : cropTile.getGrowthStage() == 4;
    }

    @Override
    public Component discoveredBy() {
        return BaseCrop.MOJANG;
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack((ItemLike)this.drop_coral);
    }

    @Override
    public double getDropChance(ICropTile cropTile) {
        return 0.8;
    }

    @Override
    public ItemStack[] getDrops(ICropTile cropTile) {
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        int random = BaseCrop.getRandom(cropTile).m_188502_() % 13;
        boolean isDead = this.isDead(cropTile);
        drops.add(new ItemStack((ItemLike)(isDead ? (ItemLike)DEAD_DROPS.get(this.drop_coral) : this.drop_coral), BaseCrop.getRandom(cropTile).m_188503_(2) + 1));
        if (random % 6 == 0) {
            drops.add(new ItemStack((ItemLike)(isDead ? (ItemLike)DEAD_DROPS.get(this.drop_fan) : this.drop_fan), BaseCrop.getRandom(cropTile).m_188503_(2)));
        }
        if (random % 4 == 0 || random % 9 == 0) {
            drops.add(new ItemStack((ItemLike)(isDead ? (ItemLike)DEAD_DROPS.get(this.drop_block) : this.drop_block), BaseCrop.getRandom(cropTile).m_188503_(2)));
        }
        if (isDead && random % 3 == 0) {
            drops.add(new ItemStack((ItemLike)Items.f_42490_));
        }
        return drops.toArray(new ItemStack[0]);
    }

    @Override
    public boolean hasCustomCropPlaceFailedMessage(ICropTile cropTile) {
        return true;
    }

    @Override
    public Component getCustomCropPlaceFailedMessage(ICropTile cropTile) {
        if (!this.secondaryGrowthCondition(cropTile)) {
            return this.translate("info.crop.ic2.plant.placement_error.quality", this.translate("info.crop.ic2.plant.placement_error.better"), this.translate("info.crop.ic2.plant.placement_error.water"));
        }
        return super.getCustomCropPlaceFailedMessage(cropTile);
    }

    private static Map<Item, Item> fillDeadMap() {
        HashMap<Item, Item> map = new HashMap<Item, Item>();
        map.putIfAbsent(Items.f_42291_, Items.f_42295_);
        map.putIfAbsent(Items.f_42286_, Items.f_42281_);
        map.putIfAbsent(Items.f_42301_, Items.f_42359_);
        map.putIfAbsent(Items.f_42292_, Items.f_42296_);
        map.putIfAbsent(Items.f_42287_, Items.f_42282_);
        map.putIfAbsent(Items.f_42302_, Items.f_42360_);
        map.putIfAbsent(Items.f_42293_, Items.f_42297_);
        map.putIfAbsent(Items.f_42288_, Items.f_42283_);
        map.putIfAbsent(Items.f_42356_, Items.f_42361_);
        map.putIfAbsent(Items.f_42294_, Items.f_42298_);
        map.putIfAbsent(Items.f_42289_, Items.f_42284_);
        map.putIfAbsent(Items.f_42357_, Items.f_42362_);
        map.putIfAbsent(Items.f_42290_, Items.f_42299_);
        map.putIfAbsent(Items.f_42285_, Items.f_42280_);
        map.putIfAbsent(Items.f_42300_, Items.f_42358_);
        return map;
    }
}

