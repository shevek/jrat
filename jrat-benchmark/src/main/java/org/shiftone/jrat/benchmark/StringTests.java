package org.shiftone.jrat.benchmark;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class StringTests {


    private static final String S10 = makeString(10);
    private static final String S100 = makeString(100);
    private static final String S1000 = makeString(1000);
    private static final String S10000 = makeString(10000);

    private static String makeString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0 ; i < length ; i ++) {
            sb.append(Integer.toHexString(i % 15));
        }
        return sb.toString();
    }

    @Benchmark(title = "concat 10+10")
    public void concat10x10() {
        String r = S10 + S10;
    }

    @Benchmark(title = "concat 100+100")
    public void concat100x100() {
        String r = S100 + S100;
    }

    @Benchmark(title = "concat 1000+1000")
    public void concat1000x1000() {
        String r = S1000 + S1000;
    }

    @Benchmark(title = "concat 10000+10000")
    public void concat10000x10000() {
        String r = S10000 + S10000;
    }

    @Benchmark(title = "concat 10000+10")
    public void concat10000x10() {
        String r = S10000 + S10;
    }

    @Benchmark(title = "concat 10+10000")
    public void concat10x10000() {
        String r = S10 + S10000;
    }
}
