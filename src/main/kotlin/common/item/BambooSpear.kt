package com.pleahmacaka.examplemod.common.item

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Vector3f
import com.pleahmacaka.examplemod.ExampleCreativeTab
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.HumanoidArm
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.SwordItem
import net.minecraft.world.item.Tiers
import net.minecraftforge.client.extensions.common.IClientItemExtensions
import java.util.function.Consumer
import kotlin.math.pow
import kotlin.math.sin

object BambooSpear : SwordItem(
    // item properties
    Tiers.DIAMOND,
    0,
    -2.4f,
    Properties()
        .stacksTo(1)
        .durability(1000)
        .tab(ExampleCreativeTab)
) {

    override fun onLeftClickEntity(stack: ItemStack, player: Player, entity: Entity): Boolean {
        if (player.level.isClientSide) return false

        player.sendSystemMessage(Component.nullToEmpty("You hit ${entity.name} with a bamboo rapier!"))
        return super.onLeftClickEntity(stack, player, entity)
    }

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
                val i = if (arm == HumanoidArm.RIGHT) 1 else -1
                // init pose

                poseStack.translate((i * 0.65f).toDouble(), -0.5, -0.72)
                poseStack.mulPose(Vector3f.XP.rotationDegrees((-80).toFloat()))
                poseStack.mulPose(Vector3f.ZP.rotationDegrees((15).toFloat()))
                if (equipProcess != 0.0f)
                    if (swingProcess < 0.3)
                        poseStack.translate(
                            0.0,
                            1.3 * sin(((swingProcess * 2) * 0.94 - 0.0).pow(2)),
                            0.0
                        ) else if (swingProcess > 0.3)
                        poseStack.translate(
                            0.0,
                            1.3 * sin((((swingProcess * 2) * -1) * 0.94 - 0.0).pow(2)),
                            0.0
                        )

                return true
            }

//            private val UNDER_SWORD = HumanoidModel.ArmPose.create(
//                "UNDER_SWORD", false
//            ) { model: HumanoidModel<*>, entity: LivingEntity?, arm: HumanoidArm ->
//                if (arm == HumanoidArm.RIGHT)
//                    model.rightArm.xRot = (0.1 / 2 * Math.PI * 2).toFloat()
//                else
//                    model.leftArm.xRot = (0.1 / 2 * Math.PI * 2).toFloat()
//            }

//            override fun getArmPose(
//                entityLiving: LivingEntity?,
//                hand: InteractionHand?,
//                itemStack: ItemStack
//            ): HumanoidModel.ArmPose {
//                return if (itemStack.isEmpty) HumanoidModel.ArmPose.EMPTY
//                else return UNDER_SWORD
//            }
        })
    }
}