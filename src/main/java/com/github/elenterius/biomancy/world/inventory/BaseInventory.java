package com.github.elenterius.biomancy.world.inventory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class BaseInventory<T extends IItemHandler & IItemHandlerModifiable & INBTSerializable<CompoundTag>> implements Container, INBTSerializable<CompoundTag> {

	protected T itemHandler;
	protected LazyOptional<IItemHandler> optionalItemHandler;

	protected Predicate<Player> canPlayerAccessInventory = x -> true;
	protected Notify markDirtyNotifier = () -> {};
	protected Consumer<Player> onOpenInventory = player -> {};
	protected Consumer<Player> onCloseInventory = player -> {};

	BaseInventory() {}

	BaseInventory(T itemHandler) {
		this.itemHandler = itemHandler;
		optionalItemHandler = LazyOptional.of(() -> this.itemHandler);
	}

	public T getItemHandler() {
		return itemHandler;
	}

	public LazyOptional<IItemHandler> getOptionalItemHandler() {
		return optionalItemHandler;
	}

	public void invalidate() {
		optionalItemHandler.invalidate();
	}

	public void revive() {
		optionalItemHandler = LazyOptional.of(() -> itemHandler);
	}

	@Override
	public CompoundTag serializeNBT() {
		return itemHandler.serializeNBT();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		itemHandler.deserializeNBT(nbt);
	}

	@Override
	public boolean stillValid(Player player) {
		return canPlayerAccessInventory.test(player);
	}

	@Override
	public void setChanged() {
		markDirtyNotifier.invoke();
	}

	@Override
	public void startOpen(Player player) {
		onOpenInventory.accept(player);
	}

	@Override
	public void stopOpen(Player player) {
		onCloseInventory.accept(player);
	}

	public void setCanPlayerAccessInventory(Predicate<Player> predicate) {
		canPlayerAccessInventory = predicate;
	}

	public void setMarkDirtyNotifier(Notify callback) {
		markDirtyNotifier = callback;
	}

	public void setOpenInventoryConsumer(Consumer<Player> consumer) {
		onOpenInventory = consumer;
	}

	public void setCloseInventoryConsumer(Consumer<Player> consumer) {
		onCloseInventory = consumer;
	}

	@Override
	public int getContainerSize() {
		return itemHandler.getSlots();
	}

	@Override
	public int getMaxStackSize() {
		return itemHandler.getSlotLimit(0);
	}

	@Override
	public boolean isEmpty() {
		for (int i = 0; i < itemHandler.getSlots(); i++) {
			if (!itemHandler.getStackInSlot(i).isEmpty()) return false;
		}
		return true;
	}

	public boolean isFull() {
		for (int i = 0; i < itemHandler.getSlots(); i++) {
			ItemStack stack = itemHandler.getStackInSlot(i);
			if (stack.isEmpty() || stack.getCount() < itemHandler.getSlotLimit(i)) return false;
		}
		return true;
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		return itemHandler.isItemValid(index, stack);
	}

	public boolean doesItemStackFit(int index, ItemStack stack) {
		if (!itemHandler.isItemValid(index, stack)) return false;
		ItemStack remainder = itemHandler.insertItem(index, stack, true);
		return remainder.isEmpty();
	}

	public boolean doesItemStackFit(ItemStack stack) {
		for (int i = 0; i < itemHandler.getSlots(); i++) {
			if (!itemHandler.isItemValid(i, stack)) return false;
			stack = itemHandler.insertItem(i, stack, true);
			if (stack.isEmpty()) return true;
		}
		return false;
	}

	private class SimulatedInventory {

		int slots = itemHandler.getSlots();
		int[] availableSlotSpace = new int[slots];
		ItemStack[] itemInSlot = new ItemStack[slots];

		public SimulatedInventory() {
			for (int i = 0; i < slots; i++) {
				ItemStack stack = itemHandler.getStackInSlot(i);
				itemInSlot[i] = stack;
				availableSlotSpace[i] = itemHandler.getSlotLimit(i) - stack.getCount();
			}
		}

		boolean canInsertAt(int index, ItemStack stack) {
			if (stack.isEmpty() || availableSlotSpace[index] <= 0) return false;
			if (!itemHandler.isItemValid(index, stack)) return false;
			if (itemInSlot[index].isEmpty()) return true;
			return ItemHandlerHelper.canItemStacksStack(stack, itemInSlot[index]);
		}

		ItemStack insertAt(int index, ItemStack stack) {
			if (!canInsertAt(index, stack)) return stack;

			int insertAmount = Math.min(availableSlotSpace[index], stack.getCount());
			availableSlotSpace[index] -= insertAmount;

			if (itemInSlot[index].isEmpty()) itemInSlot[index] = stack;

			return ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - insertAmount); //remainder
		}
	}

	public boolean doAllItemsFit(List<ItemStack> items) {
		SimulatedInventory inv = new SimulatedInventory();

		for (ItemStack stack : items) {
			if (stack.isEmpty()) continue;

			for (int i = 0; i < inv.slots; i++) {
				stack = inv.insertAt(i, stack); //override stack with remainder
			}

			if (!stack.isEmpty()) return false;
		}

		return true;
	}

	public ItemStack insertItemStack(int index, ItemStack stack) {
		return insertItemStack(index, stack, false);
	}

	public ItemStack insertItemStack(int index, ItemStack stack, boolean simulate) {
		return itemHandler.insertItem(index, stack, simulate);
	}

	public ItemStack insertItemStack(ItemStack stack) {
		return insertItemStack(stack, false);
	}

	public ItemStack insertItemStack(ItemStack stack, boolean simulate) {
		for (int i = 0; i < itemHandler.getSlots(); i++) {
			stack = itemHandler.insertItem(i, stack, simulate);
			if (stack.isEmpty()) return ItemStack.EMPTY;
		}
		return stack;
	}

	@Override
	public ItemStack getItem(int index) {
		return itemHandler.getStackInSlot(index);
	}

	@Override
	public ItemStack removeItem(int index, int count) {
		if (count < 0) throw new IllegalArgumentException("Invalid count: " + count);
		return itemHandler.extractItem(index, count, false);
	}

	@Override
	public ItemStack removeItemNoUpdate(int index) {
		int maxStackSize = itemHandler.getSlotLimit(index);
		return itemHandler.extractItem(index, maxStackSize, false);
	}

	@Override
	public void setItem(int index, ItemStack stack) {
		itemHandler.setStackInSlot(index, stack);
	}

	@Override
	public void clearContent() {
		for (int i = 0; i < itemHandler.getSlots(); ++i) {
			itemHandler.setStackInSlot(i, ItemStack.EMPTY);
		}
	}

}
