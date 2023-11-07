package generator

import model.*

enum class ResidentType {
    SUPERIOR, R1
}

class RotationCalendarGenerator {

    fun generateCalendar(
        residents: List<Resident>,
        rotations: List<Rotation>,
        residentsToLeaveInCore: Map<ResidentType, Int>): RotationCalendar {

        val firstWeek: Week = generateFirstWeek(residents, rotations, residentsToLeaveInCore)
        val weeks: MutableList<Week> = mutableListOf(firstWeek)

//        for (i in 2..52) {
//            val residentsThatWereInCore = weeks.
//
//
//        }

        return RotationCalendar(listOf(firstWeek))
    }

    private fun generateFirstWeek(residents: List<Resident>, rotations: List<Rotation>, residentsToLeaveInCore: Map<ResidentType, Int>): Week {
        val superiorResidentsForCoreRotation = residents
            .filter { (it.level == ResidentLevel.R2) || (it.level == ResidentLevel.R3) || (it.level == ResidentLevel.R4) }
            .takeRandomWithoutRepeating(residentsToLeaveInCore[ResidentType.SUPERIOR]!!)

        val r1ResidentsForCoreRotation = residents
            .filter { it.level == ResidentLevel.R1 }
            .takeRandomWithoutRepeating(residentsToLeaveInCore[ResidentType.R1]!!)

        val coreRotationsToResidentsMap = generateCoreRotationsToResidentsMap(superiorResidentsForCoreRotation, r1ResidentsForCoreRotation, rotations)

        val restOfResidents = residents - superiorResidentsForCoreRotation - r1ResidentsForCoreRotation

        val otherRotationsToResidentsMap = distributeResidentsEvenly(restOfResidents, rotations - rotations.filter { it.coreRotation })

        val week = Week(1, coreRotationsToResidentsMap + otherRotationsToResidentsMap)
        return week
    }

    fun distributeResidentsEvenly(residents: List<Resident>, rotations: List<Rotation>): Map<Rotation, List<Resident>> {
        val rotationMap = rotations.associateWith { mutableListOf<Resident>() }

        residents.forEach { resident ->
            val matchingRotations = rotations.filter { it.year == resident.level }
            val rotationWithFewestResidents = matchingRotations.minByOrNull { rotationMap[it]?.size ?: Int.MAX_VALUE }

            if (rotationWithFewestResidents != null) {
                rotationMap[rotationWithFewestResidents]?.add(resident)
            }
        }

        return rotationMap
    }

    private fun generateCoreRotationsToResidentsMap(superiorResidents: List<Resident>, r1Residents: List<Resident>, rotations: List<Rotation>): Map<Rotation, List<Resident>> {
        val coreRotationsToResidentsMap = mutableMapOf<Rotation, List<Resident>>()
        for(resident in superiorResidents) {
            val coreRotation = getCoreRotationForYear(rotations, resident.level)
            if(coreRotation != null) {
                coreRotationsToResidentsMap[coreRotation] = coreRotationsToResidentsMap.getOrDefault(coreRotation, listOf()) + resident
            }
        }
        val r1CoreRotation = getCoreRotationForYear(rotations, ResidentLevel.R1)
        if(r1CoreRotation != null) {
            coreRotationsToResidentsMap[r1CoreRotation] = coreRotationsToResidentsMap.getOrDefault(r1CoreRotation, listOf()) + r1Residents
        }
        return coreRotationsToResidentsMap

    }

    private fun getCoreRotationForYear(rotations: List<Rotation>, level: ResidentLevel): Rotation? =
        rotations.find { it.coreRotation && it.year == level }


    private fun <T> List<T>.takeRandomWithoutRepeating(n: Int): List<T> {
        if (n >= this.size) {
            // If n is greater than or equal to the list size, return a shuffled list
            val shuffledList = this.shuffled()
            return shuffledList.subList(0, minOf(n, this.size))
        } else {
            // If n is smaller than the list size, shuffle and take the first n elements
            val shuffledList = this.shuffled()
            return shuffledList.subList(0, n)
        }
    }


}