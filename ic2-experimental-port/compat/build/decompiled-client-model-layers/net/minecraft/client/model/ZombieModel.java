/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;

public class ZombieModel<T extends Zombie>
extends AbstractZombieModel<T> {
    public ZombieModel(ModelPart p_171090_) {
        super(p_171090_);
    }

    @Override
    public boolean m_7134_(T p_104155_) {
        return ((Mob)p_104155_).m_5912_();
    }
}

