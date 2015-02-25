package templemore.sbt.cucumber

import java.io.File

/**
 * Defines the output options for running cucumber.
 *
 * @author Chris Turner
 */
case class Output(prettyReport: Boolean, htmlReport: Boolean, junitReport: Boolean, jsonReport: Boolean,
                  prettyReportFile: File, htmlReportDir: File, junitReportFile: File, jsonReportFile: File) {

  def options: List[String] = {
    (if (prettyReport) {
       prettyReportFile.getParentFile.mkdirs()
       "--plugin" :: "pretty:%s".format(prettyReportFile.getPath) :: Nil
     }
     else "--plugin" :: "pretty" :: Nil) ++
    (if ( htmlReport) {
       htmlReportDir.mkdirs()
       "--plugin" :: "html:%s".format(htmlReportDir.getPath) :: Nil
     } else Nil) ++
    (if ( junitReport) {
       junitReportFile.getParentFile.mkdirs()
       "--plugin" :: "junit:%s".format(junitReportFile.getPath) :: Nil
     } else Nil) ++
    (if ( jsonReport) {
       jsonReportFile.getParentFile.mkdirs()
       "--plugin" :: "json:%s".format(jsonReportFile.getPath) :: Nil
     } else Nil)
  }
}