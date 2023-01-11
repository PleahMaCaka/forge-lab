package com.pleahmacaka.examplemod.common.item

import net.minecraft.world.item.AxeItem
import net.minecraft.world.item.Tiers

object AwesomeTextAxe : AxeItem(
    Tiers.DIAMOND,
    9f,
    0.7f,
    Properties()
        .fireResistant()
)