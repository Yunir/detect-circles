import ar.com.hjg.pngj.ImageLineInt
import ar.com.hjg.pngj.PngReader
import java.io.File
import java.util.*

class PicData(f: File) {
    private val cols: Int
    private val rows: Int
    private val data: Array<CharArray>

    init {
        val pngr = PngReader(f)
        rows = pngr.imgInfo.rows
        cols = pngr.imgInfo.cols
        data = Array(rows) { CharArray(cols) }
        readImageToMatrix(pngr)
        pngr.end()
    }

    fun printData() {
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                print(data[i][j])
            }
            println()
        }
    }

    fun countFigures() {
        var searchFigure = true
        var blackCircles = 0
        var redCircles = 0
        var pastLine = TreeSet<Int>()
        var newLine: TreeSet<Int>
        for (i in 1 until rows - 1) {
            newLine = TreeSet()
            for (j in 1 until cols - 1) {
                if (searchFigure) {
                    if (data[i][j] != 'G' && data[i - 1][j] == 'G') {
                        searchFigure = false
                        if (data[i + 1][j - 1] != 'G') {
                            var newFigure = false
                            var currJ = j
                            var nearestJ: Int?
                            nearestJ = pastLine.ceiling(j)
                            if (nearestJ != null) {
                                while (currJ != nearestJ) {
                                    ++currJ
                                    if (data[i][currJ] == 'G') newFigure = true
                                    break
                                }
                            } else {
                                newFigure = true
                            }
                            if (newFigure) {
                                if (data[i][j] == 'R') ++redCircles else ++blackCircles
                            }
                            newLine.add(j)
                        }
                    }
                } else {
                    if (data[i][j] == 'G') searchFigure = true
                }
            }
            pastLine = newLine
        }
        println("Black cirles: $blackCircles\nRed circles: $redCircles")
    }

    private fun readImageToMatrix(pngr: PngReader) {

        fun colorListOf(l: List<Int>) = when (l) {
            listOf(0, 255, 0) -> 'G'
            listOf(0, 0, 0) -> 'B'
            listOf(255, 0, 0) -> 'R'
            else -> 'G'
        }

        for (i in 0 until rows) {
            val line = (pngr.readRow(i) as ImageLineInt).scanline
            var j = 0
            while (j < line.size) {
                data[i][j / 4] = colorListOf(listOf(line[j], line[j+1], line[j+2]))
                j += 4
            }
        }

    }
}