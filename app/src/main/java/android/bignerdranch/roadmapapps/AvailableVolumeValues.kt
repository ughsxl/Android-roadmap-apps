package android.bignerdranch.roadmapapps

class AvailableVolumeValues(val values: List<Int>, val currentValue: Int) {

    companion object {
        fun createVolumeValues(currentValue: Int): AvailableVolumeValues {
            val values = (0..100 step 10)
            val currentIndex = values.indexOf(currentValue)

            return if (currentIndex == -1) {
                val list = values + currentValue
                AvailableVolumeValues(list, list.lastIndex)
            } else AvailableVolumeValues(values.toList(), currentIndex)
        }
    }
}
