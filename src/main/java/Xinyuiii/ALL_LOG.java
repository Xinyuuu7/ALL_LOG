package Xinyuiii;

import Xinyuiii.RuinedPortal.RuinedPortalFilter;
import Xinyuiii.Utils.FastRand;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.rand.seed.WorldSeed;
import com.seedfinding.mccore.util.math.DistanceMetric;
import com.seedfinding.mccore.util.pos.CPos;
import com.seedfinding.mccore.version.MCVersion;
import dev.xpple.cubiomes.Cubiomes;
import dev.xpple.cubiomes.CubiomesInit;
import dev.xpple.cubiomes.Generator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ALL_LOG {
    private final List<String> ALL_LOG = Arrays.asList("oak_log", "spruce_log", "birch_log", "jungle_log", "acacia_log",
            "dark_oak_log", "mangrove_log", "cherry_log", "pale_oak_log", "crimson_stem", "warped_stem", "poplar_log");

    private final MCVersion version = MCVersion.v1_21;
    private final RuinedPortalFilter portalFilter = new RuinedPortalFilter();

    private final long seedMin;
    private final long seedMax;
    private final int threadCount;
    private final Path outputFile;

    private static final long POISON_PILL = Long.MIN_VALUE;

    public ALL_LOG(long seedMin, long seedMax, int threadCount, String outputPath) {
        this.seedMin = seedMin;
        this.seedMax = seedMax;
        this.threadCount = threadCount;
        this.outputFile = Paths.get(outputPath);
    }

    public void run() {
        CubiomesInit.load();

        BlockingQueue<Long> resultQueue = new LinkedBlockingQueue<>(10000);
        AtomicLong processed = new AtomicLong(0);
        long totalCount = seedMax - seedMin;

        Thread writerThread = new Thread(() -> writeResults(resultQueue), "result-writer");
        writerThread.setDaemon(true);
        writerThread.start();

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        long chunkSize = Math.max(1L, totalCount / threadCount);

        for (int i = 0; i < threadCount; i++) {
            final long start = seedMin + i * chunkSize;
            final long end = (i == threadCount - 1) ? seedMax : Math.min(seedMax, start + chunkSize);
            if (start >= end) {
                latch.countDown();
                continue;
            }
            executor.submit(() -> {
                try (Arena arena = Arena.ofConfined()) {
                    FastRand frand = new FastRand();
                    ChunkRand rand = new ChunkRand();
                    MemorySegment generator = Generator.allocate(arena);
                    Cubiomes.setupGenerator(generator, Cubiomes.MC_26_3(), 0);

                    for (long structureSeed = start; structureSeed < end; structureSeed++) {
                        checkStructureSeed(structureSeed, generator, frand, rand, resultQueue);
                        long p = processed.incrementAndGet();
                        if (p % 100_000 == 0) {
                            System.out.printf("Progress: %d/%d (%.2f%%)%n",
                                    p, totalCount, p * 100.0 / totalCount);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
            resultQueue.put(POISON_PILL);
            writerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
            try {
                executor.awaitTermination(1, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Done. Results written to " + outputFile.toAbsolutePath());
    }

    private void writeResults(BlockingQueue<Long> queue) {
        try (BufferedWriter writer = Files.newBufferedWriter(outputFile,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            int count = 0;
            while (true) {
                long seed = queue.take();
                if (seed == POISON_PILL) break;
                writer.write(Long.toString(seed));
                writer.newLine();
                if (++count % 100 == 0) writer.flush();
            }
            writer.flush();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkStructureSeed(long structureSeed, MemorySegment generator, FastRand frand, ChunkRand rand, BlockingQueue<Long> resultQueue) throws InterruptedException {
        // check bastion near 0,0
        frand.setSeed(structureSeed + 30084232L);
        int bx = frand.nextInt(23);
        int bz = frand.nextInt(23);
        if (bx > 2 || bz > 2) return;
        rand.setCarverSeed(structureSeed, bx, bz, version);
        if (rand.nextInt(5) < 2) return;

        // check ruined_portal's position
        frand.setSeed(structureSeed + 34222645L);
        CPos rp = new CPos(frand.nextInt(25), frand.nextInt(25));
        if (rp.distanceTo(CPos.ZERO, DistanceMetric.CHEBYSHEV) > 12) return;

        // only considering mountains & "other" biomes:
        // swamp & ocean is unenterable, desert is buried, jungle is rare & overgrown
        // assume chest is in the start chunk (~80% accuracy)
        rand.setCarverSeed(structureSeed, rp.getX(), rp.getZ(), version);
        if (rand.nextFloat() < 0.5F) return; // air_pocket
        if (rand.nextFloat() < 0.05F) return; // giant
        int type = rand.nextInt(10);

        // check worldSeed
        for (Long worldSeed : WorldSeed.getSisterSeeds(structureSeed).asStream().boxed()
                .limit(128).toList()) {
            // load cubiomes
            Cubiomes.applySeed(generator, Cubiomes.DIM_OVERWORLD(), worldSeed);
            int biome = Cubiomes.getBiomeAt(generator, 4, rp.getX() << 2, 80, rp.getZ() << 2);

            // check ruined_portal biome & loot
            if (!portalFilter.check(worldSeed, rp, type, biome)) continue;

            // TODO:check biome for all logs






            resultQueue.put(worldSeed);
        }
    }
}