package Xinyuiii.Bastion.enumType;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.data.Pair;

import Xinyuiii.Bastion.reecriture.BastionPools.BridgeJigsawBlocks;
import Xinyuiii.Bastion.reecriture.BastionPools.BridgePool;
import Xinyuiii.Bastion.reecriture.BastionPools.HousingJigsawBlocks;
import Xinyuiii.Bastion.reecriture.BastionPools.HousingPool;
import Xinyuiii.Bastion.reecriture.BastionPools.JigsawBlock;
import Xinyuiii.Bastion.reecriture.BastionPools.StableJigsawBlocks;
import Xinyuiii.Bastion.reecriture.BastionPools.StablePool;
import Xinyuiii.Bastion.reecriture.BastionPools.TreasureJigsawBlocks;
import Xinyuiii.Bastion.reecriture.BastionPools.TreasurePool;

public enum BastionType {
    HOUSING,
    STABLES,
    TREASURE,
    BRIDGE;

    public static BastionType getRandom(ChunkRand rand) {
        return rand.getRandom(BastionType.values());
    }

    public HashMap<String, List<JigsawBlock>> getJigsawBlocks() {
        switch(this) {
            case HOUSING:
                return HousingJigsawBlocks.JIGSAW_BLOCKS;
            case STABLES:
                return StableJigsawBlocks.JIGSAW_BLOCKS;
            case TREASURE:
                return TreasureJigsawBlocks.JIGSAW_BLOCKS;
            case BRIDGE:
                return BridgeJigsawBlocks.JIGSAW_BLOCKS;
        }
        return null;
    }

    public EnumMap< PoolType, Pair<PoolType, List<Pair<String, Integer>>> > getPool() {
        switch(this) {
            case HOUSING:
                return HousingPool.BASTION_POOLS;
            case STABLES:
                return StablePool.BASTION_POOLS;
            case TREASURE:
                return TreasurePool.BASTION_POOLS;
            case BRIDGE:
                return BridgePool.BASTION_POOLS;
        }
        return null;
    }

    public List<Pair<String, Integer>> getStartTemplates() {
        switch(this) {
            case HOUSING:
                return HousingPool.BASTION_POOLS.get(PoolType.HOUSING_BASE).getSecond() ;
            case STABLES:
                return StablePool.BASTION_POOLS.get(PoolType.STABLE_ORIGIN).getSecond();
            case TREASURE:
                return TreasurePool.BASTION_POOLS.get(PoolType.TREASURE_STARTERS).getSecond();
            case BRIDGE:
                return BridgePool.BASTION_POOLS.get(PoolType.BRIDGE_START).getSecond();
        }
        return null;
    }
}