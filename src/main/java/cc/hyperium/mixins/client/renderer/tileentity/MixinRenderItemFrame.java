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

package cc.hyperium.mixins.client.renderer.tileentity;

import cc.hyperium.mixinsimp.client.renderer.tileentity.HyperiumRenderItemFrame;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.entity.item.EntityItemFrame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItemFrame.class)
public class MixinRenderItemFrame {

    private HyperiumRenderItemFrame hyperiumRenderItemFrame = new HyperiumRenderItemFrame();

    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    private void doRender(EntityItemFrame entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        hyperiumRenderItemFrame.doRender(ci);
    }
}
