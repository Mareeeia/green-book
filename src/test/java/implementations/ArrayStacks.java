package implementations;

import java.util.ArrayList;
import java.util.List;

public class ArrayStacks {
    private ArrayList<Integer> array = new ArrayList<>();
    private int[] top = {0, 1, 2};

    public void push(int stackIndex, int value) {
        if (stackIndex > 2 || stackIndex < 0) {
            return;
        }
        this.top[stackIndex] += 3;
        while (this.top[stackIndex] >= this.array.size()) {
            this.array.add(0);
        }
        this.array.set(this.top[stackIndex], value);
    }

    public int pop(int stackIndex) {
        if (stackIndex > 2 || stackIndex < 0) {
            return -1;
        }
        if (this.top[stackIndex] < 3) {
            return -1;
        }
        int value = array.get(this.top[stackIndex]);
        this.top[stackIndex] = this.top[stackIndex] - 3;
        return value;
    }
}
