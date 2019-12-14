package org.firstinspires.ftc.teamcode.lib.field

import org.futurerobotics.jargon.math.Vector2d
import org.futurerobotics.jargon.math.convert.*
import org.futurerobotics.jargon.pathing.graph.LinearInterpolator
import org.futurerobotics.jargon.pathing.graph.PathGraph
import org.futurerobotics.jargon.pathing.graph.WaypointConstraint

fun getFieldGraph() = PathGraph().apply {
    defaultInterpolator = LinearInterpolator
    addNode(startLoading).name("start loading")
    //not neutral bridge
    addNode(bridgeEnter).name("bridge enter")
        .lineTo(bridgeExit).name("bridge exit")() {
        reverse.weight = Int.MAX_VALUE
    }

    //neutral bridge
    addNode(neutralEnter).name("neutral enter")
        .lineTo(neutralExit).name("neutral exit")() {
        reverse.weight = Int.MAX_VALUE
    }

    "neutral exit"().splineTo("bridge enter")
    "bridge exit"().splineTo("neutral enter")

    addNode(look1).name("look 1")() {
        "start loading"().splineTo(this)
    }
    "look 1"().splineTo(-3 * tile, 3 * tile).name("align").setNodeConstraint(WaypointConstraint(heading = right))

    addNode(3.0 * tile, 1.3 * tile)() {
        name = "prepare move"
        "bridge exit"().splineTo(this).addOutConstraint(WaypointConstraint(direction = right, derivMagnitude = 1.0))
    }

    addNode(2.0 * tile, 3.0 * tile)() {
        name = "move"
        nodeConstraint = WaypointConstraint(heading = down)
        "prepare move"().splineTo(1.0 * tile, 1.5 * tile)() {
            addOutConstraint(WaypointConstraint(heading = 135 * deg))
        }.splineTo(this)

    }

    addNode(2.5 * tile, 2.0 * tile).name("stack")() {
        "bridge exit"().splineTo(this.location + Vector2d(-1 * tile, 0.0)).splineTo(this)
        this.splineTo("neutral enter")
    }

    addNode(0.0, 0.5 * tile)() {
        name = "park"

        nodeConstraint = WaypointConstraint(heading = down)
        "move"().splineTo(0.0, 1.2 * tile)() {
            addOutConstraint(WaypointConstraint(heading = down, direction = down, derivMagnitude = 1.0))
        }.lineTo(this)
    }
}


enum class Side(val facing: PathGraph.Facing) {
    Intake(PathGraph.Facing.Forward),
    Outtake(PathGraph.Facing.Backward),
    Any(PathGraph.Facing.Any)
}
