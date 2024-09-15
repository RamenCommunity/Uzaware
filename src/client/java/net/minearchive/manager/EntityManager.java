package net.minearchive.manager;

import net.minearchive.AccessMC;
import net.minearchive.module.modules.client.CombatManagerModule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

public class EntityManager implements AccessMC {

    public static List<Entity> getEnemies() {
        CombatManagerModule cm = CombatManagerModule.INSTANCE;
        if (client.world == null || client.player == null) return List.of();

        return StreamSupport.stream(client.world.getEntities().spliterator(), false)
                .filter(e -> e instanceof LivingEntity)
                .filter(e -> (cm.player.getValue() && e instanceof PlayerEntity) ||
                        (cm.enemy.getValue() && e instanceof HostileEntity) ||
                        (cm.passive.getValue() && e instanceof PassiveEntity) ||
                        (cm.villagerSafe.getValue() && !(e instanceof VillagerEntity)))
                .sorted(Comparator.comparing(e -> switch (cm.priority.getValue()) {
                    case Range -> client.player.distanceTo(e);
                    case Health -> ((LivingEntity) e).getHealth();
                }))
                .toList();
    }
}
