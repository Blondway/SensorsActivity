package com.md.sensorsactivity;

class WekaClassifier {

    public static double classify(Object[] i)
            throws Exception {

        double p = Double.NaN;
        p = WekaClassifier.N5a84c1db0(i);
        return p;
    }
    static double N5a84c1db0(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() <= 3.4458938445215384) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() > 3.4458938445215384) {
            p = 0;
        }
        return p;
    }
}

