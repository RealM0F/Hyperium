package com.chattriggers.ctjs.utils.config

import com.chattriggers.ctjs.minecraft.libs.MathLib
import com.chattriggers.ctjs.minecraft.libs.renderer.Rectangle
import com.chattriggers.ctjs.minecraft.libs.renderer.Renderer
import com.chattriggers.ctjs.minecraft.libs.renderer.Text
import com.chattriggers.ctjs.minecraft.wrappers.Client
import net.minecraft.client.gui.GuiButton
import java.awt.Color
import kotlin.properties.Delegates
import kotlin.reflect.KMutableProperty

open class ConfigColor
    (private val prop: KMutableProperty<Color>, name: String = "", x: Int = 0, y: Int = 0) : ConfigOption() {

    private var value: Color by Delegates.observable(prop.getter.call(Config)) { _, _, new ->
        prop.setter.call(Config, new)
    }
    private val initial = value

    init {
        this.name = name

        this.x = x
        this.y = y
    }


    private var redButton = GuiButton(
        0,
        MathLib.map(
            this.value.red.toFloat(), 0f, 255f,
            (Renderer.Screen.getWidth() / 2 - 100 + this.x).toFloat(),
            (Renderer.Screen.getWidth() / 2 + 52 + this.x).toFloat()
        ).toInt(),
        this.y + 15,
        5, 10, ""
    )


    private var greenButton = GuiButton(
        0,
        MathLib.map(
            this.value.green.toFloat(),
            0f,
            255f,
            (Renderer.Screen.getWidth() / 2 - 100 + this.x).toFloat(),
            (Renderer.Screen.getWidth() / 2 + 52 + this.x).toFloat()
        ).toInt(), this.y + 30,
        5, 10, ""
    )


    private var blueButton = GuiButton(
        0,
        MathLib.map(
            this.value.blue.toFloat(),
            0f,
            255f,
            (Renderer.Screen.getWidth() / 2 - 100 + this.x).toFloat(),
            (Renderer.Screen.getWidth() / 2 + 52 + this.x).toFloat()
        ).toInt(), this.y + 45,
        5, 10, ""
    )

    private var redHeld: Boolean = false
    private var blueHeld: Boolean = false
    private var greenHeld: Boolean = false

    override fun draw(mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (this.hidden) return

        val middle = Renderer.Screen.getWidth() / 2

        Rectangle(-0x80000000, (middle - 105 + this.x).toFloat(), (this.y - 5).toFloat(), 210f, 65f)
            .setShadow(-0x30000000, 3f, 3f)
            .draw()
        Text(this.name!!, (middle - 100 + this.x).toFloat(), this.y.toFloat()).draw()

        // red slider
        Rectangle(-0x560000, (middle - 100 + this.x).toFloat(), (this.y + 19).toFloat(), 155f, 3f)
            .setOutline(-0x1000000, 1f)
            .draw()

        this.redButton.xPosition = MathLib.map(
            this.value.red.toFloat(),
            0f,
            255f,
            (middle - 100 + this.x).toFloat(),
            (middle + 52 + this.x).toFloat()
        ).toInt()
        this.redButton.drawButton(Client.getMinecraft(), mouseX, mouseY)

        // green slider
        Rectangle(-0xff7800, (middle - 100 + this.x).toFloat(), (this.y + 34).toFloat(), 155f, 3f)
            .setOutline(-0x1000000, 1f)
            .draw()

        this.greenButton.xPosition = MathLib.map(
            this.value.green.toFloat(),
            0f,
            255f,
            (middle - 100 + this.x).toFloat(),
            (middle + 52 + this.x).toFloat()
        ).toInt()
        this.greenButton.drawButton(Client.getMinecraft(), mouseX, mouseY)

        // blue slider
        Rectangle(-0xffff34, (middle - 100 + this.x).toFloat(), (this.y + 49).toFloat(), 155f, 3f)
            .setOutline(-0x1000000, 1f)
            .draw()
        this.blueButton.xPosition = MathLib.map(
            this.value.blue.toFloat(),
            0f,
            255f,
            (middle - 100 + this.x).toFloat(),
            (middle + 52 + this.x).toFloat()
        ).toInt()
        this.blueButton.drawButton(Client.getMinecraft(), mouseX, mouseY)

        // color preview
        Rectangle(this.value.rgb, (middle + this.x + 60).toFloat(), (this.y + 15).toFloat(), 40f, 40f)
            .setOutline(-0x1000000, 1f)
            .draw()

        handleHeldButtons(mouseX, middle)

        super.draw(mouseX, mouseY, partialTicks)
    }

    private fun handleHeldButtons(mouseX: Int, middle: Int) {
        if (this.redHeld) {
            this.redButton.xPosition = mouseX - 1

            limitHeldButton(this.redButton)
            this.value = Color(
                MathLib.map(
                    this.redButton.xPosition.toFloat(),
                    (middle - 100 + this.x).toFloat(), (middle + 52 + this.x).toFloat(), 0f, 255f
                ).toInt(),
                this.value.green,
                this.value.blue
            )
        }
        if (this.greenHeld) {
            this.greenButton.xPosition = mouseX - 1

            limitHeldButton(this.greenButton)
            this.value = Color(
                this.value.red,
                MathLib.map(
                    this.greenButton.xPosition.toFloat(),
                    (middle - 100 + this.x).toFloat(), (middle + 52 + this.x).toFloat(), 0f, 255f
                ).toInt(),
                this.value.blue
            )
        }
        if (this.blueHeld) {
            this.blueButton.xPosition = mouseX - 1

            limitHeldButton(this.blueButton)
            this.value = Color(
                this.value.red,
                this.value.green,
                MathLib.map(
                    this.blueButton.xPosition.toFloat(),
                    (middle - 100 + this.x).toFloat(), (middle + 52 + this.x).toFloat(), 0f, 255f
                ).toInt()
            )
        }
    }

    private fun limitHeldButton(button: GuiButton) {
        if (button.xPosition < Renderer.Screen.getWidth() / 2 - 100 + this.x)
            button.xPosition = Renderer.Screen.getWidth() / 2 - 100 + this.x
        if (button.xPosition > Renderer.Screen.getWidth() / 2 + 52 + this.x)
            button.xPosition = Renderer.Screen.getWidth() / 2 + 52 + this.x
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int) {
        if (this.hidden) return

        if (this.redButton.mousePressed(Client.getMinecraft(), mouseX, mouseY)) {
            this.redHeld = true
            this.redButton.playPressSound(Client.getMinecraft().soundHandler)
        }
        if (this.greenButton.mousePressed(Client.getMinecraft(), mouseX, mouseY)) {
            this.greenHeld = true
            this.greenButton.playPressSound(Client.getMinecraft().soundHandler)
        }
        if (this.blueButton.mousePressed(Client.getMinecraft(), mouseX, mouseY)) {
            this.blueHeld = true
            this.blueButton.playPressSound(Client.getMinecraft().soundHandler)
        }

        if (this.resetButton.mousePressed(Client.getMinecraft(), mouseX, mouseY)) {
            this.value = this.initial
            val middle = Renderer.Screen.getWidth() / 2
            this.redButton.xPosition = MathLib.map(
                this.value.red.toFloat(),
                0f,
                255f,
                (middle - 100 + this.x).toFloat(),
                (middle + 52 + this.x).toFloat()
            ).toInt()
            this.greenButton.xPosition = MathLib.map(
                this.value.green.toFloat(),
                0f,
                255f,
                (middle - 100 + this.x).toFloat(),
                (middle + 52 + this.x).toFloat()
            ).toInt()
            this.blueButton.xPosition = MathLib.map(
                this.value.blue.toFloat(),
                0f,
                255f,
                (middle - 100 + this.x).toFloat(),
                (middle + 52 + this.x).toFloat()
            ).toInt()
            this.resetButton.playPressSound(Client.getMinecraft().soundHandler)
        }
    }

    override fun mouseReleased() {
        this.redHeld = false
        this.blueHeld = false
        this.greenHeld = false
    }
}

class SpecialConfigColor
    (prop: KMutableProperty<Color>, name: String = "", x: Int = 0, y: Int = 0) : ConfigColor(prop, name, x, y) {
    override fun draw(mouseX: Int, mouseY: Int, partialTicks: Float) {
        hidden = !Config.customTheme

        super.draw(mouseX, mouseY, partialTicks)
    }
}
