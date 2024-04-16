package mod.ikrki.expmachine;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ExpMachineItem extends BlockItem
{
	private static final Style STYLE = Style.EMPTY.applyFormatting(TextFormatting.GRAY);

	public ExpMachineItem(Block block)
	{
		super(block, new Item.Properties().group(ItemGroup.MISC));
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag)
	{
		if(!stack.hasTag())
		{
			tooltip.add(new TranslationTextComponent("info.expmachine.xp", 0).setStyle(STYLE));
		}
		else
		{
			int storedXP = stack.getTag().getCompound("Tag").getInt("xp");
			int storedFE = stack.getTag().getCompound("Tag").getInt("fe");
			tooltip.add(new TranslationTextComponent("info.expmachine.xp", storedXP).setStyle(STYLE));
			tooltip.add(new TranslationTextComponent("info.expmachine.fe", storedFE).setStyle(STYLE));
		}
	}
}
