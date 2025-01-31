package tcb.spiderstpo.compat.mobends;

import goblinbob.mobends.core.client.event.DataUpdateHandler;
import goblinbob.mobends.standard.data.SpiderData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import tcb.spiderstpo.common.entity.mob.AbstractClimberEntity;
import tcb.spiderstpo.common.entity.mob.BetterSpiderEntity;

/**
 * Original file SpiderCrawlAnimationBit.java - modified to support BetterSpiderEntity.
 * 
 * MIT License
 * 
 * Copyright (c) 2017 Iwo Plaza
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class BetterSpiderCrawlAnimationBit extends BetterSpiderAnimationBitBase
{

    protected static final String[] ACTIONS = new String[] { "crawl" };

    @Override
    public String[] getActions(BetterSpiderData entityData)
    {
        return ACTIONS;
    }

    @Override
    public void perform(BetterSpiderData data)
    {
        final float pt = DataUpdateHandler.partialTicks;
        final BetterSpiderEntity spider = data.getEntity();

        final float headYaw = data.headYaw.get();
        final float headPitch = data.headPitch.get();
        final float limbSwing = data.getInterpolatedCrawlProgress() * 5.0F;

        float groundLevel = MathHelper.sin(limbSwing * 0.6F) * 1.2F;

        if (startTransition < 1.0F)
            startTransition += DataUpdateHandler.ticksPerFrame * 0.1F;

        data.spiderHead.rotation.orientInstantX(headPitch);
        data.spiderHead.rotation.rotateY(headYaw).finish();

        // Back limbs
        animateMovingLimb(data, groundLevel, limbSwing + .0F, 0, 20.0F, 10F, -80, -50);
        animateMovingLimb(data, groundLevel, limbSwing + .3F, 1, 20.0F, 10F, -80, -50);

        // Back-middle limbs
        animateMovingLimb(data, groundLevel, limbSwing + .3F, 2, 15F, 15.0F, -30F, 10.0F);
        animateMovingLimb(data, groundLevel, limbSwing + .0F, 3, 15F, 15.0F, -30F, 10.0F);

        // Front-middle limbs
        animateMovingLimb(data, groundLevel, limbSwing + .4F, 4, 7F, 15.0F, 20, 50.0F);
        animateMovingLimb(data, groundLevel, limbSwing + .7F, 5, 7F, 15.0F, 20, 50.0F);

        // Front limbs
        animateMovingLimb(data, groundLevel, limbSwing + .7F, 6, 10F, 20.0F, 60, 80.0F);
        animateMovingLimb(data, groundLevel, limbSwing + .4F, 7, 10F, 20.0F, 60, 80.0F);

        /*final float climbingRotation = data.getCrawlingRotation();
        final float yaw = spider.prevRotationYaw + (spider.rotationYaw - spider.prevRotationYaw) * pt;
        final float renderRotationY = MathHelper.wrapDegrees(yaw - climbingRotation);
        data.renderRotation.orientX(-90F);
        data.renderRotation.setSmoothness(.6F).rotateY(renderRotationY);*/

        data.localOffset.slideToZero();
        data.globalOffset.slideToZero();
        data.renderRotation.orientZero();
        data.centerRotation.orientZero();
    }

}