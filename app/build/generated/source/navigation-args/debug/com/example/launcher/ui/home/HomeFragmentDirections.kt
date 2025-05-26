package com.example.launcher.ui.home

import android.os.Bundle
import androidx.navigation.NavDirections
import com.example.launcher.R
import kotlin.Float
import kotlin.Int
import kotlin.String

public class HomeFragmentDirections private constructor() {
  private data class ActionHomeToMap(
    public val landmarkTitle: String?,
    public val landmarkDescription: String?,
    public val landmarkImageResId: Int,
    public val latitude: Float,
    public val longitude: Float,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_home_to_map

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
    public fun actionHomeToMap(
      landmarkTitle: String?,
      landmarkDescription: String?,
      landmarkImageResId: Int,
      latitude: Float,
      longitude: Float,
    ): NavDirections = ActionHomeToMap(landmarkTitle, landmarkDescription, landmarkImageResId,
        latitude, longitude)
  }
}
