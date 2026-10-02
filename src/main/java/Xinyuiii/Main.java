package Xinyuiii;

public class Main {
    static void main(String[] args) {
        long seedMin = 0;
        long seedMax = 1_000_000L;

        int threadCount = (args.length >= 1)
                ? Integer.parseInt(args[0])
                : Runtime.getRuntime().availableProcessors();

        String outputPath = String.format("result%s-%s.txt",
                formatSeed(seedMin), formatSeed(seedMax));

        System.out.println("Seed range: " + seedMin + " ~ " + seedMax);
        System.out.println("Using " + threadCount + " threads");
        System.out.println("Output -> " + outputPath);

        new ALL_LOG(seedMin, seedMax, threadCount, outputPath).run();
    }

    private static String formatSeed(long seed) {
        if (seed == 0) {
            return "0";
        }
        if (seed % 10000 == 0) {
            return (seed / 10000) + "w";
        }
        double w = seed / 10000.0;
        String s = String.format("%.2f", w);
        s = s.replaceAll("0*$", "").replaceAll("\\.$", "");
        return s + "w";
    }
}