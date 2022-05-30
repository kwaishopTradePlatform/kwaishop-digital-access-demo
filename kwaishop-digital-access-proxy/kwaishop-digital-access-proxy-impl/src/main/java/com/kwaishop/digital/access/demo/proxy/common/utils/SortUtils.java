package com.kwaishop.digital.access.demo.proxy.common.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * @author zhangyiying
 * Created on 2022-05-16
 */
public class SortUtils {
    protected static final Logger logger = Logger.getLogger(SortUtils.class.getName());

    private SortUtils() {
    }

    public static String sortAndJoin(Map<String, String> params) {
        TreeMap<String, String> paramsTreeMap = new TreeMap();
        Iterator var2 = params.entrySet().iterator();

        while (var2.hasNext()) {
            Entry<String, String> entry = (Entry) var2.next();
            if (entry.getValue() != null) {
                paramsTreeMap.put(entry.getKey(), entry.getValue());
            }
        }

        String signCalc = "";

        Entry entry;
        for (Iterator var6 = paramsTreeMap.entrySet().iterator(); var6.hasNext();
                signCalc = String.format("%s%s=%s&", signCalc, entry.getKey(), entry.getValue(), "&")) {
            entry = (Entry) var6.next();
        }

        if (signCalc.length() > 0) {
            signCalc = signCalc.substring(0, signCalc.length() - 1);
        }

        return signCalc;
    }
}
