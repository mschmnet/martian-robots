package net.mschm.martianrobots.cli

import net.mschm.martianrobots.main
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream


internal class MartianRobotKtTest {


    @Test
    fun testMain() {
        val originalIn = System.`in`
        val originalOut = System.out
        val output = ByteArrayOutputStream()
        val input =
"""5 3
1 1 E
RFRFRFRF
3 2 N
FRRFLLFFRRFLL
0 3 W
LLFFFLFLFL
"""
        System.setIn(input.byteInputStream())
        System.setOut(PrintStream(output))

        main(arrayOf("cli"))

        System.setIn(originalIn)
        System.setOut(originalOut)

        Assertions.assertThat(output.toString()).isEqualTo(
 """1 1 E
3 3 N LOST
2 3 S

""".trimIndent()
        )
    }
}

