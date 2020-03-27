import ar.com.hjg.pngj.ImageLineInt
import ar.com.hjg.pngj.PngReader
import java.io.File

fun main() {
    val picData = PicData(File("src/main/resources/figures.png").absoluteFile)
    picData.countFigures()
    val picData2 = PicData(File("src/test/resources/2circles.png").absoluteFile)
    picData2.countFigures()
    //picData2.printData()
}