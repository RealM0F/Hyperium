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

package cc.hyperium.event;

import com.google.common.base.Preconditions;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class ArrowShootEvent extends Event {

    @NotNull
    private final EntityArrow arrow;

    @NotNull
    private final ItemStack bow;

    private final int charge;

    public ArrowShootEvent(@NotNull EntityArrow arrow, int charge, @NotNull ItemStack bow) {
        Preconditions.checkNotNull(arrow, "arrow");
        Preconditions.checkNotNull(bow, "bow");

        this.arrow = arrow;
        this.charge = charge;
        this.bow = bow;
    }

    @NotNull
    public final EntityArrow getArrow() {
        return this.arrow;
    }

    @NotNull
    public final ItemStack getBow() {
        return this.bow;
    }

    public final int getCharge() {
        return this.charge;
    }
}
