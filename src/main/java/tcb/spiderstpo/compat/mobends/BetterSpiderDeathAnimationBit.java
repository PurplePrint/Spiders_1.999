package tcb.spiderstpo.compat.mobends;

import goblinbob.mobends.core.animation.bit.AnimationBit;
import goblinbob.mobends.core.client.event.DataUpdateHandler;
import goblinbob.mobends.standard.data.SpiderData;
import net.minecraft.util.math.MathHelper;

/**
 * Original file SpiderDeathAnimationBit.java - modified to support BetterSpiderEntity.
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
public class BetterSpiderDeathAnimationBit extends AnimationBit<BetterSpiderData>
{

    protected static final float PI = (float) Math.PI;
    protected static final String[] ACTIONS = new String[] { "death" };

    protected float wiggleSpeedMultiplier = 1.0F;
    protected float wigglePhase = 0.0F;

    @Override
    public String[] getActions(BetterSpiderData entityData)
    {
        return ACTIONS;
    }

    @Override
    public void onPlay(BetterSpiderData entityData)
    {
        wiggleSpeedMultiplier = 1.0F;
        wigglePhase = 0.0F;
    }

    @Override
    public void perform(BetterSpiderData data)
    {
        data.globalOffset.slideY(10.0F, 0.3F);

        final float headYaw = data.headYaw.get();
        final float headPitch = data.headPitch.get();

        data.spiderHead.rotation.orientInstantX(headPitch);
        data.spiderHead.rotation.rotateY(headYaw);

        data.limbs[0].upperPart.rotation.orientInstantZ(-45F);
        data.limbs[1].upperPart.rotation.orientInstantZ(45F);
        data.limbs[2].upperPart.rotation.orientInstantZ(-33.3F);
        data.limbs[3].upperPart.rotation.orientInstantZ(33.3F);
        data.limbs[4].upperPart.rotation.orientInstantZ(-33.3F);
        data.limbs[5].upperPart.rotation.orientInstantZ(33.3F);
        data.limbs[6].upperPart.rotation.orientInstantZ(-45F);
        data.limbs[7].upperPart.rotation.orientInstantZ(45F);

        data.limbs[0].upperPart.rotation.rotateY(45F);
        data.limbs[1].upperPart.rotation.rotateY(-45F);
        data.limbs[2].upperPart.rotation.rotateY(22.5F);
        data.limbs[3].upperPart.rotation.rotateY(-22.5F);
        data.limbs[4].upperPart.rotation.rotateY(-22.5F);
        data.limbs[5].upperPart.rotation.rotateY(22.5F);
        data.limbs[6].upperPart.rotation.rotateY(-45F);
        data.limbs[7].upperPart.rotation.rotateY(45F);

        final float foreBend = 89;
        data.limbs[0].lowerPart.rotation.orientInstantZ(-foreBend);
        data.limbs[1].lowerPart.rotation.orientInstantZ(foreBend);
        data.limbs[2].lowerPart.rotation.orientInstantZ(-foreBend);
        data.limbs[3].lowerPart.rotation.orientInstantZ(foreBend);
        data.limbs[4].lowerPart.rotation.orientInstantZ(-foreBend);
        data.limbs[5].lowerPart.rotation.orientInstantZ(foreBend);
        data.limbs[6].lowerPart.rotation.orientInstantZ(-foreBend);
        data.limbs[7].lowerPart.rotation.orientInstantZ(foreBend);

        final float limbSwing = data.limbSwing.get() * 0.6662F;
        final float limbSwingAmount = data.limbSwingAmount.get() / (float) Math.PI * 180F;
        final float f3 = -(MathHelper.cos(limbSwing * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
        final float f4 = -(MathHelper.cos(limbSwing * 2.0F + (float) Math.PI) * 0.4F) * limbSwingAmount;
        final float f5 = -(MathHelper.cos(limbSwing * 2.0F + ((float) Math.PI / 2F)) * 0.4F) * limbSwingAmount;
        final float f6 = -(MathHelper.cos(limbSwing * 2.0F + ((float) Math.PI * 3F / 2F)) * 0.4F) * limbSwingAmount;
        final float f7 = Math.abs(MathHelper.sin(limbSwing + 0.0F) * 0.4F) * limbSwingAmount;
        final float f8 = Math.abs(MathHelper.sin(limbSwing + (float) Math.PI) * 0.4F) * limbSwingAmount;
        final float f9 = Math.abs(MathHelper.sin(limbSwing + ((float) Math.PI / 2F)) * 0.4F) * limbSwingAmount;
        final float f10 = Math.abs(MathHelper.sin(limbSwing + ((float) Math.PI * 3F / 2F)) * 0.4F) * limbSwingAmount;
        data.limbs[0].upperPart.rotation.rotateY(f3);
        data.limbs[1].upperPart.rotation.rotateY(-f3);
        data.limbs[2].upperPart.rotation.rotateY(f4);
        data.limbs[3].upperPart.rotation.rotateY(-f4);
        data.limbs[4].upperPart.rotation.rotateY(f5);
        data.limbs[5].upperPart.rotation.rotateY(-f5);
        data.limbs[6].upperPart.rotation.rotateY(f6);
        data.limbs[7].upperPart.rotation.rotateY(-f6);

        if (wiggleSpeedMultiplier > 0.0F)
        {
            wiggleSpeedMultiplier -= DataUpdateHandler.ticksPerFrame * 0.1F;
            wiggleSpeedMultiplier = Math.max(0, wiggleSpeedMultiplier);
        }

        wigglePhase += (0.3F + wiggleSpeedMultiplier * 2F) * DataUpdateHandler.ticksPerFrame;

        final float wiggleAmount = 10.0F + wiggleSpeedMultiplier * 10.0F;
        final float wiggle1 = MathHelper.cos(wigglePhase) * wiggleAmount;
        final float wiggle2 = MathHelper.cos(wigglePhase + PI / 4) * wiggleAmount;
        final float wiggle3 = MathHelper.cos(wigglePhase + PI / 2) * wiggleAmount;
        final float wiggle4 = MathHelper.cos(wigglePhase + PI / 4 * 3) * wiggleAmount;

        data.limbs[0].upperPart.rotation.rotateZ(f7 + wiggle1);
        data.limbs[1].upperPart.rotation.rotateZ(-f7 + wiggle2);
        data.limbs[2].upperPart.rotation.rotateZ(f8 + wiggle3);
        data.limbs[3].upperPart.rotation.rotateZ(-f8 + wiggle4);
        data.limbs[4].upperPart.rotation.rotateZ(f9 + wiggle1);
        data.limbs[5].upperPart.rotation.rotateZ(-f9 + wiggle2);
        data.limbs[6].upperPart.rotation.rotateZ(f10 + wiggle3);
        data.limbs[7].upperPart.rotation.rotateZ(-f10 + wiggle4);
    }

}