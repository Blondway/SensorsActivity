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
        /* data1 */
        if (i[0] == null) { return -9.769962616701389E-17; } else if (((Double)i[0]).doubleValue() <= 1500.237993174) { return -1.333333333333335; } else { return 2.999999999999986; }
    }
}
class WekaClassifier_0_1 {
    public static double classify(Object[] i) {
        /* data1 */
        if (i[0] == null) { return 0.08343146336958927; } else if (((Double)i[0]).doubleValue() <= 1500.237993174) { return -1.1291271245395162; } else { return 1.3940402060093602; }
    }
}
class WekaClassifier_0_2 {
    public static double classify(Object[] i) {
        /* data1 */
        if (i[0] == null) { return -0.1856333068838347; } else if (((Double)i[0]).doubleValue() <= 1500.237993174) { return -1.0439109030788916; } else { return 1.0647298578708846; }
    }
}
class WekaClassifier_0_3 {
    public static double classify(Object[] i) {
        /* data1 */
        if (i[0] == null) { return 0.13643254774871386; } else if (((Double)i[0]).doubleValue() <= 1500.237993174) { return -1.0166088666900404; } else { return 1.0514317945096698; }
    }
}
class WekaClassifier_0_4 {
    public static double classify(Object[] i) {
        /* data1 */
        if (i[0] == null) { return 0.3048032829887301; } else if (((Double)i[0]).doubleValue() <= 1500.237993174) { return -1.0056871555524722; } else { return 1.0224846154538811; }
    }
}
class WekaClassifier_0_5 {
    public static double classify(Object[] i) {
        /* data1 */
        if (i[0] == null) { return 0.38486146221307155; } else if (((Double)i[0]).doubleValue() <= 1500.237993174) { return -1.0031394487941567; } else { return 1.0140140735661012; }
    }
}
class WekaClassifier_0_6 {
    public static double classify(Object[] i) {
        /* data1 */
        if (i[0] == null) { return 0.46620455088815904; } else if (((Double)i[0]).doubleValue() <= 1500.237993174) { return -1.0012211001672422; } else { return 1.0072833087402848; }
    }
}
class WekaClassifier_0_7 {
    public static double classify(Object[] i) {
        /* data1 */
        if (i[0] == null) { return 0.5690415200613457; } else if (((Double)i[0]).doubleValue() <= 1500.237993174) { return -1.000540066518936; } else { return 1.0043615756223434; }
    }
}
class WekaClassifier_0_8 {
    public static double classify(Object[] i) {
        /* data1 */
        if (i[0] == null) { return 0.37298389557306355; } else if (((Double)i[0]).doubleValue() <= 1500.237993174) { return -1.0001992027842899; } else { return 1.0009622780208673; }
    }
}
class WekaClassifier_0_9 {
    public static double classify(Object[] i) {
        /* data1 */
        if (i[0] == null) { return 0.6163076858001977; } else if (((Double)i[0]).doubleValue() <= 1500.237993174) { return -1.0000644125981015; } else { return 1.0006621026814524; }
    }
}
class WekaClassifier_1_0 {
    public static double classify(Object[] i) {
        /* data46 */
        if (i[45] == null) { return 3.2862601528904687E-16; } else if (((Double)i[45]).doubleValue() <= 54.02723172405091) { return -0.8480000000000015; } else { return 2.8266666666666618; }
    }
}
class WekaClassifier_1_1 {
    public static double classify(Object[] i) {
        /* data1 */
        if (i[0] == null) { return 0.1456645936373021; } else if (((Double)i[0]).doubleValue() <= 1320.3473185535) { return 1.1314314767253164; } else { return -1.1545404396043248; }
    }
}
class WekaClassifier_1_2 {
    public static double classify(Object[] i) {
        /* data83 */
        if (i[82] == null) { return 0.14061167874675776; } else if (((Double)i[82]).doubleValue() <= 32.93144679907641) { return -0.8858144545422318; } else { return 1.0761407119626198; }
    }
}
class WekaClassifier_1_3 {
    public static double classify(Object[] i) {
        /* data128 */
        if (i[127] == null) { return -0.04569663890292675; } else if (((Double)i[127]).doubleValue() <= 13.286614429762931) { return 0.6466338781150682; } else { return -1.0401509022827808; }
    }
}
class WekaClassifier_1_4 {
    public static double classify(Object[] i) {
        /* data84 */
        if (i[83] == null) { return -0.13200435966712168; } else if (((Double)i[83]).doubleValue() <= 30.33816999513594) { return -0.7323047469397937; } else { return 0.6361521759661055; }
    }
}
class WekaClassifier_1_5 {
    public static double classify(Object[] i) {
        /* data118 */
        if (i[117] == null) { return -0.20543207297717975; } else if (((Double)i[117]).doubleValue() <= 10.347752264154053) { return 0.5130339329206116; } else { return -0.9691588620180779; }
    }
}
class WekaClassifier_1_6 {
    public static double classify(Object[] i) {
        /* data48 */
        if (i[47] == null) { return -0.22559933374053198; } else if (((Double)i[47]).doubleValue() <= 75.00781953716944) { return -0.557827453078588; } else { return 1.008322098348666; }
    }
}
class WekaClassifier_1_7 {
    public static double classify(Object[] i) {
        /* data23 */
        if (i[22] == null) { return -0.18465848420620024; } else if (((Double)i[22]).doubleValue() <= 15.961766150236603) { return 0.44207024930207556; } else { return -1.0040004344345201; }
    }
}
class WekaClassifier_1_8 {
    public static double classify(Object[] i) {
        /* data1 */
        if (i[0] == null) { return 0.05168477457833185; } else if (((Double)i[0]).doubleValue() <= 1320.3473185535) { return 0.5195347746081638; } else { return -1.0014947069878277; }
    }
}
class WekaClassifier_1_9 {
    public static double classify(Object[] i) {
        /* data84 */
        if (i[83] == null) { return -0.11067319135697168; } else if (((Double)i[83]).doubleValue() <= 46.11580368687335) { return -0.47256145795842824; } else { return 0.978370888641096; }
    }
}
class WekaClassifier_2_0 {
    public static double classify(Object[] i) {
        /* data108 */
        if (i[107] == null) { return -7.105427357601015E-16; } else if (((Double)i[107]).doubleValue() <= 13.384870302881946) { return -1.3333333333333357; } else { return 1.3749999999999996; }
    }
}
class WekaClassifier_2_1 {
    public static double classify(Object[] i) {
        /* data1 */
        if (i[0] == null) { return 0.022093420344192143; } else if (((Double)i[0]).doubleValue() <= 1500.237993174) { return 0.5073267816197818; } else { return -1.248864332540557; }
    }
}
class WekaClassifier_2_2 {
    public static double classify(Object[] i) {
        /* data20 */
        if (i[19] == null) { return -0.10601358607392723; } else if (((Double)i[19]).doubleValue() <= 15.524731541012162) { return -0.9689163137064268; } else { return 0.8417098718984972; }
    }
}
class WekaClassifier_2_3 {
    public static double classify(Object[] i) {
        /* data107 */
        if (i[106] == null) { return 0.001650785112354161; } else if (((Double)i[106]).doubleValue() <= 13.817018621489773) { return -0.957505099981145; } else { return 0.6384271117087995; }
    }
}
class WekaClassifier_2_4 {
    public static double classify(Object[] i) {
        /* data27 */
        if (i[26] == null) { return -0.1025120312066948; } else if (((Double)i[26]).doubleValue() <= 24.554941545666054) { return -0.6868019264431967; } else { return 0.523407291910991; }
    }
}
class WekaClassifier_2_5 {
    public static double classify(Object[] i) {
        /* data14 */
        if (i[13] == null) { return 0.028078406923025333; } else if (((Double)i[13]).doubleValue() <= 24.798192583764433) { return -0.6505062355661864; } else { return 0.5962407007597117; }
    }
}
class WekaClassifier_2_6 {
    public static double classify(Object[] i) {
        /* data26 */
        if (i[25] == null) { return -0.05599772477546653; } else if (((Double)i[25]).doubleValue() <= 13.349599398020766) { return -0.9494875017646139; } else { return 0.32332738725517496; }
    }
}
class WekaClassifier_2_7 {
    public static double classify(Object[] i) {
        /* data1 */
        if (i[0] == null) { return -0.08564636792351098; } else if (((Double)i[0]).doubleValue() <= 1500.237993174) { return 0.2519101794082183; } else { return -1.0043320064264698; }
    }
}
class WekaClassifier_2_8 {
    public static double classify(Object[] i) {
        /* data125 */
        if (i[124] == null) { return -0.20035089638434356; } else if (((Double)i[124]).doubleValue() <= 9.2221237283636) { return -0.9719126467113769; } else { return 0.49660905633272284; }
    }
}
class WekaClassifier_2_9 {
    public static double classify(Object[] i) {
        /* data2 */
        if (i[1] == null) { return -0.2296196932140288; } else if (((Double)i[1]).doubleValue() <= 13.286614429762924) { return -0.9180785791889501; } else { return 0.321442779558176; }
    }
}
class WekaClassifier_3_0 {
    public static double classify(Object[] i) {
        /* data4 */
        if (i[3] == null) { return 5.773159728050829E-16; } else if (((Double)i[3]).doubleValue() <= 1.288470873011147) { return 3.0000000000000018; } else { return -1.3333333333333355; }
    }
}
class WekaClassifier_3_1 {
    public static double classify(Object[] i) {
        /* data48 */
        if (i[47] == null) { return -0.3193083040909474; } else if (((Double)i[47]).doubleValue() <= 3.3879879752585342) { return 1.1333473783284809; } else { return -1.130745571147132; }
    }
}
class WekaClassifier_3_2 {
    public static double classify(Object[] i) {
        /* data91 */
        if (i[90] == null) { return 0.10607804734013647; } else if (((Double)i[90]).doubleValue() <= 3.142466989046455) { return 1.0870702696568202; } else { return -1.0443350343234312; }
    }
}
class WekaClassifier_3_3 {
    public static double classify(Object[] i) {
        /* data26 */
        if (i[25] == null) { return -0.0503054009316642; } else if (((Double)i[25]).doubleValue() <= 1.208510007999628) { return 1.0193126183370727; } else { return -1.0167465680345402; }
    }
}
class WekaClassifier_3_4 {
    public static double classify(Object[] i) {
        /* data35 */
        if (i[34] == null) { return 0.23307615068978885; } else if (((Double)i[34]).doubleValue() <= 1.6402197695644727) { return 1.0110840096414155; } else { return -1.0057206819664413; }
    }
}
class WekaClassifier_3_5 {
    public static double classify(Object[] i) {
        /* data119 */
        if (i[118] == null) { return 0.024099722260732866; } else if (((Double)i[118]).doubleValue() <= 1.3854427984574285) { return 1.0029934570951913; } else { return -1.0031482733471098; }
    }
}
class WekaClassifier_3_6 {
    public static double classify(Object[] i) {
        /* data63 */
        if (i[62] == null) { return 0.26434105715266465; } else if (((Double)i[62]).doubleValue() <= 0.8860786842311495) { return 1.001907683342029; } else { return -1.0012223085563257; }
    }
}
class WekaClassifier_3_7 {
    public static double classify(Object[] i) {
        /* data40 */
        if (i[39] == null) { return 0.14636450775285653; } else if (((Double)i[39]).doubleValue() <= 1.9472469419789897) { return 1.0005833216112767; } else { return -1.0005400047587856; }
    }
}
class WekaClassifier_3_8 {
    public static double classify(Object[] i) {
        /* data38 */
        if (i[37] == null) { return 0.4035798703450313; } else if (((Double)i[37]).doubleValue() <= 1.6267780682312631) { return 1.0003802888132185; } else { return -1.0001990582422655; }
    }
}
class WekaClassifier_3_9 {
    public static double classify(Object[] i) {
        /* data4 */
        if (i[3] == null) { return 0.6586266820177843; } else if (((Double)i[3]).doubleValue() <= 1.288470873011147) { return 1.0002588814161328; } else { return -1.0000643464077401; }
    }
}
