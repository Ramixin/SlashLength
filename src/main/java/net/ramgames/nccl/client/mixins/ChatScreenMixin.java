package net.ramgames.nccl.client.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {

    @Shadow protected TextFieldWidget chatField;

    @Inject(method = "onChatFieldUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ChatInputSuggestor;setWindowActive(Z)V", shift = At.Shift.BEFORE))
    private void modifyChatLengthIfCommand(String chatText, CallbackInfo ci) {
        if(this.chatField.getText().startsWith("/")) this.chatField.setMaxLength(Integer.MAX_VALUE);
        else this.chatField.setMaxLength(256);
    }

}
