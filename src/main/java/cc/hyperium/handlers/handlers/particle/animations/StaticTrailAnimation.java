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

package cc.hyperium.handlers.handlers.particle.animations;

import cc.hyperium.handlers.handlers.particle.AbstractAnimation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by mitchellkatz on 6/25/18. Designed for production use on Sk1er.club
 */
public class StaticTrailAnimation extends AbstractAnimation {
    @Override
    public List<Vec3> render(EntityPlayer player, double x, double y, double z) {
        ArrayList<Vec3> vec3s;
        float rotationYaw = player.rotationYawHead;
        rotationYaw -= 90;
        Vec3 base = new Vec3(x + MathHelper.cos((float) Math.toRadians(rotationYaw)), y + 1.8D, z + MathHelper.sin((float) Math.toRadians(rotationYaw)));
        vec3s = IntStream.range(0, 4).mapToObj(i -> base.addVector(0, -.4 * i, 0)).collect(Collectors.toCollection(ArrayList::new));
        return vec3s;
    }
}
