/*
 *       Copyright (C) 2018-present Hyperium <https://hyperium.cc/>
 *
 *       This program is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU Lesser General Public License as published
 *       by the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cc.hyperium.mixinsimp.gui;

import cc.hyperium.gui.hyperium.HyperiumMainGui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.resources.I18n;

import java.util.List;

public class HyperiumGuiOptions {
    private GuiOptions parent;

    public HyperiumGuiOptions(GuiOptions parent) {
        this.parent = parent;
    }

    public void initGui(List<GuiButton> buttonList) {
        buttonList.forEach(b -> {
            if (b.id == 200) {
                b.yPosition = parent.height - 30;
            }
        });
        buttonList.add(new GuiButton(114514, parent.width / 2 - 155, parent.height / 6 + 18, 150, 20, I18n.format("button.ingame.hyperiumsettings")));
    }

    public void actionPerformed(GuiButton button) {
        if (button.id == 114514) {
            HyperiumMainGui.INSTANCE.show();
        }
    }
}
