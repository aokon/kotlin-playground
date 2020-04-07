package pl.rebased.fiften

class Puzzle(width: Int) {
    var sequence: List<Int>

    init {
        var seq = (0 until (width*width)).toList()
        while(true) {
            seq = seq.shuffled()
            if (solvable(seq, width)) {
                this.sequence = seq
                break
            }
        }
    }

    fun asList(): List<Int> = sequence

    fun solvable(seq: List<Int>, width: Int) : Boolean {
        val inversions = countInversions(seq)

        if (width % 2 == 1) {
            return inversions % 2 == 0
        } else {
            val blankRowFromBottom = width - findBlankPos(seq) / width
            return if (blankRowFromBottom % 2 == 0) inversions % 2 == 1
            else inversions %2 == 0
        }
    }


    fun countInversions(seq: List<Int>) : Int {
        var inversions = 0
        seq.forEachIndexed { i, v ->
            inversions += seq.slice(i until seq.size).count { it != 0 && it < v }
        }
        return inversions
    }

    fun findBlankPos(seq: List<Int>): Int =
        seq.indexOf(0)
}
