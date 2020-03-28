
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Oj {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int n;
            List<List<Integer>> result = test(n = scanner.nextInt());
            for (int i = 2; i <= n; i++) {
                int m = n - i;
                for (List<Integer> current : result) {
                    int c = current.get(0);
                    int sub = c - m;
                    if (sub >= 0) {
                        StringBuilder builder = new StringBuilder(i + "=");
                        int k, p;
                        for (k = 0; k < sub; k++) {
                            builder.append("1+");
                        }
                        for (p = 1; p < current.size(); p++) {
                            builder.append(current.get(p)).append("+");
                        }
                        System.out.println(builder.substring(0, builder.length() - 1));
                        if (k + p == 2) {
                            break;
                        }
                    }
                }
            }
        }
    }

    public static List<List<Integer>> test(int n) {
        if (n <= 1) {
            return new ArrayList<List<Integer>>(){{
                add(new ArrayList<Integer>() {{ add(n); }});
            }};
        }
        List<List<Integer>> result = new ArrayList<List<Integer>>(){{
            add(new ArrayList<Integer>() {{ add(n); }});
            add(new ArrayList<Integer>() {{add(n - 2); add(2); }});
        }};
        List<Integer> next;
        int index = 1;
        while (true) {
            next = result.get(index);
            index++;
            int oneNum = next.get(0);
            if (oneNum == 0 && next.size() == 2) {
                break;
            }

            List<Integer> newLine = new ArrayList<>();
            List<Integer> secondLine = new ArrayList<>();
            int newLineN = -1;
            int newLineNum = -1;
            int secondLineN = -1;
            int secondLineNum = -1;
            int size = next.size();

            int putIndexNew = size;
            int putIndexSecond = size;
            int leftNum = next.get(1);
            int rightNum = size > 2 ? next.get(2) : -1;
            if (rightNum == -1) {
                newLineN = oneNum - 1;
                newLineNum = leftNum + 1;
                if (oneNum > 1) {
                    secondLineN = oneNum - 2;
                    secondLineNum = 2;
                    putIndexSecond = 1;
                }
            } else {
                if (rightNum > leftNum) {
                    newLineN = oneNum - 1;
                    newLineNum = leftNum + 1;
                    putIndexNew = 2;
                    if (oneNum > 1) {
                        secondLineN = oneNum - 2;
                        secondLineNum = 2;
                        putIndexSecond = 1;
                    }
                } else if (oneNum > 1) {
                    newLineN = oneNum - 2;
                    newLineNum = 2;
                    putIndexNew = 1;
                }
            }
            merge(result, next, newLine, newLineN, newLineNum, size, putIndexNew);
            merge(result, next, secondLine, secondLineN, secondLineNum, size, putIndexSecond);
        }
        return result;
    }

    private static void merge(List<List<Integer>> result, List<Integer> next, List<Integer> secondLine, int secondLineN, int secondLineNum, int size, int putIndexSecond) {
        if (secondLineN != -1) {
            secondLine.add(secondLineN);
            secondLine.add(secondLineNum);
            for (int i = putIndexSecond; i < size; i++) {
                secondLine.add(next.get(i));
            }
            result.add(secondLine);
        }
    }
}
