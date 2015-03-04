package trnqilo

import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import trnqilo.telecomando.lib.Command
import trnqilo.telecomando.lib.getMessage
import java.lang.Thread.sleep
import java.util.*

data class SshCommand(
  val command: String,
  val destination: String="localhost",
  val port: Int = 22,
  val user: String = "",
  val password: String = "",
  val keyFile: String = "",
  val jSch: JSch = JSch()
) : Command {
  override fun execute(): String = try {
    val session = session(jSch)
    val channel = session.openChannel("exec")
    (channel as ChannelExec).setCommand(command)
    channel.connect()
    val bytes = ByteArray(1024)
    val result = mutableListOf<String>()
    for (count in 0..99) {
      while (channel.inputStream.available() > 0) {
        val length = channel.inputStream.read(bytes, 0, 1024)
        if (length < 0) break
        result += String(bytes, 0, length)
      }
      if (channel.isClosed() && channel.inputStream.available() < 1) break
      sleep(1000)
    }
    channel.disconnect()
    session.disconnect()
    result.joinToString(" ") { it }
  } catch (e: Exception) {
    e.getMessage()
  }

  private fun session(jSch: JSch): Session {
    if (keyFile.isNotBlank()) {
      jSch.addIdentity(keyFile)
    }

    return jSch.getSession(user, destination, port).apply {
      if (password.isNotEmpty()) {
        setPassword(password)
      }
      setConfig(
        Properties().apply {
          this["StrictHostKeyChecking"] = "no"
        })
      connect()
    }
  }
}
