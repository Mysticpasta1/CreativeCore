package com.creativemd.creativecore.common.utils;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class CubeObject {
	
	//public double rotation = 0;
	
	public Block block;
	public int meta = 0;
	public int color = ColorUtils.WHITE;
	
	//public boolean normalBlock = true;
	
	public double minX;
	public double minY;
	public double minZ;
	public double maxX;
	public double maxY;
	public double maxZ;
	
	public CubeObject(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
	{
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}
	
	public CubeObject(AxisAlignedBB box)
	{
		this(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
	}
	
	public CubeObject()
	{
		this(0, 0, 0, 1, 1, 1);
	}
	
	public CubeObject(CubeObject cube)
	{
		this(cube.minX, cube.minY, cube.minZ, cube.maxX, cube.maxY, cube.maxZ, cube);
	}
	
	public CubeObject(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, CubeObject cube)
	{
		this(minX, minY, minZ, maxX, maxY, maxZ);
		this.block = cube.block;
	}
	
	public CubeObject(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Block block)
	{
		this(minX, minY, minZ, maxX, maxY, maxZ);
		this.block = block;
	}
	
	public CubeObject(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Block block, int meta)
	{
		this(minX, minY, minZ, maxX, maxY, maxZ);
		this.block = block;
		this.meta = meta;
	}
	
	/*public CubeObject(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, boolean normalBlock)
	{
		this(minX, minY, minZ, maxX, maxY, maxZ);
		this.normalBlock = normalBlock;
	}*/
	
	public CubeObject setColor(Vec3d color)
	{
		this.setColor(ColorUtils.RGBToInt(color));
		return this;
	}
	
	public CubeObject setColor(int color)
	{
		this.color = color;
		return this;
	}
	
	public Vec3d getSize()
	{
		return new Vec3d(maxX-minX, maxY-minY, maxZ-minZ);
	}
	
	@Override
	public String toString()
    {
        return "cube[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
    }
	
	public AxisAlignedBB getAxis()
	{
		return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	public static Vec3d getSizeOfCubes(ArrayList<CubeObject> cubes)
	{
		if(cubes.size() == 0)
			return new Vec3d(0, 0, 0);
		CubeObject cube = new CubeObject(cubes.get(0));
		for (int i = 1; i < cubes.size(); i++) {
			cube.minX = Math.min(cube.minX, cubes.get(i).minX);
			cube.minY = Math.min(cube.minY, cubes.get(i).minY);
			cube.minZ = Math.min(cube.minZ, cubes.get(i).minZ);
			cube.maxX = Math.max(cube.maxX, cubes.get(i).maxX);
			cube.maxY = Math.max(cube.maxY, cubes.get(i).maxY);
			cube.maxZ = Math.max(cube.maxZ, cubes.get(i).maxZ);
		}
		return cube.getSize();
	}
	
	public static Vec3d getBigestCubeSize(ArrayList<CubeObject> cubes)
	{
		Vec3d size = null;
		for (int i = 0; i < cubes.size(); i++) {
			Vec3d newSize = cubes.get(i).getSize();
			if(size == null)
				size = newSize;
			else
			{
				size = new Vec3d(Math.max(size.xCoord, newSize.xCoord), Math.max(size.yCoord, newSize.yCoord), Math.max(size.zCoord, newSize.zCoord));
			}
		}
		return size;
	}
	
	public static CubeObject rotateCube(CubeObject cube, EnumFacing direction)
	{
		return rotateCube(cube, direction, new Vec3d(0.5, 0.5, 0.5));
	}
	
	public static CubeObject rotateCube(CubeObject cube, EnumFacing direction, Vec3d center)
	{
		return rotateCube(cube, Rotation.getRotationByFacing(direction), center);
	}
	
	public static CubeObject rotateCube(CubeObject cube, Rotation direction, Vec3d center)
	{
		CubeObject rotateCube = new CubeObject(cube);
		RotationUtils.applyCubeRotation(rotateCube, direction, center);
		return rotateCube;
	}
}