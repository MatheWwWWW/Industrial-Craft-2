/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.npc.Villager
 *  net.minecraft.world.entity.npc.VillagerProfession
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.machines.logic.villager;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.ForgeRegistries;

public interface IVillagerTarget {
    public boolean matches(Villager var1);

    public boolean isGroup();

    public Component getGroupName();

    public static IVillagerTarget simple(UUID id) {
        return new Single(id);
    }

    public static IVillagerTarget simple(Villager villager) {
        return new Single(villager.m_20148_());
    }

    public static IVillagerTarget group(VillagerProfession profession) {
        return new Group(profession);
    }

    public static IVillagerTarget group(Villager villager) {
        return new Group(villager.m_7141_().m_35571_());
    }

    public static IVillagerTarget any() {
        return All.INSTANCE;
    }

    public static class Single
    implements IVillagerTarget {
        UUID owner;

        public Single(UUID owner) {
            this.owner = owner;
        }

        @Override
        public boolean matches(Villager target) {
            return target.m_20148_().equals(this.owner);
        }

        @Override
        public boolean isGroup() {
            return false;
        }

        @Override
        public Component getGroupName() {
            return Component.m_237119_();
        }
    }

    public static class Group
    implements IVillagerTarget {
        VillagerProfession profession;

        public Group(VillagerProfession profession) {
            this.profession = profession;
        }

        @Override
        public boolean matches(Villager target) {
            return target.m_7141_().m_35571_() == this.profession;
        }

        @Override
        public boolean isGroup() {
            return true;
        }

        @Override
        public Component getGroupName() {
            return Component.m_237110_((String)"gui.ic2.villager.cat", (Object[])new Object[]{this.getTypeName(this.profession)});
        }

        protected Component getTypeName(VillagerProfession profession) {
            ResourceLocation profName = ForgeRegistries.VILLAGER_PROFESSIONS.getKey((Object)profession);
            return Component.m_237115_((String)("entity.minecraft.villager." + (String)(!"minecraft".equals(profName.m_135827_()) ? profName.m_135827_() + "." : "") + profName.m_135815_()));
        }
    }

    public static class All
    implements IVillagerTarget {
        public static final IVillagerTarget INSTANCE = new All();

        @Override
        public boolean matches(Villager target) {
            return true;
        }

        @Override
        public boolean isGroup() {
            return true;
        }

        @Override
        public Component getGroupName() {
            return Component.m_237115_((String)"gui.ic2.villager.all");
        }
    }
}

