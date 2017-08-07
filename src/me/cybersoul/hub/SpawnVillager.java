package me.cybersoul.hub;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import net.minecraft.server.v1_12_R1.EntityVillager;
import net.minecraft.server.v1_12_R1.GenericAttributes;

public class SpawnVillager extends EntityVillager {
    
    public SpawnVillager(World world) {
        super(((CraftWorld) world).getHandle());
    }
    
    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0D);
    }
    
   /* @Override
    protected void r() {
    	this.goalSelector.a(10, new PathfinderGoalLookAtPlayer(this, EntityInsentient.class, 8.0F));
    }*/
    
}