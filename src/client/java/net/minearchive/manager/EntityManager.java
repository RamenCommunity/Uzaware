package net.minearchive.manager;

import net.minearchive.AccessMC;
import net.minearchive.module.Module;
import net.minearchive.module.modules.combat.CombatManagerModule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

public class EntityManager implements AccessMC {

    @Nullable
    private Entity targeting = null;
    private Module usingModule;
    private List<Entity> entities = new ArrayList<>();

    public void update() {
        CombatManagerModule cm = CombatManagerModule.INSTANCE;
        if (client.world == null || client.player == null) return;

        entities = StreamSupport.stream(client.world.getEntities().spliterator(), false)
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

    public @Nullable Entity getTargeting() {
        return targeting;
    }

    public void setTargeting(@Nullable Entity targeting) {
        this.targeting = targeting;
    }

    public Module getUsingModule() {
        return usingModule;
    }

    public void setUsingModule(Module usingModule) {
        this.usingModule = usingModule;
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
