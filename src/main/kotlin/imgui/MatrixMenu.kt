package com.pleahmacaka.examplemod.imgui

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import imgui.ImGuiIO
import imgui.gl3.ImGuiImplGl3
import imgui.glfw.ImGuiImplGlfw
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import org.joml.Quaternionf

class MatrixMenu : Screen(Component.literal("Matrix Menu")) {

    var shouldPause = false

    companion object {
        private val window: Long = Minecraft.getInstance().window.window
        private val implGl3: ImGuiImplGl3 = ImGuiImplGl3()
        private val implGlfw: ImGuiImplGlfw = ImGuiImplGlfw()
        private val io: ImGuiIO
        private val translation = FloatArray(3)
        private val rotation = FloatArray(3)

        init {
            val context = ImGui.createContext()
            ImGui.setCurrentContext(context)
            io = ImGui.getIO()
            implGlfw.init(window, false)
            implGl3.init("#version 150")
        }

        fun apply(poseStack: PoseStack) {
            poseStack.translate(translation[0].toDouble(), translation[1].toDouble(), translation[2].toDouble())

            poseStack.mulPose(Quaternionf(rotation[0], rotation[1], rotation[2], 1.0f))
        }
    }

    override fun isPauseScreen(): Boolean = shouldPause

    override fun render(poseStack: PoseStack, x: Int, y: Int, partialTicks: Float) {
        implGlfw.newFrame()

        ImGui.newFrame()

        ImGui.begin(title.string)

        ImGui.sliderFloat3("translation", translation, -10F, 10F)
        ImGui.sliderFloat3("angle", rotation, -360F, 360F)

        ImGui.end()
        ImGui.render()
        implGl3.renderDrawData(ImGui.getDrawData())
    }

}