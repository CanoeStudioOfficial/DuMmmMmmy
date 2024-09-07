package net.mehvahdjukaar.dummmmmmy.network;

import net.mehvahdjukaar.dummmmmmy.Dummmmmmy;
import net.mehvahdjukaar.dummmmmmy.common.CritRecord;
import net.mehvahdjukaar.dummmmmmy.common.TargetDummyEntity;
import net.mehvahdjukaar.dummmmmmy.configs.ClientConfigs;
import net.mehvahdjukaar.dummmmmmy.configs.CommonConfigs;
import net.mehvahdjukaar.dummmmmmy.configs.CritMode;
import net.mehvahdjukaar.moonlight.api.platform.network.ChannelHandler;
import net.mehvahdjukaar.moonlight.api.platform.network.Message;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ClientBoundDamageNumberMessage implements Message {
    private final int entityID;
    private final float damageAmount;
    private final ResourceLocation damageType;
    private final boolean isCrit;
    private final float critMult;

    public ClientBoundDamageNumberMessage(FriendlyByteBuf buf) {
        this.entityID = buf.readInt();
        this.damageAmount = buf.readFloat();
        this.damageType = buf.readResourceLocation();
        this.isCrit = buf.readBoolean();
        this.critMult = isCrit ? buf.readFloat() : 0;
    }

    public ClientBoundDamageNumberMessage(int id, float damage, DamageSource source, @Nullable CritRecord critical) {
        this(id, damage, encodeDamage(source), critical != null, critical == null ? 0 : critical.getMultiplier());
    }

    public static ResourceLocation encodeDamage(DamageSource source) {
        if (source == null) return Dummmmmmy.TRUE_DAMAGE;
        //if (critical) return Dummmmmmy.CRITICAL_DAMAGE;
        DamageType damageType = source.type();
        if(damageType == null) throw new AssertionError("Damage source has null type. How?: " + source);
        var id = Utils.hackyGetRegistry(Registries.DAMAGE_TYPE).getKey(damageType);
        if (id == null) throw new AssertionError("Damage type not found in registry. This is a bug from that mod that added it!: " + damageType);
        return id;
    }

    protected ClientBoundDamageNumberMessage(int id, float damage, ResourceLocation damageType, boolean isCrit, float critMult) {
        this.entityID = id;
        this.damageAmount = damage;
        this.damageType = damageType;
        this.isCrit = isCrit;
        this.critMult = critMult;
    }

    @Override
    public void writeToBuffer(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeFloat(this.damageAmount);
        buf.writeResourceLocation(this.damageType);
        buf.writeBoolean(this.isCrit);
        if (isCrit) buf.writeFloat(this.critMult);
    }

    @Override
    public void handle(ChannelHandler.Context context) {
        Entity entity = Minecraft.getInstance().level.getEntity(this.entityID);
        if (entity instanceof TargetDummyEntity dummy) {
            if (ClientConfigs.DAMAGE_NUMBERS.get()) {
                int i = dummy.getNextNumberPos();
                spawnParticle(entity, i);
            }
        } else if (entity != null) {
            spawnParticle(entity, 0);
        }
    }

    private void spawnParticle(Entity entity, int animationPos) {
        ResourceLocation type = damageType;
        float mult = 0;
        CritMode critMode = ClientConfigs.CRIT_MODE.get();
        if (critMode != CritMode.OFF && isCrit) {
            type = Dummmmmmy.CRITICAL_DAMAGE;
            if (critMode == CritMode.COLOR_AND_MULTIPLIER) {
               mult = critMult;
            }
        }
        double z = CritMode.encodeIntFloatToDouble(animationPos, mult);
        int color = ClientConfigs.getDamageColor(type);

        entity.level().addParticle(Dummmmmmy.NUMBER_PARTICLE.get(),
                entity.getX(), entity.getY() + 1, entity.getZ(), damageAmount, color, z);
    }


}

