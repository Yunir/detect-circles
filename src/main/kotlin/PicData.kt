import ar.com.hjg.pngj.ImageLineInt
import ar.com.hjg.pngj.PngReader
import java.io.File
import java.util.*

/**
 * Class for image processing and counting circles in it
 */
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

    /**
     * Prettify output of 2-dimensional array (2D-image)
     */
    fun printData() {
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                print(data[i][j])
            }
            println()
        }
    }

    /**
     * Count circles inside 2-dimensional array (2D-image)
     */
    fun countFigures() {
        var searchFigure = true
        var blackCircles = 0
        var redCircles = 0
        var pastLine = TreeSet<Int>()
        var newLine: TreeSet<Int>

        fun checkBounds(x: Int, y: Int) = x in 0 until rows && y in 0 until cols
        fun isGreen(x: Int, y: Int) = checkBounds(x, y) && data[x][y] == 'G'
        fun isRed(x: Int, y: Int) = checkBounds(x, y) && data[x][y] == 'R'
        fun isNewFigureDetected(x: Int, y: Int) = !isGreen(x, y) && isGreen(x-1, y)
        fun isCircleFound(x: Int, y: Int) = !isGreen(x+1, y-1)

        for (i in 1 until rows - 1) {
            newLine = TreeSet()
            for (j in 1 until cols - 1) {
                if (searchFigure) {
                    if (isNewFigureDetected(i, j)) {
                        if (isCircleFound(i, j)) {
                            var isNewFigure = false
                            val nearestJ: Int? = pastLine.ceiling(j)
                            if (nearestJ != null) {
                                for (k in j until nearestJ) {
                                    if (isGreen(i, k)) isNewFigure = true
                                    break
                                }
                            } else if (!isGreen(i-1, j+1)) {
                                isNewFigure = false
                            } else {
                                isNewFigure = true
                            }
                            if (isNewFigure) {
                                if (isRed(i,j)) ++redCircles else ++blackCircles
                            }
                            newLine.add(j)
                        }
                    }
                    if (! isGreen(i, j)) searchFigure = false
                } else {
                    if (data[i][j] == 'G') searchFigure = true
                }
            }
            pastLine = newLine
        }
        println("Black cirles: $blackCircles\nRed circles: $redCircles")
    }

    /**
     * Transform image to integer 2-dimensional array
     */
    private fun readImageToMatrix(pngr: PngReader) {

        fun colorListOf(l: List<Int>) = when (l) {
            listOf(0, 255, 0) -> 'G'
            listOf(0, 0, 0) -> 'B'
            listOf(255, 0, 0) -> 'R'
            else -> 'G'
        }

        for (i in 0 until rows) {
            val line = (pngr.readRow(i) as ImageLineInt).scanline
            val m = if (line.size % 4 == 0) 4 else 3
            var j = 0
            while (j < line.size) {
                data[i][j / m] = colorListOf(listOf(line[j], line[j+1], line[j+2]))
                j += m
            }
        }

    }
}