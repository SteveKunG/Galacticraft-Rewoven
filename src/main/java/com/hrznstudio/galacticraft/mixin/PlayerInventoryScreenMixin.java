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

import com.hrznstudio.galacticraft.Constants;
import com.hrznstudio.galacticraft.container.screen.PlayerInventoryGCScreen;
import com.hrznstudio.galacticraft.items.GalacticraftItems;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.container.PlayerContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author <a href="https://github.com/StellarHorizons">StellarHorizons</a>
 */
@Mixin(InventoryScreen.class)
public abstract class PlayerInventoryScreenMixin extends AbstractInventoryScreen<PlayerContainer> {
    public PlayerInventoryScreenMixin(PlayerContainer container, PlayerInventory playerInventory, Text textComponent) {
        super(container, playerInventory, textComponent);
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    public void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> ci) {
//        System.out.println("X: " + mouseX);
//        System.out.println("Y: " + mouseY);
//        System.out.println("b: " + button);

        if (PlayerInventoryGCScreen.isCoordinateBetween((int) Math.floor(mouseX), left + 30, left + 59)
                && PlayerInventoryGCScreen.isCoordinateBetween((int) Math.floor(mouseY), top - 26, top)) {
            System.out.println("Clicked on GC tab!");
            minecraft.openScreen(new PlayerInventoryGCScreen(playerInventory.player));
        }
    }

    @Inject(method = "drawBackground", at = @At("TAIL"))
    public void drawBackground(float v, int i, int i1, CallbackInfo callbackInfo) {
        this.minecraft.getTextureManager().bindTexture(new Identifier(Constants.MOD_ID, Constants.ScreenTextures.getRaw(Constants.ScreenTextures.PLAYER_INVENTORY_TABS)));
        this.blit(this.left, this.top - 28, 0, 0, 57, 32);
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(int mouseX, int mouseY, float v, CallbackInfo callbackInfo) {
        GuiLighting.enableForItems();
        this.itemRenderer.renderGuiItem(Items.CRAFTING_TABLE.getStackForRender(), this.left + 6, this.top - 20);
        this.itemRenderer.renderGuiItem(GalacticraftItems.OXYGEN_MASK.getStackForRender(), this.left + 35, this.top - 20);
    }
}