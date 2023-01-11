package com.pleahmacaka.examplemod.common.item

import com.mojang.blaze3d.vertex.PoseStack
import com.pleahmacaka.examplemod.imgui.MatrixMenu
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.model.HumanoidModel.ArmPose
import net.minecraft.client.player.LocalPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.HumanoidArm
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.AxeItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Tiers
import net.minecraftforge.client.extensions.common.IClientItemExtensions
import java.util.function.Consumer

object AwesomeTextAxe : AxeItem(
    Tiers.DIAMOND,
    9f,
    0.7f,
    Properties()
        .fireResistant()
) {
    override fun initializeClient(consumer: Consumer<IClientItemExtensions>) {
        consumer.accept(object : IClientItemExtensions {


            override fun applyForgeHandTransform(
                poseStack: PoseStack,
                player: LocalPlayer,
                arm: HumanoidArm,
                itemInHand: ItemStack,
                partialTick: Float,
                equipProcess: Float,
                swingProcess: Float
            ): Boolean {
                val hand = if (arm == HumanoidArm.LEFT) 1 else -1
                poseStack.translate((hand * 0.56f).toDouble(), -0.42, -0.72)
                MatrixMenu.apply(poseStack)
                return true
            }

            private val UNDER_SWORD = ArmPose.create(
                "UNDER_SWORD", false
            ) { model: HumanoidModel<*>, _: LivingEntity?, arm: HumanoidArm ->
                if (arm == HumanoidArm.RIGHT)
                    model.rightArm.xRot = (Math.random() / 2 * Math.PI * 2).toFloat()
                else
                    model.leftArm.xRot = (Math.random() / 2 * Math.PI * 2).toFloat()
            }

            override fun getArmPose(
                entityLiving: LivingEntity,
                hand: InteractionHand,
                itemStack: ItemStack
            ): ArmPose {
                return if (itemStack.isEmpty) HumanoidModel.ArmPose.EMPTY
                else return UNDER_SWORD
            }
        })
    }
}