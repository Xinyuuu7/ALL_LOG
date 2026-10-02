package Xinyuiii.AbandonedCamp;

import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.version.MCVersion;
import dev.xpple.cubiomes.Cubiomes;
import dev.xpple.cubiomes.CubiomesInit;
import dev.xpple.cubiomes.Generator;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public final class AbandonedCampGenerator {
    public static final int VERSION = Cubiomes.MC_26_3();

    private static final int CAMPSITE_COUNT = 3 * 15 + 4;

    private AbandonedCampGenerator() {}

    public static String[] getPieces(long seed, int chunkX, int chunkZ) {
        ensureGenerator(seed);
        String dir = biomeDirectory(Cubiomes.getBiomeAt(biomeGenerator, 4, ((chunkX << 4) + 8) >> 2, 320 >> 2, ((chunkZ << 4) + 8) >> 2));
        ChunkRand rand = new ChunkRand();
        rand.setCarverSeed(seed, chunkX, chunkZ, MCVersion.v1_21); // mc_core only supports up to version 1.21
        rand.nextInt(4);
        int tentType = 1 + rand.nextInt(10);
        int campType = 1 + campsite(rand);
        return new String[] {
            "abandoned_camp/tent/" + dir + "/tent_" + dir + "_" + tentType,
            campsiteName(dir, campType),
        };
    }

    private static String campsiteName(String biomeDir, int campType) {
        if (campType <= 15) {
            return "abandoned_camp/camp/default/campsite_default_chest_" + campType;
        }
        if (campType <= 30) {
            return "abandoned_camp/camp/default/campsite_default_barrel_" + (campType - 15);
        }
        if (campType <= 45) {
            return "abandoned_camp/camp/default/campsite_default_special_" + (campType - 30);
        }
        return "abandoned_camp/camp/" + biomeDir + "/campsite_" + biomeDir + "_" + (campType - 45);
    }

    private static int campsite(ChunkRand rand) {
        int[] positions = new int[CAMPSITE_COUNT];
        for (int i = 0; i < CAMPSITE_COUNT; i++) {
            positions[i] = i;
        }
        for (int i = CAMPSITE_COUNT; i > 1; --i) {
            int swapTo = rand.nextInt(i);
            int tmp = positions[swapTo];
            positions[swapTo] = positions[i - 1];
            positions[i - 1] = tmp;
        }
        return positions[0];
    }

    private static String biomeDirectory(int biome) {
        return switch (biome) {
            case 4 -> "forest";
            case 5 -> "taiga";
            case 6 -> "swamp";
            case 23 -> "sparse_jungle";
            case 27 -> "birch_forest";
            case 30 -> "snowy_taiga";
            case 32 -> "old_growth_pine_taiga";
            case 34 -> "windswept_forest";
            case 35 -> "savanna";
            case 38 -> "wooded_badlands";
            case 132 -> "flower_forest";
            case 155 -> "old_growth_birch_forest";
            case 160 -> "old_growth_spruce_taiga";
            case 168 -> "bamboo_jungle";
            case 177 -> "meadow";
            case 185 -> "cherry_grove";
            case 186 -> "pale_garden";
            case 188 -> "dappled_forest";
            default -> "forest";
        };
    }

    private static boolean loaded;
    private static Arena biomeArena;
    private static MemorySegment biomeGenerator;
    private static long biomeSeed = Long.MIN_VALUE;

    private static synchronized void ensureGenerator(long seed) {
        if (!loaded) {
            CubiomesInit.load();
            loaded = true;
        }
        if (biomeGenerator == null || biomeSeed != seed) {
            if (biomeArena != null) {
                biomeArena.close();
            }
            biomeArena = Arena.ofShared();
            biomeGenerator = Generator.allocate(biomeArena);
            Cubiomes.setupGenerator(biomeGenerator, VERSION, 0);
            Cubiomes.applySeed(biomeGenerator, Cubiomes.DIM_OVERWORLD(), seed);
            biomeSeed = seed;
        }
    }
}