package cli

import org.kohsuke.args4j.Option

object CliArgs {
  @Option(name = "--click-files", required = true,
    usage = "List of click files separated with comma")
  var clickFiles: String = _

  @Option(name = "--impression-files", required = true,
    usage = "List of impression files separated with comma")
  var impressionFiles: String = _
}