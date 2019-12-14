package org

import org.firstinspires.ftc.teamcode.lib.field.getFieldGraph
import org.futurerobotics.jargon.pathing.graph.PathGraph
import org.junit.jupiter.api.Test

class RunIt {
    @Test
    fun run() {
        getFieldGraph().getPath("grab 1", "stack", PathGraph.Facing.Forward, PathGraph.Facing.Forward)
    }
}
