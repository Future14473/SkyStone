package org.firstinspires.ftc.teamcode.lib.field

import org.futurerobotics.jargon.pathing.Path
import org.futurerobotics.jargon.pathing.graph.PathGraph

class GraphTracker(val graph: PathGraph, node: String, side: Side) {

    var node = node
        private set
    var side = side
        private set

    fun pathTo(name: String, startSide: Side = side, toSide: Side = startSide): Path =
        graph.getPath(node, name, startSide.facing, toSide.facing).also {
            node = name
            side = toSide
        }
}
