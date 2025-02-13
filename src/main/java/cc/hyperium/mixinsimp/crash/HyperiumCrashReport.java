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

package cc.hyperium.mixinsimp.crash;

import cc.hyperium.Metadata;
import cc.hyperium.commands.defaults.CommandDebug;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class HyperiumCrashReport {
    private CrashReport parent;

    public HyperiumCrashReport(CrashReport crashReport) {
        this.parent = crashReport;
    }

    public void add() {
        CrashReportCategory category = parent.makeCategoryDepth("Affected level", 1);
        category.addCrashSection("Hyperium Version", Metadata.getVersion() + " (" + Metadata.getVersionID() + ")");
        category.addCrashSection("Everything else", CommandDebug.get());
    }
}
