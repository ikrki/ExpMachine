package mod.ikrki.expmachine;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ExpMachineTileEntity extends TileEntity implements ITickableTileEntity, IEnergyStorage
{
	private int xp;
	private int energy;
	private final int maxEnergy=2048000;
	private final int maxXp=102400;
	public ExpMachineTileEntity()
	{
		super(ExpMachine.exp_machine_te);
	}
	public int getXp(){return xp;}
	public void removeXp() {xp=0;}
	@Override
	public CompoundNBT write(CompoundNBT tag) {
		tag.putInt("xp", xp);
		tag.putInt("fe",energy);
		return super.write(tag);
	}
	@Override
	public void read(BlockState state, CompoundNBT tag) {
		xp=tag.getInt("xp");
		energy=tag.getInt("fe");
		super.read(state, tag);
	}
	@Override
	public void tick() {
		if(!world.isRemote) {
			if (energy >= 10240 && xp < maxXp) {
				xp=Math.min(maxXp, xp + 10);
				energy -= 10240;
			}
		}
	}
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int energyReceived=Math.min(maxReceive,maxEnergy-energy);
		if(!simulate)
			this.energy += energyReceived;
		return energyReceived;
	}
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}
	@Override
	public int getEnergyStored() {
		return energy;
	}
	@Override
	public int getMaxEnergyStored() {
		return maxEnergy;
	}
	@Override
	public boolean canExtract() {
		return false;
	}
	@Override
	public boolean canReceive() {
		return true;
	}
	@Override
	public @Nonnull <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		LazyOptional<IEnergyStorage> lazy=LazyOptional.of(() -> this);
		if(cap==CapabilityEnergy.ENERGY)
			return lazy.cast();
		return super.getCapability(cap,side);
	}
}
