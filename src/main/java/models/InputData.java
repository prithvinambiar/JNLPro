package models;

import java.util.Arrays;

public class InputData<$Input> {
    private final $Input inputs[];

    public InputData($Input... inputs) {
        this.inputs = inputs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InputData inputData = (InputData) o;

        if (!Arrays.equals(inputs, inputData.inputs)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return inputs != null ? Arrays.hashCode(inputs) : 0;
    }

    public $Input[] getInputs() {
        return inputs;
    }
}
