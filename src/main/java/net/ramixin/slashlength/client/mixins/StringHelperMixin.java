package net.ramixin.slashlength.client.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.util.StringUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StringUtil.class)
public class StringHelperMixin {

    @WrapOperation(method = "trimChatMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/StringUtil;truncateStringIfNecessary(Ljava/lang/String;IZ)Ljava/lang/String;"))
    private static String removeTruncationLimitIfCommand(String s, int maxLength, boolean addDotDotDotIfTruncated, Operation<String> original) {
        if(s.startsWith("/")) return s;
        return original.call(s, maxLength, addDotDotDotIfTruncated);
    }

}
