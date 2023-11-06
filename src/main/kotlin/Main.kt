import generator.RotationCalendarGenerator
import model.Resident
import model.ResidentLevel
import model.Rotation

fun main(args: Array<String>) {
    val residents: List<Resident> = generateResidents()
    val rotations: List<Rotation> = generateRotations()

    val generator = RotationCalendarGenerator()
    val calendar = generator.generateCalendar(residents, rotations)


}

fun generateRotations(): List<Rotation> {
    val list = listOf(
        Rotation("SALA", ResidentLevel.R2, 24, 12, true),
        Rotation("UTI", ResidentLevel.R2, 12, 12, false),
        Rotation("UCO", ResidentLevel.R2, 12, 12, false),

        Rotation("SALA", ResidentLevel.R3, 24, 8, true),
        Rotation("CONSUL", ResidentLevel.R3, 8, 8, false),
        Rotation("EMERGENCIA", ResidentLevel.R3, 8, 8, false),
        Rotation("CIRUGIA", ResidentLevel.R3, 4, 4, false),
        Rotation("INFECTO", ResidentLevel.R3, 4, 4, false),

        Rotation("SALA", ResidentLevel.R4, 20, 8, true),
        Rotation("INVEST", ResidentLevel.R4, 8, 8, false),
        Rotation("GAS/DER", ResidentLevel.R4, 4, 4, false),
        Rotation("ELECTIVA", ResidentLevel.R4, 8, 8, false),
        Rotation("CONSUL", ResidentLevel.R4, 8, 8, false),

    )
    return list

}

fun generateResidents(): List<Resident> {
    val r2Residents = listOf(
        Resident("LESLIE", ResidentLevel.R2),
        Resident("RODRI", ResidentLevel.R2),
        Resident("ANITA", ResidentLevel.R2),
        Resident("ENYA", ResidentLevel.R2),
        Resident("MARTU", ResidentLevel.R2),
        Resident("INGRID", ResidentLevel.R2),
        Resident("ADRI", ResidentLevel.R2),
        Resident("NACHO", ResidentLevel.R2),
        Resident("SHEILLY", ResidentLevel.R2),
    )

    val r3Residents = listOf(
        Resident("CATA", ResidentLevel.R3),
        Resident("ROCHI", ResidentLevel.R3),
        Resident("AGUSTIN", ResidentLevel.R3),
        Resident("ADRI2", ResidentLevel.R3),
        Resident("VICKY", ResidentLevel.R3),
        Resident("BRUNO", ResidentLevel.R3),
        Resident("GIULI", ResidentLevel.R3),
    )

    val r4Residents = listOf(
        Resident("SANTI", ResidentLevel.R4),
        Resident("EUGE", ResidentLevel.R4),
        Resident("VICKY", ResidentLevel.R4),
        Resident("MARIAN", ResidentLevel.R4),
        Resident("TOMI", ResidentLevel.R4),
        Resident("MAI", ResidentLevel.R4),
        Resident("SOFI", ResidentLevel.R4)
    )

    return r2Residents + r3Residents + r4Residents
}
