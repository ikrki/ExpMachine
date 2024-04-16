package mod.ikrki.expmachine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;


public class ExpMachineBlock extends Block {


	public ExpMachineBlock() {
		super(Block.Properties.create(Material.IRON).hardnessAndResistance(12.5F, 2000.0F).sound(SoundType.METAL));
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		TileEntity tile = world.getTileEntity(pos);

		if (tile instanceof ExpMachineTileEntity) {
			if (!world.isRemote) {
				ExpMachineTileEntity te = (ExpMachineTileEntity) tile;
				if (player.isSneaking()) {
					ExperienceOrbEntity orb = new ExperienceOrbEntity(world, player.getPosX(), player.getPosY(), player.getPosZ(), te.getXp());
					world.addEntity(orb);
					te.removeXp();
					return ActionResultType.SUCCESS;
				}
				else {
					player.sendStatusMessage(new StringTextComponent("EXP: "+Integer.toString(te.getXp())+" FE: "+Integer.toString(te.getEnergyStored())),true);
				}
			}
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (world.isRemote || !stack.hasTag())
			return;

		TileEntity te = world.getTileEntity(pos);

		if (te instanceof ExpMachineTileEntity) {
			CompoundNBT tag = stack.getTag().getCompound("Tag");
			tag.putInt("x",pos.getX());
			tag.putInt("y",pos.getY());
			tag.putInt("z",pos.getZ());
			tag.putInt("xp",tag.getInt("xp"));
			tag.putInt("fe",tag.getInt("fe"));
			te.read(state, tag);
		}
	}
	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		ItemStack istack = new ItemStack(asItem());
		CompoundNBT stackTag = new CompoundNBT();
		stackTag.put("Tag", te.write(new CompoundNBT()));
		istack.setTag(stackTag);
		spawnAsEntity(worldIn,pos,istack);
	}
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ExpMachineTileEntity();
	}
}
