package com.ehernndez.poketest.utils

import android.os.Parcel
import android.os.Parcelable
import com.google.android.material.datepicker.CalendarConstraints

// This class is created from the example of the material design documentation.
// Is used to config a custom range of dates.
class DateValidatorRange(private val start: Long, private val end: Long) :
    CalendarConstraints.DateValidator {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(start)
        dest.writeLong(end)
    }

    override fun isValid(date: Long): Boolean {
        return date in start..end
    }

    override fun equals(other: Any?): Boolean {
        return if (this === other) {
            true
        } else other is DateValidatorRange
    }

    override fun hashCode(): Int {
        val hashedFields = arrayOf<Any>(start, end)
        return hashedFields.contentHashCode()
    }

    companion object CREATOR : Parcelable.Creator<DateValidatorRange> {
        override fun createFromParcel(parcel: Parcel): DateValidatorRange {
            return DateValidatorRange(parcel)
        }

        override fun newArray(size: Int): Array<DateValidatorRange?> {
            return arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(parcel.readLong(), parcel.readLong())
}