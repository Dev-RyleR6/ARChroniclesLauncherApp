package com.example.launcher.ui.map

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Float
import kotlin.Int
import kotlin.String
import kotlin.jvm.JvmStatic

public data class MapFragmentArgs(
  public val landmarkTitle: String?,
  public val landmarkDescription: String?,
  public val landmarkImageResId: Int,
  public val latitude: Float,
  public val longitude: Float,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("landmarkTitle", this.landmarkTitle)
    result.putString("landmarkDescription", this.landmarkDescription)
    result.putInt("landmarkImageResId", this.landmarkImageResId)
    result.putFloat("latitude", this.latitude)
    result.putFloat("longitude", this.longitude)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("landmarkTitle", this.landmarkTitle)
    result.set("landmarkDescription", this.landmarkDescription)
    result.set("landmarkImageResId", this.landmarkImageResId)
    result.set("latitude", this.latitude)
    result.set("longitude", this.longitude)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): MapFragmentArgs {
      bundle.setClassLoader(MapFragmentArgs::class.java.classLoader)
      val __landmarkTitle : String?
      if (bundle.containsKey("landmarkTitle")) {
        __landmarkTitle = bundle.getString("landmarkTitle")
      } else {
        throw IllegalArgumentException("Required argument \"landmarkTitle\" is missing and does not have an android:defaultValue")
      }
      val __landmarkDescription : String?
      if (bundle.containsKey("landmarkDescription")) {
        __landmarkDescription = bundle.getString("landmarkDescription")
      } else {
        throw IllegalArgumentException("Required argument \"landmarkDescription\" is missing and does not have an android:defaultValue")
      }
      val __landmarkImageResId : Int
      if (bundle.containsKey("landmarkImageResId")) {
        __landmarkImageResId = bundle.getInt("landmarkImageResId")
      } else {
        throw IllegalArgumentException("Required argument \"landmarkImageResId\" is missing and does not have an android:defaultValue")
      }
      val __latitude : Float
      if (bundle.containsKey("latitude")) {
        __latitude = bundle.getFloat("latitude")
      } else {
        throw IllegalArgumentException("Required argument \"latitude\" is missing and does not have an android:defaultValue")
      }
      val __longitude : Float
      if (bundle.containsKey("longitude")) {
        __longitude = bundle.getFloat("longitude")
      } else {
        throw IllegalArgumentException("Required argument \"longitude\" is missing and does not have an android:defaultValue")
      }
      return MapFragmentArgs(__landmarkTitle, __landmarkDescription, __landmarkImageResId,
          __latitude, __longitude)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): MapFragmentArgs {
      val __landmarkTitle : String?
      if (savedStateHandle.contains("landmarkTitle")) {
        __landmarkTitle = savedStateHandle["landmarkTitle"]
      } else {
        throw IllegalArgumentException("Required argument \"landmarkTitle\" is missing and does not have an android:defaultValue")
      }
      val __landmarkDescription : String?
      if (savedStateHandle.contains("landmarkDescription")) {
        __landmarkDescription = savedStateHandle["landmarkDescription"]
      } else {
        throw IllegalArgumentException("Required argument \"landmarkDescription\" is missing and does not have an android:defaultValue")
      }
      val __landmarkImageResId : Int?
      if (savedStateHandle.contains("landmarkImageResId")) {
        __landmarkImageResId = savedStateHandle["landmarkImageResId"]
        if (__landmarkImageResId == null) {
          throw IllegalArgumentException("Argument \"landmarkImageResId\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"landmarkImageResId\" is missing and does not have an android:defaultValue")
      }
      val __latitude : Float?
      if (savedStateHandle.contains("latitude")) {
        __latitude = savedStateHandle["latitude"]
        if (__latitude == null) {
          throw IllegalArgumentException("Argument \"latitude\" of type float does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"latitude\" is missing and does not have an android:defaultValue")
      }
      val __longitude : Float?
      if (savedStateHandle.contains("longitude")) {
        __longitude = savedStateHandle["longitude"]
        if (__longitude == null) {
          throw IllegalArgumentException("Argument \"longitude\" of type float does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"longitude\" is missing and does not have an android:defaultValue")
      }
      return MapFragmentArgs(__landmarkTitle, __landmarkDescription, __landmarkImageResId,
          __latitude, __longitude)
    }
  }
}
