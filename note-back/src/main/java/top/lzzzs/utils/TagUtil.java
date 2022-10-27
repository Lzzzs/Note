package top.lzzzs.utils;


public class TagUtil {
    private final static String[] tagValue = {"HTML", "MySQL", "PHP", "Java", "Python"};

    public static boolean contains(String value) {
        boolean flag = false;
        value = value.toLowerCase();

        for (String item : tagValue) {
            // 匹配
            if (item.toLowerCase().equals(value)) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    public static String getCurrentTag(String value) {
        String currentTag = "";
        value = value.toLowerCase();

        for (String item : tagValue) {
            // 匹配
            if (item.toLowerCase().equals(value)) {
                currentTag = item;
                break;
            }
        }

        return currentTag;
    }


}
