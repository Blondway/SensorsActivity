package com.md.sensorsactivity;

import android.util.Log;

import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class Classification {

    public double runClassification(double[] dataArray) {

        double result = -1;
        Instance inst_co;

        try {

            //Create list of attributes
            ArrayList<Attribute> attributeList = new ArrayList<Attribute>(dataArray.length);

            //Set attributes
            Attribute data1 = new Attribute("data1");
            Attribute data2 = new Attribute("data2");
            Attribute data3 = new Attribute("data3");
            Attribute data4 = new Attribute("data4");
            Attribute data5 = new Attribute("data5");
            Attribute data6 = new Attribute("data6");
            Attribute data7 = new Attribute("data7");
            Attribute data8 = new Attribute("data8");
            Attribute data9 = new Attribute("data9");
            Attribute data10 = new Attribute("data10");
            Attribute data11 = new Attribute("data11");
            Attribute data12 = new Attribute("data12");
            Attribute data13 = new Attribute("data13");
            Attribute data14 = new Attribute("data14");
            Attribute data15 = new Attribute("data15");
            Attribute data16 = new Attribute("data16");
            Attribute data17 = new Attribute("data17");
            Attribute data18 = new Attribute("data18");
            Attribute data19 = new Attribute("data19");
            Attribute data20 = new Attribute("data20");
            Attribute data21 = new Attribute("data21");
            Attribute data22 = new Attribute("data22");
            Attribute data23 = new Attribute("data23");
            Attribute data24 = new Attribute("data24");
            Attribute data25 = new Attribute("data25");
            Attribute data26 = new Attribute("data26");
            Attribute data27 = new Attribute("data27");
            Attribute data28 = new Attribute("data28");
            Attribute data29 = new Attribute("data29");
            Attribute data30 = new Attribute("data30");
            Attribute data31 = new Attribute("data31");
            Attribute data32 = new Attribute("data32");
            Attribute data33 = new Attribute("data33");
            Attribute data34 = new Attribute("data34");
            Attribute data35 = new Attribute("data35");
            Attribute data36 = new Attribute("data36");
            Attribute data37 = new Attribute("data37");
            Attribute data38 = new Attribute("data38");
            Attribute data39 = new Attribute("data39");
            Attribute data40 = new Attribute("data40");
            Attribute data41 = new Attribute("data41");
            Attribute data42 = new Attribute("data42");
            Attribute data43 = new Attribute("data43");
            Attribute data44 = new Attribute("data44");
            Attribute data45 = new Attribute("data45");
            Attribute data46 = new Attribute("data46");
            Attribute data47 = new Attribute("data47");
            Attribute data48 = new Attribute("data48");
            Attribute data49 = new Attribute("data49");
            Attribute data50 = new Attribute("data50");
            Attribute data51 = new Attribute("data51");
            Attribute data52 = new Attribute("data52");
            Attribute data53 = new Attribute("data53");
            Attribute data54 = new Attribute("data54");
            Attribute data55 = new Attribute("data55");
            Attribute data56 = new Attribute("data56");
            Attribute data57 = new Attribute("data57");
            Attribute data58 = new Attribute("data58");
            Attribute data59 = new Attribute("data59");
            Attribute data60 = new Attribute("data60");
            Attribute data61 = new Attribute("data61");
            Attribute data62 = new Attribute("data62");
            Attribute data63 = new Attribute("data63");
            Attribute data64 = new Attribute("data64");

            //Class attribute
            ArrayList<String> classVal = new ArrayList<String>();
            classVal.add("chodzenie");
            classVal.add("stanie");

            //Add attributes to list
            attributeList.add(data1);
            attributeList.add(data2);
            attributeList.add(data3);
            attributeList.add(data4);
            attributeList.add(data5);
            attributeList.add(data6);
            attributeList.add(data7);
            attributeList.add(data8);
            attributeList.add(data9);
            attributeList.add(data10);
            attributeList.add(data11);
            attributeList.add(data12);
            attributeList.add(data13);
            attributeList.add(data14);
            attributeList.add(data15);
            attributeList.add(data16);
            attributeList.add(data17);
            attributeList.add(data18);
            attributeList.add(data19);
            attributeList.add(data20);
            attributeList.add(data21);
            attributeList.add(data22);
            attributeList.add(data23);
            attributeList.add(data24);
            attributeList.add(data25);
            attributeList.add(data26);
            attributeList.add(data27);
            attributeList.add(data28);
            attributeList.add(data29);
            attributeList.add(data30);
            attributeList.add(data31);
            attributeList.add(data32);
            attributeList.add(data33);
            attributeList.add(data34);
            attributeList.add(data35);
            attributeList.add(data36);
            attributeList.add(data37);
            attributeList.add(data38);
            attributeList.add(data39);
            attributeList.add(data40);
            attributeList.add(data41);
            attributeList.add(data42);
            attributeList.add(data43);
            attributeList.add(data44);
            attributeList.add(data45);
            attributeList.add(data46);
            attributeList.add(data47);
            attributeList.add(data48);
            attributeList.add(data49);
            attributeList.add(data50);
            attributeList.add(data51);
            attributeList.add(data52);
            attributeList.add(data53);
            attributeList.add(data54);
            attributeList.add(data55);
            attributeList.add(data56);
            attributeList.add(data57);
            attributeList.add(data58);
            attributeList.add(data59);
            attributeList.add(data60);
            attributeList.add(data61);
            attributeList.add(data62);
            attributeList.add(data63);
            attributeList.add(data64);

            attributeList.add(new Attribute("@@class@@",classVal));

            // Create new dataset
            Instances data = new Instances("TestInstances",attributeList,0);

            // Create new instance
            inst_co = new DenseInstance(data.numAttributes());

            // Set instance's values for attributes
            inst_co.setValue(data1, dataArray[0]);
            inst_co.setValue(data2, dataArray[1]);
            inst_co.setValue(data3, dataArray[2]);
            inst_co.setValue(data4, dataArray[3]);
            inst_co.setValue(data5, dataArray[4]);
            inst_co.setValue(data6, dataArray[5]);
            inst_co.setValue(data7, dataArray[6]);
            inst_co.setValue(data8, dataArray[7]);
            inst_co.setValue(data9, dataArray[8]);
            inst_co.setValue(data10, dataArray[9]);
            inst_co.setValue(data11, dataArray[10]);
            inst_co.setValue(data12, dataArray[11]);
            inst_co.setValue(data13, dataArray[12]);
            inst_co.setValue(data14, dataArray[13]);
            inst_co.setValue(data15, dataArray[14]);
            inst_co.setValue(data16, dataArray[15]);
            inst_co.setValue(data17, dataArray[16]);
            inst_co.setValue(data18, dataArray[17]);
            inst_co.setValue(data19, dataArray[18]);
            inst_co.setValue(data20, dataArray[19]);
            inst_co.setValue(data21, dataArray[20]);
            inst_co.setValue(data22, dataArray[21]);
            inst_co.setValue(data23, dataArray[22]);
            inst_co.setValue(data24, dataArray[23]);
            inst_co.setValue(data25, dataArray[24]);
            inst_co.setValue(data26, dataArray[25]);
            inst_co.setValue(data27, dataArray[26]);
            inst_co.setValue(data28, dataArray[27]);
            inst_co.setValue(data29, dataArray[28]);
            inst_co.setValue(data30, dataArray[29]);
            inst_co.setValue(data31, dataArray[30]);
            inst_co.setValue(data32, dataArray[31]);
            inst_co.setValue(data33, dataArray[32]);
            inst_co.setValue(data34, dataArray[33]);
            inst_co.setValue(data35, dataArray[34]);
            inst_co.setValue(data36, dataArray[35]);
            inst_co.setValue(data37, dataArray[36]);
            inst_co.setValue(data38, dataArray[37]);
            inst_co.setValue(data39, dataArray[38]);
            inst_co.setValue(data40, dataArray[39]);
            inst_co.setValue(data41, dataArray[40]);
            inst_co.setValue(data42, dataArray[41]);
            inst_co.setValue(data43, dataArray[42]);
            inst_co.setValue(data44, dataArray[43]);
            inst_co.setValue(data45, dataArray[44]);
            inst_co.setValue(data46, dataArray[45]);
            inst_co.setValue(data47, dataArray[46]);
            inst_co.setValue(data48, dataArray[47]);
            inst_co.setValue(data49, dataArray[48]);
            inst_co.setValue(data50, dataArray[49]);
            inst_co.setValue(data51, dataArray[50]);
            inst_co.setValue(data52, dataArray[51]);
            inst_co.setValue(data53, dataArray[52]);
            inst_co.setValue(data54, dataArray[53]);
            inst_co.setValue(data55, dataArray[54]);
            inst_co.setValue(data56, dataArray[55]);
            inst_co.setValue(data57, dataArray[56]);
            inst_co.setValue(data58, dataArray[57]);
            inst_co.setValue(data59, dataArray[58]);
            inst_co.setValue(data60, dataArray[59]);
            inst_co.setValue(data61, dataArray[60]);
            inst_co.setValue(data62, dataArray[61]);
            inst_co.setValue(data63, dataArray[62]);
            inst_co.setValue(data64, dataArray[63]);

            // Add instance to dataset
            data.add(inst_co);
            data.setClassIndex(data.numAttributes() - 1);

            // Intialize and run classifier
            WekaWrapper clasifyData = new WekaWrapper();
            result = clasifyData.classifyInstance(data.firstInstance());

        } catch (Exception e) {

            e.printStackTrace();
        }

        Log.d("", "Classification result = " + result);

        return result;
    }

}
