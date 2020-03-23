import ar.com.hjg.pngj.PngReader
import java.io.File

fun main() {
    val pngr : PngReader = PngReader(File("src/main/resources/figures.png"))
    println(pngr.toString())
}