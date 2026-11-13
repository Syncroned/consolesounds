package com.hattolo.consolesounds.mixin;

import com.hattolo.consolesounds.ConsoleSoundsClient;
import com.hattolo.consolesounds.ConsoleSoundsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(AbstractWidget.class)
public class SelectClickableWidgetMixin {
	private static float randFloat(float min, float max) {
		Random rand = new Random();
		return rand.nextFloat() * (max - min) + min;
	}

	@Inject(at = @At("RETURN"), method = "isHovered")
	private void isHovered(CallbackInfoReturnable<Boolean> cir) {
		var widget = (AbstractWidget) (Object) this;

		if (cir.getReturnValue()) {
			if (!widget.active) return;
			if (!selected) {
				selected = true;
				if (AutoConfig.getConfigHolder(ConsoleSoundsConfig.class).getConfig().enableHoverSounds) Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ConsoleSoundsClient.UI_SELECT_EVENT, randFloat(0.95f, 1.05f)));
			}
		}
		else {
			selected = false;
		}
	}

	Boolean selected = false;
}
