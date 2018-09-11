package com.md.sensorsactivity;

class WekaClassifier {

    private static double RtoP(double []R, int j) {
        double Rcenter = 0;
        for (int i = 0; i < R.length; i++) {
            Rcenter += R[i];
        }
        Rcenter /= R.length;
        double Rsum = 0;
        for (int i = 0; i < R.length; i++) {
            Rsum += Math.exp(R[i] - Rcenter);
        }
        return Math.exp(R[j]) / Rsum;
    }

    public static double classify(Object[] i) {
        double [] d = distribution(i);
        double maxV = d[0];
        int maxI = 0;
        for (int j = 1; j < 4; j++) {
            if (d[j] > maxV) { maxV = d[j]; maxI = j; }
        }
        return (double) maxI;
    }

    public static double [] distribution(Object [] i) {
        double [] Fs = new double [4];
        double [] Fi = new double [4];
        Fs[0] = 0.0;
        Fs[1] = 0.0;
        Fs[2] = 0.0;
        Fs[3] = 0.0;
        double Fsum;
        Fsum = 0;
        Fi[0] = 1.0 * WekaClassifier_0_0.classify(i); Fsum += Fi[0];
        Fi[1] = 1.0 * WekaClassifier_1_0.classify(i); Fsum += Fi[1];
        Fi[2] = 1.0 * WekaClassifier_2_0.classify(i); Fsum += Fi[2];
        Fi[3] = 1.0 * WekaClassifier_3_0.classify(i); Fsum += Fi[3];
        Fsum /= 4;
        for (int j = 0; j < 4; j++) { Fs[j] += (Fi[j] - Fsum) * 3 / 4; }
        Fsum = 0;
        Fi[0] = 1.0 * WekaClassifier_0_1.classify(i); Fsum += Fi[0];
        Fi[1] = 1.0 * WekaClassifier_1_1.classify(i); Fsum += Fi[1];
        Fi[2] = 1.0 * WekaClassifier_2_1.classify(i); Fsum += Fi[2];
        Fi[3] = 1.0 * WekaClassifier_3_1.classify(i); Fsum += Fi[3];
        Fsum /= 4;
        for (int j = 0; j < 4; j++) { Fs[j] += (Fi[j] - Fsum) * 3 / 4; }
        Fsum = 0;
        Fi[0] = 1.0 * WekaClassifier_0_2.classify(i); Fsum += Fi[0];
        Fi[1] = 1.0 * WekaClassifier_1_2.classify(i); Fsum += Fi[1];
        Fi[2] = 1.0 * WekaClassifier_2_2.classify(i); Fsum += Fi[2];
        Fi[3] = 1.0 * WekaClassifier_3_2.classify(i); Fsum += Fi[3];
        Fsum /= 4;
        for (int j = 0; j < 4; j++) { Fs[j] += (Fi[j] - Fsum) * 3 / 4; }
        Fsum = 0;
        Fi[0] = 1.0 * WekaClassifier_0_3.classify(i); Fsum += Fi[0];
        Fi[1] = 1.0 * WekaClassifier_1_3.classify(i); Fsum += Fi[1];
        Fi[2] = 1.0 * WekaClassifier_2_3.classify(i); Fsum += Fi[2];
        Fi[3] = 1.0 * WekaClassifier_3_3.classify(i); Fsum += Fi[3];
        Fsum /= 4;
        for (int j = 0; j < 4; j++) { Fs[j] += (Fi[j] - Fsum) * 3 / 4; }
        Fsum = 0;
        Fi[0] = 1.0 * WekaClassifier_0_4.classify(i); Fsum += Fi[0];
        Fi[1] = 1.0 * WekaClassifier_1_4.classify(i); Fsum += Fi[1];
        Fi[2] = 1.0 * WekaClassifier_2_4.classify(i); Fsum += Fi[2];
        Fi[3] = 1.0 * WekaClassifier_3_4.classify(i); Fsum += Fi[3];
        Fsum /= 4;
        for (int j = 0; j < 4; j++) { Fs[j] += (Fi[j] - Fsum) * 3 / 4; }
        Fsum = 0;
        Fi[0] = 1.0 * WekaClassifier_0_5.classify(i); Fsum += Fi[0];
        Fi[1] = 1.0 * WekaClassifier_1_5.classify(i); Fsum += Fi[1];
        Fi[2] = 1.0 * WekaClassifier_2_5.classify(i); Fsum += Fi[2];
        Fi[3] = 1.0 * WekaClassifier_3_5.classify(i); Fsum += Fi[3];
        Fsum /= 4;
        for (int j = 0; j < 4; j++) { Fs[j] += (Fi[j] - Fsum) * 3 / 4; }
        Fsum = 0;
        Fi[0] = 1.0 * WekaClassifier_0_6.classify(i); Fsum += Fi[0];
        Fi[1] = 1.0 * WekaClassifier_1_6.classify(i); Fsum += Fi[1];
        Fi[2] = 1.0 * WekaClassifier_2_6.classify(i); Fsum += Fi[2];
        Fi[3] = 1.0 * WekaClassifier_3_6.classify(i); Fsum += Fi[3];
        Fsum /= 4;
        for (int j = 0; j < 4; j++) { Fs[j] += (Fi[j] - Fsum) * 3 / 4; }
        Fsum = 0;
        Fi[0] = 1.0 * WekaClassifier_0_7.classify(i); Fsum += Fi[0];
        Fi[1] = 1.0 * WekaClassifier_1_7.classify(i); Fsum += Fi[1];
        Fi[2] = 1.0 * WekaClassifier_2_7.classify(i); Fsum += Fi[2];
        Fi[3] = 1.0 * WekaClassifier_3_7.classify(i); Fsum += Fi[3];
        Fsum /= 4;
        for (int j = 0; j < 4; j++) { Fs[j] += (Fi[j] - Fsum) * 3 / 4; }
        Fsum = 0;
        Fi[0] = 1.0 * WekaClassifier_0_8.classify(i); Fsum += Fi[0];
        Fi[1] = 1.0 * WekaClassifier_1_8.classify(i); Fsum += Fi[1];
        Fi[2] = 1.0 * WekaClassifier_2_8.classify(i); Fsum += Fi[2];
        Fi[3] = 1.0 * WekaClassifier_3_8.classify(i); Fsum += Fi[3];
        Fsum /= 4;
        for (int j = 0; j < 4; j++) { Fs[j] += (Fi[j] - Fsum) * 3 / 4; }
        Fsum = 0;
        Fi[0] = 1.0 * WekaClassifier_0_9.classify(i); Fsum += Fi[0];
        Fi[1] = 1.0 * WekaClassifier_1_9.classify(i); Fsum += Fi[1];
        Fi[2] = 1.0 * WekaClassifier_2_9.classify(i); Fsum += Fi[2];
        Fi[3] = 1.0 * WekaClassifier_3_9.classify(i); Fsum += Fi[3];
        Fsum /= 4;
        for (int j = 0; j < 4; j++) { Fs[j] += (Fi[j] - Fsum) * 3 / 4; }
        double [] dist = new double [4];
        for (int j = 0; j < 4; j++) {
            dist[j] = RtoP(Fs, j);
        }
        return dist;
    }
}
class WekaClassifier_0_0 {
    public static double classify(Object[] i) {
        /* data33 */
        if (i[32] == null) { return 6.838973831690979E-16; } else if (((Double)i[32]).doubleValue() <= 62.945775651) { return -1.333333333333335; } else { return 2.9999999999999982; }
    }
}
class WekaClassifier_0_1 {
    public static double classify(Object[] i) {
        /* data33 */
        if (i[32] == null) { return -0.20009327580198538; } else if (((Double)i[32]).doubleValue() <= 62.945775651) { return -1.1537228475460983; } else { return 1.2578272866887918; }
    }
}
class WekaClassifier_0_2 {
    public static double classify(Object[] i) {
        /* data33 */
        if (i[32] == null) { return 0.19595267621814316; } else if (((Double)i[32]).doubleValue() <= 62.945775651) { return -1.043549378693492; } else { return 1.1336418472790026; }
    }
}
class WekaClassifier_0_3 {
    public static double classify(Object[] i) {
        /* data33 */
        if (i[32] == null) { return 0.3237515732762958; } else if (((Double)i[32]).doubleValue() <= 62.945775651) { return -1.0186386165352508; } else { return 1.0636717507371878; }
    }
}
class WekaClassifier_0_4 {
    public static double classify(Object[] i) {
        /* data33 */
        if (i[32] == null) { return 0.09287690146920455; } else if (((Double)i[32]).doubleValue() <= 62.945775651) { return -1.006819887422941; } else { return 1.012972648566035; }
    }
}
class WekaClassifier_0_5 {
    public static double classify(Object[] i) {
        /* data33 */
        if (i[32] == null) { return 0.32779849519405535; } else if (((Double)i[32]).doubleValue() <= 62.945775651) { return -1.003087551534576; } else { return 1.0083675867196955; }
    }
}
class WekaClassifier_0_6 {
    public static double classify(Object[] i) {
        /* data33 */
        if (i[32] == null) { return 0.4478509440563192; } else if (((Double)i[32]).doubleValue() <= 62.945775651) { return -1.001163740543267; } else { return 1.0050408837906977; }
    }
}
class WekaClassifier_0_7 {
    public static double classify(Object[] i) {
        /* data33 */
        if (i[32] == null) { return 0.5249111459560831; } else if (((Double)i[32]).doubleValue() <= 62.945775651) { return -1.0003779551393948; } else { return 1.0024765695520468; }
    }
}
class WekaClassifier_0_8 {
    public static double classify(Object[] i) {
        /* data33 */
        if (i[32] == null) { return 0.6705150550999551; } else if (((Double)i[32]).doubleValue() <= 62.945775651) { return -1.0001965092817673; } else { return 1.0015078518707174; }
    }
}
class WekaClassifier_0_9 {
    public static double classify(Object[] i) {
        /* data33 */
        if (i[32] == null) { return 0.8092243845160331; } else if (((Double)i[32]).doubleValue() <= 62.945775651) { return -1.0000814358328178; } else { return 1.001110058602224; }
    }
}
class WekaClassifier_1_0 {
    public static double classify(Object[] i) {
        /* data12 */
        if (i[11] == null) { return 5.950795411990849E-16; } else if (((Double)i[11]).doubleValue() <= 30.87112969696856) { return -0.8945147679324918; } else { return 2.4090909090909105; }
    }
}
class WekaClassifier_1_1 {
    public static double classify(Object[] i) {
        /* data21 */
        if (i[20] == null) { return -0.010918137310806646; } else if (((Double)i[20]).doubleValue() <= 31.80909707245071) { return -0.8054042968967704; } else { return 2.047842546671922; }
    }
}
class WekaClassifier_1_2 {
    public static double classify(Object[] i) {
        /* data28 */
        if (i[27] == null) { return 0.01464927751341984; } else if (((Double)i[27]).doubleValue() <= 8.243113255115421) { return 0.7232529315045657; } else { return -1.2834023456782604; }
    }
}
class WekaClassifier_1_3 {
    public static double classify(Object[] i) {
        /* data5 */
        if (i[4] == null) { return -0.22837163478373512; } else if (((Double)i[4]).doubleValue() <= 7.647374951756019) { return 0.46033230000619807; } else { return -1.1188419482098881; }
    }
}
class WekaClassifier_1_4 {
    public static double classify(Object[] i) {
        /* data2 */
        if (i[1] == null) { return 0.09856318345920366; } else if (((Double)i[1]).doubleValue() <= 7.912958878373608) { return 0.6682949529716722; } else { return -0.831403476133606; }
    }
}
class WekaClassifier_1_5 {
    public static double classify(Object[] i) {
        /* data22 */
        if (i[21] == null) { return -0.04906798244894377; } else if (((Double)i[21]).doubleValue() <= 40.08180046499548) { return -0.6013638596886655; } else { return 1.0523172247025365; }
    }
}
class WekaClassifier_1_6 {
    public static double classify(Object[] i) {
        /* data13 */
        if (i[12] == null) { return 0.08483753699736993; } else if (((Double)i[12]).doubleValue() <= 22.58380922727819) { return -0.7031003031332593; } else { return 0.8914252716103469; }
    }
}
class WekaClassifier_1_7 {
    public static double classify(Object[] i) {
        /* data64 */
        if (i[63] == null) { return 0.028254310585249267; } else if (((Double)i[63]).doubleValue() <= 4.332541284095264) { return 0.7319420255157567; } else { return -1.044455103211339; }
    }
}
class WekaClassifier_1_8 {
    public static double classify(Object[] i) {
        /* data18 */
        if (i[17] == null) { return -0.2927988255986717; } else if (((Double)i[17]).doubleValue() <= 9.090327742145277) { return 0.5460388363486853; } else { return -0.919635786130214; }
    }
}
class WekaClassifier_1_9 {
    public static double classify(Object[] i) {
        /* data12 */
        if (i[11] == null) { return -0.2374550826244331; } else if (((Double)i[11]).doubleValue() <= 28.320582678772045) { return -0.7956692421636434; } else { return 0.8845408384597421; }
    }
}
class WekaClassifier_2_0 {
    public static double classify(Object[] i) {
        /* data62 */
        if (i[61] == null) { return 3.2862601528904683E-16; } else if (((Double)i[61]).doubleValue() <= 6.793913992759864) { return -0.3737024221453289; } else { return 2.999999999999994; }
    }
}
class WekaClassifier_2_1 {
    public static double classify(Object[] i) {
        /* data29 */
        if (i[28] == null) { return 0.3666372562431076; } else if (((Double)i[28]).doubleValue() <= 7.174517140553248) { return -1.052816032353059; } else { return 1.5101702190742088; }
    }
}
class WekaClassifier_2_2 {
    public static double classify(Object[] i) {
        /* data4 */
        if (i[3] == null) { return 0.01612704251306423; } else if (((Double)i[3]).doubleValue() <= 9.872363568878704) { return -0.6621819475855385; } else { return 0.6485478785323863; }
    }
}
class WekaClassifier_2_3 {
    public static double classify(Object[] i) {
        /* data33 */
        if (i[32] == null) { return 0.09231900987297033; } else if (((Double)i[32]).doubleValue() <= 62.945775651) { return 0.49491268082121526; } else { return -1.0572041844588231; }
    }
}
class WekaClassifier_2_4 {
    public static double classify(Object[] i) {
        /* data16 */
        if (i[15] == null) { return -0.11364451246285576; } else if (((Double)i[15]).doubleValue() <= 9.09032774214528) { return -0.8358462423032502; } else { return 0.53930318471605; }
    }
}
class WekaClassifier_2_5 {
    public static double classify(Object[] i) {
        /* data22 */
        if (i[21] == null) { return -0.07603248726744909; } else if (((Double)i[21]).doubleValue() <= 22.437836403227337) { return 0.45886297256156255; } else { return -0.8450273762767905; }
    }
}
class WekaClassifier_2_6 {
    public static double classify(Object[] i) {
        /* data13 */
        if (i[12] == null) { return -0.17969179829543014; } else if (((Double)i[12]).doubleValue() <= 22.35891666532082) { return 0.5083774851764269; } else { return -0.8991722797398839; }
    }
}
class WekaClassifier_2_7 {
    public static double classify(Object[] i) {
        /* data64 */
        if (i[63] == null) { return -0.10849464293683078; } else if (((Double)i[63]).doubleValue() <= 4.332541284095264) { return -0.795361142399483; } else { return 0.7843331132293806; }
    }
}
class WekaClassifier_2_8 {
    public static double classify(Object[] i) {
        /* data18 */
        if (i[17] == null) { return 0.13311401496363032; } else if (((Double)i[17]).doubleValue() <= 9.090327742145277) { return -0.7338892600976451; } else { return 0.6025248428738162; }
    }
}
class WekaClassifier_2_9 {
    public static double classify(Object[] i) {
        /* data12 */
        if (i[11] == null) { return 0.024537690050832357; } else if (((Double)i[11]).doubleValue() <= 22.43783640322733) { return 0.48735615085746936; } else { return -0.8939926864854174; }
    }
}
class WekaClassifier_3_0 {
    public static double classify(Object[] i) {
        /* data3 */
        if (i[2] == null) { return 5.773159728050829E-16; } else if (((Double)i[2]).doubleValue() <= 0.6945822886692612) { return 3.0000000000000018; } else { return -1.3333333333333355; }
    }
}
class WekaClassifier_3_1 {
    public static double classify(Object[] i) {
        /* data14 */
        if (i[13] == null) { return -0.2726318792682954; } else if (((Double)i[13]).doubleValue() <= 1.1293545294943113) { return 1.17229709935292; } else { return -1.1541106164741635; }
    }
}
class WekaClassifier_3_2 {
    public static double classify(Object[] i) {
        /* data13 */
        if (i[12] == null) { return -0.395789146458344; } else if (((Double)i[12]).doubleValue() <= 1.734031461247159) { return 1.0340102565760698; } else { return -1.0439204205525754; }
    }
}
class WekaClassifier_3_3 {
    public static double classify(Object[] i) {
        /* data9 */
        if (i[8] == null) { return -0.2767636395503811; } else if (((Double)i[8]).doubleValue() <= 0.6451811786472226) { return 1.015316713672295; } else { return -1.0187674836136216; }
    }
}
class WekaClassifier_3_4 {
    public static double classify(Object[] i) {
        /* data40 */
        if (i[39] == null) { return 0.023159217418376392; } else if (((Double)i[39]).doubleValue() <= 0.47444608376325836) { return 1.0095441882405487; } else { return -1.0068499535790607; }
    }
}
class WekaClassifier_3_5 {
    public static double classify(Object[] i) {
        /* data37 */
        if (i[36] == null) { return 0.24878448757700913; } else if (((Double)i[36]).doubleValue() <= 0.5511953158944001) { return 1.0057265515483316; } else { return -1.0030950403744165; }
    }
}
class WekaClassifier_3_6 {
    public static double classify(Object[] i) {
        /* data9 */
        if (i[8] == null) { return 0.2106032795683428; } else if (((Double)i[8]).doubleValue() <= 0.6451811786472226) { return 1.0019699382071225; } else { return -1.0011651504180812; }
    }
}
class WekaClassifier_3_7 {
    public static double classify(Object[] i) {
        /* data30 */
        if (i[29] == null) { return 0.2575635225459554; } else if (((Double)i[29]).doubleValue() <= 0.6249985086705601) { return 1.0007426417776597; } else { return -1.000378169457526; }
    }
}
class WekaClassifier_3_8 {
    public static double classify(Object[] i) {
        /* data12 */
        if (i[11] == null) { return 0.423091227840796; } else if (((Double)i[11]).doubleValue() <= 0.9030252458478016) { return 1.0004228400014095; } else { return -1.0001965162033575; }
    }
}
class WekaClassifier_3_9 {
    public static double classify(Object[] i) {
        /* data3 */
        if (i[2] == null) { return 0.6059251919164672; } else if (((Double)i[2]).doubleValue() <= 0.6945822886692612) { return 1.0002630621044466; } else { return -1.0000814146916828; }
    }
}
