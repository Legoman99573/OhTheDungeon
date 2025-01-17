package forge_sandbox.twilightforest.structures.lichtower;

import forge_sandbox.StructureBoundingBox;
import forge_sandbox.twilightforest.TFFeature;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import otd.lib.async.AsyncWorldEditor;


public class ComponentTFTowerRoofGableForwards extends ComponentTFTowerRoof {

    public ComponentTFTowerRoofGableForwards() {
        super();
    }

    public ComponentTFTowerRoofGableForwards(TFFeature feature, int i, ComponentTFTowerWing wing) {
        super(feature, i, wing);

        // same facing
        this.setCoordBaseMode(wing.getCoordBaseMode());

        this.size = wing.size + 2; // assuming only square towers and roofs right now.
        this.height = size;

        // just hang out at the very top of the tower
        this.makeAttachedOverhangBB(wing);

    }

    /**
     * Makes a pointy roof out of stuff
     */
    @Override
    public boolean addComponentParts(AsyncWorldEditor world, Random rand, StructureBoundingBox sbb) {
        BlockData birchSlab = Bukkit.createBlockData(Material.BIRCH_SLAB);
        BlockData birchPlanks = Bukkit.createBlockData(Material.BIRCH_PLANKS);
        
        {
            Slab slab = (Slab) birchSlab;
            slab.setType(Slab.Type.BOTTOM);
            birchSlab = slab;
        }

        int slopeChange = slopeChangeForSize(size);
        for (int y = 0; y <= height; y++) {
            int min, max;
            if (y < slopeChange) {
                min = y;
                max = size - y - 1;
            } else {
                min = (y + slopeChange) / 2;
                max = size - ((y + slopeChange) / 2) - 1;
            }
            for (int x = 0; x <= size - 2; x++) {
                for (int z = min; z <= max; z++) {
                    if (z == min || z == max) {
                        setBlockState(world, birchPlanks, x, y, z, sbb);
                    } else if (x < size - 2) {
                        setBlockState(world, birchPlanks, x, y, z, sbb);
                    }
                }
            }
        }

        // put on the little figurehead-like "cap"

        // where is even the top of our roof?
        int top = (size + 1) - slopeChange;
        int zMid = size / 2;
        
        BlockData slab_top = birchSlab;
        {
            Slab slab = (Slab) birchSlab;
            slab.setType(Slab.Type.TOP);
            birchSlab = slab;
        }

        setBlockState(world, slab_top, size - 1, top - 1, zMid, sbb);
//        setBlockState(world, birchSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP), size - 1, top - 1, zMid, sbb);
        setBlockState(world, birchSlab, 0, top, zMid, sbb);
        setBlockState(world, birchSlab, size - 3, top, zMid, sbb);
        setBlockState(world, birchPlanks, size - 2, top, zMid, sbb);
        setBlockState(world, birchPlanks, size - 1, top, zMid, sbb);
        setBlockState(world, birchPlanks, size - 1, top + 1, zMid, sbb);

        return true;
    }


    public int slopeChangeForSize(int pSize) {
        if (size > 10) {
            return 3;
        } else if (size > 6) {
            return 2;
        } else {
            return 1;
        }
    }
}
