package org.shiftone.jrat.benchmark;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public abstract class FormatTests {

//    private final NumberFormat instance = DecimalFormat.getInstance();
//    private final NumberFormat currencyInstance = DecimalFormat.getCurrencyInstance();
//    private final NumberFormat percentInstance = DecimalFormat.getPercentInstance();
    //    private final NumberFormat integerInstance  = DecimalFormat.getIntegerInstance();
    //    private final NumberFormat numberInstance = DecimalFormat.getNumberInstance();
    //
    private final NumberFormat numberFormat;
    public static final Class[] CLASSES = {
            GetInstance.class,
            GetNumberInstance.class,
            GetIntegerInstance.class,
            GetPercentInstance.class,
            GetCurrencyInstance.class};

    //
    public static class GetInstance extends FormatTests {

        public GetInstance() {
            super(DecimalFormat.getInstance());
        }
    }

    public static class GetNumberInstance extends FormatTests {

        public GetNumberInstance() {
            super(DecimalFormat.getNumberInstance());
        }
    }


    public static class GetIntegerInstance extends FormatTests {

        public GetIntegerInstance() {
            super(DecimalFormat.getIntegerInstance());
        }
    }

    public static class GetPercentInstance extends FormatTests {

        public GetPercentInstance() {
            super(DecimalFormat.getPercentInstance());
        }
    }

    public static class GetCurrencyInstance extends FormatTests {

        public GetCurrencyInstance() {
            super(DecimalFormat.getCurrencyInstance());
        }
    }

    private static final String N1 = "10.0";

    public FormatTests(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }


    @Benchmark(title = "parse " + N1)
    public void parseN1() throws Exception {
        numberFormat.parse(N1);
    }


}
