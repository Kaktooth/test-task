package org.kaktooth;

import org.apache.commons.math3.stat.StatUtils;
import org.kaktooth.exceptions.ResourceNotFound;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        final var fileName = "10m.txt";
        var textFile = ClassLoader.getSystemResourceAsStream(fileName);
        if (textFile == null) {
            throw new ResourceNotFound("Cannot get resource: " + fileName);
        }
        var bytes = textFile.readAllBytes();

        var text = new String(bytes).split("\n");
        double[] numbers = Arrays.stream(text)
                .mapToDouble(Double::parseDouble)
                .toArray();

        double[] sortedNumbers = Arrays.copyOfRange(numbers, 0, numbers.length);

        Arrays.sort(sortedNumbers);

        var max = sortedNumbers[sortedNumbers.length - 1];
        var min = sortedNumbers[0];

        var median = 0.0;
        var halfSize = (sortedNumbers.length - 1) / 2;
        if (sortedNumbers.length % 2 == 0) {
            var firstElement = sortedNumbers[halfSize];
            var secondElement = sortedNumbers[halfSize + 1];
            median = (firstElement + secondElement) * 0.5;
        } else {
            median = sortedNumbers[halfSize];
        }
        var avg = StatUtils.mean(numbers);

        var maxAscSequence = 0;
        var maxDscSequence = 0;
        var ascSequence = 1;
        var dscSequence = 0;
        for (int i = 1; i < numbers.length - 1; i += 2) {
            var isPrevAscSequence = numbers[i - 1] < numbers[i];
            var isNextAscSequence = numbers[i] < numbers[i + 1];
            var isPrevDscSequence = numbers[i - 1] > numbers[i];
            var isNextDscSequence = numbers[i] > numbers[i + 1];

            if (isPrevAscSequence) {
                ascSequence++;
                maxAscSequence = Math.max(ascSequence, maxAscSequence);
            } else {
                ascSequence = 1;
            }

            if (isNextAscSequence) {
                ascSequence++;
                maxAscSequence = Math.max(ascSequence, maxAscSequence);
            } else {
                ascSequence = 1;
            }

            if (isPrevDscSequence) {
                dscSequence++;
                maxDscSequence = Math.max(dscSequence, maxDscSequence);
            } else {
                dscSequence = 0;
            }

            if (isNextDscSequence) {
                dscSequence++;
                maxDscSequence = Math.max(dscSequence, maxDscSequence);
            } else {
                dscSequence = 0;
            }
        }

        System.out.println("Max number is: " + (int) max);
        System.out.println("Min number is: " + (int) min);
        System.out.println("Median is: " + median);
        System.out.println("Arithmetic mean is: " + avg);
        System.out.println("Max sequence of ascending numbers is: " + maxAscSequence);
        System.out.println("Max sequence of descending numbers is: " + maxDscSequence);
    }
}