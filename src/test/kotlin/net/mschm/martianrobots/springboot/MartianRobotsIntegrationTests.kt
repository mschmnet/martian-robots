package net.mschm.martianrobots.springboot

import org.assertj.core.api.Assertions
import org.assertj.core.util.Lists
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.*
import org.springframework.web.socket.WebSocketHttpHeaders
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import org.springframework.web.socket.sockjs.client.SockJsClient
import org.springframework.web.socket.sockjs.client.Transport
import org.springframework.web.socket.sockjs.client.WebSocketTransport
import java.lang.reflect.Type
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MartianRobotsIntegrationTests {
    @LocalServerPort
    private val port = 0
    private var sockJsClient: SockJsClient? = null
    private var stompClient: WebSocketStompClient? = null
    private val headers = WebSocketHttpHeaders()

    @BeforeEach
    fun setup() {
        val transports: MutableList<Transport> = ArrayList()
        transports.add(WebSocketTransport(StandardWebSocketClient()))
        sockJsClient = SockJsClient(transports)
        stompClient = WebSocketStompClient(sockJsClient!!)
        stompClient!!.messageConverter = MappingJackson2MessageConverter()
    }

    @Test
    fun testMartianRobots() {
        val latch = CountDownLatch(1)
        val failure = AtomicReference<Throwable?>()
        val handler: StompSessionHandler = object : TestSessionHandler(failure) {
            override fun afterConnected(session: StompSession, connectedHeaders: StompHeaders) {
                session.subscribe("/output/robot", object : StompFrameHandler {
                    override fun getPayloadType(headers: StompHeaders): Type {
                        return RobotOutputList::class.java
                    }

                    override fun handleFrame(headers: StompHeaders, payload: Any?) {
                        val robotOutputList = payload as RobotOutputList?
                        try {
                            assertTestOuput(robotOutputList)
                        } catch (t: Throwable) {
                            failure.set(t)
                        } finally {
                            session.disconnect()
                            latch.countDown()
                        }
                    }
                })
                try {
                    session.send("/input/robot", getTestInput())
                } catch (t: Throwable) {
                    failure.set(t)
                    latch.countDown()
                }
            }
        }
        stompClient!!.connect("ws://localhost:{port}/martian-robots", headers, handler, port)
        if (latch.await(60, TimeUnit.SECONDS)) {
            if (failure.get() != null) {
                throw AssertionError("", failure.get())
            }
        } else {
            org.junit.jupiter.api.Assertions.fail<Any>("Greeting not received")
        }
    }

    private fun assertTestOuput(robotOutputList: RobotOutputList?) {
        Assertions.assertThat(robotOutputList!!.robots).hasSize(3)
        Assertions.assertThat(robotOutputList.robots.get(0).xPos).isEqualTo(1)
        Assertions.assertThat(robotOutputList.robots.get(0).yPos).isEqualTo(1)
        Assertions.assertThat(robotOutputList.robots.get(0).orientation).isEqualTo('E')
        Assertions.assertThat(robotOutputList.robots.get(0).isLost).isEqualTo(false)
        Assertions.assertThat(robotOutputList.robots.get(1).xPos).isEqualTo(3)
        Assertions.assertThat(robotOutputList.robots.get(1).yPos).isEqualTo(3)
        Assertions.assertThat(robotOutputList.robots.get(1).orientation).isEqualTo('N')
        Assertions.assertThat(robotOutputList.robots.get(1).isLost).isEqualTo(true)
        Assertions.assertThat(robotOutputList.robots.get(2).xPos).isEqualTo(2)
        Assertions.assertThat(robotOutputList.robots.get(2).yPos).isEqualTo(3)
        Assertions.assertThat(robotOutputList.robots.get(2).orientation).isEqualTo('S')
        Assertions.assertThat(robotOutputList.robots.get(2).isLost).isEqualTo(false)
    }

    private fun getTestInput(): RobotInput{
        val robotInput = RobotInput()
        robotInput.maxX = 5
        robotInput.maxY = 3
        robotInput.robots = ArrayList()
        var robot = Robot()
        robot.initialX = 1
        robot.initialY = 1
        robot.initialOrientation = 'E'
        robot.movements = Lists.newArrayList('R', 'F', 'R', 'F', 'R', 'F', 'R', 'F')
        robotInput.robots.add(robot)
        robot = Robot()
        robot.initialX = 3
        robot.initialY = 2
        robot.initialOrientation = 'N'
        robot.movements =
            Lists.newArrayList('F', 'R', 'R', 'F', 'L', 'L', 'F', 'F', 'R', 'R', 'F', 'L', 'L')
        robotInput.robots.add(robot)
        robot = Robot()
        robot.initialX = 0
        robot.initialY = 3
        robot.initialOrientation = 'W'
        robot.movements = Lists.newArrayList('L', 'L', 'F', 'F', 'F', 'L', 'F', 'L', 'F', 'L')
        robotInput.robots.add(robot)
        return robotInput
    }

    private open inner class TestSessionHandler(private val failure: AtomicReference<Throwable?>) :
        StompSessionHandlerAdapter() {
        override fun handleFrame(headers: StompHeaders, payload: Any?) {
            failure.set(Exception(headers.toString()))
        }

        override fun handleException(s: StompSession, c: StompCommand?, h: StompHeaders, p: ByteArray, ex: Throwable) {
            failure.set(ex)
        }

        override fun handleTransportError(session: StompSession, ex: Throwable) {
            failure.set(ex)
        }
    }
}