/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.upgrade;

import ic2.api.upgrade.IAugmentationUpgrade;
import ic2.api.upgrade.IEnergyStorageUpgrade;
import ic2.api.upgrade.IFluidConsumingUpgrade;
import ic2.api.upgrade.IFluidProducingUpgrade;
import ic2.api.upgrade.IItemConsumingUpgrade;
import ic2.api.upgrade.IItemProducingUpgrade;
import ic2.api.upgrade.IProcessingUpgrade;
import ic2.api.upgrade.IRedstoneSensitiveUpgrade;
import ic2.api.upgrade.IRemoteAccessUpgrade;
import ic2.api.upgrade.ITransformerUpgrade;

public interface IFullUpgrade
extends IAugmentationUpgrade,
IEnergyStorageUpgrade,
IFluidConsumingUpgrade,
IFluidProducingUpgrade,
IItemConsumingUpgrade,
IItemProducingUpgrade,
IProcessingUpgrade,
IRedstoneSensitiveUpgrade,
ITransformerUpgrade,
IRemoteAccessUpgrade {
}

