package appfree.io.locationtracking.view.extensions

import android.graphics.Bitmap
import java.io.File

/**
 * Created By Ben on 7/11/20
 */

fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
    outputStream().use { out ->
        bitmap.compress(format, quality, out)
        out.flush()
    }
    //File(exportDir, "map.png").writeBitmap(bitmap, Bitmap.CompressFormat.PNG, 85)
}