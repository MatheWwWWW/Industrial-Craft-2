/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.commands;

import net.minecraft.network.chat.Component;

public interface CommandSource {
    public static final CommandSource f_80164_ = new CommandSource(){

        @Override
        public void m_213846_(Component p_230799_) {
        }

        @Override
        public boolean m_6999_() {
            return false;
        }

        @Override
        public boolean m_7028_() {
            return false;
        }

        @Override
        public boolean m_6102_() {
            return false;
        }
    };

    public void m_213846_(Component var1);

    public boolean m_6999_();

    public boolean m_7028_();

    public boolean m_6102_();

    default public boolean m_142559_() {
        return false;
    }
}

