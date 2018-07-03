package cn.hhit.canteen.app.utils.network;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

//库依赖：compile 'com.google.code.gson:gson:2.7'
public class GsonUtil {
    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private GsonUtil() {
    }

    public static String bean2Json(Object object) {
        String jsonString = null;
        if (gson != null) {
            jsonString = gson.toJson(object);
        }
        return jsonString;
    }

    public static <T> T json2Bean(String jsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(jsonString, cls);
        }
        return t;
    }

    public static <T> List<T> jsonList2BeanList(String jsonList, Class<T> cls) {
        List<T> list = null;
        JsonArray arry = null;

        if (gson != null) {
            list = new ArrayList<>();
            arry = new JsonParser().parse(jsonList).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, cls));
            }
        }
        return list;
    }

    public static <T> String beanList2JsonList(List<T> list) {
        String jsonList = null;
        if (gson != null) {
            jsonList = gson.toJson(list);
        }
        return jsonList;
    }
}
