
import javafx.application.Application
import javafx.application.HostServices
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.stage.StageStyle

class WindowsAfk : Application() {
    companion object {
        lateinit var pStage: Stage
        var alwaysOnTop: Boolean = false
        var stayAwake: Boolean = true

        @JvmStatic
        fun main(args: Array<String>) {
            for (i in args.indices) {
                when (args[i]) {
                    "--alwaysOnTop" -> alwaysOnTop = true
                    "--dont-stay-awake" -> stayAwake = false
                }
            }
            launch(WindowsAfk::class.java)
        }
    }

    lateinit var controller: AfkController

    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/appTemplate.fxml"))
        val root = fxmlLoader.load<Any>() as BorderPane

        stage.initStyle(StageStyle.UNDECORATED)
        stage.isAlwaysOnTop = alwaysOnTop

        val scene = Scene(root, 300.0, 150.0)
        scene.fill = Color.TRANSPARENT
        stage.scene = scene

        controller = fxmlLoader.getController()

        stage.icons.add(Image(javaClass.getResourceAsStream("/icons8_clown_fish_96px.png")))
        stage.title = "Smart Keyboard"
        stage.show()

        pStage = stage
    }

    override fun stop() {
        controller.keyBoarding?.stop()
        controller.isOpen = false
        super.stop()
    }
}
