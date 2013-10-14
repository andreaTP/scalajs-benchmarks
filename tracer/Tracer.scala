// The ray tracer code in this file is written by Adam Burmister. It
// is available in its original form from:
//
//   http://labs.flog.co.nz/raytracer/
//
// Ported from the v8 benchmark suite by Google 2012.
// Ported from the Dart benchmark_harness to Scala.js by Jonas Fonseca 2013

package benchmarks.tracer

class Tracer extends benchmarks.Benchmark {

  val config = EngineConfiguration(
    imageWidth = 100,
    imageHeight = 100,
    pixelWidth = 5,
    pixelHeight = 5,
    rayDepth = 2,
    renderDiffuse = true,
    renderShadows = true,
    renderHighlights = true,
    renderReflections = true
  )

  override def prefix = "Tracer"

  def run {
    new RenderScene().renderScene(config, null)
  }

}
