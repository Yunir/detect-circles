import ar.com.hjg.pngj.ImageLineInt
import ar.com.hjg.pngj.PngReader
import java.io.File

fun main() {
    val picData = PicData(File("src/main/resources/figures.png").absoluteFile)
    picData.countFigures()
}