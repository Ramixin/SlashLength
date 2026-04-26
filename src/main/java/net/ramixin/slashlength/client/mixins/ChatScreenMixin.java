package net.ramixin.slashlength.client.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ChatScreen;
import net.ramixin.slashlength.TextFieldWidgetDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {

    @Shadow protected EditBox input;

    @Inject(method = "init", at = @At("TAIL"))
    private void markTextWidgetAsChatBox(CallbackInfo ci) {
        if(this.input != null)
            ((TextFieldWidgetDuck)this.input).slashLength$setAsChatBox();
    }

    @Inject(method = "onEdited", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/CommandSuggestions;setAllowSuggestions(Z)V"))
    private void modifyChatLengthIfCommand(String value, CallbackInfo ci) {
        if(this.input.getValue().startsWith("/")) this.input.setMaxLength(Integer.MAX_VALUE);
        else this.input.setMaxLength(256);
    }

    @WrapOperation(method = "moveInHistory", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/EditBox;setValue(Ljava/lang/String;)V"))
    private void allowCommandsInHistoryToPassLimit(EditBox instance, String value, Operation<Void> original) {
        if(value.startsWith("/")) instance.setMaxLength(Integer.MAX_VALUE);
        else instance.setMaxLength(256);
        original.call(instance, value);
    }

}
