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

package cc.hyperium.gui.playerrenderer;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

import static net.minecraft.client.Minecraft.getMinecraft;
import static net.minecraft.client.renderer.GlStateManager.*;

/**
 * @author ScottehBoeh
 * @author diesieben07
 * @author TechnicianLP
 */
public class FakePlayerRendering {

    private final Renderer normalArms;
    private final Renderer smallArms;
    private final AbstractClientPlayer player;

    public FakePlayerRendering(GameProfile profile) {
        RenderManager renderManager = getMinecraft().getRenderManager();
        normalArms = new Renderer(renderManager, false);
        smallArms = new Renderer(renderManager, true);

        boolean isSessionProfile = getMinecraft().getSession().getProfile() == profile;
        if (!isSessionProfile) {
            profile = getMinecraft().getSessionService().fillProfileProperties(profile, true);
        }
        player = new FakePlayer(profile);
    }

    public void renderPlayerModel(int posX, int posY, float scale, float rotation) {
        Minecraft mc = getMinecraft();

        mc.getRenderManager().pointedEntity = player;
        mc.getRenderManager().renderEngine = mc.getTextureManager();

        pushMatrix();
        translate(posX, posY, 50.0F);
        scale(-scale, scale, scale);
        rotate(180.0F, 0.0F, 0.0F, 1.0F);

        color(1, 1, 1, 1);
        enableCull();
        enableAlpha();
        enableDepth();

        RenderHelper.enableStandardItemLighting();

        rotate(rotation, 0.0F, 1.0F, 0.0F);
        player.rotationYawHead = player.rotationYaw + rotation;

        translate(0.0F, player.getYOffset(), 0.0F);

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);

        Renderer renderer = player.getSkinType().equals("slim") ? smallArms : normalArms;
        renderer.doRender(player, 0.0D, 0.0D, 0.0F, 0.0F, 0.625F);

        disableDepth();
        disableAlpha();
        RenderHelper.disableStandardItemLighting();

        popMatrix();

    }

    private static final class Renderer extends RenderPlayer {

        public Renderer(RenderManager renderManager, boolean useSmallArms) {
            super(renderManager, useSmallArms);
        }

        @Override
        protected boolean canRenderName(AbstractClientPlayer targetEntity) {
            return false;
        }
    }

    private static final class FakePlayer extends AbstractClientPlayer {

        private final NetworkPlayerInfo playerInfo;

        FakePlayer(GameProfile profile) {
            super(new FakeWorld(), profile);
            playerInfo = new NetworkPlayerInfo(profile);
            playerInfo.getLocationSkin();
        }

        @Override
        protected NetworkPlayerInfo getPlayerInfo() {
            return playerInfo;
        }

        @Override
        public boolean isSpectator() {
            return false;
        }

        @Override
        public float getBrightness(float brightness) {
            return 0;
        }
    }

    private static final class FakeWorld extends World {

        FakeWorld() {
            super(null, null, new FakeWorldProvider(), null, true);
        }

        @Override
        protected IChunkProvider createChunkProvider() {
            return null;
        }

        @Override
        protected boolean isChunkLoaded(int x, int z, boolean allowEmpty) {
            return false;
        }

        @Override
        protected int getRenderDistanceChunks() {
            return 0;
        }

        @Override
        public BlockPos getSpawnPoint() {
            return new BlockPos(0, 0, 0);
        }
    }

    private static final class FakeWorldProvider extends WorldProvider {

        FakeWorldProvider() {
        }

        @Override
        public String getDimensionName() {
            return "Overworld";
        }

        @Override
        public String getInternalNameSuffix() {
            return "";
        }

    }

}
