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

package cc.hyperium.mods.togglechat.toggles.defaults;

import cc.hyperium.mods.togglechat.toggles.ToggleBase;

import java.util.LinkedList;

public class TypeMessages extends ToggleBase {

    private boolean enabled = true;

    @Override
    public String getName() {
        return "Messages";
    }

    @Override
    public boolean shouldToggle(String message) {
        return message.startsWith("To ") || message.startsWith("From ");
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public LinkedList<String> getDescription() {
        return asLinked(
            "Toggles all incoming",
            "private messages",
            "or any being sent",
            "",
            "These are the formats",
            "&dFrom &7Player&r: Hello",
            "&dTo &7Player&r: Hello"
        );
    }
}
