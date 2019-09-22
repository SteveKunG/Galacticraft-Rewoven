/*
 * Copyright (c) 2019 HRZN LTD
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

package com.hrznstudio.galacticraft.mixin;

import alexiil.mc.lib.attributes.item.impl.SimpleFixedItemInv;
import com.hrznstudio.galacticraft.accessor.GCPlayerAccessor;
import com.hrznstudio.galacticraft.api.entity.EvolvedEntity;
import com.hrznstudio.galacticraft.api.space.CelestialBody;
import com.hrznstudio.galacticraft.entity.damage.GalacticraftDamageSource;
import com.hrznstudio.galacticraft.items.OxygenTankItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author <a href="https://github.com/StellarHorizons">StellarHorizons</a>
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    private int air;

    @Inject(method = "baseTick", at = @At("HEAD"))
    private void baseTick(CallbackInfo ci) {
        try {
            if (((PlayerEntity) (Object) this).isCreative()) {
                air = 0;
                return;
            }
        } catch (ClassCastException ignore) {
        }
        air = ((LivingEntity) (Object) this).getBreath();
    }

    @Inject(method = "baseTick", at = @At("RETURN"))
    private void oxygenDamage(CallbackInfo ci) {
        Entity entity = (LivingEntity) (Object) this;
        if (entity.isAlive()) {
            if (entity.world.dimension instanceof CelestialBody) {
                if (!((CelestialBody) entity.world.dimension).hasOxygen()) {
                    entity.setBreath(air - 1);
                    if (entity.getBreath() == -20) {
                        entity.setBreath(0);
                        air = 0;
                        try {
                            SimpleFixedItemInv gearInventory = ((GCPlayerAccessor) entity).getGearInventory();
                            if (gearInventory.getInvStack(6).getItem() instanceof OxygenTankItem && ((gearInventory.getInvStack(6).getMaxDamage() - gearInventory.getInvStack(6).getDamage()) > 0)) {
                                gearInventory.getInvStack(6).setDamage(gearInventory.getInvStack(6).getDamage() + 1);
                                return;
                            } else if (gearInventory.getInvStack(7).getItem() instanceof OxygenTankItem && ((gearInventory.getInvStack(7).getMaxDamage() - gearInventory.getInvStack(7).getDamage()) > 0)) {
                                gearInventory.getInvStack(7).setDamage(gearInventory.getInvStack(7).getDamage() + 1);
                                return;
                            }
                        } catch (ClassCastException ignore) {
                            if (entity instanceof EvolvedEntity) {
                                return;
                            }
                        }
                        entity.damage(GalacticraftDamageSource.SUFFOCATION, 2.0F);
                    }
                }
            }
        }
    }

    /*@ModifyVariable(method = "travel", at = @At(value = "FIELD"), ordinal = 1, index = 11, name = "double_1")
    private double gravityEffect(double double_1) {
        if (((LivingEntity) (Object) this).world.getDimension() instanceof CelestialBody) {
            if (double_1 < (-((CelestialBody) ((LivingEntity) (Object) this).world.getDimension()).getGravity() * 2.5D)) {
                double_1 += ((CelestialBody) ((LivingEntity) (Object) this).world.getDimension()).getGravity();
            }
        }
        return double_1;
    }

    @ModifyVariable(method = "jump", at = @At(value = "FIELD"), ordinal = 0, index = 8, name = "float_2")
    private float gravityJumpEffect(float float_2) {
        if (((LivingEntity) (Object) this).world.getDimension() instanceof CelestialBody) {
            if (float_2 > 0) {
                float_2 = float_2 + (((CelestialBody) ((LivingEntity) (Object) this).world.getDimension()).getGravity() * 7);
            }
        }
        return float_2;
    }*/

}