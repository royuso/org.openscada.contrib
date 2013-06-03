== Performing a release ==

 * Check that git is clean (no local changes and pulled)
 * Ensure that the maven settings are correct (see below)
 * run: mvn release:prepare release:perform -Dusername=jreimann -P jenkins

== Maven Settings ==


