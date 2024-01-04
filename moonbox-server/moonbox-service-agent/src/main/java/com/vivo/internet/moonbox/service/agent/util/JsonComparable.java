package com.vivo.internet.moonbox.service.agent.util;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
public class JsonComparable implements Comparable {

    public Boolean compare(Object actual, Object expect) {
        boolean isEqual = compareJsonObjects((JSONObject) actual, (JSONObject) expect);
        return isEqual;
    }

    private boolean compareJsonElements(Object obj1, Object obj2) {
        if (obj1 instanceof JSONObject && obj2 instanceof JSONObject) {
            return compareJsonObjects((JSONObject) obj1, (JSONObject) obj2);
        } else if (obj1 instanceof JSONArray && obj2 instanceof JSONArray) {
            return compareJsonArrays((JSONArray) obj1, (JSONArray) obj2);
        } else {
            // 比较基本类型或其他类型
            return obj1.equals(obj2);
        }
    }

    private boolean compareJsonObjects(JSONObject json1, JSONObject json2) {
        if (!json1.keySet().equals(json2.keySet())) {
            return false;
        }
        for (String key : json1.keySet()) {
            if (!compareJsonElements(json1.get(key), json2.get(key))) {
                return false;
            }
        }
        return true;
    }

    private boolean compareJsonArrays(JSONArray arr1, JSONArray arr2) {
        if (arr1.size() != arr2.size()) {
            return false;
        }

        for (int i = 0; i < arr1.size(); i++) {
            Object item1 = arr1.get(i);
            boolean found = false;
            for (int j = 0; j < arr2.size(); j++) {
                if (compareJsonElements(item1, arr2.get(j))) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}