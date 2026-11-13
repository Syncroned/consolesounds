package com.hattolo.consolesounds.mixin;

import com.hattolo.consolesounds.ConsoleSoundsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class OpenPauseMenuMixin {
    @Shadow public abstract Screen screen();

    @Inject(at = @At("HEAD"), method = "setPauseScreen")
    private void setPauseScreen(boolean pause, boolean showPauseMenu, CallbackInfo ci) {
        // setPauseScreen only opens a PauseScreen when no other screen is
        // open; matching that check keeps the sound playing exactly once per
        // open, since pauseIfInactive calls pauseGame every frame while the
        // window is unfocused.
        if (this.screen() == null) {
            if (AutoConfig.getConfigHolder(ConsoleSoundsConfig.class).getConfig().playSoundOnPauseMenu) Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }
    }
}
