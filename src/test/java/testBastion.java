import Xinyuiii.ChestLoot.DecoratorRand;

public class testBastion {
    static void main() {
        long seed = 761079L;
        DecoratorRand rand = new DecoratorRand();
        long populationSeed = rand.getPopulationSeed(seed, 1244 << 4, 110 << 4);
        rand.setDecoratorSeed(populationSeed, 18, 4);
        for (int i = 0; i < 4; i++) {
            System.out.println(rand.nextLong());
        }
        //LootTableSeed:-8709370961531856491L
    }
}