package net.minearchive.module.modules.misc;

import com.mojang.authlib.GameProfile;
import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.Random;
import java.util.UUID;

@SuppressWarnings("DataFlowIssue")
@ModuleInfo(name = "FakePlayer", category = Category.MISC)
public class FakePlayerModule extends Module {
    private OtherClientPlayerEntity fakePlayer;
    private int id = -1;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        fakePlayer = new OtherClientPlayerEntity(client.world, new GameProfile(UUID.randomUUID(), "FakePlayer"));

        fakePlayer.preferredHand = client.player.preferredHand;
        fakePlayer.getInventory().clone(client.player.getInventory());
        fakePlayer.setPosition(client.player.getX(), client.player.getBoundingBox().minY, client.player.getZ());
        fakePlayer.setBodyYaw(client.player.getYaw());
        fakePlayer.setPitch(client.player.getPitch());
        fakePlayer.headYaw = client.player.headYaw;
        fakePlayer.setOnGround(client.player.isOnGround());
        fakePlayer.setSneaking(client.player.isSneaking());
        fakePlayer.setHealth(client.player.getHealth());
        fakePlayer.setAbsorptionAmount(client.player.getAbsorptionAmount());

        for (StatusEffectInstance statusEffect : client.player.getStatusEffects()) {
            fakePlayer.addStatusEffect(statusEffect);
        }

        while (client.world.getEntityById(id) != null) {
            id = new Random().nextInt(1000);
        }

        client.world.addEntity(id, fakePlayer);
    }

    @Override
    public void onDisable() {
        if (nullCheck() || fakePlayer == null) return;

        fakePlayer.kill();
        client.world.removeEntity(fakePlayer.getId(), Entity.RemovalReason.KILLED);
        id = -1;
    }
}
