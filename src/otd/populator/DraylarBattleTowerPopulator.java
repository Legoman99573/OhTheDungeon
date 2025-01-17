/* 
 * Copyright (C) 2021 shadow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package otd.populator;

import java.util.Random;
import java.util.Set;
import org.bukkit.Chunk;
import org.bukkit.World;
import otd.dungeon.draylar.BattleTowerSchematics;
import otd.util.AsyncLog;
import otd.config.SimpleWorldConfig;
import otd.config.WorldConfig;

/**
 *
 * @author
 */
public class DraylarBattleTowerPopulator implements IPopulator {
    @Override
    public Set<String> getBiomeExclusions(World world) {
        SimpleWorldConfig swc = WorldConfig.wc.dict.get(world.getName());
        return swc.draylar_battletower.biomeExclusions;
    }
    @Override
    public boolean generateDungeon(World world, Random random, Chunk chunk) {
        
        int rx = chunk.getX() * 16 + 7;
        int rz = chunk.getZ() * 16 + 7;
        
        BattleTowerSchematics.place(world, random, rx, rz);
        
        AsyncLog.logMessage("[DraylarBattleTower Dungeon @ " + world.getName() + "] x=" + rx + ", z=" + rz);
        return true;
    }
}
