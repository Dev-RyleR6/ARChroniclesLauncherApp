package com.example.launcher.ui.map

import android.os.Bundle
import androidx.navigation.NavDirections
import com.example.launcher.R
import kotlin.Float
import kotlin.Int
import kotlin.String

public class MapFragmentDirections private constructor() {
  private data class ActionMapToDetail(
    public val landmarkTitle: String,
    public val landmarkDescription: String,
    public val landmarkImageResId: Int,
    public val latitude: Float,
    public val longitude: Float,
  ) : NavDirections {
    public override val actionId: Int = R.id.actionMapToDetail

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("landmarkTitle", this.landmarkTitle)
        result.putString("landmarkDescription", this.landmarkDescription)
        result.putInt("landmarkImageResId", this.landmarkImageResId)
        result.putFloat("latitude", this.latitude)
        result.putFloat("longitude", this.longitude)
        return result
      }
  }

  public companion object {
    public fun actionMapToDetail(
      landmarkTitle: String,
      landmarkDescription: String,
      landmarkImageResId: Int,
      latitude: Float,
      longitude: Float,
    ): NavDirections = ActionMapToDetail(landmarkTitle, landmarkDescription, landmarkImageResId,
        latitude, longitude)
  }
}
