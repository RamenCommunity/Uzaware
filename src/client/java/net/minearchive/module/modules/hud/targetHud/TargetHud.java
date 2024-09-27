package net.minearchive.module.modules.hud.targetHud;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Pair;

public abstract class TargetHud {
    public abstract void render(LivingEntity target);
    public abstract Pair<Integer, Integer> getWH();
}
