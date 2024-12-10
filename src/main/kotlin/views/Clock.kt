package views

import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.HBox.setHgrow
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import java.time.LocalDateTime
import java.time.ZoneOffset

class Clock private constructor() : VBox() {
    companion object {
        var instance: Clock = Clock()
    }

    private val hourMinLabel = Label("00:00")
    private val secLabel = Label("00")

    init {
        alignment = Pos.CENTER
        style = "-fx-background-color: #2C2F33;"
        setVgrow(this, Priority.ALWAYS)


        children.add(HBox().apply {
            alignment = Pos.BOTTOM_CENTER
            children.add(hourMinLabel.apply {
                style =
                    "-fx-font-size: 60px; -fx-text-fill: linear-gradient(from 25% 25% to 75% 75%,-fx-discord-red, -fx-discord-yellow, -fx-discord-blue); -fx-font-weight: bold;"
            })

            children.add(secLabel.apply {
                this.padding = javafx.geometry.Insets(0.0, 0.0, 10.0, 0.0)
                style =
                    "-fx-font-size: 30px; -fx-text-fill: linear-gradient(from 25% 25% to 75% 75%,-fx-discord-red, -fx-discord-yellow, -fx-discord-blue); -fx-font-weight: bold;"
            })
        })

        Thread {
            while (true) {
                // Get current time BUT take care of the timezone
                val current = LocalDateTime.now()
                val now = current.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()

                val hour = now / 1000 / 60 / 60 % 24
                val min = now / 1000 / 60 % 60
                val sec = now / 1000 % 60

                Platform.runLater {
                    hourMinLabel.text = "${if (hour < 10) "0" else ""}$hour:${if (min < 10) "0" else ""}$min"
                    secLabel.text = if (sec < 10) ":0$sec" else ":" + sec.toString()
                }

                Thread.sleep(1000)
            }
        }.start()

    }


}