import ar.com.hjg.pngj.ImageLineInt
import ar.com.hjg.pngj.PngReader
import java.io.File

fun main() {
    val pngr : PngReader = PngReader(File("src/main/resources/figures.png"))
    println(pngr.toString())
    val rows = pngr.imgInfo.rows
    val cols = pngr.imgInfo.cols
    val image = Array(rows) { CharArray(cols) }
    for (i in 0 until rows) {
        val ili = pngr.readRow(i) as ImageLineInt
        val ii = ili.scanline
        var j = 0
        while (j < ii.size) {
            if (ii[j] == 0 && ii[j + 1] == 255 && ii[j + 2] == 0) image[i][j / 4] =
                'G' else if (ii[j] == 0 && ii[j + 1] == 0 && ii[j + 2] == 0) image[i][j / 4] =
                'B' else if (ii[j] == 255 && ii[j + 1] == 0 && ii[j + 2] == 0) image[i][j / 4] =
                'R' else image[i][j / 4] = 'G'
            j += 4
        }
    }
    pngr.end()
    for (i in 0 until rows) {
        for (j in 0 until cols) {
            print(image[i][j])
        }
        println()
    }
}