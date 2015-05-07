package rocks.bdd.sbt.cucumber

import java.io.File

/**
 * The options to pass to cucumber.
 *
 * @author Chris Turner, Mark Olliver (updates)
 */
case class Options(featuresLocation: String,
                   basePackage: String,
                   extraOptions: List[String],
                   beforeFunc: () => Unit,
                   afterFunc: () => Unit,
                   strict: Boolean = false,
                   monochrome: Boolean = false,
                   dryRun: Boolean = false) {

  def featuresPresent = featuresLocation.startsWith("classpath:") || (new File(featuresLocation).exists)
  def asDryRun = copy(dryRun = true)
}
