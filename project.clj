(defproject jobagator2015 "0.1"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [enlive "1.1.5"]
                 [clj-http "1.0.1"]
                 [cheshire "5.4.0"]
                 [environ "0.5.0"]]
  :plugins [[environ/environ.lein "0.2.1"]]
  :hooks [environ.leiningen.hooks]
  :uberjar-name "app-standalone.jar"
  :profiles {:dev {:env {:site-url "http://localhost:5000"}}})
