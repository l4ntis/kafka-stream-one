package com.l4ntis

import com.l4ntis.models.Person
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}
import org.apache.kafka.streams.scala.serialization.Serdes.stringSerde
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization.write

import java.util.Properties

object KafkaStreamOne extends App {

  import org.apache.kafka.streams.scala._
  import ImplicitConversions._
  implicit val formats: Formats = DefaultFormats

  val config: Properties = new Properties
  config.put(StreamsConfig.APPLICATION_ID_CONFIG, "KafkaStreamOne")
  config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")

  val builder = new StreamsBuilder

  val source = builder.stream[String, String](topic = "message-in")

  source.mapValues(parse(_).extract[Person])
    //to some task .map(TASK1)
    .map { (_,v) =>
      println(v)
      (v.name, write(v))
    }
    .to("message-out")

  val topology = builder.build()
  val streams = new KafkaStreams(topology, config)
  streams.start()

  Runtime.getRuntime.addShutdownHook(new Thread {
    override def run(): Unit = {
      streams.close()
    }
  })
}