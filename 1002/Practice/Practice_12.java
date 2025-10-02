// 12. 다음은 '키'와 '값'의 두 문자열을 하나의 아이템으로 저장하는 추상 클래스 PairMap이다.
/*
    abstract class PairMap {
        protected String keyArray [];
        protected String valueArray [];
        abstract String get(String key);
        abstract void put(String key, String value);
        abstract String delete(String key);
        abstract int length();
    }
*/
//    PairMap을 상속받아 Dictionary 클래스를 구현하고 다음의 main() 메소드를 가진
//    DictionaryApp 클래스를 작성하라.
/*
    이재문의 값은 C++
    황기태의 값은 자바
    황기태의 값은 null
*/

abstract class PairMap {
    protected String keyArray[];
    protected String valueArray[];
    abstract String get(String key);
    abstract void put(String key, String value);
    abstract String delete(String key);
    abstract int length();
}

class Dictionary extends PairMap {
    private int count = 0;

    public Dictionary(int capacity) {
        keyArray = new String[capacity];
        valueArray = new String[capacity];
    }

    @Override
    String get(String key) {
        for (int i = 0; i < count; i++) {
            if (keyArray[i].equals(key)) {
                return valueArray[i];
            }
        }
        return null;
    }

    @Override
    void put(String key, String value) {
        for (int i = 0; i < count; i++) {
            if (keyArray[i].equals(key)) {
                valueArray[i] = value;
                return;
            }
        }
        if (count < keyArray.length) {
            keyArray[count] = key;
            valueArray[count] = value;
            count++;
        }
    }

    @Override
    String delete(String key) {
        int targetIndex = -1;
        for (int i = 0; i < count; i++) {
            if (keyArray[i].equals(key)) {
                targetIndex = i;
                break;
            }
        }

        if (targetIndex != -1) {
            String deletedValue = valueArray[targetIndex];
            for (int i = targetIndex; i < count - 1; i++) {
                keyArray[i] = keyArray[i + 1];
                valueArray[i] = valueArray[i + 1];
            }
            count--;
            return deletedValue;
        }
        return null;
    }

    @Override
    int length() {
        return count;
    }
}

public class DictionaryApp {
    public static void main(String[] args) {
        Dictionary dic = new Dictionary(10);
        dic.put("황기태", "자바");
        dic.put("이재문", "C++");
        dic.put("이재문", "C++");
        System.out.println("이재문의 값은 " + dic.get("이재문"));
        System.out.println("황기태의 값은 " + dic.get("황기태"));
        dic.delete("황기태");
        System.out.println("황기태의 값은 " + dic.get("황기태"));
    }
}
