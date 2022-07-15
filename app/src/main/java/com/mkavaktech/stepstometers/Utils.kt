package com.mkavaktech.stepstometers

fun calculateStrideLength(
    heightVal: Int,
    genderVal: Int
): Int {
    return if (genderVal == 0) {
        (0.415 * heightVal).toInt()
    } else {
        (0.413 * heightVal).toInt()
    }
}

fun calculateStepsToMeters(
    strideHeightValue: Int,
    stepsValue: Int
): Int {
    return stepsValue * strideHeightValue
}